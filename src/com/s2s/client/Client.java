package com.s2s.client;

import java.io.IOException;
import java.util.Scanner;


public class Client extends Thread {

    private Actions actions;

    public Client(Actions actions) throws IOException {
        this.actions = actions;
    }

    private void register() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Username");
        String username = scan.next();
        System.out.println("Password");
        String password = scan.next();
        this.actions.register(username, password);
    }

    private void login() throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Username");
        String username = scan.next();
        System.out.println("Password");
        String password = scan.next();
        this.actions.login(username, password);
    }
}
