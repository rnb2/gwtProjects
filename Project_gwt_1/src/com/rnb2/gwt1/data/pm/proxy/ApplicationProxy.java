/**
 * 
 */
package com.rnb2.gwt1.data.pm.proxy;

import java.io.Serializable;

/**
 * @author budukh-rn
 * 
 */
public class ApplicationProxy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String shortName;
	private String fullName;
	private String description;
	private String architect;
	private String programmer;

	private String idString;
	/**
	 * 
	 */
	public ApplicationProxy() {
	}

	public String getIdString() {
		idString = id.toString();
		return idString;
	}
		
	public void setIdString(String idString) {
		this.idString = idString;
	}

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

}
