/**
 * 
 */
package com.rnb2.gwt1.data.pm.proxy;

import java.io.Serializable;

/**
 * @author budukh-rn
 * 
 */
public class PermissionProxy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String description;
	private String shortName;

	/**
	 * 
	 */
	public PermissionProxy() {
	}
	
	public String getFullName(){
		if(description != null){
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(name);
			stringBuilder.append(" (");
			//stringBuilder.append("<br/>");
			stringBuilder.append(description);
			stringBuilder.append(")");
			return stringBuilder.toString();
		}
		return name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Override
	public String toString() {
		if(description != null)
			return name + " (" + description +")";
		return name;
	}

}
