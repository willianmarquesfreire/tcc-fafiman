package com.wmfsystem.muniserver;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created by wmfsystem on 4/19/17.
 */
public class Application {
    public static void main(String[] args) {
        try {
            HttpRequest httpRequest = new HttpRequest();
            StringBuffer buffer = new StringBuffer();
            String register = httpRequest.readFile(Thread.currentThread().getContextClassLoader().getResource("register.json").getPath(), Charset.defaultCharset());

            buffer.append(register);


            httpRequest.sendPost("http://localhost:8000/eureka/apps/appID", buffer.toString());
            HttpServer server = HttpServer.create(new InetSocketAddress(18000), 0);
            server.createContext("/", new MuniHandler());
            server.setExecutor(null); // creates a default executor
            server.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


