/**
 * 
 */
package com.rnb2.gwt1.data.idsugdt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * @author budukh-rn
 * 
 */
@Entity
@Table(schema="IDS_UGDT", name = "GD_DOCUMENTPERMISSION")
@SequenceGenerator(name = "seq_documentpermission", sequenceName = "seq_ora_documentpermission", initialValue = 1, allocationSize = 0)
public class DocumentPermission implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_documentpermission")
	private Long id;

	@Column
	@Version
	private Long version;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateInput = new Date();

	@Column
	private String username;

	@Column(name = "ROLE", nullable = false)
	@Enumerated(EnumType.STRING)
	private DocumentRoles documentRoles = DocumentRoles.DOCUMENT_VIEW;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DICTIONARY_ID", nullable = false)
	private DocumentDictionary dictionary;
	
	@Transient
	public String getDictionaryString(){
		return dictionary.getDictionary();
	}
	
	@Transient
	public String getEntityRolesString(){
		return documentRoles.name();
	}

	/**
	 * 
	 */
	public DocumentPermission() {
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

	public DocumentRoles getDocumentRoles() {
		return documentRoles;
	}

	public void setDocumentRoles(DocumentRoles documentRoles) {
		this.documentRoles = documentRoles;
	}

	public DocumentDictionary getDictionary() {
		return dictionary;
	}

	public void setDictionary(DocumentDictionary dictionary) {
		this.dictionary = dictionary;
	}

	public Long getId() {
		return id;
	}

	public Long getVersion() {
		return version;
	}

	@Override
	public String toString() {
		return dictionary.getDictionary() + " (" + documentRoles + ")";
	}

}
