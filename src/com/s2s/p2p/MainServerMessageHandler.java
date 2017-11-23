package com.s2s.p2p;

import java.io.*;

/**
 * Listener to the main server socket
 */
public class MainServerMessageHandler extends Thread {
    private BufferedReader in;
    private String response;
    private BufferedWriter out;

    public MainServerMessageHandler(InputStream in, OutputStream out) {
        super("Main Server Message Handler Thread");
        this.in = new BufferedReader(new InputStreamReader(in));
        Writer ouw = new OutputStreamWriter(out);
        this.out = new BufferedWriter(ouw);
    }

    /**
     * Writes a request to the server
     *
     * @param message
     * @throws IOException
     */
    public void request(String message) {
        try {
            this.out.write(message + "\r\n");
            this.out.flush();
        } catch (IOException e) {
            System.out.println("Erro ao enviar mensagem");
            e.printStackTrace();
        }
    }

    /**
     * Listen the main server for new responses
     */
    public void run() {
        String inputLine;
        try {
            while ((inputLine = this.in.readLine()) != null) {
                System.out.println("ClientServer: Message from main server");
                System.out.println(inputLine);
                this.response = inputLine;
            }
            //close the buffer reader
            this.in.close();
        } catch (IOException e) {
            System.out.println("Error reading buffer");
            e.printStackTrace();
        }
    }
}
