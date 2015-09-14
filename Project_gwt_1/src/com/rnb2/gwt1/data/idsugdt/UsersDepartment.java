package com.rnb2.gwt1.data.idsugdt;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="GD_USERSDEPARTMENT")
@SequenceGenerator(name="seq_usersdepartment", sequenceName="seq_ora_usersdepartment", initialValue=1, allocationSize=0)
public class UsersDepartment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2387828676574618872L;

	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_usersdepartment")
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="DEPARTMENT_ID", nullable=false)
	private Department department;
	
	@Column(name="ROLES", nullable=false)
	@Enumerated(EnumType.ORDINAL)
	private EdcPermissionRoles roles;
	
	@Transient
	public String getDepartmentString(){
		return department.getName();
	}
	
	@Transient
	public String getEntityRolesString(){
		return roles.toString();
	}

	public UsersDepartment() {}
	
	public Long getId() {
		return id;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public EdcPermissionRoles getRoles() {
		return roles;
	}

	public void setRoles(EdcPermissionRoles roles) {
		this.roles = roles;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		UsersDepartment other = (UsersDepartment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	
}
