package com.rnb2.gwt1.data.pm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class AclPermissionPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5479228222275664597L;

	public enum AccessType {
		ALL, READ, WRITE; 
	}
	
	@Column(name="PRINCIPAL")
	private String principal;
	
	@Column(name="ENTITY_ID")
	private String entityId;
	
	@Column(name="APP_ID")
	private String appId;
	
	@Column(name="ACCESS_TYPE")
	@Enumerated(value=EnumType.STRING)	
	private AccessType accessType = AccessType.ALL;
	
	@Column(name="PROPERTY")
	private String property;

	public AclPermissionPK() {
	}
	
	
	public AclPermissionPK(String principal, String entityId, String appId,
			AccessType accessType, String property) {
		super();
		this.principal = principal;
		this.entityId = entityId;
		this.appId = appId;
		this.accessType = accessType;
		this.property = property;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accessType == null) ? 0 : accessType.hashCode());
		result = prime * result + ((appId == null) ? 0 : appId.hashCode());
		result = prime * result
				+ ((entityId == null) ? 0 : entityId.hashCode());
		result = prime * result
				+ ((principal == null) ? 0 : principal.hashCode());
		result = prime * result
				+ ((property == null) ? 0 : property.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AclPermissionPK other = (AclPermissionPK) obj;
		if (accessType == null) {
			if (other.accessType != null)
				return false;
		} else if (!accessType.equals(other.accessType))
			return false;
		if (appId == null) {
			if (other.appId != null)
				return false;
		} else if (!appId.equals(other.appId))
			return false;
		if (entityId == null) {
			if (other.entityId != null)
				return false;
		} else if (!entityId.equals(other.entityId))
			return false;
		if (principal == null) {
			if (other.principal != null)
				return false;
		} else if (!principal.equals(other.principal))
			return false;
		if (property == null) {
			if (other.property != null)
				return false;
		} else if (!property.equals(other.property))
			return false;
		return true;
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

	public AccessType getAccessType() {
		return accessType;
	}

	public void setAccessType(AccessType accessType) {
		this.accessType = accessType;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}


	@Override
	public String toString() {
		return "AclPermissionPK [principal=" + principal + ", entityId="
				+ entityId + ", appId=" + appId + "]";
	}


	
}
