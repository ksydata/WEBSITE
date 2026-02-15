package util;

// import java.lang.Enum;
// @https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%97%B4%EA%B1%B0%ED%98%95Enum-%ED%83%80%EC%9E%85-%EB%AC%B8%EB%B2%95-%ED%99%9C%EC%9A%A9-%EC%A0%95%EB%A6%AC

// [TO-BE] ENUMERATION 매핑 클래스
public enum RoleEnum {
	ROLE_001("student"),
	ROLE_002("professor"),
	ROLE_003("employee"),
	ROLE_004("admin");
	// enum 상수 하나당 자신의 인스턴스를 하나씩 만들어 public static final 필드로 공개
	
	private String role;
	// 각 역할 문자열을 저장할 필드
	
	private RoleEnum(String role) {
	// 생성자(싱글톤)
		this.role = role;
		// Implicit super constructor Enum(String, int) is undefined. Must explicitly invoke another constructor
	}
	
	public String getRole() {
	// enum 상수 객체를 반환하는 getter 메서드
		return role;		
	}
	
	// Servlet에서 사용할 역할 검증 메서드
	public boolean isROLE_001() {
		return this == ROLE_001;
	}
	public boolean isROLE_002() {
		return this == ROLE_002;
	}
	public boolean isROLE_003() {
		return this == ROLE_003;
	}
	public boolean isROLE_004() {
		return this == ROLE_004;
	}
}



/* [AS-IS]
public final class Constants{
	// 공통 상수를 선언 [하드코딩 방지]
	public static final String ROLE_001 = "student";
	public static final String ROLE_002 = "professor";
	public static final String ROLE_003 = "employee";
	public static final String ROLE_004 = "admin";
}
*/