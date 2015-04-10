package com.rnb.plategka.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.util.logging.Logger;

public class LoginServ extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(LoginServ.class.getName());

	public LoginServ() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.info("--------------------------------LoginServ: do post");   

		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        String guestbookName = req.getParameter("guestbookName");
        /*Key guestbookKey = KeyFactory.createKey("Guestbook", guestbookName);
        String content = req.getParameter("content");
        Date date = new Date();
        Entity greeting = new Entity("Greeting", guestbookKey);
        greeting.setProperty("user", user);
        greeting.setProperty("date", date);
        greeting.setProperty("content", content);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(greeting);*/

        resp.sendRedirect("/login.jsp?guestbookName=" + guestbookName);
	}

}
