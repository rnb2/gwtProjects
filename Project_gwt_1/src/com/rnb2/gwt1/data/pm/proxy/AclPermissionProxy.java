/**
 * 
 */
package com.rnb2.gwt1.data.pm.proxy;

import java.io.Serializable;

/**
 * @author budukh-rn
 * 
 */
public class AclPermissionProxy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Имя пользователя
	 */
	private String principal;

	/**
	 * Тип контролируемого объекта
	 */
	private String entityId;

	/**
	 * Тип приложения
	 */
	private String appId;

	/**
	 * доступ (ALL,READ,CREATE,DELETE,MODIFY)
	 */
	private String accessType;

	/**
	 * Атрибут контролируемого объекта
	 */
	private String property;

	/**
	 * операция сравнения (EQ,NOT_EQ,GT,LT,GTE,LTE,LIKE,MATCH,IN)
	 */
	private String operationType;

	/**
	 * значение
	 */
	private String permissiomValue;

	public AclPermissionProxy() {
	}

	public AclPermissionProxy(Object[] o) {
		this.principal = (String) o[0];
		this.entityId = (String) o[1];
		this.appId = (String) o[2];
		this.accessType = (String) o[3];
		this.property = (String) o[4];
		this.operationType = (String) o[5];
		this.permissiomValue = (String) o[6];
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAccessType() {
		return accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getPermissiomValue() {
		return permissiomValue;
	}

	public void setPermissiomValue(String permissiomValue) {
		this.permissiomValue = permissiomValue;
	}

	
}
