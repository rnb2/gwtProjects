package com.rnb2.gwt1.client.utils;

import com.rnb2.gwt1.client.messages.MyMessages;

public abstract class UtilString {
	
	public static boolean isCyrillicCharacter(String value){
		if(value.isEmpty()){
			return false;
		}
		return value.matches(Constants.REGEX_CYR_CHAR);
	}	
	
	/**
	 * ¬озвращает строку сообщени€ дл€ удалени€
	 * @param fullName
	 * @param messages
	 * @return
	 */
	public static String getMessageForDelete(String fullName, MyMessages messages) {
		StringBuilder sb = new StringBuilder();
		sb.append(messages.deleteRecordQuestion());
		sb.append(" ");
		sb.append(fullName);
		sb.append("?");
		return sb.toString();
	}
	
	public static boolean isLatinCharacterLite(String value){
		return value.matches(Constants.REGEX_LATIN_CHAR);
	}
	
	public static boolean isLatinCharacter(String value){
		if(value.isEmpty()){
			return false;
		}
		
		char charAt = value.charAt(0);
		if(Character.isLetter(charAt)){
			return value.matches(Constants.REGEX_LATIN_CHAR_WITH_DOP);
		}
		
		return false;
	}

}
