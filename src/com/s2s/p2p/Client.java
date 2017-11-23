package com.s2s.p2p;

import java.io.IOException;
import java.util.Scanner;


public class Client extends Thread {

    private Actions actions;

    public Client(Actions actions) throws IOException {
        this.actions = actions;
    }

    public void run() {
        try {
            this.menu1();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menu1() throws Exception {
        boolean run = true;
        while (run) {
            Interface.mainMenu();
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
                register();
            } else {
                if (i == 2) {
                    login();
                }
            }
        }

    }

    private void register() {
        boolean success = false;
        //do {
        Scanner scan = new Scanner(System.in);
        System.out.println("Username");
        String username = scan.next();
        System.out.println("Password");
        String password = scan.next();
        //} while (!success);
        this.actions.register(username, password);
        //System.out.println("Sucess Register, you can login now");
        //this.server.serving();
    }

    private void login() throws IOException {
        boolean success = false;
        //do {
        Scanner scan = new Scanner(System.in);
        System.out.println("Username");
        String username = scan.next();
        System.out.println("Password");
        String password = scan.next();
        //success = this.server.login(username, password);
        //} while (!success);
        System.out.println("Sucess Login, you can chat now");
        //this.server.serving();
    }

}
