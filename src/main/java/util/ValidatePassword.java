package util;

import java.util.Arrays;

import org.passay.EnglishSequenceData;
import org.passay.IllegalRegexRule;
import org.passay.IllegalSequenceRule;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

// 비밀번호가 각 메서드별로 조건을 통과하면 True, 통과 불가하면 False를 return 하도록 만들기
public class ValidatePassword {
	/**
     * 정규식으로 비밀번호 정책 검증
     * - 8자리 이상
     * - 대문자, 소문자, 숫자, 특수문자 중 3가지 이상 조합
     * - 조건을 모두 충족시키는 경우 True, 아닌 경우 False를 return하도록 함
     */
    public static boolean isValidByRegex(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        // 요건 3개를 달아놓고, 요건 하나 당 count를 1씩 더해서 모든 요건 달성, 즉 count가 3 이상이어야 true를 return하게 함
        int count = 0;
        if (password.matches(".*[A-Z].*")) count++;        // 대문자 포함
        if (password.matches(".*[a-z].*")) count++;        // 소문자 포함
        if (password.matches(".*[0-9].*")) count++;        // 숫자 포함
        if (password.matches(".*[!@#$%^&*(),.?\":{}|<>\\[\\]\\\\/`~_+=-].*")) count++; // 특수문자 포함

        return count >= 3;
    }
	
    // 비밀번호에 아이디가 포함되어 있는지 검사하는 유틸 메서드
    public static boolean isValidById(String userID, String password) {
        if (userID == null || password == null) return false;

        // 모두 소문자로 변환하여 대소문자 구분 없이 비교
        String lowerUsername = userID.toLowerCase();
        String lowerPassword = password.toLowerCase();

        // 비밀번호에 아이디가 포함되어 있으면 실패
        if (lowerPassword.contains(lowerUsername)) {
            return false;
        }
        return true;
    }
    
    /**
     * 비밀번호에 이메일의 로컬파트(아이디 부분)나 도메인명(회사명 등)이 포함되어 있는지 검사하는 유틸 메서드
     * 
     * 예: 이메일이 a30001@univ.com 일 때
     *  - "a30001" 또는 "univ" 가 비밀번호에 포함되어 있으면 false 반환
     */
    public static boolean isValidByEmail(String email, String password) {
        if (email == null || password == null) return false;

        // 모두 소문자로 변환해 대소문자 구분 없이 비교
        String lowerEmail = email.toLowerCase();
        String lowerPassword = password.toLowerCase();

        // 이메일 구조 분석
        int atIndex = lowerEmail.indexOf("@");
        int dotIndex = lowerEmail.lastIndexOf(".");

        if (atIndex == -1 || dotIndex == -1 || dotIndex < atIndex) {
            // 이메일 형식이 아니면 검증 불가 → true 반환 (별도 정책에 따라 조정 가능)
            return true;
        }

        // 로컬파트 (예: a30001)
        String localPart = lowerEmail.substring(0, atIndex);

        // 도메인명 (예: univ)
        String domain = lowerEmail.substring(atIndex + 1, dotIndex);

        // 비밀번호가 로컬파트나 도메인명을 포함하면 실패
        if (lowerPassword.contains(localPart) || lowerPassword.contains(domain)) {
            return false;
        }

        return true;
    }
    
