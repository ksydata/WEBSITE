package util;

import java.io.Console;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * DB 비밀번호 AES-256 양방향 암호화 유틸리티
 * 
 * 비밀번호 보안 구조: javax.crypto.Cipher + AES-256/CBC/PKCS5Padding + 환경변수 조합
 * 
 * 실행 방법 (터미널에서만 실행 가능):
 * 1. EncryptDBPassword.java 가 있는 디렉토리로 이동
 *    (mac) cd /Users/minjoo/codeStudy/FDSStudy/WEBSITE/src/main/java
 * 2. 컴파일
 *    (mac) javac EncryptDBPassword.java
 * 3. 실행
 *    (mac) java util/EncryptDBPassword
 * 4. Input master-key 와 Input plain text to encrypt 를 차례로 입력
 *    → 출력 예시: db.password=ENC(Base64EncodedIV:Base64EncodedCipherText)
 * 5. 출력된 값을 config.properties 의 db.password 에 복사
 * 6. 마스터키는 운영 서버 환경변수 JASYPT_ENCRYPTOR_KEY 에 등록
 *    (mac/linux) export JASYPT_ENCRYPTOR_KEY=마스터키값
 *    (win)       set JASYPT_ENCRYPTOR_KEY=마스터키값
 * 
 * 알고리즘: AES-256/CBC/PKCS5Padding
 *  - IV(초기화 벡터) 16바이트를 매번 랜덤 생성 → 동일 평문도 매번 다른 암호문 출력
 *  - 마스터키 → SHA-256 → 32바이트 AES 키로 변환
 *  - 암호화 결과: Base64(IV) + ":" + Base64(CipherText) 형태로 저장
 */
public class EncryptDBPassword {

	/** AES-256/CBC 알고리즘 명칭 */
	private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

	/**
	 * 마스터키 문자열을 SHA-256으로 해시하여 32바이트 AES 키 스펙 생성
	 *
	 * @param masterKey 사용자가 입력한 마스터키 문자열
	 * @return 32바이트 SecretKeySpec (AES-256 용)
	 */
	public static SecretKeySpec deriveKey(String masterKey) throws Exception {
		MessageDigest sha = MessageDigest.getInstance("SHA-256");
		byte[] keyBytes = sha.digest(masterKey.getBytes(StandardCharsets.UTF_8));
		// SHA-256 결과 32바이트를 그대로 AES-256 키로 사용
		return new SecretKeySpec(keyBytes, "AES");
	}

	/**
	 * 평문을 AES-256/CBC 로 암호화하여 Base64(IV):Base64(CipherText) 형태로 반환
	 *
	 * @param plainText 암호화할 평문
	 * @param masterKey 마스터키
	 * @return "Base64(IV):Base64(CipherText)" 형태의 문자열
	 */
	public static String encrypt(String plainText, String masterKey) throws Exception {
		SecretKeySpec keySpec = deriveKey(masterKey);

		// 16바이트 랜덤 IV 생성
		byte[] iv = new byte[16];
		new java.security.SecureRandom().nextBytes(iv);
		IvParameterSpec ivSpec = new IvParameterSpec(iv);

		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
		byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

		// IV 와 암호문을 각각 Base64 인코딩 후 ':' 로 구분하여 결합
		String ivBase64 = Base64.getEncoder().encodeToString(iv);
		String cipherBase64 = Base64.getEncoder().encodeToString(encryptedBytes);
		return ivBase64 + ":" + cipherBase64;
	}

	/**
	 * "Base64(IV):Base64(CipherText)" 형태의 문자열을 AES-256/CBC 로 복호화하여 평문 반환
	 *
	 * @param encryptedValue "Base64(IV):Base64(CipherText)" 형태의 암호화된 값
	 * @param masterKey      마스터키
	 * @return 복호화된 평문
	 */
	public static String decrypt(String encryptedValue, String masterKey) throws Exception {
		// ':' 기준으로 IV 와 암호문 분리
		String[] parts = encryptedValue.split(":", 2);
		if (parts.length != 2) {
			throw new IllegalArgumentException("암호화된 값의 형식이 올바르지 않습니다. (예상 형식: Base64IV:Base64CipherText)");
		}

		byte[] iv = Base64.getDecoder().decode(parts[0]);
		byte[] cipherBytes = Base64.getDecoder().decode(parts[1]);

		SecretKeySpec keySpec = deriveKey(masterKey);
		IvParameterSpec ivSpec = new IvParameterSpec(iv);

		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
		byte[] decryptedBytes = cipher.doFinal(cipherBytes);

		return new String(decryptedBytes, StandardCharsets.UTF_8);
	}

	/**
	 * 터미널 전용 실행 진입점
	 * IDE 콘솔에서는 System.console() 이 null 을 반환하므로 반드시 터미널에서 실행
	 */
	public static void main(String[] args) {
		Console console = System.console();
		// IDE 환경의 콘솔을 사용할 수 없는 경우 종료 (터미널에서만 실행 가능)
		if (console == null) {
			System.err.println("[ERROR] 터미널(Terminal/CMD)에서 실행해 주세요. IDE 콘솔에서는 실행할 수 없습니다.");
			System.exit(1);
		}

		// 마스터키와 암호화할 평문을 입력받음 (화면에 입력값이 보이지 않도록 readPassword 사용)
		String masterKey = new String(console.readPassword("Input master-key: "));
		String plainText = new String(console.readPassword("Input plain text to encrypt: "));

		try {
			String encryptedValue = encrypt(plainText, masterKey);
			System.out.println("db.password=ENC(" + encryptedValue + ")");
			System.out.println();
			System.out.println("[안내] 위 출력값을 config.properties 의 db.password 항목에 복사하세요.");
			System.out.println("[안내] 마스터키는 환경변수 JASYPT_ENCRYPTOR_KEY 에 등록하세요.");
		} catch (Exception e) {
			System.err.println("[ERROR] 암호화 중 오류가 발생했습니다: " + e.getMessage());
			e.printStackTrace();
		}

		// 민감 데이터 메모리에서 즉시 제거
		Arrays.fill(masterKey.toCharArray(), '\0');
		Arrays.fill(plainText.toCharArray(), '\0');
	}
}