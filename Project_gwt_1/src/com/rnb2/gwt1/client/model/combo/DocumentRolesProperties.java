package com.rnb2.gwt1.client.model.combo;

import com.google.gwt.editor.client.Editor.Path;
import com.rnb2.gwt1.data.idsugdt.DocumentRoles;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface DocumentRolesProperties extends PropertyAccess<DocumentRoles> {
	
	@Path(value = "code")
	ModelKeyProvider<DocumentRoles> key();
	
	@Path("text")
	LabelProvider<DocumentRoles> nameLabel();
	
	ValueProvider<DocumentRoles, String> text();
	
}
