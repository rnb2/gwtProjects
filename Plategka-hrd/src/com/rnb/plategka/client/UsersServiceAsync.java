/**
 * 
 */
package com.rnb.plategka.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author budukh-rn
 * 15 апр. 2015 г.	
 *
 */
public interface UsersServiceAsync {

	/**
	 * 
	 * @see com.rnb.plategka.client.UsersService#getUserName()
	 */
	void getUserName(AsyncCallback<String> callback);

	void logout(AsyncCallback<String> callback);

}
