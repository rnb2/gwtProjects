/**
 * 
 */
package com.rnb2.gwt1.data.idsugdt.proxy;

import java.io.Serializable;

import com.rnb2.gwt1.data.idsugdt.Users;

/**
 * @author budukh-rn
 * 
 */
public class UsersProxy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private String fio;
	
	public UsersProxy(Users u) {
		// TODO Auto-generated constructor stub
		this.id = u.getId();
		this.name  = u.getName();
		this.fio = u.getFio();
	}
	
	public UsersProxy() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFio() {
		return fio;
	}

	public void setFio(String fio) {
		this.fio = fio;
	}
	
	

}
