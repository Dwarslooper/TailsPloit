package com.eimacon.bugfix.utils;

import java.io.*;
import java.net.URI;

public class InputStreamToFile1 {

    /**
     * The default buffer size
     */
    public static final int DEFAULT_BUFFER_SIZE = 8192;

    public static void main(String[] args) throws IOException {

        URI u = URI.create("https://www.google.com/");
        try (InputStream inputStream = u.toURL().openStream()) {

            File file = new File("c:\\test\\google.txt");

            copyInputStreamToFile(inputStream, file);

        }

    }

    private static void copyInputStreamToFile(InputStream inputStream, File file)
            throws IOException {

        // append = false
        try (FileOutputStream outputStream = new FileOutputStream(file, false)) {
            int read;
            byte[] bytes = new byte[DEFAULT_BUFFER_SIZE];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }

    }
}
