/**
 * 
 */
package com.rnb2.gwt1.client.model;

import com.google.gwt.editor.client.Editor.Path;
import com.rnb2.gwt1.data.pm.proxy.UserProxy;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/**
 * @author budukh-rn
 *
 */
public interface UserXlsProperties extends PropertyAccess<UserProxy> {

	@Path(value = "loginName")
	ModelKeyProvider<UserProxy> key();
	
	ValueProvider<UserProxy, String> loginName();
	ValueProvider<UserProxy, String> fullName();
}
