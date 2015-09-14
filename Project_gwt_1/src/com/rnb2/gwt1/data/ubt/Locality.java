package com.rnb2.gwt1.data.ubt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Территория движения
 * 
 * @author daduev-ad
 * 
 */
@Entity
@Table(name = "TS_LOCALITY")
@NamedQueries({
	@NamedQuery(name = "allActiveLocality", query = "select o from Locality as o where o.signBlock = false order by o.name"),
	@NamedQuery(name = "allLocality", query = "select o from Locality as o order by o.name") })
public class Locality implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;
	
	/** Наименование */
	@Column(name = "NAME", length = 25, nullable = false)
	private String name;	

	/** Признак блокировки */
	@Column(name = "SIGN_BLOCK", nullable = false)
	private boolean signBlock = false;

	@Column(name = "USER_UPDATE", length = 25, nullable = false)
	private String userUpdate;

	@Column(name = "DATE_UPDATE", nullable = false)
	private Date dateUpdate = new Date();	
	
	public Locality() {}
	
	

	public Locality(String name, String userUpdate) {
		super();
		this.name = name;
		this.userUpdate = userUpdate;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSignBlock() {
		return signBlock;
	}

	public void setSignBlock(boolean signBlock) {
		this.signBlock = signBlock;
	}

	public String getUserUpdate() {
		return userUpdate;
	}

	public void setUserUpdate(String userUpdate) {
		this.userUpdate = userUpdate;
	}

	public Date getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return name;
	}
}
