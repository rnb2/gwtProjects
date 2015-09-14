package com.rnb2.gwt1.client;


public class TestRegx {

	public static final String REGEX_LATIN_CHAR_WITH_DOP = "[A-Za-z-.]+";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		String currentValue = "prikhodko-sn";
		String currentValue2 = "prikhodko-";
		String currentValue3 = "prikhodko";
		String currentValue4 = "-";
		
		if(isLatinCharacter(currentValue)){
			System.out.println("11111111111111");
		}else{
			System.out.println("2222222222222");
		}
		
	}
	
	public static boolean isLatinCharacter(String value){
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
