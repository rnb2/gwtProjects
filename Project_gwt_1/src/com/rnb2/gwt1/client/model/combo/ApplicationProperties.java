package com.rnb2.gwt1.client.model.combo;

import com.google.gwt.editor.client.Editor.Path;
import com.rnb2.gwt1.data.pm.proxy.ApplicationProxy;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface ApplicationProperties extends PropertyAccess<ApplicationProxy> {
	@Path("id")
	ModelKeyProvider<ApplicationProxy> key();

	@Path("fullName")
	LabelProvider<ApplicationProxy> fullName();
}