package com.rnb2.gwt1.data.idsugdt;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;



@Entity
@Table(schema="IDS_UGDT", name="GD_COUNTRY")
@SequenceGenerator(name="seq_country", sequenceName="seq_ora_country", initialValue=1, allocationSize=0)
@NamedQueries( {
		@NamedQuery(name = "getCountryByCodeNUM", query = "select o from Country as o where o.codeNUM = ?1 "),
		@NamedQuery(name = "getCountryAll", query = "select o from Country as o")
	})
public class Country implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 427472574474923870L;

	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_country")
	private Long id;

	@Column
	private String fullName;
	
	@Column 
	private String shortName;
	
	@Column 
	private String codeISOA2;
	
	@Column 
	private String codeISOA3;

	@Column 
	private String codeNUM;

	@Column @Version 
	private Long version;	
	
	public Country() {}
	
	public Country(String fullName, String shortName){
		this.fullName = fullName;
		this.shortName = shortName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Long getId() {
		return id;
	}

	public int compareTo(Country arg0) {
		return fullName.compareTo(arg0.getFullName());
	}

	public String getCodeISOA2() {
		return codeISOA2;
	}

	public void setCodeISOA2(String codeISOA2) {
		this.codeISOA2 = codeISOA2;
	}

	public String getCodeISOA3() {
		return codeISOA3;
	}

	public void setCodeISOA3(String codeISOA3) {
		this.codeISOA3 = codeISOA3;
	}


	public String getCodeNUM() {
		return codeNUM;
	}

	public void setCodeNUM(String codeNUM) {
		this.codeNUM = codeNUM;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return fullName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fullName == null) ? 0 : fullName.hashCode());
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
		Country other = (Country) obj;
		if (fullName == null) {
			if (other.fullName != null)
				return false;
		} else if (!fullName.equals(other.fullName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	
}
