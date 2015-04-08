package com.rnb.plategka.client.model;

import com.google.gwt.editor.client.Editor.Path;
import com.rnb.plategka.data.ConstantsProxy;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface ConstantsProperties extends PropertyAccess<ConstantsProxy> {

	@Path(value = "id")
	ModelKeyProvider<ConstantsProxy> id();
	
	ValueProvider<ConstantsProxy, Integer> lightPredel();
	
	ValueProvider<ConstantsProxy, Double> water();
	
	ValueProvider<ConstantsProxy, Double> light();

	ValueProvider<ConstantsProxy, Double> lightMore();
	
	ValueProvider<ConstantsProxy, Integer> yearOfPay();

	ValueProvider<ConstantsProxy, String> date();
}
