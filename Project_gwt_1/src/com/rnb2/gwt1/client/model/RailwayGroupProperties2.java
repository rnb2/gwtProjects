package com.rnb2.gwt1.client.model;

import com.google.gwt.editor.client.Editor.Path;
import com.rnb2.gwt1.data.idsugdt.RailwayGroup;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface RailwayGroupProperties2 extends PropertyAccess<RailwayGroup> {
	
	@Path(value = "id")
	ModelKeyProvider<RailwayGroup> key();
	
	@Path("fullName")
	LabelProvider<RailwayGroup> nameLabel();
	
	ValueProvider<RailwayGroup, String> fullName();

	
}
