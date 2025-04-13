package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
//import java.io.Console;
//import java.io.FileInputStream;
// import java.util.Scanner;

import org.jasypt.util.text.BasicTextEncryptor;

import java.sql.Connection;
import java.sql.DriverManager;

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
            } else {
                System.out.println("config.properties 파일을 성공적으로 로드했습니다.");
            }
			properties.load(inputStream);
			// properties.load(new FileInputStream("resources/config.properties"));
			
			String dbURL = properties.getProperty("db.url");
			String dbID = properties.getProperty("db.user");

			// 복호화된 데이터베이스 비밀번호 불러오기
			String encryptedPW = properties.getProperty("db.password");

			// Jasypt 라이브러리로 키값을 활용하여 복호화
			BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
			// 윈도우 시스템 환경변수에 저장된 복호화 키값 불러오기 
			String encryptKey = System.getenv("JASYPT_ENCRYPTOR_KEY");
			textEncryptor.setPassword(encryptKey);
			// 프로퍼티 파일 내 암호화된 비밀번호를 복호화된 키값을 통해 보안 해제 후 문자열로 저장 
			String dbPW = textEncryptor.decrypt(
				    encryptedPW.replace(
				    		"ENC(", "").replace(")", ""));

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