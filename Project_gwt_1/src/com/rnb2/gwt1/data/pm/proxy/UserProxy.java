/**
 * 
 */
package com.rnb2.gwt1.data.pm.proxy;

import java.io.Serializable;
import java.util.List;

/**
 * @author budukh-rn
 * 
 */
public class UserProxy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String loginName;
	private String fullName;
	private String workPhone;
	private Integer id;
	private String employeeID;
	/** ѕризнак, что пользователь найдень из AD*/
	private boolean isFromAD = false;
	private List<Object> memberOf;
	private String company;
	private String department;
	

	public UserProxy() {
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isFromAD() {
		return isFromAD;
	}

	public void setFromAD(boolean isFromAD) {
		this.isFromAD = isFromAD;
	}

	public List<Object> getMemberOf() {
		return memberOf;
	}

	public void setMemberOf(List<Object> memberOf) {
		this.memberOf = memberOf;
	}

	public String getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}


}
