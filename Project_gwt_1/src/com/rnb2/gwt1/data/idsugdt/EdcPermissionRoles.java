package com.rnb2.gwt1.data.idsugdt;

/**Роли для ЭГД*/
public enum EdcPermissionRoles {
	
	/**Подразделение Отправитель*/
	SENDER("S"),
	/**Подразделение  Получатель*/
	RECEIVER("R"),
	/**Станция Прибытие*/
	STATION_ARRIVEL("A"),
	/**Станция Отправление*/
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
