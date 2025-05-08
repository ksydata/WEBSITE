package dto;

import java.sql.Time;

public class StudentDTO {
	// 화면 구성요소: 학번, 단과대학, 전공, 입학년도, 상태값, 비밀번호, 이름, 주민등록번호, 개인연락처, 이메일, 주소
	private String userID; // USER table 

	private String college; // PERSONAL_INFO table 
	private String major; // PERSONAL_INFO table 
	private Time admissionYear; // PERSONAL_INFO table 
	private String status; // PERSONAL_INFO table 

	private String userPaswword; // USER table 
	private String name; // USER table 
	private String residentNumber; // USER table 
	private String phoneNumber; // USER table 
	private String email; // USER table 
	
	private String address; // PERSONAL_INFO table 

	
	private String getUserPaswword() {
		return userPaswword;
	}
	private void setUserPaswword(String userPaswword) {
		this.userPaswword = userPaswword;
	}
	private String getResidentNumber() {
		return residentNumber;
	}
	private void setResidentNumber(String residentNumber) {
		this.residentNumber = residentNumber;
	}
	private String getPhoneNumber() {
		return phoneNumber;
	}
	private void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
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
	public Time getAdmissionYear() {
		return admissionYear;
	}
	public void setAdmissionYear(Time admissionYear) {
		this.admissionYear = admissionYear;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
}

/*
Alt + Shift + S를 누르고, Generate Getter and Setter
[public] 외부 클래스 자유롭게 접근 가능
[protected] 같은 패키지나 자식 클래스 접근 가능
[private] 선언된 클래스 내에서만 접근 가능
[default: package-private] 같은 패키지 내의 클래스에서만 접근 가능
 */