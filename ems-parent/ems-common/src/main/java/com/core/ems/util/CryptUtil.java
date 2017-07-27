/*
 * Copyright (c) EmHospital (India) 2015. ALL rights
 * reserved. The contents of this document are property of Land Transport
 * Authority (India). no part of this work may be reproduced or transmitted
 * in any form or by any means, except as permitted by written license agreement
 * with EmHospital(India).
 */

package com.core.ems.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;

public class CryptUtil {
	
	private static final SecretKey SECRET_KEY = CryptUtil.getSecretEncryptionKey();
	
    /**
     * gets the AES encryption key. In your actual programs, this should be safely
     * stored.
     * @return
     * @throws Exception
     */
    public static SecretKey getSecretEncryptionKey() {
    	SecretKey secKey = null;
    	try {
	        KeyGenerator generator = KeyGenerator.getInstance("AES");
	        generator.init(128); // The AES key size in number of bits
	        secKey = generator.generateKey();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
        return secKey;
    }
     
    /**
     * Encrypts plainText in AES using the secret key
     * @param plainText
     * @param secKey
     * @return
     * @throws Exception
     */
    public static String encryptText(String plainText) throws Exception{
        // AES defaults to AES/ECB/PKCS5Padding in Java 7
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.ENCRYPT_MODE, SECRET_KEY);
        byte[] byteCipherText = aesCipher.doFinal(plainText.getBytes());
        return DatatypeConverter.printHexBinary(byteCipherText);
    }
     
    /**
     * Decrypts encrypted byte array using the key used for encryption.
     * @param byteCipherText
     * @param secKey
     * @return
     * @throws Exception
     */
    public static String decryptText(String byteCipherText) throws Exception {
        // AES defaults to AES/ECB/PKCS5Padding in Java 7
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.DECRYPT_MODE, SECRET_KEY);
        byte[] bytePlainText = aesCipher.doFinal(DatatypeConverter.parseHexBinary(byteCipherText));
        return new String(bytePlainText);
    }
     
    /**
     * Convert a binary byte array into readable hex form
     * @param hash
     * @return
     */
    private static String  bytesToHex(byte[] hash) {
    	hash = DatatypeConverter.parseHexBinary("0DB44004E814886E2F8078B780C4B4D1F5EBD456404226AD527A505FFA41ADF4");
    	
    	
        return DatatypeConverter.printHexBinary(hash);
    }
    
    /**
     * 1. Generate a plain text for encryption
     * 2. Get a secret key (printed in hexadecimal form). In actual use this must
     * by encrypted and kept safe. The same key is required for decryption.
     * 3.
     */
/*    public static void main(String[] args) throws Exception {
        String plainText = "10000055|1|1212121212121212";
        SecretKey secKey = getSecretEncryptionKey();
        
        System.out.println(SECRET_KEY.toString());
        System.out.println(SECRET_KEY.getEncoded());
        System.out.println(bytesToHex(SECRET_KEY.getEncoded()));
        
        String cipherText = encryptText(plainText);
        System.out.println("Strig ciper  :  "+ cipherText);
        System.out.println("byte[] ciper :  "+ DatatypeConverter.printHexBinary(DatatypeConverter.parseHexBinary(cipherText)));
        
        String decryptedText = decryptText(cipherText);
         
        System.out.println("Original Text:" + plainText);
        System.out.println("AES Key (Hex Form):"+bytesToHex(secKey.getEncoded()));
        System.out.println("Encrypted Text (Hex Form):"+(cipherText));
        System.out.println("Descrypted Text:"+decryptedText);
         
    }
*/    
}