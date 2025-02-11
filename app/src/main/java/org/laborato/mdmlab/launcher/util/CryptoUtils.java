package org.laborato.mdmlab.launcher.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptoUtils {
    public static String calculateChecksum(InputStream fileContent) {
        // Calculate checksum
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        try (InputStream is = new BufferedInputStream(fileContent);
             DigestInputStream dis = new DigestInputStream(is, md)) {
            /* Read decorated stream (dis) to EOF as normal... */
            int b;
            while ((b = dis.read()) != -1) {
                // digest will consume the content when read() called
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        // to calculate message digest of the input string
        // returned as array of byte
        byte[] digest = md.digest();

        // Convert byte array into signum representation
        BigInteger no = new BigInteger(1, digest);

        // Convert message digest into hex value
        String hashtext = no.toString(16);

        // Add preceding 0s to make it 32 bit
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }

        return hashtext;
    }

}
