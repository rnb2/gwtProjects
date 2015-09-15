/**
 * 
 */
package com.rnb2.gwt1.client.model;

import com.rnb2.gwt1.data.pm.proxy.AclPermissionProxy;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/**
 * @author budukh-rn
 *
 */
public interface AclPermProperties extends PropertyAccess<AclPermissionProxy> {

	/*
	 * ModelKeyProvider in TableAclPerm.java 
	 * */

	ValueProvider<AclPermissionProxy, String> principal();
	ValueProvider<AclPermissionProxy, String> entityId();
	ValueProvider<AclPermissionProxy, String> appId();
	ValueProvider<AclPermissionProxy, String> accessType();
	ValueProvider<AclPermissionProxy, String> property();
	ValueProvider<AclPermissionProxy, String> operationType();
	ValueProvider<AclPermissionProxy, String> permissiomValue();
}
