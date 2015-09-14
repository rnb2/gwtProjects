package com.rnb2.gwt1.client.model;

import com.google.gwt.editor.client.Editor.Path;
import com.rnb2.gwt1.data.idsugdt.Country;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface CountryProperties extends PropertyAccess<Country> {

	@Path(value = "id")
	ModelKeyProvider<Country> key();
	
	@Path("fullName")
	LabelProvider<Country> nameLabel();
	
	ValueProvider<Country, String> fullName();
	
	ValueProvider<Country, String> shortName();
	
	ValueProvider<Country, String> codeISOA2();
	
	ValueProvider<Country, String> codeISOA3();

	ValueProvider<Country, String> codeNUM();
}
