package dto;

//USER 테이블과 PERSONAL_INFO 테이블 데이터를 담는 사용자 1명에 대한 전송 객체
public class UserInfoDTO {
	// 개인정보 조회/수정 화면 구성요소: 사번, 단과대학, 전공, 입학년도, 상태값, 비밀번호, 이름, 주민등록번호, 개인연락처, 이메일, 주소
	private String userID; // USER table 
	private String userPassword; // USER table 
	private String name; // USER table 
	private String residentNumber; // PERSONAL_INFO table 
	private String phoneNumber; // USER table
	private String officeNumber; // USER table 
	private String email; // USER table 
	private String address; // PERSONAL_INFO table
	
	// 수강생 학사정보 조회 화면 구성요소: 사번, 단과대학, 전공, 상태값
	private String college; // PERSONAL_INFO table
	private String major; // PERSONAL_INFO table
	private String status; // PERSONAL_INFO table
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getResidentNumber() {
		return residentNumber;
	}
	public void setResidentNumber(String residentNumber) {
		this.residentNumber = residentNumber;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getOfficeNumber() {
		return officeNumber;
	}
	public void setOfficeNumber(String officeNumber) {
		this.officeNumber = officeNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
