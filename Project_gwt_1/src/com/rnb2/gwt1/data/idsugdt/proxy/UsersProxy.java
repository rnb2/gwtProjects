/**
 * 
 */
package com.rnb2.gwt1.data.idsugdt.proxy;

import java.io.Serializable;
import java.util.Date;

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
	
	private Date dateInput;
	
	private String userInput;
	
	public UsersProxy(Users u) {
		this.id = u.getId();
		this.name  = u.getName();
		this.fio = u.getFio();

		this.dateInput = u.getDateInput();
		this.userInput = u.getUsername();
	}
	
	public UsersProxy() {
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

	public Date getDateInput() {
		return dateInput;
	}

	public void setDateInput(Date dateInput) {
		this.dateInput = dateInput;
	}

	public String getUserInput() {
		return userInput;
	}

	public void setUserInput(String userInput) {
		this.userInput = userInput;
	}


	

}
