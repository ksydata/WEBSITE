package user;

// Data Transport Object: getter/setter method
// CRUD 작업을 만드는 클래스
public class UserDTO {
	// 로그인 페이지 인증 아이디, 비밀번호
	String userID;
	String userPassword;

	// 설정한 아이디를 불러오는 getter 메서드
	public String getUserID() {
		return userID;
	}

	// 아이디를 설정하는 setter 메서드
	public void setUserID(String userID) {
		this.userID = userID;
	}

	// 설정한 비밀번호를 불러오는 getter 메서드
	public String getUserPassword() {
		return userPassword;
	}

	// 비밀번호를 설정하는 setter 메서드
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
}
