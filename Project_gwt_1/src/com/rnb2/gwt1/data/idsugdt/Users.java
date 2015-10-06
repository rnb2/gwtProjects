package com.rnb2.gwt1.data.idsugdt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
@Table(schema="IDS_UGDT",name = "GD_USERS")
@SequenceGenerator(name = "seq_users", sequenceName = "seq_ora_users", initialValue = 1, allocationSize = 0)
@NamedQueries( {

	@NamedQuery(name = "getUserIdsByLogin", query = "select o from Users as o where o.name = ?1 "),
	
	@NamedQuery(name = "getUserStationByLogin", 
				query = "select gr.id, gr.fullName, t.fullName from Users o "+
						"left join o.railwayGroup gr "+
						"inner join gr.railwayObjectType t "+
						"where o.name = ?1 "),
	@NamedQuery(name = "getUserEntityByLogin", 
				query = "select ep from Users o "+
						"inner join o.usersEntityPermitions ep "+
						"where o.name = ?1"),
	@NamedQuery(name = "getUserDocumentByLogin", 
	query = "select ep from Users o "+
			"inner join o.usersDocumentPermitions ep "+
			"where o.name = ?1"),
	@NamedQuery(name = "getUserDepartmentByLogin", 
	query = "select ep from Users o "+
			"inner join o.usersDepartment ep "+
			"where o.name = ?1")			
	
})
public class Users implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1037962601311013258L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_users")
	private Long id;

	@Column
	private String name;

	@Column
	private String fio;

	@Column
	@Version
	private Long version;

	@OneToMany(cascade = {})
	@JoinTable(schema= "IDS_UGDT", name = "GD_USERSSTATIONS", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "railwaygroup_id") })
	@OrderBy("fullName asc")
	private Set<RailwayGroup> railwayGroup = new HashSet<RailwayGroup>();

	@Column
	@Temporal(TemporalType.DATE)
	private Date dateInput = new Date();

	@Column
	private String username;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinColumn(name = "USERS_ID")
	@Fetch(FetchMode.SUBSELECT)
	private List<UsersDepartment> usersDepartment = new ArrayList<UsersDepartment>();

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinColumn(name = "ENTITYPERMISSION_ID")
	@Fetch(FetchMode.SUBSELECT)
	private List<EntityPermission> usersEntityPermitions = new ArrayList<EntityPermission>();

	public Map<String, List<EntityRoles>> getUserRolesMap() {
		Map<String, List<EntityRoles>> rsltMap = new HashMap<String, List<EntityRoles>>();
		List<EntityPermission> permissions = getUsersEntityPermitions();
		for (EntityPermission perm : permissions) {
			String clazz = perm.getDictionary().getClazz();
			if (!rsltMap.containsKey(clazz)) {
				List<EntityRoles> list = new ArrayList<EntityRoles>();
				list.add(perm.getEntityRoles());
				rsltMap.put(clazz, list);
			} else {
				rsltMap.get(clazz).add(perm.getEntityRoles());
			}
		}
		return rsltMap;
	}
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinColumn(name = "DOCUMENTPERMISSION_ID")
	@Fetch(FetchMode.SUBSELECT)
	private List<DocumentPermission> usersDocumentPermitions = new ArrayList<DocumentPermission>();

	public Map<String, List<DocumentRoles>> getUserDocumentRolesMap() {
		Map<String, List<DocumentRoles>> rsltMap = new HashMap<String, List<DocumentRoles>>();
		List<DocumentPermission> permissions = getUsersDocumentPermitions();
		for (DocumentPermission perm : permissions) {
			String clazz = perm.getDictionary().getClazz();
			if (!rsltMap.containsKey(clazz)) {
				List<DocumentRoles> list = new ArrayList<DocumentRoles>();
				list.add(perm.getDocumentRoles());
				rsltMap.put(clazz, list);
			} else {
				rsltMap.get(clazz).add(perm.getDocumentRoles());
			}
		}
		return rsltMap;
	}

	public Users() {
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

	public Long getVersion() {
		return version;
	}

	public Set<RailwayGroup> getRailwayGroup() {
		return railwayGroup;
	}

	public void setRailwayGroup(Set<RailwayGroup> railwayGroup) {
		this.railwayGroup = railwayGroup;
	}

	public Date getDateInput() {
		return dateInput;
	}

	public void setDateInput(Date dateInput) {
		this.dateInput = dateInput;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<EntityPermission> getUsersEntityPermitions() {
		return usersEntityPermitions;
	}

	public void setUsersEntityPermitions(
			List<EntityPermission> usersEntityPermitions) {
		this.usersEntityPermitions = usersEntityPermitions;
	}

	public String getFio() {
		return fio;
	}

	public void setFio(String fio) {
		this.fio = fio;
	}

	public List<UsersDepartment> getUsersDepartment() {
		return usersDepartment;
	}

	public void setUsersDepartment(List<UsersDepartment> usersDepartment) {
		this.usersDepartment = usersDepartment;
	}

	public List<DocumentPermission> getUsersDocumentPermitions() {
		return usersDocumentPermitions;
	}

	public void setUsersDocumentPermitions(
			List<DocumentPermission> usersDocumentPermitions) {
		this.usersDocumentPermitions = usersDocumentPermitions;
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
		Users other = (Users) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
