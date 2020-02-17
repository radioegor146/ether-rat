package me.Eclipse.Utils;

import java.io.File;

public class FileData {
    public static File Ether;
    public static File Keyboard;
    public static String Username = System.getProperty("user.name");

    static {
        Ether = new File("C:\\Users\\" + Username + "\\AppData\\Local\\Ether");
        Keyboard = new File("C:\\Users\\" + Username + "\\AppData\\Local\\Ether\\Keyboard.txt");
    }
}
