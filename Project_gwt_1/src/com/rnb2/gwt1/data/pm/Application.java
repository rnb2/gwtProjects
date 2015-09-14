package com.rnb2.gwt1.data.pm;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "APPLICATION", uniqueConstraints={@UniqueConstraint(columnNames="SHORT_NAME")})
@SequenceGenerator(name="APPLICATION_SEQUENCE", sequenceName="SEQ_APPLICATION")
public class Application implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5776298886923955968L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APPLICATION_SEQUENCE")
	private Integer id;

	@Column(name = "SHORT_NAME", nullable = false)

	private String shortName;
	
	@Column(name = "FULL_NAME", nullable = false)

	private String fullName;

	@Column(name = "DESCR")
	private String description;
	
	private String architect;
	
	private String programmer;

	@OneToMany(mappedBy = "application")
	private Collection<Permission> permissions = new HashSet<Permission>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getArchitect() {
		return architect;
	}

	public void setArchitect(String architect) {
		this.architect = architect;
	}

	public String getProgrammer() {
		return programmer;
	}

	public void setProgrammer(String programmer) {
		this.programmer = programmer;
	}

	public Collection<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Collection<Permission> permissions) {
		this.permissions = permissions;
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
		final Application other = (Application) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
