/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wmfsystem.eurekaserver.broadcast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author willian
 */
public class Client {

    private ConnectionToServer server;
    private LinkedBlockingQueue<Object> messages = new LinkedBlockingQueue<>();
    private Socket socket;

    public Client(String IPAddress, int port) {
        try {
            socket = new Socket(IPAddress, port);
            messages = new LinkedBlockingQueue<Object>();

            server = new ConnectionToServer(socket);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

        Thread messageHandling = new Thread() {
            public void run() {
                while (true) {
                    try {
                        Object message = messages.take();
                        // Do some handling here...
                        System.out.println("Message Received: " + message);
                    } catch (InterruptedException e) {
                    }
                }
            }
        };

        messageHandling.setDaemon(true);
        messageHandling.start();
    }

    private class ConnectionToServer {

        ObjectInputStream in;
        ObjectOutputStream out;
        Socket socket;

        ConnectionToServer(Socket socket) throws IOException {
            this.socket = socket;
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            Thread read = new Thread() {
                public void run() {
                    while (true) {
                        try {
                            Object obj = in.readObject();
                            messages.put(obj);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            };

            read.setDaemon(true);
            read.start();
        }

        private void write(Object obj) {
            try {
                out.writeObject(obj);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void send(Object obj) {
        server.write(obj);
    }
}
