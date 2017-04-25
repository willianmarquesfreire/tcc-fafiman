package com.wmfsystem.muniserver;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.*;
import java.nio.charset.Charset;
import java.util.Enumeration;

/**
 * Created by wmfsystem on 4/19/17.
 */
public class Application {
    public static void main(String[] args) {
        try {
            HttpRequest httpRequest = new HttpRequest();
            StringBuffer buffer = new StringBuffer();
            String register = httpRequest.readFile(Thread.currentThread().getContextClassLoader().getResource("register.json").getPath(), Charset.defaultCharset());

            InetAddress address = InetAddress.getLocalHost();

            InetAddress localAddress = getLocalAddress();

            String ip = localAddress.getHostAddress();

            register = register.replaceAll("\\(myip\\)", ip);

            buffer.append(register);

            httpRequest.sendRequest("http://localhost:8000/eureka/apps/appID", buffer.toString(), "POST");
            HttpServer server = HttpServer.create(new InetSocketAddress(18000), 0);
            server.createContext("/", new MuniHandler());
            server.setExecutor(null); // creates a default executor
            server.start();

            while(true) {
                Thread.sleep(1000 * 30);
                System.out.println("HEARTBEART");
                httpRequest.sendRequest("http://localhost:8000/eureka/apps/appID", buffer.toString(), "PUT");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static InetAddress getLocalAddress() throws SocketException {
        Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
        while (ifaces.hasMoreElements()) {
            NetworkInterface iface = ifaces.nextElement();
            Enumeration<InetAddress> addresses = iface.getInetAddresses();

            while (addresses.hasMoreElements()) {
                InetAddress addr = addresses.nextElement();
                if (addr instanceof Inet4Address && !addr.isLoopbackAddress()) {
                    return addr;
                }
            }
        }

        return null;
    }

}


