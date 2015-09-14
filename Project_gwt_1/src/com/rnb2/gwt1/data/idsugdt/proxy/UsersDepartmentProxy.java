/**
 * 
 */
package com.rnb2.gwt1.data.idsugdt.proxy;

import java.io.Serializable;

import com.rnb2.gwt1.data.idsugdt.UsersDepartment;

/**
 * список подразделений Ё√ƒ у пользовател€
 * @author budukh-rn
 *
 */
public class UsersDepartmentProxy implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String roles;
	
	public UsersDepartmentProxy() {
	}

	public UsersDepartmentProxy(UsersDepartment o) {
		this.id = o.getId();
		this.name = o.getDepartment().getName();
		this.roles = o.getEntityRolesString();
	}
	
	public UsersDepartmentProxy(Object[] o) {
		this.id = (Long) o[0];
		this.name = (String) o[1];
		this.roles = (String) o[2];
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
