package com.rnb2.gwt1.data.idsugdt;

/**
 * Роли для справочников
 * 
 * @author budukh-rn
 * 
 */
public enum EntityRoles {
	/**
	 * Право на добавление в справочник
	 */
	DICTIONARY_ADD("Создание"),
	/**
	 * Право на редактирование в справочнике
	 */
	DICTIONARY_EDIT("Редактирование"),
	/**
	 * Право на удаление из справочника
	 */
	DICTIONARY_DELETE("Удаление"),
	/**
	 * Право на восстановление записи в справочнике
	 */
	DICTIONARY_RESTORE("Восстановление");


	private EntityRoles(String text) {
		this.text = text;
	}

	private String text;
	private int code = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return text;
	}

	public int getCodeRole() {
		code = ordinal();
		return code;
	}

	public String getText() {
		return text;
	}
	
	public int getCode() {
		code = ordinal();
		return code;
	}
}
