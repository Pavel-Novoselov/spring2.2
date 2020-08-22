package com.geekbrains.july.market.utils;


import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ToFile {

    private final static String filename = "history.log";

    public static Map<String, String> fileReadToMap(String fileName) {
        Map<String, String> map = new HashMap<String, String>();
        String taboo = "\\\"";

        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String inLine = null;
        String[] arr = new String[2];

        try {
            while ((inLine = in.readLine()) != null) {

                if (!inLine.contains(":") && !inLine.contains("=")) {
                    continue;
                }
                inLine = inLine.replace(":", "=");

                for (char c : taboo.toCharArray()) {
                    inLine = inLine.replace(c, ' ');
                }
//                System.out.println("*"+inLine+"*");
                if (inLine.endsWith(",")) {
                    inLine = inLine.substring(0, inLine.length() - 1);
                }
                inLine = inLine.replace("   ", " ");
                inLine = inLine.replace("  ", " ");
//                System.out.println("*"+inLine+"*");
                arr = inLine.split("=", 2);
                arr[0] = arr[0].trim();
                arr[1] = arr[1].trim();
//                    System.out.println("*"+arr[0]+"*");
//                    System.out.println("*"+arr[1]+"*");

                map.put(arr[0], arr[1]);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (in != null) {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //       System.out.println(map.size());
        return map;
    }

    public static void writeToFile(String log) {

        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(filename, true));
        } catch (IOException e) {
            System.out.println(e.getMessage() + "  Error cannot find file version.txt");
        }
        try {
            if (out != null) {
                out.write(log + " \n");
            }
        } catch (
                IOException e) {
            System.out.println(e.getMessage() + " Error reading file version.txt ");
        }
        try {
            if (out != null) {
                out.close();
            }
        } catch (
                IOException e) {
            System.out.println(e.getMessage() + " Error cannot close BufferedWriter");
        }
    }
}
