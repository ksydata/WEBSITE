package user;

// Data Transport Object: getter/setter method
// CRUD 작업을 만드는 클래스
public class UserDTO {
	// 로그인 페이지 인증 아이디, 비밀번호
	String userID;
	String userPassword;
	// userID를 통해 DB USER 테이블에서 불러올 사용자 유형 (학생/교수/교직원/관리자)
	private String userRole;
	private String userName;	

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

	// 설정한 사용자 유형을 불러오는 getter 메서드
	public String getUserRole() {
		return userRole;
	}

	// 사용자 유형을 설정하는 setter 메서드
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
