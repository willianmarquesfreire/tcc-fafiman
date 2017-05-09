package com.wmfsystem.broadcastclient;

/**
 * Created by wmfsystem on 5/6/17.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author willian
 */
public class Server
{
    public static final int DEFAULT_PORT = 1234;
    private DatagramSocket socket;
    private DatagramPacket packet;

    public void run()
    {
        try
        {
            socket = new DatagramSocket(DEFAULT_PORT);
        }
        catch( Exception ex )
        {
            System.out.println("Problem creating socket on port: " + DEFAULT_PORT );
        }

        packet = new DatagramPacket (new byte[1], 1);

        while (true)
        {
            try
            {
                socket.receive (packet);
                System.out.println("Received from: " + packet.getAddress () + ":" +
                        packet.getPort ());
                byte[] outBuffer = new java.util.Date ().toString ().getBytes ();
                packet.setData (outBuffer);
                packet.setLength (outBuffer.length);
                socket.send (packet);
            }
            catch (IOException ie)
            {
                ie.printStackTrace();
            }
        }
    }
}