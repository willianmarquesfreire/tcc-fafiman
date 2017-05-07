package com.wmfsystem.eurekaserver.broadcast;

/**
 * Created by wmfsystem on 5/6/17.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.*;
import java.util.*;

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

                RestTemplate template = new RestTemplate();

                template.exchange("http://" + packet.getAddress().getHostAddress().concat(":8000?ip={ip}"),
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        Void.class,
                        getLocalAddress().get(0).getHostAddress());

                System.out.println("Message ----> " + packet.getAddress().getHostAddress());

            }
            catch (IOException ie)
            {
                ie.printStackTrace();
            }
        }
    }

    public static List<InetAddress> getLocalAddress() throws SocketException {
        List<InetAddress> address = new ArrayList<>();
        Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
        while (ifaces.hasMoreElements()) {
            NetworkInterface iface = ifaces.nextElement();
            Enumeration<InetAddress> addresses = iface.getInetAddresses();

            while (addresses.hasMoreElements()) {
                InetAddress addr = addresses.nextElement();

                if (addr instanceof Inet4Address && !addr.isLoopbackAddress()) {
                    address.add(addr);
                }
            }
        }

        return address;
    }
}