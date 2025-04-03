package main.java.user;

// Data Transport Object: getter method
public class UserDTO {
	// 로그인 페이지 인증 아이디, 비밀번호
	String userID;
	String userPassword;
	
	// 아이디, 비밀번호를 설정하고 불러오는 메서드
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
}

