package com.rnb.plategka.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * @author budukh-rn
 * 15 ���. 2015 �.	
 *
 */
@RemoteServiceRelativePath("usersService")
public interface UsersService extends RemoteService {
	String getUserName();
	String logout();
}
