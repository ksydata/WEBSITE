package util;

import java.io.Console;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncryptDBPassword {
    public static void main(String[] args) {
        Console console = System.console();
        if (console == null) {
            System.err.println("콘솔을 사용할 수 없습니다. 터미널에서 실행해주세요.");
            System.exit(1);
        }

        String masterKey = new String(console.readPassword("Input master-key: "));
        String plainText = new String(console.readPassword("Input plain text to encrypt: "));

        if (masterKey.isEmpty() || plainText.isEmpty()) {
            System.err.println("마스터 키와 평문은 비워둘 수 없습니다.");
            return;
        }

        try {
            // 마스터 키를 SHA-256으로 해시하여 32바이트 키 생성
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] key = sha.digest(masterKey.getBytes(StandardCharsets.UTF_8));
            key = Arrays.copyOf(key, 32); // 256비트(32바이트) 키로 조정
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");

            // AES/ECB/PKCS5Padding으로 암호화
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            // Base64로 인코딩하여 출력
            String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
            System.out.println("db.password=ENC(" + encryptedText + ")");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/*
[실행 방법]
1. EncryptDBPassword.java 
	: 클래스 파일 마우스 우클릭
2. Show in Local Terminal
	: Terminal 선택
3. 컴파일 명령어
	: javac -d . EncryptDBPassword.java
4. 실행 명령어
	: java util.EncryptDBPassword
5. Input master-key & Input plain text to encrypt
*/