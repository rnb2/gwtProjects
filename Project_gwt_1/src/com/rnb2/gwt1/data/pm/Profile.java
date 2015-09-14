package com.rnb2.gwt1.data.pm;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;



@Entity
@Table(name = "PROFILE", uniqueConstraints={@UniqueConstraint(columnNames="NAME")})
@SequenceGenerator(name="PROFILE_SEQUENCE", sequenceName="SEQ_PROFILE")
public class Profile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2953559725251978308L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PROFILE_SEQUENCE")
    private Integer id;
	
	@Column(name = "NAME", nullable = false)
    private String name;
    
    @Column(name = "DESCR")
    private String description;
    
    @ManyToMany
    @JoinTable(name="PROFILE_PERMISSIONS", joinColumns = { @JoinColumn(name = "ID_PROFILE") }, inverseJoinColumns = { @JoinColumn(name = "ID_PERMISSION") })
    private Collection<Permission> permissions = new HashSet<Permission>();
    
    @ManyToMany(mappedBy="profiles")
    private Collection<User> users;

	public Profile() {
	}
    
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Collection<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Collection<Permission> permissions) {
		this.permissions = permissions;
	}

	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		final Profile other = (Profile) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
    
	
}
