/**
 * 
 */
package com.rnb2.gwt1.data.idsugdt.proxy;

import java.io.Serializable;

/**
 * @author budukh-rn
 * 
 */
public class RailwayGroupProxy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String fullNameSt;
	private String fullNameType;

	public RailwayGroupProxy() {
	}
	
	public RailwayGroupProxy(Object[] obj) {
		this.id = (Long) obj[0];
		this.fullNameSt = (String) obj[1];
		this.fullNameType = (String) obj[2];
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullNameSt() {
		return fullNameSt;
	}

	public void setFullNameSt(String fullNameSt) {
		this.fullNameSt = fullNameSt;
	}

	public String getFullNameType() {
		return fullNameType;
	}

	public void setFullNameType(String fullNameType) {
		this.fullNameType = fullNameType;
	}

	@Override
	public String toString() {
		return "RailwayGroupProxy [id=" + id + ", fullNameSt=" + fullNameSt
				+ ", fullNameType=" + fullNameType + "]";
	}

}
