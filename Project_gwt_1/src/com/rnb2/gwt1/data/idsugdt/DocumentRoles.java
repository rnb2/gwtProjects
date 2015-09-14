package com.rnb2.gwt1.data.idsugdt;

/**
 * –оли дл€ форм(документов/журналов)
 * 
 * @author budukh-rn
 * 
 */
public enum DocumentRoles {
	/**
	 * ѕраво на просмотр документов/журнале
	 */
	DOCUMENT_VIEW("\u041F\u0440\u043E\u0441\u043C\u043E\u0442\u0440 \u0444\u043E\u0440\u043C\u044B"),
	/**
	 * ѕраво на редактирование в форме(документе/журнале и т.д.)
	 */
	DOCUMENT_EDIT("\u0420\u0435\u0434\u0430\u043A\u0442\u0438\u0440\u043E\u0432\u0430\u043D\u0438\u0435 \u0444\u043E\u0440\u043C\u044B");

	private String text;
	private int code = 0;

	private DocumentRoles(String text) {
		this.text = text;
	}

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
	
	public int getCode() {
		code = ordinal();
		return code;
	}
	
	public String getText() {
		return text;
	}
}
