package test;

import java.sql.Connection;
import java.util.Properties;
import java.io.InputStream;
import java.net.URL;
// import org.jasypt.util.text.BasicTextEncryptor;
import java.sql.DriverManager;

public class DBConnectionTester {
    public static void main(String[] args) {
        try {
            // 설정 파일 로딩
            Properties properties = new Properties();
            URL url = DBConnectionTester.class
            		.getClassLoader().getResource("config.properties");
            if (url == null) System.err.println("config.properties 파일을 찾을 수 없습니다.");
            
            System.out.println("실제 로딩된 config.properties 경로는 " + url + "입니다.");
            InputStream inputStream = DBConnectionTester.class
                    .getClassLoader()
                    .getResourceAsStream("config.properties");
            
            // 리소스(프로퍼티) 경로 디버깅
            if (inputStream == null) {
                System.err.println("config.properties 파일을 찾을 수 없습니다.");
            } else {
                System.out.println("config.properties 파일을 성공적으로 로드했습니다.");
            }
            properties.load(inputStream);

            // 설정 정보 가져오기(공백 제거)
            String dbURL = properties.getProperty("db.url").trim();
            String dbID = properties.getProperty("db.user").trim();
            String dbPW = properties.getProperty("db.password").trim();
            
            /*
            String encryptedPW = properties.getProperty("db.password");
            // 복호화 키 환경변수에서 가져오기
            String encryptKey = System.getenv("JASYPT_ENCRYPTOR_KEY");
            if (encryptKey == null) {
                System.err.println("환경변수 JASYPT_ENCRYPTOR_KEY가 설정되지 않음");
                return;
            }

            // 비밀번호 복호화
            BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
            textEncryptor.setPassword(encryptKey);
            String dbPW = textEncryptor.decrypt(
                    encryptedPW.replace("ENC(", "").replace(")", ""));
			*/
            
            // MySQL 드라이버 로딩
            System.out.println("🔍 DB URL: " + dbURL);
            System.out.println("🔍 DB User: " + dbID);
            System.out.println("🔍 DB PW: " + dbPW);
            Class.forName("com.mysql.cj.jdbc.Driver");

            // DB 연결 시도
            Connection connection = DriverManager.getConnection(dbURL, dbID, dbPW);

            if (connection != null) {
                System.out.println("DB 연결 성공");
                connection.close();
            } else {
                System.out.println("DB 연결 실패 (connection is null)");
            }

        } catch (Exception e) {
            System.err.println("예외 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

/*
[실행 방법]
1. DBConnectionTester.java 
	: 테스트 클래스 파일 마우스 우클릭
2. Show in Local Terminal
	: Terminal 선택
3. 컴파일 명령어
cd C:\WEBSITE\WEBSITE
javac -d bin -cp ".;C:\Users\sooyeon Kang\.m2\repository\org\jasypt\jasypt\1.9.3\jasypt-1.9.3.jar" src/test/java/test/DBConnectionTester.java
4. 실행 명령어
java -cp "bin;src/test/resources;C:\Users\sooyeon Kang\.m2\repository\org\jasypt\jasypt\1.9.3\jasypt-1.9.3.jar;C:\Users\sooyeon Kang\.m2\repository\mysql\mysql-connector-java\8.0.33\mysql-connector-java-8.0.33.jar" test.DBConnectionTester

[에러 메시지]
config.properties 파일을 성공적으로 로드했습니다.
// EncryptionOperationNotPossibleException 문제 해결
예외 발생: com.mysql.cj.jdbc.Driver
// MySQL 드라이버가 클래스패스에 없음
java.lang.ClassNotFoundException: com.mysql.cj.jdbc.Driver
        at java.base/jdk.internal.loader.BuiltinClassLoader.loadClass(BuiltinCla
ssLoader.java:641)
        at java.base/jdk.internal.loader.ClassLoaders$AppClassLoader.loadClass(ClassLoaders.java:188)
        at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:526)      
        at java.base/java.lang.Class.forName0(Native Method)
        at java.base/java.lang.Class.forName(Class.java:421)
        at java.base/java.lang.Class.forName(Class.java:412)
        at test.DBConnectionTester.main(DBConnectionTester.java:47)
        
[디렉터리 구조를 출력하는 명령어] tree /f bin
Folder PATH listing
Volume serial number is 000000E6 8C88:2BD0
C:\WEBSITE\WEBSITE\SRC\TEST\JAVA\TEST\BIN
Invalid path - \WEBSITE\WEBSITE\SRC\TEST\JAVA\TEST\BIN
No subfolders exist        
*/