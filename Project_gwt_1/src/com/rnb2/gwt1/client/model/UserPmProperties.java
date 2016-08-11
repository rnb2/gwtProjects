/**
 * 
 */
package com.rnb2.gwt1.client.model;

import com.google.gwt.editor.client.Editor.Path;
import com.rnb2.gwt1.data.pm.proxy.UserProxy;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/**
 * @author budukh-rn
 *
 */
public interface UserPmProperties extends PropertyAccess<UserProxy> {

	@Path(value = "loginName")
	ModelKeyProvider<UserProxy> key();
	
	@Path("fullName")
	LabelProvider<UserProxy> nameLabel();
	
	ValueProvider<UserProxy, String> loginName();
	ValueProvider<UserProxy, String> fullName();
	ValueProvider<UserProxy, String> workPhone();
	ValueProvider<UserProxy, String> employeeID();
	ValueProvider<UserProxy, String> company();
	ValueProvider<UserProxy, String> department();
}
