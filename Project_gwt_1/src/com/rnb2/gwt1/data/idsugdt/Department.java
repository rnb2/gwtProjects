package com.rnb2.gwt1.data.idsugdt;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(schema = "IDS_UGDT", name = "GD_DEPARTMENT")
@SequenceGenerator(name = "seq_department", sequenceName = "seq_ora_department", initialValue = 1, allocationSize = 0)
@NamedQueries({ @NamedQuery(name = "getAllDepartment", query = "select o from Department o "
		+ "order by o.fullName") })
public class Department implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6180543389914423787L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_department")
	private Long id;

	@Column
	private String fullName;

	@Column
	private String name;

	@Column
	@Version
	private Integer version;

	@Column
	private Integer code; // Код департамента

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "organization_id")
	private Organization organization;

	public Department() {
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@Override
	public String toString() {
		if (code == null) {
			return name;
		}
		return "(" + code + ") " + name;
	}

	public Integer getVersion() {
		return version;
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
		Department other = (Department) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
