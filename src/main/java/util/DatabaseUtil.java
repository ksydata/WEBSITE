package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * MySQL DB 연결 유틸리티 클래스 (JDBC)
 *
 * DB 접속 비밀번호 보안 구조:
 *  - EncryptDBPassword 로 AES-256/CBC/PKCS5Padding 암호화 후 config.properties 에 ENC(...) 형태로 저장
 *  - 마스터키는 환경변수 JASYPT_ENCRYPTOR_KEY 에서 읽어 복호화
 *  - 암호화/복호화 핵심 로직은 EncryptDBPassword 의 정적 메서드(encrypt/decrypt/deriveKey) 활용
 */
public class DatabaseUtil {

	public static Connection getConnection() {
		try {
			// config.properties 로드
			Properties properties = new Properties();
			InputStream inputStream = DatabaseUtil.class
					.getClassLoader()
					.getResourceAsStream("config.properties");

			if (inputStream == null) {
				System.err.println("[ERROR] config.properties 파일을 찾을 수 없습니다.");
				return null;
			} else {
				System.out.println("[INFO] config.properties 파일을 성공적으로 로드했습니다.");
			}
			properties.load(inputStream);

			String dbURL = properties.getProperty("db.url");
			String dbID  = properties.getProperty("db.user");

			// config.properties 에서 암호화된 비밀번호 읽기 (ENC(...) 형태)
			String encryptedPW = properties.getProperty("db.password");

			// ENC( ... ) 래퍼 제거 후 "Base64IV:Base64CipherText" 추출
			String encryptedValue = encryptedPW
					.replace("ENC(", "")
					.replace(")", "")
					.trim();

			// 환경변수에서 마스터키 읽기
			String masterKey = System.getenv("JASYPT_ENCRYPTOR_KEY");
			System.out.println("[INFO] 환경변수 JASYPT_ENCRYPTOR_KEY: " + (masterKey != null ? "[SET]" : "[NOT SET]"));
			if (masterKey == null || masterKey.isEmpty()) {
				System.err.println("[ERROR] 환경변수 JASYPT_ENCRYPTOR_KEY 가 설정되지 않았습니다.");
				return null;
			}
			

			// AES-256/CBC 복호화 (EncryptDBPassword 의 정적 메서드 활용)
			String dbPW = EncryptDBPassword.decrypt(encryptedValue, masterKey);
			
		
			// MySQL 드라이버 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");

			// DB 연결
			Connection connection = DriverManager.getConnection(dbURL, dbID, dbPW);
			return connection;

		} catch (IOException e) {
			System.err.println("[ERROR] 설정 파일 읽기 오류: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("[ERROR] DB 연결 실패: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}