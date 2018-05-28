package com.core;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Security {
	
	
	public static final String DEFAULT_ENCODING="UTF-8"; 
	private static final String ALGORITHM = "AES";
    private static final byte[] keyValue = "Un179s&d$%[]huac".getBytes();
	
	static BASE64Encoder enc=new BASE64Encoder();
	static BASE64Decoder dec=new BASE64Decoder();

	
	public Security() {
		// TODO Auto-generated constructor stub
	}
	
	

	 public static String base64encode(String text){
	      try {
	         String rez = enc.encode( text.getBytes( DEFAULT_ENCODING ) );
	         return rez;         
	      }
	      catch ( UnsupportedEncodingException e ) {
	         return null;
	      }
	   }//base64encode

	   
	 public static String base64decode(String text){

	         try {
	            return new String(dec.decodeBuffer( text ),DEFAULT_ENCODING);
	         }
	         catch ( IOException e ) {
	           return null;
	         }

	      }//base64decode
	   
	   
	   
	   public static String xorMessage(String message, String key){
	       try {
	          if (message==null || key==null ) return null;

	         char[] keys=key.toCharArray();
	         char[] mesg=message.toCharArray();

	         int ml=mesg.length;
	         int kl=keys.length;
	         char[] newmsg=new char[ml];

	         for (int i=0; i<ml; i++){
	            newmsg[i]=(char)(mesg[i]^keys[i%kl]);
	         }//for i
	         mesg=null; keys=null;
	         return new String(newmsg);
	      }
	      catch ( Exception e ) {
	         return null;
	       }  
	      }//xorMessage
	   
	   
	   
	   public static String encrypt(String valueToEnc) throws Exception {
		   /*
		            Key key = generateKey();
		            Cipher c = Cipher.getInstance(ALGORITHM);
		            c.init(Cipher.ENCRYPT_MODE, key);
		            
		            byte[] byteDataToEncrypt = valueToEnc.getBytes();
		            
		            byte[] byteCipherText = c.doFinal(byteDataToEncrypt);
		            
		        
		            String cipherText = new BASE64Encoder().encode(byteCipherText);

		            return cipherText;
		            */
		        return base64encode(valueToEnc);
		        }
		       
		       
		       
		       
		       public static String decrypt(String encryptedValue) throws Exception {
		            /*
		         Key key = generateKey();
		            Cipher c = Cipher.getInstance(ALGORITHM);
		            c.init(Cipher.DECRYPT_MODE, key,c.getParameters());
		            
		            
		            byte[] byteCipherText = new BASE64Decoder(). decodeBuffer(encryptedValue); 
		            byte[] byteDecryptedText = c.doFinal(byteCipherText);
		            
		            String decryptedText = new String(byteDecryptedText);
		            
		            return decryptedText;
		            */
		            return base64decode(encryptedValue);
		        }
	   
	   
	   
	   private static Key generateKey() throws Exception {
	        Key key = new SecretKeySpec(keyValue, ALGORITHM);
	        return key;
	    }
	   

}
