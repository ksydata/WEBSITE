package util;

public class MaskingUtil {
	// 마스킹 메서드
	public static String maskPhoneNumber(String phoneNumber) {			
		if (phoneNumber != null && phoneNumber.length() >= 4) {
			String maskedPhoneNumber = phoneNumber
					.replaceAll("(\\d{3}-\\d{4})-\\d{4}", "$1-****");
	        // 전화번호 뒷 4자리 마스킹 (예: 010-1234-****)
	        // (\\d{3}): 연속된 전화번호 앞 3자리 숫자
	        // (\\d{4}): 연속된 전화번호 가운데, 끝 4자리 숫자
			
			return maskedPhoneNumber;
		} else {
			return null;
		}
	}
		
	public static String maskResidentNumber(String residentNumber) {			
		if (residentNumber != null && residentNumber.length() >= 7) {
			String maskedResidentNumber = residentNumber
					.replaceAll("(\\d{6})-([1-4]{1})([0-9]{6})", "$1-$2******");
            // 주민등록번호 뒷 6자리 마스킹 (예: 010101-1******)
            // (\\d{6}): 연속된 주민등록번호 앞 6자리 숫자
            // ([1-4]{1}): 주민번호 뒷자리 중 맨 앞자리 1~4 중 하나의 숫자
            // ([0-9]{6}): 마스킹할 주민등록번호 뒷자리 7자리 중 끝 6자리
            // String maskedResidentNumber = resident.substring(0, 9) + "******";
			
			return maskedResidentNumber;
		} else {
			return null;
		}
	}
}