package com.s2s.repository;

import com.s2s.models.Slacker;
import com.s2s.models.User;
import utils.FileUtil;

import java.io.*;
import java.util.HashMap;

public class Users extends Repository<String, User> {

    public Users(HashMap<String, User> users) {
        super(users);
    }
    public Users(){
        super();
    }

    public User validate(String username, String password) {
        User exists = this.exists(username);
        if (exists != null) {
            if (exists.getUsername().equals(username) && exists.getPassword().equals(password)) {
                return exists;
            } else return null;
        }
        return null;
    }

    public static boolean saveNewUser(Users users,User user) throws Exception {
        FileUtil.createUserFolder(user.getUsername());
        saveUserOnFile(users);
        return true;
    }

    public static void saveUserOnFile(Users users) throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("users.txt"));
        oos.writeObject(users.getModels());
        System.out.println("Done");
    }

    public static HashMap<String, User> loadUsersFile() throws Exception {
        FileInputStream fis;
        HashMap<String, User> users = new HashMap<>();
        File yourFile = new File("users.txt");
        yourFile.createNewFile();
        fis = new FileInputStream("users.txt");
        ObjectInputStream ois = new ObjectInputStream(fis);
        users = (HashMap<String, User>) ois.readObject();
        ois.close();
        return users;
    }

}
