/**
 * 
 */
package com.rnb2.gwt1.client.utils;

import java.util.ArrayList;

/**
 * @author budukh-rn
 *
 */
public abstract class Constants {
	
	public final static String server_name_AD = "Active Directory"; 
	public final static String server_name_jboss = "JBOSS"; 
	public final static String server_name_jboss_01 = "JBOSS01"; 
	public final static String server_name_jboss_5 = "JBOSS5";
	
	public static final String PANEL_MAIN_NORTH = "panelMainNorth";
	public static final String GRID_RAILWAY_GROUP_PROXY = "gridRailwayGroupProxy";
	public static final String REGEX_LATIN_CHAR_WITH_DOP = "[A-Za-z-.]+";
	public static final String REGEX_LATIN_CHAR = "[A-Za-z]+";
	
	public final static String[] ARRAY_DOP_CHAR = new String[]{"-","."};
	
	public final static Integer ID_APPLICATION_IDS = 1120;
	
	public static ArrayList<Character> getInputCharacter(String... parameters) {
		ArrayList<Character> rslt = new ArrayList<Character>();
		for (String s : parameters) {
			rslt.add(new Character(s.charAt(0)));
		}
		return rslt;
	}
}
