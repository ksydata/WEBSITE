package main;

import java.io.Console;
import org.jasypt.util.text.BasicTextEncryptor;

// 비밀번호 보안 구조: Jasypt 라이브러리 + 환경변수 + 프로파일 분리 조합
public class EncryptDBPassword {
	public static void main(String[] args) {
		Console console = System.console();
		// IDE 환경의 콘솔을 사용할 수 없는 경우로 터미널에서 작업
		if (console == null) {
			System.exit(1);
		}
		
		// 원하는 마스터 키와 암호화할 비밀번호 평문을 입력
		// 단, 보안 목적으로 IDE 콘솔창이 아닌 cmd 터미널(명령 프롬프트)에서 실행될 때만 동작
		String masterKey = new String(console.readPassword("Input master-key"));
		String plainText = new String(console.readPassword("Input plain text to encrypt"));
		
		BasicTextEncryptor encryptor = new BasicTextEncryptor();
		encryptor.setPassword(masterKey);
		String encrytedText = encryptor.encrypt(plainText);
		
        System.out.println("db.password=ENC(" + encrytedText + ")");
	}
}

/*
[실행 방법]
1. EncryptDBPassword.java 
	: 클래스 파일 마우스 우클릭
2. Show in Local Terminal
	: Terminal 선택
3. 컴파일 명령어
	: javac -d . -cp ".;C:\Users\sooyeon Kang\.m2\repository\org\jasypt\jasypt\1.9.3\jasypt-1.9.3.jar" EncryptDBPassword.java
4. 실행 명령어
	: java -cp ".;C:\Users\sooyeon Kang\.m2\repository\org\jasypt\jasypt\1.9.3\jasypt-1.9.3.jar" main.EncryptDBPassword
5. Input master-key & Input plain text to encrypt
	: db.password=ENC(BLIsxSBSJefAMW9U0BI/3g==)
*/

/*
EncryptDBPassword.java:4: error: package org.jasypt.util.text does not exist
import org.jasypt.util.text.BasicTextEncryptor;
                           ^
EncryptDBPassword.java:20: error: cannot find symbol
                BasicTextEncryptor encryptor = new BasicTextEncryptor();
                ^
  symbol:   class BasicTextEncryptor
  location: class EncryptDBPassword
EncryptDBPassword.java:20: error: cannot find symbol
                BasicTextEncryptor encryptor = new BasicTextEncryptor();
                                                   ^
  symbol:   class BasicTextEncryptor
  location: class EncryptDBPassword
3 errors
*/