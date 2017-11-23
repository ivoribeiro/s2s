package com.s2s.p2p;

import java.util.Scanner;

public class Interface {

    public static void mainMenu() {
        System.out.println("Choose one of the fallowing options:");
        System.out.println("1-Register");
        System.out.println("2-Login");
        System.out.println("0-Close");
    }

    public static void logedIn() {
        System.out.println("1-Private Chats");
        System.out.println("2-Group Chats");
    }

    public static void privateChats() {
        System.out.println("1-New chat");
        System.out.println("2-Select");
    }

    public static void newChat() {
        System.out.println("Online users");
    }

    public static void groupChats() {
        //TODO list my chats
        System.out.println("1-New Group");
        System.out.println("2-Enter Group");
        System.out.println("3-Select my group");
    }

    public static int menuChooser() {
        Scanner sc = new Scanner(System.in);
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.println("Insert a number");
        }
        int i = sc.nextInt();
        return i;
    }
}
