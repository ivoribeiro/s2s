package com.s2s.p2p;

import java.io.IOException;

public class P2p {
    public static void main(String args[]) {
        try {
            if (args.length > 0) {
                //  First argument is the server port
                Server slackerServer = new Server(Integer.parseInt(args[0]));
                slackerServer.start();

            } else {
                // Default Slacker server port
                Server slackerServer = new Server(2048);
                slackerServer.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
