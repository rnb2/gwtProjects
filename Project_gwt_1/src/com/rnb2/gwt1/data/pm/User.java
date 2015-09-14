package com.rnb2.gwt1.data.pm;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;



@Entity
@Table(name = "USERS", uniqueConstraints={@UniqueConstraint(columnNames="LOGIN_NAME")} )
@SequenceGenerator(name = "USER_SEQUENCE", sequenceName = "SEQ_USER")
@NamedQueries( {
	@NamedQuery(name = "getUserPMLite.by.name", query = "select o.id as id, o.loginName as loginName, o.fullName as fullName, o.workPhone as workPhone	from User o where o.loginName = :param1"),
	@NamedQuery(name = "getUserPMLite", query = "select o.id as id, o.loginName as loginName, o.fullName as fullName, o.workPhone as workPhone	from User o")
})
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQUENCE")
	private Integer id;

	@Column(name = "LOGIN_NAME", nullable = false)
	private String loginName;

	@Column(name = "FULL_NAME")
	private String fullName;

	@Column(name = "WORK_PHONE")
	private String workPhone;

	@ManyToMany
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinTable(name = "USER_PERMISSIONS", joinColumns = { @JoinColumn(name = "ID_USER") }, inverseJoinColumns = { @JoinColumn(name = "ID_PERMISSION") })
	private Set<Permission> permissions = new HashSet<Permission>();

	@ManyToMany
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinTable(name = "USER_PROFILE", joinColumns = { @JoinColumn(name = "ID_USER") }, inverseJoinColumns = { @JoinColumn(name = "ID_PROFILE") })
	private Set<Profile> profiles = new HashSet<Profile>();
	
	//@OneToMany(mappedBy="user")
	@OneToMany
	@JoinColumn(name="PRINCIPAL", referencedColumnName="LOGIN_NAME")
	private Set<AclPermission> aclPermissions = new HashSet<AclPermission>(); 
	
	public User() {
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public Set<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(Set<Profile> profiles) {
		this.profiles = profiles;
	}
	
	public Set<AclPermission> getAclPermissions() {
		return aclPermissions;
	}

	public void setAclPermissions(Set<AclPermission> aclPermissions) {
		this.aclPermissions = aclPermissions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((loginName == null) ? 0 : loginName.hashCode());
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
		final User other = (User) obj;
		if (loginName == null) {
			if (other.loginName != null)
				return false;
		} else if (!loginName.equals(other.loginName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		
		return loginName + " - " + fullName + " - " + workPhone;
	}
	
}
