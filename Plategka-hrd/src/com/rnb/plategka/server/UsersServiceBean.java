/**
 * 
 */
package com.rnb.plategka.server;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.rnb.plategka.client.UsersService;

/**
 * @author budukh-rn
 * 15 апр. 2015 г.	
 *
 */
public class UsersServiceBean extends RemoteServiceServlet implements
		UsersService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(UsersServiceBean.class.getName());

	/**
	 * 
	 */
	public UsersServiceBean() {
	}

	/**
	 * @param delegate
	 */
	public UsersServiceBean(Object delegate) {
		super(delegate);
	}

	/**
	 * 16.04.2015
	 */
	@Override
	public String logout() {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		if(user != null){
			LOGGER.info("---------------------------------------------");
			LOGGER.info("--------------------------------------------- this.getThreadLocalRequest().getAttributeNames().toString()=" + this.getThreadLocalRequest().getAttributeNames().toString());
			LOGGER.info("--------------------------------------------- this.getServletContext().getAttributeNames();=" + this.getServletContext().getAttributeNames());
			this.getServletContext().setAttribute("user", null);
			this.getThreadLocalRequest().setAttribute("user", null);
			//PageContext context = getj
			//return userService.createLogoutURL(this.getThreadLocalRequest().getRequestURI());
			return "/login.jsp";
		}	
		
		return "";
	}
	
	/* (non-Javadoc)
	 * @see com.rnb.plategka.client.UsersService#getUserName()
	 */
	@Override
	public String getUserName() {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		if(user == null)
			return null;
		
		return user.getEmail();
		
	}


	
}
