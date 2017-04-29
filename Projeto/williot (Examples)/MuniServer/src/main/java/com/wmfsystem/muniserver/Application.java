package com.wmfsystem.muniserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

/**
 * Created by wmfsystem on 4/19/17.
 */
public class Application {
    public static void main(String[] args) {
        try {

            StringBuffer buffer = new StringBuffer();
            buffer.append("{\n" +
                    "    \"instance\": {\n" +
                    "        \"hostName\": \"wmfsystem-W240HU-W250HUQ\",\n" +
                    "        \"app\": \"MUNISERVER\",\n" +
                    "        \"ipAddr\": \"127.0.1.1\",\n" +
                    "        \"status\": \"UP\",\n" +
                    "        \"port\": {\n" +
                    "            \"@enabled\": \"true\",\n" +
                    "            \"$\": \"18000\"\n" +
                    "        },\n" +
                    "        \"securePort\": {\n" +
                    "            \"@enabled\": \"false\",\n" +
                    "            \"$\": \"443\"\n" +
                    "        },\n" +
                    "        \"dataCenterInfo\": {\n" +
                    "            \"@class\": \"com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo\",\n" +
                    "            \"name\": \"MyOwn\"\n" +
                    "        },\n" +
                    "        \"leaseInfo\": {\n" +
                    "            \"renewalIntervalInSecs\": 30,\n" +
                    "            \"durationInSecs\": 90,\n" +
                    "            \"registrationTimestamp\": 1492644843509,\n" +
                    "            \"lastRenewalTimestamp\": 1492649644434,\n" +
                    "            \"evictionTimestamp\": 0,\n" +
                    "            \"serviceUpTimestamp\": 1492644813469\n" +
                    "        },\n" +
                    "        \"homePageUrl\": \"http://wmfsystem-W240HU-W250HUQ:18000/\",\n" +
                    "        \"statusPageUrl\": \"http://wmfsystem-W240HU-W250HUQ:18000/info\",\n" +
                    "        \"healthCheckUrl\": \"http://wmfsystem-W240HU-W250HUQ:18000/health\",\n" +
                    "        \"vipAddress\": \"MuniServer\"\n" +
                    "    }\n" +
                    "}");

            HttpRequest httpRequest = new HttpRequest();
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

class MuniHandler implements HttpHandler {

    public void handle(HttpExchange he) throws IOException {
        he.getResponseHeaders().set("Content-Type", "text/event-stream");


        he.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        he.getResponseHeaders().set("Access-Control-Allow-Method", "POST,  GET, PUT, DELETE, OPTIONS,HEAD");
        he.sendResponseHeaders(200, 0);

        try {
            StringBuilder sb = new StringBuilder(1024);
            sb.append("{");
            sb.append("\"nome\":\"Munif Gebara Junior 75\"");
            sb.append("}");
            byte[] bytes = sb.toString().getBytes();
            OutputStream responseBody = he.getResponseBody();
            responseBody.write(bytes);

        } catch (Exception e) {

        }

        he.close();
    }
}
