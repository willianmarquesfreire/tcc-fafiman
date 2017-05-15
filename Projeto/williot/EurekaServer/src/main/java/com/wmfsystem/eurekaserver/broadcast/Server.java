package com.wmfsystem.eurekaserver.broadcast;

/**
 * Created by wmfsystem on 5/6/17.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author willian
 */
public class Server {

    public static final int DEFAULT_PORT = 1234;
    private DatagramSocket socket;
    private DatagramPacket packet;

    public void run() {
        try {
            socket = new DatagramSocket(DEFAULT_PORT);
        } catch (Exception ex) {
            System.out.println("Problem creating socket on port: " + DEFAULT_PORT);
        }

        packet = new DatagramPacket(new byte[1], 1);

        while (true) {
            try {
                socket.receive(packet);
                System.out.println("Received from: " + packet.getAddress() + ":" +
                        packet.getPort());
                byte[] outBuffer = new java.util.Date().toString().getBytes();
                packet.setData(outBuffer);
                packet.setLength(outBuffer.length);
                socket.setBroadcast(true);
                socket.send(packet);

                Set<InetAddress> localAddress = getLocalAddress();

                Set<String> ips = localAddress.stream()
                        .map(ad -> ad.getHostAddress())
                        .collect(Collectors.toSet())
                        .stream().sorted()
                        .collect(Collectors.toSet());

                RestTemplate template = new RestTemplate();

                ips.forEach(ip -> {
                    template.exchange("http://" + packet.getAddress().getHostAddress().concat(":8000?ip={ip}"),
                            HttpMethod.GET,
                            HttpEntity.EMPTY,
                            Void.class,
                            ip.concat(":8000"));
                    try {
                        template.exchange("http://" + packet.getAddress().getHostAddress().concat(":8000?ip={ip}"),
                                HttpMethod.GET,
                                HttpEntity.EMPTY,
                                Void.class,
                                ip.concat(":8000"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                System.out.println("Message ----> " + packet.getAddress().getHostAddress());

            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }
    }

    public static Set<InetAddress> getLocalAddress() throws SocketException {
        Set<InetAddress> address = new HashSet<>();
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