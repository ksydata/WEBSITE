package test;

import java.sql.Connection;
import util.DatabaseUtil;

public class DBConnectionTest {
    public static void main(String[] args) {
        System.out.println("데이터베이스 연결 테스트를 시작합니다...");
        try {
            Connection conn = DatabaseUtil.getConnection();
            if (conn != null) {
                System.out.println("데이터베이스 연결 성공!");
                System.out.println("연결 객체: " + conn);
                conn.close();
            } else {
                System.err.println("데이터베이스 연결 실패. getConnection()이 null을 반환했습니다.");
            }
        } catch (Exception e) {
            System.err.println("데이터베이스 연결 중 예외가 발생했습니다.");
            e.printStackTrace();
        }
    }
}
