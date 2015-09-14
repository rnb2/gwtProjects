package com.rnb2.gwt1.data.idsugdt;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * @author budukh-rn
 * 
 */
@Entity
@Table(schema = "IDS_UGDT", name = "GD_ENTITYDICTIONARY")
@SequenceGenerator(name = "seq_entitydictionary", sequenceName = "seq_ora_entitydictionary", initialValue = 1, allocationSize = 0)
@NamedQueries({ @NamedQuery(name = "getAllEntityDictionary", query = "select o from EntityDictionary o order by o.dictionary") })
public class EntityDictionary implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1428289940561759441L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_entitydictionary")
	private Long id;

	@Column(name = "CLASS", nullable = false)
	private String clazz;

	@Column(name = "NAME", nullable = false)
	private String dictionary;

	public EntityDictionary() {
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getDictionary() {
		return dictionary;
	}

	public void setDictionary(String dictionary) {
		this.dictionary = dictionary;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return dictionary;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clazz == null) ? 0 : clazz.hashCode());
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
		EntityDictionary other = (EntityDictionary) obj;
		if (clazz == null) {
			if (other.clazz != null)
				return false;
		} else if (!clazz.equals(other.clazz))
			return false;
		return true;
	}

}
