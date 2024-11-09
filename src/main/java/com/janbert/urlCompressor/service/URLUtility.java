package com.janbert.urlCompressor.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class URLUtility {

    public static final int HASHEND = 5;
    private static final int HASHSTART = 0;
    public static final String BASE_64_STRING = "base64String";
    public static final String HASH_STRING = "hash";
    public static final String BASE_64_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    public static Map<String, String> split(String code){
        var theMap = new HashMap<String, String>();

        String base64String = code.substring(HASHEND);
        String hash = code.substring(HASHSTART, HASHEND);

        theMap.put(BASE_64_STRING, base64String);
        theMap.put(HASH_STRING, hash);

        return theMap;
    }

    public static Long convertBase64ToBase10(String base64Number) {
        String base64Chars = BASE_64_CHARS;
        BigInteger base10Number = BigInteger.ZERO;
        for (int i = 0; i < base64Number.length(); i++) {
            int digitValue = base64Chars.indexOf(base64Number.charAt(i));
            base10Number = base10Number.multiply(BigInteger.valueOf(64)).add(BigInteger.valueOf(digitValue));
        }
        return Long.valueOf(base10Number.longValue());
    }

    public static String convertBase10ToBase64(Long urlId) {
        BigInteger base10Number = BigInteger.valueOf(urlId);
        String base64Chars = BASE_64_CHARS;
        StringBuilder base64Number = new StringBuilder();
        while (base10Number.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] divmod = base10Number.divideAndRemainder(BigInteger.valueOf(64));
            base10Number = divmod[0];
            int digitValue = divmod[1].intValue();
            base64Number.insert(0, base64Chars.charAt(digitValue));
        }
        return base64Number.toString();
    }

    public static String encrypt(String inString){
        String md5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(inString.getBytes());
            byte[] digest = md.digest();
            BigInteger no = new BigInteger(1, digest);
            md5 = no.toString(16);
            while (md5.length() < 32) {
                md5 = "0" + md5;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return md5.substring(0, 5);
    }
}
