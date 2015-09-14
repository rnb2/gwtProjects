package com.rnb2.gwt1.data.idsugdt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;



@Entity
@Table(schema="IDS_UGDT", name="GD_RAILWAYOBJECTTYPE")
@SequenceGenerator(name="seq_railwayobjecttype", sequenceName="seq_ora_railwayobjecttype", initialValue=1, allocationSize=0)
public class RailwayObjectType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1222205386294728965L;

	@Id 
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_railwayobjecttype") 
	private Long id;
	
	@Column 
	private String fullName;

	@Column
	private Boolean isGroup;
	
	
	@Column @Version
	private Integer version;
	
	@Column
	private String  sysCode;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Date dateInput = new Date();
	
	@Column 
	private String username;
	
	
	
	public RailwayObjectType(){}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Boolean getIsGroup() {
		return isGroup;
	}

	public void setIsGroup(Boolean isGroup) {
		this.isGroup = isGroup;
	}

	public Long getId() {
		return id;
	}
	
	
	@PreUpdate
	@PrePersist
	public void isRailwayObjectTypeValid(){
		/*if (getFullName() == null) { throw new UserApplicationException("RailwayObjectTypeMustValid"); }
		if (getFullName().equals("") ) { throw new UserApplicationException("RailwayObjectTypeMustValid"); }*/
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
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
	
	public Integer getVersion() {
		return version;
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
		RailwayObjectType other = (RailwayObjectType) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
