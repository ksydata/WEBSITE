package test;
import org.jasypt.util.text.BasicTextEncryptor;

public class TestDecrypt {
    public static void main(String[] args) {
        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        encryptor.setPassword("JASYPT_ENCRYPTOR_KEY");
        
        String encrypted = "xWcd0DPKjVPvbCBUzVF0pQ==";
        System.out.println("복호화 시도: " + encrypted);
        
        try {
            String decrypted = encryptor.decrypt(encrypted);
            System.out.println("복호화 성공: " + decrypted);
        } catch (Exception e) {
            System.out.println("복호화 실패: " + e.getMessage());
        }
    }
}

// javac -d . -cp ".;C:\Users\sooyeon Kang\.m2\repository\org\jasypt\jasypt\1.9.3\jasypt-1.9.3.jar" TestDecrypt.java  
// java -cp ".;C:\Users\sooyeon Kang\.m2\repository\org\jasypt\jasypt\1.9.3\jasypt-1.9.3.jar" test.TestDecrypt