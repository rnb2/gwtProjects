package com.rnb2.gwt1.data.idsugdt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;


@Entity
@Table(schema="IDS_UGDT", name="GD_RAILWAYOBJECT")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DISCRIMINATOR", discriminatorType=DiscriminatorType.STRING)
@SequenceGenerator(name="seq_railwayobject", sequenceName="seq_ora_railwayobject", initialValue=1, allocationSize=0)
@NamedQueries({
	@NamedQuery(name="asugtAllRailwayObject", query="select o from RailwayObject as o ")
})

public abstract class RailwayObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_railwayobject") 
	private Long id;
	
	@Column
	private String fullName;
	
	@Column 
	private String shortName;
	
	
	@ManyToOne(optional=false)
	@JoinColumn(name="railwayobjecttype_id")
	private RailwayObjectType railwayObjectType;
		 
	@Column @Version
	private Integer version;
	
	@Column 
	private Boolean regObject;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateInput = new Date();
	
	@Column 
	private String username;
	
	@Column 
	private Integer length;
	
	@Transient
	private Integer countRollingStock = 0;
	
	public RailwayObject(){}
	
	public RailwayObject(Long id) {
		super();
		this.id = id;
	}


	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public RailwayObjectType getRailwayObjectType() {
		return railwayObjectType;
	}

	public void setRailwayObjectType(RailwayObjectType railwayObjectType) {
		this.railwayObjectType = railwayObjectType;
	}

	public Long getId() {
		return id;
	}
	

	public Boolean getRegObject() {
		return regObject;
	}

	public void setRegObject(Boolean regObject) {
		this.regObject = regObject;
	}


	public String getSimpleName() {
		return (railwayObjectType != null ? railwayObjectType.getFullName() : "")
			+ ": " + fullName;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		if(railwayObjectType != null){
			sb.append(railwayObjectType.getFullName()+"");
		}
		sb.append(": " + fullName);
		if(countRollingStock>0){
			sb.append(" ("+countRollingStock+")");
		}
		return sb.toString();
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

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}
	public Integer getVersion() {
		return version;
	}

	public Integer getCountRollingStock() {
		return countRollingStock;
	}

	public void setCountRollingStock(Integer countRollingStock) {
		this.countRollingStock = countRollingStock;
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
		RailwayObject other = (RailwayObject) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
