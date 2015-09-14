package com.rnb2.gwt1.data.pm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;



@Entity
@Table(name="PERMISSION")
@SequenceGenerator(name="PERMISSION_SEQUENCE", sequenceName="SEQ_PERMISSION")
public class Permission implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PERMISSION_SEQUENCE")
    private Integer id;
	
	@Column(name = "NAME", nullable = false)
    private String name;
    
    @Column(name = "DESCR")
    private String description;
    
    @ManyToOne(optional=false, fetch = FetchType.EAGER)
    @JoinColumn(name="ID_APPLICATION")
    private Application application;
    
    @ManyToMany(mappedBy="permissions")
    private Collection<User> users = new HashSet<User>();
    
    @ManyToMany(mappedBy="permissions")
    private Collection<Profile> profiles = new HashSet<Profile>();

	public Permission() {
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

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	public Collection<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(Collection<Profile> profiles) {
		this.profiles = profiles;
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
		Permission other = (Permission) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Collection<ProfileUser> getProfileUsers(){
		Collection<ProfileUser> retVal = new ArrayList<ProfileUser>();
		for (Profile profile : profiles){
			for (User user : profile.getUsers()){
			//	user.setProfile(profile);
				retVal.add(new ProfileUser(user, profile));	
			}
		}
		return retVal;
	}
}
