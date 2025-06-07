package dto;

public class AdminRecordDTO {

	// 학사정보 조회 화면 구성요소: 학번, 단과대학, 전공, 
 	// {수강연도, 학기, 과목코드, 과목명, 과목유형(전필/전선/교필/교선), PF여부, PF학점, 인정학점(2/3), 학점(성적)}
 	// {재수강연도, 재수강학기, 재수강과목코드, 재수강사유}
	private String userID;
	private String college;
    private String major;
 	private int academicYear;
 	private String semester;
 	private int courseID;
 	private String courseName;
 	private String courseType;
 	private String coursePF;
 	private int credit;
 	private boolean passOrFail;
 	private String grade;
 	private float gradePoint;
 	private int retakeYear;
 	private String retakeSemester;
 	private int retakeCourseID;
 	private String enrollmentReason;
 	
 	// Getter & Setter
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
