package com.rnb2.gwt1.data.pm;

import java.io.Serializable;
import java.security.Principal;
import java.util.List;

/**
 * 
 * @author budukh-rn
 * 02 мая 2013 г.	
 *
 */
public class LdapPrincipal implements Principal,Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sAMAccountName;
	private String title;
	private String company;
	private String employeeID;
	private String employeeNumber;
	private String department;
	private String description;
	private String displayName;
	private String distinguishedName;
	private String givenName;
	private String initials;
	private String mail;
	private String sn;
	private String telephoneNumber;
	private String userPrincipalName;
	private List memberOf;
	
	
	
	public LdapPrincipal(String sAMAccountName) {
		this.sAMAccountName = sAMAccountName;
	}
	

	public String getName() {
		return sAMAccountName;
	}
	
	
	@Override
	public String toString() {
		
		return getDisplayName();
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
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getDistinguishedName() {
		return distinguishedName;
	}
	public void setDistinguishedName(String distinguishedName) {
		this.distinguishedName = distinguishedName;
	}
	public String getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}
	public String getGivenName() {
		return givenName;
	}
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	public String getInitials() {
		return initials;
	}
	public void setInitials(String initials) {
		this.initials = initials;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getSAMAccountName() {
		return sAMAccountName;
	}
	public void setSAMAccountName(String accountName) {
		sAMAccountName = accountName;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getTelephoneNumber() {
		return telephoneNumber;
	}
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUserPrincipalName() {
		return userPrincipalName;
	}
	public void setUserPrincipalName(String userPrincipalName) {
		this.userPrincipalName = userPrincipalName;
	}
	public List getMemberOf() {
		return memberOf;
	}
	public void setMemberOf(List memberOf) {
		this.memberOf = memberOf;
	}
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
}
