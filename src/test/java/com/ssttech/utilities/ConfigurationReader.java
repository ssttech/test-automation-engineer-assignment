package com.ssttech.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public abstract class ConfigurationReader {

    //1- Create the object of Properties
    private static final Properties PROPERTIES = new Properties();

    static {

        try {

            //2- We need to open the file in java memory: FileInputStream
            FileInputStream file = new FileInputStream("configuration.properties");

            //3- Load the properties object using FileInputStream object
            PROPERTIES.load(file);

            //close the file
            file.close();


        } catch (IOException e) {
            System.out.println("File not found in the ConfigurationReader class.");
            e.printStackTrace();
        }

    }

    public static String getProperty(String keyword) {
        return PROPERTIES.getProperty(keyword);
    }

}