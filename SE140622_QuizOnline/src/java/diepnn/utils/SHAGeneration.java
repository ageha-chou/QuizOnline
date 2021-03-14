/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diepnn.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Delwyn
 */
public class SHAGeneration {
    private static byte[] getSHA(String input) throws NoSuchAlgorithmException{
        //static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        
        //digest() method called to calculate message digest of an input
        //and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }
    
    private static String toHexString(byte[] hash){
        //convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);
        
        //convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));
        
        //pad with leading zeros
        while(hexString.length() < 32){
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }
    
    public static String generateSHA(String input){
        try{
            return toHexString(getSHA(input));
        }catch(NoSuchAlgorithmException ex){
            ex.printStackTrace();
        }
        return input;
    }
}
