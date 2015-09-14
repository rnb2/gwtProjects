/**
 * 
 */
package com.rnb2.gwt1.data.idsugdt;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;


/**
 * @author budukh-rn
 *
 */
@Entity
@Table(schema="IDS_UGDT",name = "GD_ENTITYPERMISSION")
@SequenceGenerator(name = "seq_entitypermission", sequenceName = "seq_ora_entitypermission", initialValue = 1, allocationSize = 0)
public class EntityPermission implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5898922151683082807L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_entitypermission")
	private Long id;

	@Column @Version 
	private Long version;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateInput = new Date();
	
	@Column 
	private String username;
	
	@Column(name="ROLE",nullable=false)
	@Enumerated(EnumType.STRING)
	private EntityRoles entityRoles = EntityRoles.DICTIONARY_ADD;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="DICTIONARY_ID", nullable=false)
	private EntityDictionary dictionary;
	
	@Transient
	public String getDictionaryString(){
		return dictionary.getDictionary();
	}
	
	@Transient
	public String getEntityRolesString(){
		return entityRoles.toString();
	}
	
	/**
	 * 
	 */
	public EntityPermission() {
	}


	public Date getDateInput() {
		return dateInput;
	}


	public void setDateInput(Date dateInput) {
		this.dateInput = dateInput;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public EntityRoles getEntityRoles() {
		return entityRoles;
	}


	public void setEntityRoles(EntityRoles entityRoles) {
		this.entityRoles = entityRoles;
	}


	public EntityDictionary getDictionary() {
		return dictionary;
	}


	public void setDictionary(EntityDictionary dictionary) {
		this.dictionary = dictionary;
	}


	public Long getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return dictionary.getDictionary() + " ("+entityRoles+")";
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
		EntityPermission other = (EntityPermission) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}
