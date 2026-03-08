package test;

import user.UserDAO;
import user.UserDTO;
import util.RoleEnum;

public class LoginTest {
    public static void main(String[] args) {
        System.out.println("로그인 테스트를 시작합니다...");
        UserDAO userDAO = new UserDAO();
        
        // 여기에 실제 테스트할 사용자 ID와 비밀번호를 입력하세요.
        String testUserID = "20201234"; // 테스트용 사용자 ID
        String testPassword = "password123!"; // 테스트용 사용자 비밀번호

        System.out.println("테스트 계정 정보: ");
        System.out.println("  - ID: " + testUserID);
        System.out.println("  - PW: " + testPassword);

        try {
            UserDTO user = userDAO.login(testUserID, testPassword);

            if (user != null) {
                System.out.println("\n[로그인 성공]");
                System.out.println("  - 사용자 이름: " + user.getUserName());
                System.out.println("  - 사용자 권한: " + user.getUserRole().getRole());
            } else {
                System.err.println("\n[로그인 실패]");
                System.err.println("  - 아이디 또는 비밀번호가 올바르지 않거나, 데이터베이스 오류가 발생했습니다.");
            }
        } catch (Exception e) {
            System.err.println("\n로그인 테스트 중 예외가 발생했습니다.");
            e.printStackTrace();
        }
    }
}
