package com.kony;

import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;

@Controller
public class Encryptor {
	
	String key = "Kony9999Kony2347"; // 128 bit key
    String initVector = "RandomInitVector"; // 16 bytes IV
    
    public static String encrypt(String key, String initVector, String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            System.out.println("encrypted string: "
                    + Base64.encodeBase64String(encrypted));

            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String decrypt(String key, String initVector, String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
    
    public String[] QRParser(String encrptCode){
		String arr[] = new String[4];
		
		 Scanner sc = new Scanner(encrptCode);
         sc.useDelimiter("\\s* \\s*");
         int i = 0;
         while(sc.hasNext()){
             arr[i] = sc.next();
             i++;
          }
		new Encryptor();
		String attendance_decryptdata = Encryptor.decrypt(key, initVector, arr[0]);
		String userid = arr[1];
		
		sc = new Scanner(attendance_decryptdata);
        sc.useDelimiter(",");
       
        i = 0;
        while(sc.hasNext()){
            arr[i] = sc.next();
            i++;
         }
        arr[3] = userid;
		
		//System.out.println(attendance_decryptdata);
//		for(i = 0; i < arr.length; i++){
//			System.out.println(arr[i]);
//		}
		
		
		return arr;
		
	}
	
//	public static void main(String args[]){
//		new QRInfo().QRParser("J9MMYcZyWhV2A9IjorHTIhIZhKC3feYE6K1cd0oVBjU= piyush.mittal@kony.com");
//		
//	}

    public static void main(String[] args) {
        String key = "Kony9999Kony2347"; // 128 bit key
        String initVector = "RandomInitVector"; // 16 bytes IV

        System.out.println(decrypt(key, initVector,
                encrypt(key, initVector, "Sample2,Sample2,349839")));
    }
}