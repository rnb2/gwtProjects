/**
 * 
 */
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
 * @author budukh-rn
 * 
 */
@Entity
@Table(name = "GD_DOCUMENTDICTIONARY")
@SequenceGenerator(name = "seq_documentdictionary", sequenceName = "seq_ora_documentdictionary", initialValue = 1, allocationSize = 0)

@NamedQueries( {
			@NamedQuery(name = "getAllDocumentDictionary", query = "select o from DocumentDictionary o order by o.dictionary")
		})
public class DocumentDictionary  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6611605336274638975L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_documentdictionary")
	private Long id;

	@Column(name = "CLASS", nullable = false)
	private String clazz;

	@Column(name = "NAME", nullable = false)
	private String dictionary;

	/**
	 * 
	 */
	public DocumentDictionary() {
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

}
