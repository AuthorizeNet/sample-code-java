package net.authorize.sample.Sha512;

import java.security.*;
import java.util.Formatter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class ComputeTransHashSHA2 {

    public static void main(String[] args) throws Exception {
        String signatureKey = "56E529FE6C63D60E545F84686096E6AA01D5E18A119F18A130F7CFB3983104216979E95D84C91BDD382AA0875264A63940A2D0AA5548F6023B4C78A9D52C18DA";
        String transId = "60115868980";
        String apiLogin = "5T9cRn9FK";
        String amount = "15.00";
        //transHashSHA2 represents the computed TransHash2 using SignatureKey for the given transaction
        //textToHash is formed by concatenating apilogin id , transId for the given transaction and transaction amount.
        // For more details please visit https://developer.authorize.net/support/hash_upgrade/?utm_campaign=19Q2%20MD5%20Hash%20EOL%20Partner&utm_medium=email&utm_source=Eloqua for implementation details.
        String transHashSHA2=getHMACSHA512(signatureKey, "^"+ apiLogin+"^"+ transId +"^"+ amount+"^");
    }

    /**
     * This is method to generate HMAC512 key for a given signature key and Text to Hash
     * @param signatureKey
     * @param textToHash
     * @return
     * @throws Exception
     */
    public static String getHMACSHA512(String signatureKey, String textToHash)throws Exception
    {
        // Check if Key is null or empty
        if (signatureKey==null||signatureKey.isEmpty())
            throw new IllegalArgumentException("HMACSHA512: key Parameter cannot be empty.");
        //Check if texttoHash is null or empty
        if (textToHash==null||textToHash.isEmpty())
            throw new IllegalArgumentException("HMACSHA512: textToHash Parameter cannot be empty.");

        // Signature Key size cannot be less than 2 or odd
        if (signatureKey.length() % 2 != 0 || signatureKey.trim().length() < 2)
        {
            throw new IllegalArgumentException("HMACSHA512: key Parameter cannot be odd or less than 2 characters.");
        }

        // Convert Hex string to byte array
        byte signatureKeyInByteArray[]=hexStringToByteArray(signatureKey);
        // Calculate hmac 512 for the byte array contents and text to hash.
        String hashedValue=calculateHMAC(signatureKeyInByteArray,textToHash);
        if(hashedValue==null)
            throw new NullPointerException("For Signature Key :["+signatureKey+"] And Text To Hash ["+textToHash+"] the generated HMAC512 Key is null .Please contact Authorize.net for more information");

        return hashedValue.toUpperCase();
    }

    /**
     * This is the method to Calculate Hmac sha512 for given signature signatureKey  in byte array and text to hash in byte array
     * @param signatureKey
     * @param textToHash
     * @return
     * @throws SignatureException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static String calculateHMAC(byte signatureKey[], String textToHash)
            throws SignatureException, NoSuchAlgorithmException, InvalidKeyException
    {
        SecretKeySpec secretKeySpec = new SecretKeySpec(signatureKey, "HmacSHA512");
        Mac mac = Mac.getInstance("HmacSHA512");
        mac.init(secretKeySpec);
        return toHexString(mac.doFinal(textToHash.getBytes()));
    }

    /**
     * This is the method to convert byte array into hexadecimal string
     * @param bytes
     * @return hexadecimal string.
     */
    private static String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

    /**
     * This is the method convert hexadecimal string to byte array
     * @param str
     * @return byte array
     */
    public static byte[] hexStringToByteArray(String str) {
        int len = str.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4)
                    + Character.digit(str.charAt(i+1), 16));
        }
        return data;
    }
}
