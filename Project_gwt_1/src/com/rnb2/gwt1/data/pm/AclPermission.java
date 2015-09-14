package com.rnb2.gwt1.data.pm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;


import com.rnb2.gwt1.data.pm.AclPermissionPK.AccessType;


@Entity
@Table(name = "ACL_PERMISSION")
//@IdClass(value=AclPermissionPK.class)
public class AclPermission implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public enum OperationType{
		EQ, LIKE
	}
	
	@Id
	private AclPermissionPK id;
	
	//@Id
	//@Column(updatable=false, insertable=false)
	//private String principal;
	
	//@Id
	//@Column(name="ENTITY_ID", updatable=false, insertable=false)
	//private String entityId;
	
	//@Id
	//@Column(updatable=false, insertable=false)
	//private String appId;
	
	//@Id
	//@Column(name="ACCESS_TYPE", updatable=false, insertable=false)
	//private AccessType accessType = AccessType.ALL;
	
	//@Id
	//@Column(name="PROPERTY", updatable=false, insertable=false)
	//private String property;
	
	@Column(name="OPERATION_TYPE")
	@Enumerated(value=EnumType.STRING)
	private OperationType operationType = OperationType.EQ;
	
	@Column(name="PERMISSIOM_VALUE")
	private String permissionValue;
	
	
	/*@ManyToOne(optional=false)
	@JoinColumn(name="PRINCIPAL", referencedColumnName="LOGIN_NAME", updatable=false, insertable=false)
	private User user;
	
	@ManyToOne(optional=false) 
	@JoinColumn(name="APP_ID", referencedColumnName="SHORT_NAME")
	private Application application;*/
	

	public AclPermission() {
	}


	/*public String getPrincipal() {
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
	}*/


	public OperationType getOperationType() {
		return operationType;
	}


	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}


	public String getPermissionValue() {
		return permissionValue;
	}


	public void setPermissionValue(String permissionValue) {
		this.permissionValue = permissionValue;
	}


	/*public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Application getApplication() {
		return application;
	}


	public void setApplication(Application application) {
		this.application = application;
	}*/

	public AclPermissionPK getId() {
		return id;
	}


	public void setId(AclPermissionPK id) {
		this.id = id;
	}


	/*@PrePersist
	@PreUpdate
	public void onPersist(){

		if (principal == null){
			//log.debug("principal is null");
			principal = user.getLoginName(); 
		}
		
		if (appId == null){
			//log.debug("appId is null");
			appId = application.getShortName();
		}
	}*/


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		//sb.append("principal = " + principal);
		//sb.append(", entityId = " + entityId);
		//sb.append(", appId = " + appId);
		//sb.append(", accessType = " + accessType);
		//sb.append(", property = " + property);
		sb.append(", operationType = " + operationType);
		sb.append(", permissionValue = " + permissionValue);
		//sb.append(", user = " + user);
		//sb.append(", application = " + application);		
		
		return sb.toString();
	}

	
	
	
}
