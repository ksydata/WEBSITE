package dto;

// import java.sql.Time;

// USER 테이블과 PERSONAL_INFO 테이블 데이터를 담는 학생 1명의 정보에 대한 전송 객체
public class StudentDTO {
	// 화면 구성요소: 학번, 단과대학, 전공, 입학년도, 상태값, 비밀번호, 이름, 주민등록번호, 개인연락처, 이메일, 주소
	private String userID; // USER table 

	private String college; // PERSONAL_INFO table 
	private String major; // PERSONAL_INFO table 
	private int admissionYear; // PERSONAL_INFO table 
	private String status; // PERSONAL_INFO table 

	private String userPassword; // USER table 
	private String name; // USER table 
	private String residentNumber; // PERSONAL_INFO table 
	private String phoneNumber; // USER table 
	private String email; // USER table 
	private String address; // PERSONAL_INFO table 
	// getter/setter는 public이어야 jsp, Service, DAO 등 다른 클래스에서 자유롭게 값을 읽고 수정 가능
	
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
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
	public int getAdmissionYear() {
		return admissionYear;
	}
	public void setAdmissionYear(int admissionYear) {
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