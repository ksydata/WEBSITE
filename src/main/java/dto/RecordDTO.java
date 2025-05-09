package dto;

public class RecordDTO {
	// 학사정보 페이지 구성요소: 수강연도, 학기, 단과대, 전공, 전공/교양/필수/선택 구분, 과목명, 평점 알파벳, 평점 숫자, PF 여부, PF 학점, 재수강 여부, 재수강 사유
	int recordID;
	String userID;
	String college;
	String major;
	int academicYear;
	String semester;
	int courseID;
	String courseName;
	String courseType;
	String coursePF;
	int credit;
	boolean passOrFail;
	String grade;
	float gradePoint;
	int retakeYear;
	String retakeSemester;
	int retakeCourseID;
	String enrollmentReason;
	
	public int getRecordID() {
		return recordID;
	}
	public void setRecordID(int recordID) {
		this.recordID = recordID;
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
	public int getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(int academicYear) {
		this.academicYear = academicYear;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public int getCourseID() {
		return courseID;
	}
	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseType() {
		return courseType;
	}
	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}
	public String getCoursePF() {
		return coursePF;
	}
	public void setCoursePF(String coursePF) {
		this.coursePF = coursePF;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public boolean isPassOrFail() {
		return passOrFail;
	}
	public void setPassOrFail(boolean passOrFail) {
		this.passOrFail = passOrFail;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public float getGradePoint() {
		return gradePoint;
	}
	public void setGradePoint(float gradePoint) {
		this.gradePoint = gradePoint;
	}
	public int getRetakeYear() {
		return retakeYear;
	}
	public void setRetakeYear(int retakeYear) {
		this.retakeYear = retakeYear;
	}
	public String getRetakeSemester() {
		return retakeSemester;
	}
	public void setRetakeSemester(String retakeSemester) {
		this.retakeSemester = retakeSemester;
	}
	public int getRetakeCourseID() {
		return retakeCourseID;
	}
	public void setRetakeCourseID(int retakeCourseID) {
		this.retakeCourseID = retakeCourseID;
	}
	public String getEnrollmentReason() {
		return enrollmentReason;
	}
	public void setEnrollmentReason(String enrollmentReason) {
		this.enrollmentReason = enrollmentReason;
	}
	

}
