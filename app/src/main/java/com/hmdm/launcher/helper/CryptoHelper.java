package com.hmdm.launcher.helper;

import java.security.MessageDigest;

public class CryptoHelper {

    private final static String MD5 = "MD5";
    private final static String UTF8 = "UTF-8";

    private final static char[] hexArray = "0123456789abcdef".toCharArray();

    public static String getMD5String( String value ) {
        try {
            MessageDigest md = MessageDigest.getInstance( MD5 );
            md.update( value.getBytes( UTF8 ) );
            byte[] digest = md.digest();

            char[] hexChars = new char[ digest.length * 2 ];
            for ( int i = 0; i < digest.length; i++ ) {
                int v = digest[ i ] & 0xFF;
                hexChars[ i * 2 ] = hexArray[ v >>> 4 ];
                hexChars[ i * 2 + 1 ] = hexArray[ v & 0x0F ];
            }
            return new String( hexChars ).toUpperCase();
        } catch ( Exception e ) { throw new RuntimeException( e ); }
    }

    public static String getSHA1String( String value ) {
        try {
            MessageDigest md = MessageDigest.getInstance( "SHA-1" );
            md.update( value.getBytes( UTF8 ) );
            byte[] digest = md.digest();

            char[] hexChars = new char[ digest.length * 2 ];
            for ( int i = 0; i < digest.length; i++ ) {
                int v = digest[ i ] & 0xFF;
                hexChars[ i * 2 ] = hexArray[ v >>> 4 ];
                hexChars[ i * 2 + 1 ] = hexArray[ v & 0x0F ];
            }
            return new String( hexChars ).toUpperCase();
        } catch ( Exception e ) { throw new RuntimeException( e ); }
    }

}
