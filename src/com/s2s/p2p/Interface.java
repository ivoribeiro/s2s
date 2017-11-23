package com.s2s.p2p;

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
        System.out.println("1-My chats");
        System.out.println("2-New chat");
    }
}
