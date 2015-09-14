package com.rnb2.gwt1.client.model.combo;

import com.google.gwt.editor.client.Editor.Path;
import com.rnb2.gwt1.data.idsugdt.EdcPermissionRoles;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface EdcPermissionRolesProperties extends PropertyAccess<EdcPermissionRoles> {
	
	@Path(value = "code")
	ModelKeyProvider<EdcPermissionRoles> key();
	
	@Path("text")
	LabelProvider<EdcPermissionRoles> nameLabel();
	
	ValueProvider<EdcPermissionRoles, String> text();
	
}
