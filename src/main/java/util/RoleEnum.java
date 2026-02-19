package util;

// import java.lang.Enum;
// @https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%97%B4%EA%B1%B0%ED%98%95Enum-%ED%83%80%EC%9E%85-%EB%AC%B8%EB%B2%95-%ED%99%9C%EC%9A%A9-%EC%A0%95%EB%A6%AC
// @https://jojoldu.tistory.com/137

// [TO-BE] ENUMERATION 매핑 클래스
public enum RoleEnum {
	ROLE_001("student", "학생"),
	ROLE_002("professor", "교수"),
	ROLE_003("employee", "교직원"),
	ROLE_004("admin", "관리자");
	// enum 상수 하나당 자신의 인스턴스를 하나씩 만들어 public static final 필드로 공개
	// DB에 한국어로 저장된 역할 변수와 Enum의 role값이 불일치하여 로그인 후 권한별 페이지 조회 시 오류 발생
	// RoleEnum에 한국어 매핑 추가
	
	private String role;
	private String koreanRole;
	// 각 역할 문자열을 저장할 필드
	
	private RoleEnum(String role, String koreanRole) {
	// 생성자(싱글톤)
		this.role = role;
		this.koreanRole = koreanRole;
		// Implicit super constructor Enum(String, int) is undefined. Must explicitly invoke another constructor
	}
	
	public String getRole() { return role; }
	public String getKoreanRole() { return koreanRole; }
	// enum 상수 객체를 반환하는 getter 메서드
	
	public static RoleEnum fromKorean(String role) {
	// [AS-IS] DB에서 받아온 한국어 role 문자열로 enum을 찾는 팩토리 메서드 
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