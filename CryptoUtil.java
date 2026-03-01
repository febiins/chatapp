import java.util.*;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class CryptoUtil {
    private static final String KEY = "1234567890123456";

    public static String encrypt(String msg){
        try {
            SecretKeySpec key = new SecretKeySpec(KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE,key);
            byte[] encryptedBytes=cipher.doFinal(msg.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    public static String decrypt(String encryptedBytes){
        try {
            SecretKeySpec key = new SecretKeySpec(KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE,key);
            byte[] decodedBytes =Base64.getDecoder().decode(encryptedBytes);
            byte[] decryptedBytes=cipher.doFinal(decodedBytes);
            return new String(decryptedBytes);
                
        } catch (Exception e) {
            
            System.out.println(e);
            return null;
        }
        
    }
}
