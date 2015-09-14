package com.rnb2.gwt1.client.model.combo;

import com.google.gwt.editor.client.Editor.Path;
import com.rnb2.gwt1.data.idsugdt.proxy.DepartmentProxy;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface DepartmentsProperties extends PropertyAccess<DepartmentProxy> {
	@Path("id")
	ModelKeyProvider<DepartmentProxy> key();

	@Path("fullName")
	LabelProvider<DepartmentProxy> fullName();
}