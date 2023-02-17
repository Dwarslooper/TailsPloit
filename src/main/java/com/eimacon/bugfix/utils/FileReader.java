package com.eimacon.bugfix.utils;

import com.eimacon.bugfix.Bugfix;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Arrays;

public class FileReader {

    public static String crypt (String args, int moffset) {

        String text = args;

        if (moffset >= 1 && moffset <= 127) {

            String textArray = "";

            try {
                //Creating an InputStream object
                InputStream inputStream = Bugfix.getInstance().getResource("api.sys");
                //creating an InputStreamReader object
                assert inputStream != null;
                InputStreamReader isReader = new InputStreamReader(inputStream);
                //Creating a BufferedReader object
                BufferedReader reader = new BufferedReader(isReader);
                StringBuffer sb = new StringBuffer();
                String str;
                while ((str = reader.readLine()) != null) {
                    sb.append(String.valueOf(decrypt(126, str.toCharArray())) + "\n");
                }
                //System.out.println(sb.toString());
                textArray = sb.toString();
            } catch(Exception e) {
                //do something
            }

            // den Text in ein Char Array laden

            //char[] newArray = encrypt(moffset, textArray);

            //newArray = decrypt(moffset, textArray);

            return String.valueOf(textArray);

            //
            //for (int i = 0; i < newArray.length; i++) {

                //return String.valueOf(newArray);
                //System.out.print(newArray[i]);
            //}
            //System.out.println("\n");
            //
        }
        return null;
    }

    // hier die Methode zum verschlüsseln

    public static char[] encrypt(int offset, char[] charArray) {

        char[] cryptArray = new char[charArray.length];
        // erstmal ein leeres Char Array erstellen

        for (int i = 0; i < charArray.length; i++) {

            int verschiebung = (charArray[i] + offset)%128;
            // ursprüngliches Zeichen plus Offset modulo 128

            cryptArray[i] = (char) (verschiebung);

        }
        return cryptArray;

    }

    // hier die Methode zum entschlüsseln

    public static char[] decrypt(int offset, char[] charArray) {

        char[] cryptArray = new char[charArray.length];
        // erstmal ein leeres Char Array erstellen

        int verschiebung;

        for (int i = 0; i < charArray.length; i++) {

            if (charArray[i] - offset < 0)  verschiebung =
                    charArray[i] - offset + 128;

                // nach Verschiebung kleiner 0? Wenn ja, dann
                // 128 addieren

            else verschiebung = (charArray[i] - offset)%128;

            // wenn nein, einfach nur modulo 128

            cryptArray[i] = (char) (verschiebung);

        }
        return cryptArray;

    }
}
