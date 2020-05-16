package chat_client;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Cryptography {
    private String text;  
    private final String encKeyString;
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    public Cryptography(String text) {
        this.text = text;
        encKeyString = "1234567890123456";
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    /*public String encode() 
        throws InvalidKeyException, UnsupportedEncodingException, 
            IllegalBlockSizeException, BadPaddingException, 
            NoSuchAlgorithmException, NoSuchPaddingException{
      
      KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
      keyPairGen.initialize(2048);
      KeyPair pair = keyPairGen.generateKeyPair();
      Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
      cipher.init(Cipher.ENCRYPT_MODE, pair.getPublic());
      byte[] input = getText().getBytes();	  
      cipher.update(input);
      return new String(cipher.doFinal(), "UTF8");
    } 
    
    public String decode() 
            throws NoSuchAlgorithmException, NoSuchPaddingException, 
                InvalidKeyException, IllegalBlockSizeException, 
                BadPaddingException {
        String output;
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(2048);
            KeyPair pair = keyPairGen.generateKeyPair();
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, pair.getPrivate());
            cipher.update(getText().getBytes());
            return new String(cipher.doFinal(), "UTF8");
        } catch (Exception e) {
            output = e.getMessage();
        }
      return output;
    } */
    public String encode() throws InvalidKeyException, NoSuchPaddingException,
        NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, 
        UnsupportedEncodingException {
            byte[] decodedKey = Base64.getDecoder().decode(encKeyString);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            SecretKey secretKey = new SecretKeySpec(Arrays.copyOf(decodedKey, 16), "AES");//encKeyString.getBytes(), ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedMessage = cipher.doFinal(getText().getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encryptedMessage);
        }

    public String decode() throws NoSuchPaddingException,
        NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String output;
        try {
            byte[] decodedKey = Base64.getDecoder().decode(encKeyString);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            SecretKey secretKey = new SecretKeySpec(Arrays.copyOf(decodedKey, 16), "AES");//encKeyString.getBytes(), ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] cipherText = cipher.doFinal(Base64.getDecoder().decode(getText()));
            return new String(cipherText);
        } catch (Exception e) {
            output = e.getMessage();
        }
        return output;
    }
}
