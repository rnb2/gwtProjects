package com.rnb2.gwt1.data.idsugdt;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;




@Entity
@Table(schema="IDS_UGDT", name="GD_ORGANIZATION")
@SequenceGenerator(name="seq_organization", sequenceName="seq_ora_organization", initialValue=1, allocationSize=0)
@NamedQueries( {
		@NamedQuery(name = "asuAllOrganizations", query = "select o from Organization as o"),
		@NamedQuery(name = "asuOrganizationByNumber", query = "select o from Organization as o where o.codeGD = ?1 "),
		@NamedQuery(name = "asuOrganizationByName", query = "select o from Organization as o where o.fullName = ?1 ")
	})
public class Organization  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 978264667599133537L;

	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_organization") 
	private Long id;
	
	@Column 
	private String fullName;
	
	@Column
	private String shortName;

	@Column(name = "LOCATION", length=500)
	private String location;	//	Адрес
	
	@Column
	private Long codeGD; 		//	Код организации по Ж.Д.
	
	@Column 
	private String codeOKPO;
	
	@Column 
	private String r3Debitor;
	
	@Column	
	private String r3Creditor;
	
	@Column @Version 
	private Long version;

		
	@Column(name = "ATRIBUTE", length=10)
	private String atribute;

	

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id")
	private Country country;
	
	
	
	@OneToMany(mappedBy="organization", cascade={}, fetch=FetchType.LAZY)
	private Set<Department> departments = new HashSet<Department>();
	
	
	public Organization(){}
	

	public Organization(String fullName, String shortName, String codeOKPO,
			String debitor, String creditor, String location, Long codeGD, String atribute) {
		super();
		this.fullName = fullName;
		this.shortName = shortName;
		this.codeOKPO = codeOKPO;
		r3Debitor = debitor;
		r3Creditor = creditor;
		this.location = location;
		this.codeGD = codeGD;
		this.atribute = atribute;
	}




	public Organization(String fullName, String shortName, String codeOKPO, String r3Debitor, String r3Creditor){
		this.fullName = fullName;
		this.shortName = shortName;
		this.codeOKPO = codeOKPO;
		this.r3Creditor = r3Creditor;
		this.r3Debitor = r3Debitor;
	}
	
	
	
	public Long getCodeGD() {
		return codeGD;
	}


	public void setCodeGD(Long codeGD) {
		this.codeGD = codeGD;
	}


	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCodeOKPO() {
		return codeOKPO;
	}
	public void setCodeOKPO(String codeOKPO) {
		this.codeOKPO = codeOKPO;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getR3Creditor() {
		return r3Creditor;
	}
	public void setR3Creditor(String creditor) {
		r3Creditor = creditor;
	}
	public String getR3Debitor() {
		return r3Debitor;
	}
	public void setR3Debitor(String debitor) {
		r3Debitor = debitor;
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

	public void setId(Long id) {
		this.id = id;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Set<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(Set<Department> departments) {
		this.departments = departments;
	}


	public String getAtribute() {
		return atribute;
	}


	public void setAtribute(String atribute) {
		this.atribute = atribute;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return fullName;
	}
	public Long getVersion() {
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
		Organization other = (Organization) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
