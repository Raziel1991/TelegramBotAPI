package com.telegramWeather.util;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadKeyFromFile {

    // Static method to get the key from the file
    public static String getKeyFromFile(String fileName) {
        String key = null;
        try (Scanner scanner = new Scanner(new File(fileName))){
            key = scanner.nextLine();
        }catch (FileNotFoundException e){
            System.err.println("File not found at path: " + fileName);
        }
        return key;
    }
}

