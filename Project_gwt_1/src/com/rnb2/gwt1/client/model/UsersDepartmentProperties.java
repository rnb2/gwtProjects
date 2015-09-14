package com.rnb2.gwt1.client.model;

import com.google.gwt.editor.client.Editor.Path;
import com.rnb2.gwt1.data.idsugdt.proxy.UsersDepartmentProxy;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface UsersDepartmentProperties extends PropertyAccess<UsersDepartmentProxy> {
	
	@Path(value = "id")
	ModelKeyProvider<UsersDepartmentProxy> key();
	
	@Path("name")
	LabelProvider<UsersDepartmentProxy> nameLabel();
	
	ValueProvider<UsersDepartmentProxy, String> name();
	ValueProvider<UsersDepartmentProxy, String> roles();
	
}
