package com.rnb2.gwt1.data.idsugdt;

/**���� ��� ���*/
public enum EdcPermissionRoles {
	
	/**������������� �����������*/
	SENDER("S"),
	/**�������������  ����������*/
	RECEIVER("R"),
	/**������� ��������*/
	STATION_ARRIVEL("A"),
	/**������� �����������*/
	STATION_DEPARTURE("D");
	
	private EdcPermissionRoles(String text){
		this.text = text;
	}
	
	private String text;
	
	public String getText() {
		return text;
	}
	
	@Override
	public String toString() {
		return text;
	}
	
	public int getCode(){
		return ordinal();
	}

}
