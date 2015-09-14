package com.rnb2.gwt1.client.model.combo;

import com.google.gwt.editor.client.Editor.Path;
import com.rnb2.gwt1.data.idsugdt.DocumentDictionary;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface DocumentDictionaryProperties extends PropertyAccess<DocumentDictionary> {
	@Path("id")
	ModelKeyProvider<DocumentDictionary> key();

	@Path("dictionary")
	LabelProvider<DocumentDictionary> dictionary();
}