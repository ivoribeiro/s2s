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
            int choise = Interface.menuChooser();
            if (choise > 2) {
                System.out.println("Invalid option");
            }
            if (choise == 1) {
                register();
            } else {
                if (choise == 2) {
                    login();
                }
            }
        }
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
        this.logedIn();
    }

    private void logedIn() {
        Interface.logedIn();
        int choise = Interface.menuChooser();
        if (choise == 1) {
            this.privateChats();
        } else {
            if (choise == 2) {
            }
        }
    }

    private void privateChats() {
        Interface.privateChats();
        this.actions.getOnlineUsers();
        int choise = Interface.menuChooser();
    }

    private void groupChats() {
        Interface.groupChats();
        int choise = Interface.menuChooser();
    }

}
