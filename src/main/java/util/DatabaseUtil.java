package util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.Base64;
import java.util.Properties;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

// JAVA에서 MySQL에 JDBC(Java Database Connectivity)를 통해 연결하는 기능을 제공하는 유틸리티 클래스
// DB 접속 비밀번호는 해시(일방향 암호화)가 아닌, 복호화 가능한 양방향 암호화
public class DatabaseUtil {
	public static Connection getConnection() {
	// public static void main(String[] args) {
	
		try {
			// 설정 파일을 로드하여 DB 정보(테이블명, 사용자 ID 가져오기
			Properties properties = new Properties();
			// 클래스 경로 기준으로 설정 파일 불러오기
			InputStream inputStream = DatabaseUtil.class
					.getClassLoader()
					.getResourceAsStream("config.properties");
            
            // 리소스(프로퍼티) 경로 디버깅
            if (inputStream == null) {
                System.err.println("config.properties 파일을 찾을 수 없습니다.");
                return null;
            }
            properties.load(inputStream);

			String dbURL = properties.getProperty("db.url");
			String dbID = properties.getProperty("db.user");
			String encryptedPW = properties.getProperty("db.password");

			// 환경 변수에서 마스터 키 가져오기
			String masterKey = System.getenv("DB_MASTER_KEY");
			if (masterKey == null || masterKey.isEmpty()) {
				System.err.println("DB_MASTER_KEY 환경 변수가 설정되지 않았습니다.");
				// 로컬 개발 환경을 위한 대체 키 (실제 운영에서는 사용하지 마세요)
				masterKey = "your-default-master-key";
				System.err.println("대체 마스터 키를 사용합니다. (개발용)");
			}

			String dbPW = decrypt(encryptedPW, masterKey);

			// MySQL 드라이버 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");
			// `com.mysql.jdbc.Driver`, `com.mysql.cj.jdbc.Driver`

			// 입력받은 정보로 데이터베이스 연결
			try {
			// try (Connection connection = DriverManager.getConnection(dbURL, dbID, dbPW)) {
				Connection connection = DriverManager.getConnection(dbURL, dbID, dbPW);
				return connection;
			} finally {
			}
		} catch (IOException e) {
			// 설정 파일 읽어오면서 발생한 오류 메시지 출력
			e.printStackTrace();
			System.err.println(e.getMessage());
		} catch (Exception e) {
			// DB 연결 실패 시 오류 메시지 출력
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		// 예외 발생 시 null 반환
		return null;
	}

    private static String decrypt(String encryptedText, String masterKey) throws Exception {
        if (encryptedText == null || !encryptedText.startsWith("ENC(") || !encryptedText.endsWith(")")) {
            throw new IllegalArgumentException("암호화된 문자열 형식이 올바르지 않습니다.");
        }
        String base64Encrypted = encryptedText.substring(4, encryptedText.length() - 1);

        // 마스터 키를 SHA-256으로 해시하여 32바이트 키 생성
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] key = sha.digest(masterKey.getBytes(StandardCharsets.UTF_8));
        key = Arrays.copyOf(key, 32); // 256비트(32바이트) 키로 조정
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");

        // AES/ECB/PKCS5Padding으로 복호화
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(base64Encrypted));

        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}

/*
[사용자로부터 데이터베이스 비밀번호가 화면에 보이지 않도록 입력받기]
Console console = System.console();
char[] dbPWArray = console.readPassword("Enter Enter DB password: ");
String dbPW = new String(dbPWArray);

// 단, JSP에서 비밀번호를 콘솔로 입력받는건 불가능
// Cannot invoke "java.io.Console.readPassword(String, Object[])" because "console" is null
// java.lang.NullPointerException: Cannot invoke "java.sql.Connection.prepareStatement(String)" because "connection" is null

[대체 코드]
Scanner scanner = new Scanner(System.in) // 사용자로부터 데이터베이스 테이블명을 입력받음
System.out.print("Enter DB table name: "); String dbURL = scanner.nextLine();

// 사용자로부터 데이터베이스 사용자 ID를 입력받음
System.out.print("Enter DB id: "); String dbID = scanner.nextLine();
 */