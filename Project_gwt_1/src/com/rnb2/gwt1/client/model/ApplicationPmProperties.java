package com.rnb2.gwt1.client.model;

import com.google.gwt.editor.client.Editor.Path;
import com.rnb2.gwt1.data.pm.proxy.ApplicationProxy;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface ApplicationPmProperties extends PropertyAccess<ApplicationProxy> {

	@Path(value = "id")
	ModelKeyProvider<ApplicationProxy> key();
	
	@Path("fullName")
	LabelProvider<ApplicationProxy> nameLabel();

	ValueProvider<ApplicationProxy, String> shortName();

	ValueProvider<ApplicationProxy, String> fullName();

	ValueProvider<ApplicationProxy, String> description();

	ValueProvider<ApplicationProxy, String> architect();

	ValueProvider<ApplicationProxy, String> programmer();
	
	ValueProvider<ApplicationProxy, String> idString();

}
