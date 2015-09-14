package com.rnb2.gwt1.client.model.combo;

import com.google.gwt.editor.client.Editor.Path;
import com.rnb2.gwt1.shared.ServerProxy;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface ServerProperties extends PropertyAccess<ServerProxy> {
	@Path("id")
	ModelKeyProvider<ServerProxy> key();

	@Path("shortName")
	LabelProvider<ServerProxy> fullName();
}