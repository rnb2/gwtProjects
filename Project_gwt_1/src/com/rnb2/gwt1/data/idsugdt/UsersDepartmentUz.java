/**
 * 
 */
package com.rnb2.gwt1.data.idsugdt;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author budukh-rn
 * 19 но€б. 2015 г.	
 *
 */
@Entity
@Table(schema="IDS_UGDT", name="UZ_USERSDEPARTMENT")
@SequenceGenerator(name="seq_usersdepartment_uz", sequenceName="seq_ora_uz_usersdepartment", initialValue=116, allocationSize=0)
public class UsersDepartmentUz  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_usersdepartment_uz")
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="DEPARTMENT_ID", nullable=false)
	private Department department;
	
	
	/**
	 * 
	 */
	public UsersDepartmentUz() {
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Department getDepartment() {
		return department;
	}


	public void setDepartment(Department department) {
		this.department = department;
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
		UsersDepartmentUz other = (UsersDepartmentUz) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
