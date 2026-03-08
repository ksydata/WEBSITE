package util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 비밀번호 SHA-256 단방향 해시 유틸리티 클래스
 *
 * 사용 목적:
 *  - 회원가입 시 비밀번호 평문을 해시하여 DB에 저장
 *  - 로그인/비밀번호 변경 전 검증 시 입력값을 동일하게 해시하여 DB 해시값과 비교
 *
 * 주의:
 *  - SHA-256은 단방향 함수이므로 복호화 불가
 *  - 운영 환경에서는 bcrypt / Argon2 등 salt 기반 알고리즘 도입을 권장
 */
public class PasswordHashUtil {

    private PasswordHashUtil() {
        // 인스턴스화 방지
    }

    /**
     * 평문 비밀번호를 SHA-256으로 해시하여 16진수(hex) 문자열로 반환
     *
     * @param plainPassword 해시할 평문 비밀번호
     * @return SHA-256 해시값 (64자리 16진수 문자열)
     * @throws RuntimeException SHA-256 알고리즘을 사용할 수 없는 경우 (JVM 환경 문제)
     */
    public static String hash(String plainPassword) {
        if (plainPassword == null) {
            throw new IllegalArgumentException("비밀번호가 null 입니다.");
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(plainPassword.getBytes(StandardCharsets.UTF_8));

            // byte[] → hex string 변환
            StringBuilder hexBuilder = new StringBuilder(hashBytes.length * 2);
            for (byte b : hashBytes) {
                hexBuilder.append(String.format("%02x", b));
            }
            return hexBuilder.toString();

        } catch (NoSuchAlgorithmException e) {
            // SHA-256은 Java 표준 알고리즘이므로 정상 환경에서는 발생하지 않음
            throw new RuntimeException("SHA-256 알고리즘을 찾을 수 없습니다.", e);
        }
    }

    /**
     * 입력한 평문 비밀번호와 DB에 저장된 해시값이 일치하는지 검사
     *
     * @param plainPassword  사용자 입력 평문 비밀번호
     * @param hashedPassword DB에 저장된 SHA-256 해시값
     * @return 일치 여부 (true: 일치, false: 불일치)
     */
    public static boolean matches(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }
        return hash(plainPassword).equals(hashedPassword);
    }
}
