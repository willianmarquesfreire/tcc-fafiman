package com.wmfsystem.broadcastclient;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

/**
 * Created by wmfsystem on 5/6/17.
 */
public class Application {

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.run();
    }
}
