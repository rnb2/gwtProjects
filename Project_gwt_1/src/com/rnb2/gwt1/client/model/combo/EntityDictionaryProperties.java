package com.rnb2.gwt1.client.model.combo;

import com.google.gwt.editor.client.Editor.Path;
import com.rnb2.gwt1.data.idsugdt.EntityDictionary;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface EntityDictionaryProperties extends PropertyAccess<EntityDictionary> {
	@Path("id")
	ModelKeyProvider<EntityDictionary> key();

	@Path("dictionary")
	LabelProvider<EntityDictionary> dictionary();
}