package com.rnb2.gwt1.client.model.combo;

import com.google.gwt.editor.client.Editor.Path;
import com.rnb2.gwt1.data.idsugdt.EntityRoles;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface EntityRolesProperties extends PropertyAccess<EntityRoles> {
	
	@Path(value = "code")
	ModelKeyProvider<EntityRoles> key();
	
	@Path("text")
	LabelProvider<EntityRoles> nameLabel();
	
	ValueProvider<EntityRoles, String> text();
	
}
