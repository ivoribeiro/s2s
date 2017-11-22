package com.s2s.p2p;

import java.io.IOException;
import java.util.Scanner;


public class Client extends Thread {

    private Server server;


    public Client(Server server) throws IOException {
        this.server = server;
        this.menu1();
    }

    public void menu1() throws IOException {
        boolean run = true;
        while (run) {
            System.out.println("1-Login");
            System.out.println("2-Close");
            System.out.println("Escolha uma das opções anteriores:");
            Scanner sc = new Scanner(System.in);
            while (!sc.hasNextInt()) {
                sc.next();
                System.out.println("Insert a number");
            }
            int i = sc.nextInt();
            if (i > 2) {
                System.out.println("Invalid option");
            }
            if (i == 1) {
                login();
            } else {
                run = false;
            }
        }

    }

    private void login() throws IOException {
        boolean success = false;
        do {
            Scanner scan = new Scanner(System.in);
            System.out.println("Username");
            String username = scan.next();
            System.out.println("Password");
            String password = scan.next();
            success = this.server.login(username, password);
        } while (!success);
        System.out.println("Sucess Login, now you can chat");
        this.server.serving();
    }

    public void logedIn() {
        System.out.println("1-Private Chats");
        System.out.println("2-Group Chats");
    }

    public void privateChats() {
        System.out.println("1-My chats");
        System.out.println("2-New chat");
    }

}
