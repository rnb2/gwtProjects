package com.rnb2.gwt1.client.model;

import com.google.gwt.editor.client.Editor.Path;
import com.rnb2.gwt1.data.idsugdt.proxy.UsersProxy;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface UserIdsProperties extends  PropertyAccess<UsersProxy>{

	@Path(value = "id")
	ModelKeyProvider<UsersProxy> key();
	
	@Path("name")
	LabelProvider<UsersProxy> nameLabel();
	
	ValueProvider<UsersProxy, String> name();
	ValueProvider<UsersProxy, String> fio();
}
