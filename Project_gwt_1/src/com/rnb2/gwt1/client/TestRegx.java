package com.rnb2.gwt1.client;


public class TestRegx {

	public static final String REGEX_LATIN_CHAR_WITH_DOP = "[^a-zA-Z]+";
	public static final String REGEX_CYR_CHAR_WITH = "^\\p{IsCyrillic}+$";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		String currentValue = "pr.ik_hodko.sn";
		String currentValue2 = "prikhodko-";
		String currentValue3 = "врор";
		String currentValue4 = "test_name.pol2";
		
		if(isCyrillicCharacter(currentValue4)){
			System.out.println("11111111111111");
		}else{
			System.out.println("2222222222222");
		}
		
	}
	public static boolean isCyrillicCharacter(String value){
		if(value.isEmpty()){
			return false;
		}
		return value.matches(REGEX_CYR_CHAR_WITH);
	}	
	
	public static boolean isLatinCharacter(String value){
		if(value.isEmpty()){
			return false;
		}
		return value.matches(REGEX_LATIN_CHAR_WITH_DOP);
	}	
	
	public static boolean isLatinCharacter2(String value){
		if(value.isEmpty()){
			return false;
		}
		
		char charAt = value.charAt(0);
		if(Character.isLetter(charAt)){
			return value.matches(REGEX_LATIN_CHAR_WITH_DOP);
		}
		
		return false;
	}

}
