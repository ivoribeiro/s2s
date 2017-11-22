package com.s2s.p2p;

import com.s2s.models.Slacker;

import java.io.IOException;

public class P2p {
    public static void main(String args[]) {
        try {
            Server slackerServer = null;
            if (args.length > 2) {
                //  First argument is the server port
                slackerServer = new Server(Integer.parseInt(args[0]), args[1], Integer.parseInt(args[2]));
                slackerServer.start();
            } else {
                // Default Slacker server port
                slackerServer = new Server(1024, "localhost", 312);
                slackerServer.start();
            }
            new Client(slackerServer).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
