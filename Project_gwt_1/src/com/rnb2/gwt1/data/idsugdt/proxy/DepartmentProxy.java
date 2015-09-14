/**
 * 
 */
package com.rnb2.gwt1.data.idsugdt.proxy;

import java.io.Serializable;

/**
 * 
 * @author budukh-rn
 * 
 */
public class DepartmentProxy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String fullName;

	/**
	 * 
	 */
	public DepartmentProxy() {
	}

	public DepartmentProxy(Long id, String fullName) {
		super();
		this.id = id;
		this.fullName = fullName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

}
