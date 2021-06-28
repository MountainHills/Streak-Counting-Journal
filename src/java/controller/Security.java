package controller;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.*;

public class Security {

	private static byte[] key = {
	 	0x74, 0x68, 0x69, 0x73, 0x49, 0x73, 0x41, 0x53, 
                0x65, 0x63, 0x72, 0x65, 0x74, 0x4b, 0x65, 0x79
	};
    
	public static String encrypt(String strToEncrypt) 
        {
            String encryptedString = null;
            try 
            {
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		encryptedString = Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes()));
            }   
            catch (Exception e) 
            {
		System.err.println(e.getMessage());
            }
            return encryptedString;
	}
        
    public static void main(String[] args) 
    {
        String sample = "admin123";
        
        sample = encrypt(sample);
        
        System.out.println(sample);
    }
}
