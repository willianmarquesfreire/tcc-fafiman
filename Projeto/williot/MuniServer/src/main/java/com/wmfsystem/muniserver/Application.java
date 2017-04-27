package com.wmfsystem.muniserver;

import com.sun.net.httpserver.HttpServer;

import java.net.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wmfsystem on 4/19/17.
 */
public class Application {
    public static void main(String[] args) {
        try {
            HttpRequest httpRequest = new HttpRequest();
            StringBuffer buffer = new StringBuffer();

            InetAddress address = InetAddress.getLocalHost();

            Set<InetAddress> localAddress = getLocalAddress();

            Set<String> ips = localAddress.stream()
                    .map(ad -> ad.getHostAddress())
                    .collect(Collectors.toSet())
                    .stream().sorted()
                    .collect(Collectors.toSet());

            ips.forEach(ip -> {
                String register = null;
                try {
                    register = httpRequest.readFile(Thread.currentThread().getContextClassLoader().getResource("register.json").getPath(), Charset.defaultCharset());
                    register = register.replaceAll("\\(myip\\)", ip);

                    buffer.append(register);
                    httpRequest.sendRequest("http://localhost:8000/eureka/apps/appID", buffer.toString(), "POST");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });

            HttpServer server = HttpServer.create(new InetSocketAddress(18000), 0);
            server.createContext("/", new MuniHandler());
            server.setExecutor(null); // creates a default executor
            server.start();

            while(true) {
                Thread.sleep(1000 * 30);
                System.out.println("HEARTBEART");
                httpRequest.sendRequest("http://localhost:8000/eureka/apps/appID", buffer.toString(), "PUT");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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


// && addr.getHostAddress().contains("192.")