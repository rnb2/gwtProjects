package com.rnb.plategka.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * @author budukh-rn
 * 15 апр. 2015 г.	
 *
 */
@RemoteServiceRelativePath("usersService")
public interface UsersService extends RemoteService {
	String getUserName();
	String logout();
}
