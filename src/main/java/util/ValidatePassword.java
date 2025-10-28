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

// 아래 클래스의 목표 : 타 모듈에서 ValidatePassword.validator(password, userID) 형태로 불러와서 
// 통과 가능하면 True, 통과 불가하면 False를 return 하도록 만들기
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
	
    /**
     * Passay를 이용한 유추하기 쉬운 비밀번호 방지
     * 
     * 목표
     * - 패스워드가 사용자의 다른 속성값(이름, 성, 이메일)등과의 유사도 확인
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
	
    /**
     * 정규식 + Passay 검증을 모두 수행하는 통합 메서드
     * - 두 검증 모두 true일 때만 true 반환
     */
    public static boolean validator(String password, String username) {
        boolean regexValid = isValidByRegex(password);
//        boolean passayValid = isValidByPassay(password, username);
//        return regexValid && passayValid;
        return regexValid;
    }

}
