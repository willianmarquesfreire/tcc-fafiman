package com.wmfsystem.muniserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by willian on 20/04/17.
 */
public class MuniHandler implements HttpHandler {

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
