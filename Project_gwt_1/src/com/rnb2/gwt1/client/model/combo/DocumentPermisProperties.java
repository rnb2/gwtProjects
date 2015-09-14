package com.rnb2.gwt1.client.model.combo;

import com.google.gwt.editor.client.Editor.Path;
import com.rnb2.gwt1.data.idsugdt.DocumentPermission;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface DocumentPermisProperties extends PropertyAccess<DocumentPermission> {
	
	@Path(value = "id")
	ModelKeyProvider<DocumentPermission> key();
	
	@Path("dictionaryString")
	LabelProvider<DocumentPermission> nameLabel();
	
	ValueProvider<DocumentPermission, String> dictionaryString();
	ValueProvider<DocumentPermission, String> entityRolesString();
	
}
