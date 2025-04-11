import java.sql.Connection;
import java.util.Properties;
import java.io.InputStream;
import org.jasypt.util.text.BasicTextEncryptor;
import java.sql.DriverManager;

public class DBConnectionTester {
    public static void main(String[] args) {
        try {
            // 설정 파일 로딩
            Properties properties = new Properties();
            InputStream inputStream = DBConnectionTester.class
                    .getClassLoader()
                    .getResourceAsStream("config.properties");
            properties.load(inputStream);

            // 설정 정보 가져오기
            String dbURL = properties.getProperty("db.url");
            String dbID = properties.getProperty("db.user");
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

            // MySQL 드라이버 로딩
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
