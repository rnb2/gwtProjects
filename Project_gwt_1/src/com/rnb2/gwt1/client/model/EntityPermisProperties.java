package com.rnb2.gwt1.client.model;

import com.google.gwt.editor.client.Editor.Path;
import com.rnb2.gwt1.data.idsugdt.EntityPermission;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface EntityPermisProperties extends PropertyAccess<EntityPermission> {
	
	@Path(value = "id")
	ModelKeyProvider<EntityPermission> key();
	
	@Path("dictionaryString")
	LabelProvider<EntityPermission> nameLabel();
	
	ValueProvider<EntityPermission, String> dictionaryString();
	ValueProvider<EntityPermission, String> entityRolesString();
	
}
