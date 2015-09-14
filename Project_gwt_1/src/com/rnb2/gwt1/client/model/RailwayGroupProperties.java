package com.rnb2.gwt1.client.model;

import com.google.gwt.editor.client.Editor.Path;
import com.rnb2.gwt1.data.idsugdt.proxy.RailwayGroupProxy;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface RailwayGroupProperties extends PropertyAccess<RailwayGroupProxy> {
	
	@Path(value = "id")
	ModelKeyProvider<RailwayGroupProxy> key();
	
	@Path("fullNameSt")
	LabelProvider<RailwayGroupProxy> nameLabel();
	
	ValueProvider<RailwayGroupProxy, String> fullNameSt();
	ValueProvider<RailwayGroupProxy, String> fullNameType();
	
}
