package com.rnb.plategka.client.model.combo;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/**
 * 
 * @author budukh-rn
 *
 */
public interface YearProperties extends PropertyAccess<Integer> {
	@Path("id")
	ModelKeyProvider<Integer> key();

	@Path("fullName")
	LabelProvider<Integer> name();
}