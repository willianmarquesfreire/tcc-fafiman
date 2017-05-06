package com.wmfsystem.broadcastserver;

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
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author willian
 */
public class Client
{
    private String hostname= "192.168.1.104";
    private int port=1234;
    private InetAddress host;
    private DatagramSocket socket;
    DatagramPacket packet;

    public void run(String hostname) {
        this.hostname = hostname;
        this.run();
    }

    public void run()
    {
        try
        {
            host = InetAddress.getByName(hostname);
            socket = new DatagramSocket (null);

            byte[] sendData = "Muito legal xkodgjidsji sueriowuiruioe riwrweri wiwieruwei wruwiriowe riewriowrw willuian ".getBytes("UTF-8");
            packet=new DatagramPacket (sendData, sendData.length,host, port);

            socket.send (packet);
            packet.setLength(sendData.length);
            socket.receive (packet);
            socket.close ();
            byte[] data = packet.getData ();
            String time=new String(data);  // convert byte array data into string
            System.out.println(time);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
