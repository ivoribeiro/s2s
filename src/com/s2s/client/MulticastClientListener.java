package com.s2s.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

class MulticastClientListener extends Thread {

    private MulticastSocket multicastSocket;

    public MulticastClientListener(MulticastSocket multicastSocket) {
        this.multicastSocket = multicastSocket;
    }

    public void run() {
        DatagramPacket packet;
        while (true) {
            byte[] buf = new byte[256];
            packet = new DatagramPacket(buf, buf.length);
            try {
                this.multicastSocket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println(received);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