    /**
     * 비밀번호에 전화번호의 중간번호나 끝번호가 포함되어 있는지 검사하는 유틸 메서드
     * 
     * 예: 전화번호가 010-1234-5678 이라면
     *  - "1234" 또는 "5678" 이 비밀번호에 포함되어 있으면 false 반환
     */
    public static boolean isValidByPhone(String phone, String password) {
        if (phone == null || password == null) return false;

        // 모두 소문자로 변환 (숫자만 있더라도 일관성 유지)
        String lowerPassword = password.toLowerCase();

        // 숫자만 추출 (010-1234-5678 → 01012345678)
        String digitsOnly = phone.replaceAll("[^0-9]", "");

        // 전화번호 형식이 정상인지 확인 (길이가 너무 짧으면 true 반환)
        if (digitsOnly.length() < 8) {
            // 형식이 이상하면 검증 불가 → true (정책에 따라 조정 가능)
            return true;
        }

        // 중간번호와 끝번호 추출
        // 01012345678 → middle = "1234", last = "5678"
        String middle = "";
        String last = "";

        if (digitsOnly.length() >= 11) {
            // 일반적인 한국 휴대폰 번호 (11자리)
            middle = digitsOnly.substring(3, 7);
            last = digitsOnly.substring(7);
        } else if (digitsOnly.length() == 10) {
            // 지역번호 포함 일반 전화번호 (10자리)
            middle = digitsOnly.substring(3, 6);
            last = digitsOnly.substring(6);
        }

        // 비밀번호에 중간번호나 끝번호가 포함되어 있으면 실패
        if (lowerPassword.contains(middle) || lowerPassword.contains(last)) {
            return false;
        }

        return true;
    }
    
    /**
     * 비밀번호에 주민등록번호 앞자리(생년월일)의 일부가 포함되어 있는지 검사하는 메서드
     * 
     * 예: 주민등록번호가 990501-7059641 일 때
     *  - "9905" 또는 "0501" 이 비밀번호에 포함되어 있으면 false 반환
     */
    public static boolean isValidByBirthdate(String rrn, String password) {
        if (rrn == null || password == null) return false;

        // 모두 소문자로 변환해 대소문자 구분 없이 비교
        String lowerPassword = password.toLowerCase();

        // 주민등록번호에서 숫자만 추출
        String digitsOnly = rrn.replaceAll("[^0-9]", "");

        // 최소 6자리(생년월일) 이상이어야 함
        if (digitsOnly.length() < 6) {
            // 형식이 올바르지 않으면 true 반환 (검증 불가)
            return true;
        }

        // 앞 6자리 (예: 990501)
        String birth = digitsOnly.substring(0, 6);

        // 앞 4자리(연월)와 뒤 4자리(월일) 추출
        String first4 = birth.substring(0, 4);  // 9905
        String last4 = birth.substring(2, 6);   // 0501

        // 비밀번호에 생년월일 일부가 포함되어 있으면 실패
        if (lowerPassword.contains(first4) || lowerPassword.contains(last4)) {
            return false;
        }

        return true;
    }
    
    /**
     * Passay를 이용한 유추하기 쉬운 비밀번호 방지 (샘플 코드)
     * 
     * 목표
     * - 패스워드가 사용자의 다른 속성값(아이디, 이메일, 전화번호, 생년월일)와의 유사도 확인
     * - 아이디: USER 테이블의 userID / 이메일: USER 테이블의 email / 전화번호: USER 테이블의 phoneNumber / 생년월일: PERSONAL_INFO 테이블의 birthDate
     * - 0000, 1234, password 등 사람들이 가장 많이 사용하는 패스워드 20,000개에 해당하는지 확인
     * 
     */
    public static boolean isValidByPassay(String password, String username) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                // 공통 규칙
                new LengthRule(8, 64),
                new IllegalSequenceRule(EnglishSequenceData.Numerical, 4, false),  // 1234 등
                new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 4, false), // abcd 등
                new IllegalRegexRule("(?i).*password.*"), // "password" 금지 (대소문자 무시)
                new IllegalRegexRule("(?i).*qwerty.*"),   // 키보드 패턴 금지
                new IllegalRegexRule(".*(0000|1111|2222|3333).*"), // 반복 숫자
                new WhitespaceRule() // 공백 금지
        ));

        RuleResult result = validator.validate(new PasswordData(password));

        // username이 포함되어 있으면 금지
        if (username != null && !username.isEmpty() && password.toLowerCase().contains(username.toLowerCase())) {
            return false;
        }

        return result.isValid();
    }
	
}
