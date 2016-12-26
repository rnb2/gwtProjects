package com.rnb2.gwt1.server;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.rnb2.gwt1.client.ManageService;
import com.rnb2.gwt1.data.idsugdt.Country;
import com.rnb2.gwt1.data.idsugdt.Department;
import com.rnb2.gwt1.data.idsugdt.DocumentDictionary;
import com.rnb2.gwt1.data.idsugdt.DocumentPermission;
import com.rnb2.gwt1.data.idsugdt.DocumentRoles;
import com.rnb2.gwt1.data.idsugdt.EdcPermissionRoles;
import com.rnb2.gwt1.data.idsugdt.EntityDictionary;
import com.rnb2.gwt1.data.idsugdt.EntityPermission;
import com.rnb2.gwt1.data.idsugdt.EntityRoles;
import com.rnb2.gwt1.data.idsugdt.RailwayGroup;
import com.rnb2.gwt1.data.idsugdt.Users;
import com.rnb2.gwt1.data.idsugdt.UsersDepartment;
import com.rnb2.gwt1.data.idsugdt.proxy.DepartmentProxy;
import com.rnb2.gwt1.data.idsugdt.proxy.RailwayGroupProxy;
import com.rnb2.gwt1.data.idsugdt.proxy.UsersDepartmentProxy;
import com.rnb2.gwt1.data.idsugdt.proxy.UsersProxy;
import com.rnb2.gwt1.data.pm.AclPermission;
import com.rnb2.gwt1.data.pm.Application;
import com.rnb2.gwt1.data.pm.LdapPrincipal;
import com.rnb2.gwt1.data.pm.LdapUtil;
import com.rnb2.gwt1.data.pm.Permission;
import com.rnb2.gwt1.data.pm.User;
import com.rnb2.gwt1.data.pm.proxy.AclPermissionProxy;
import com.rnb2.gwt1.data.pm.proxy.ApplicationProxy;
import com.rnb2.gwt1.data.pm.proxy.ApplicationProxyFull;
import com.rnb2.gwt1.data.pm.proxy.PermissionProxy;
import com.rnb2.gwt1.data.pm.proxy.PermissionProxyFull;
import com.rnb2.gwt1.data.pm.proxy.UserProxy;
import com.rnb2.gwt1.server.utils.Constants;

public class ManageImpl extends RemoteServiceServlet implements ManageService {
	
	private static final String COM_RNB2_FILTER_BASE_KEY = "com.rnb2.filterBase.key";

	private static final int maxResults = 250;

	@PersistenceContext(unitName = "ugdtEm")
	private EntityManager emIds;

	@PersistenceContext(unitName = "ugdtEmTest")
	private EntityManager emIdsTest;
			
	private final String getUserPMEmptyEmploee = "select o from User o where o.employeeID is null";
	private final String getUserPMbyName = "select o from User o where o.loginName = :param1";
	private final String getUserPMbyname = "select o from User o left join fetch o.permissions where o.loginName = :param1";
	private final String getUserPMbynameFetch = "select o from User o "
			+ "left join fetch o.permissions "
			+ "left join fetch o.profiles "
			+ "left join fetch o.aclPermissions "
			+ "where o.loginName = :param1";
	private final String getUserPMLitebyname = "select o.id as id, o.loginName as loginName, o.fullName as fullName, o.workPhone as workPhone,	o.employeeID as employeeID from User o where upper(o.loginName) like :param1";
	private final String getUserPMLitebyFullName = "select o.id as id, o.loginName as loginName, o.fullName as fullName, o.workPhone as workPhone, o.employeeID as employeeID from User o where upper(o.fullName) like :param1";
	private final String getUserPMLite = "select o.id as id, o.loginName as loginName, o.fullName as fullName, o.workPhone as workPhone, o.employeeID as employeeID from User o";
	private final String getApplicationPm = "select distinct o.id as id, o.fullName as fullName, o.shortName as shortName, o.programmer as programmer, o.architect as architect from Application o inner join o.permissions p inner join p.users u with u.loginName = :param1";
	private final String getApplicationPmPermission = "select  distinct p.id as id, p.name as name, p.description as description, o.shortName as shortName from com.rnb2.gwt1.data.pm.Application o "+
														"inner join o.permissions p "+
														"inner join p.users u with u.loginName = :param1 "+
														"where o.shortName = :param2";
	private final String getApplicationPmPermissionAll = "select  distinct p.id as id, p.name as name, p.description as description, o.shortName as shortName from Application o "+
			"inner join o.permissions p "+
			"where o.shortName = :param1 and p.id not in(:param2)";
	private final String getApplicationPmAll = "select distinct o.id as id, o.fullName as fullName, o.shortName as shortName, o.programmer as programmer, o.architect as architect from Application o";	
	private final String getApplicationPmAllUser = "select distinct o.id as id, o.fullName as fullName, o.shortName as shortName, o.programmer as programmer, o.architect as architect " +
							"from Application o where o.id not in(:param1)";
	
	private final String findUserIdsQuery = "select o.id as id, o.name as name, o.fio as fio, o.dateInput as dateInput, o.username as userInput from Users as o where o.name like :param1";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<String, Object> params = new HashMap<String, Object>(2);

	private final String queryNativeAclPermissionBylogin = "select * from ACL_PERMISSION where PRINCIPAL = :param1";

	private final String getUserIdsByLoginFetch = "	select o from Users as o join fetch o.railwayGroup rg where o.name = :param1";
	
	
	/**
	 * 
	 * @param serverName
	 * @return
	 */
	private EntityManager getEntityManagerByServerName(String serverName) {
		switch (serverName) {
		case Constants.server_name_jboss_5:
			return emIds;
		case Constants.server_name_jboss_01:
			return emIdsTest;

		default:
			return emIds;
		}
	}
	
	/**
	 * Синхранизация полей пользователя из AD
	 * @param loginName
	 * @param serverName
	 * @return
	 */
	public String syncUsersFromAD(Map<String, String> environment, String loginName, String serverName){
		SessionFactory factory = getSessionFactoryByServer(serverName);
		
		params.clear();
		params.put("param1", loginName);
		List<User> users = runHibernateNamedQuery(getUserPMbyName, null, params, false, factory.getCurrentSession());
						
		for (User user : users) {
			List<UserProxy> list = searchUserAd(environment, user.getLoginName(), null, null);
			if(list.isEmpty()){
				//System.out.println("user... " + user.getLoginName()  +" not found in AD!!!");
				continue;
			}
						
			UserProxy userProxy = list.get(0);
			user.setEmployeeID(userProxy.getEmployeeID());
			user.setFullName(userProxy.getFullName());
			user.setWorkPhone(userProxy.getWorkPhone());
			mergeUserPm(user, user.getId().longValue(), factory.getCurrentSession());
			
		}
	
	return "1";
	}
	
	/**
	 * Синхранизация полей пользователй из AD(EmployeeID, FullName)
	 * 06.10.2015
	 * @param serverName
	 * @return
	 */
	public String syncUsersFromAD(Map<String, String> environment, String serverName){
			SessionFactory factory = getSessionFactoryByServer(serverName);
			List<User> users = runHibernateNamedQuery(getUserPMEmptyEmploee, null, null, false, factory.getCurrentSession());

			for (User user : users) {
				List<UserProxy> list = searchUserAd(environment, user.getLoginName(), null, null);
				if(list.isEmpty()){
					//System.out.println("user... " + user.getLoginName()  +" not found in AD!!!");
					continue;
				}
				
				UserProxy userProxy = list.get(0);
				user.setEmployeeID(userProxy.getEmployeeID());
				user.setFullName(userProxy.getFullName());
				mergeUserPm(user, user.getId().longValue(), factory.getCurrentSession());
				
			}
	
		
		
		return "1";
	}
		
	/**
	 * 
	 * Копирование пользователей
	 * @param list
	 * @param serverName
	 * @param userNameCreated пользователь выполняющий операцию
	 * @return
	 */
	public String addUserCopyPmAll(List<UserProxy> list, String serverName, String userNameCreated){
		String result ="-1";
		SessionFactory sessionFactoryPm = getSessionFactoryByServer(serverName);
		SessionFactory sessionFactoryIDS = getSessionFactoryIDSByServer(serverName);
		for (UserProxy p : list) {
			result = addUserCopyPm(p.getFullName(), p.getLoginName(), serverName, sessionFactoryPm, sessionFactoryIDS, userNameCreated);
		}
		
		return result;
	}
	
	/**
	 * получение пользователей из файла
	 */
	public List<UserProxy> readFileXls(String fileName, int rangeBegin, int rangeEnd, 
			int columnIndexLoginNameOld, int columnIndexLoginNameNew){
				
		Object attribute = getServletContext().getAttribute(Constants.contextAttributeStreamXlsFile);
		
		if(attribute == null)
			return new ArrayList<UserProxy>();
		
		InputStream inputStream = (InputStream) attribute;
		
        List<UserProxy> proxyList = new ArrayList<UserProxy>();	
        Workbook workbook = null;
        try {
			workbook = new HSSFWorkbook(inputStream);
			
			int numberOfSheets = 1;//workbook.getNumberOfSheets();
			for (int i = 0; i < numberOfSheets; i++) {
				Sheet sheetAt = workbook.getSheetAt(i);
				Iterator<Row> iteratorRow = sheetAt.iterator();
				feelFromXls(proxyList, iteratorRow, rangeBegin, rangeEnd, columnIndexLoginNameOld, columnIndexLoginNameNew);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
              if(inputStream != null)
                 {   
                    try{ 
                    	if(workbook != null)
                    		workbook.close();
                    	
                    	inputStream.close();
                  
                    } catch(Exception e){ e.printStackTrace(); } 
                 }
			getServletContext().removeAttribute(Constants.contextAttributeStreamXlsFile);
		}
        
        return proxyList;
	}
	
	public List<UserProxy> readFileXlsLocal(String fileName, int rangeBegin, int rangeEnd, 
			int columnIndexLoginNameOld, int columnIndexLoginNameNew){
       InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);
		
        List<UserProxy> proxyList = new ArrayList<UserProxy>();	
        Workbook workbook = null;
        try {
			workbook = new HSSFWorkbook(inputStream);
			
			int numberOfSheets = 1;//workbook.getNumberOfSheets();
			for (int i = 0; i < numberOfSheets; i++) {
				Sheet sheetAt = workbook.getSheetAt(i);
				Iterator<Row> iteratorRow = sheetAt.iterator();
				feelFromXls(proxyList, iteratorRow, rangeBegin, rangeEnd, columnIndexLoginNameOld, columnIndexLoginNameNew);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(workbook != null)
					workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			getServletContext().removeAttribute("com.rnb2.gwt1.streamFile.xls");
		}
        
        return proxyList;
	}
	
	/**
	 * 
	 * @param proxyList
	 * @param iteratorRow
	 */
	private void feelFromXls(List<UserProxy> proxyList,
			Iterator<Row> iteratorRow, 
			int rangeBegin, int rangeEnd, 
			int columnIndexLoginNameOld, int columnIndexLoginNameNew) {
		
		while (iteratorRow.hasNext()) {
			UserProxy proxy = new UserProxy();
			Row row = iteratorRow.next();
			Iterator<Cell> cellIterator = row.cellIterator();

			if(row.getRowNum() > rangeEnd -1){
				break;
			}
			
			if(row.getRowNum() >= (rangeBegin -1)){
			
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();


			        if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
			        	
			            if (cell.getColumnIndex() == columnIndexLoginNameOld) {
			            	proxy.setLoginName(cell.getStringCellValue());
			            } else if(cell.getColumnIndex() == columnIndexLoginNameNew){
			            	proxy.setFullName(cell.getStringCellValue());
			            } 
	
			        } 
					
				}//cell
				proxyList.add(proxy);
			}
			
		}//row
	}
	
	public String getUserName(){
		String ss = System.getProperty("user.name");
		
		HttpSession session = getThreadLocalRequest().getSession(false);
		//if(session != null){
		//	Object attribute = session.getAttribute("loged_user");
			//System.out.println("attribute: loged_user=" + attribute);
		//}
				
		ss = getThreadLocalRequest().getRemoteUser();
		//final String localName = getThreadLocalRequest().getLocalName();
		//final String remoteAddr = getThreadLocalRequest().getRemoteAddr();
		//final String remoteHost = getThreadLocalRequest().getRemoteHost();
		//String requestURI = getThreadLocalRequest().getRequestURI();
		//Principal nameUserPrincipal = getThreadLocalRequest().getUserPrincipal();
		
			
		//System.out.println("getRemoteUser:"+ss);
		//System.out.println("remoteAddr:"+remoteAddr);
		//System.out.println("localName:"+localName);
		//System.out.println("remoteHost:"+remoteHost);
		//System.out.println("requestURI:"+requestURI);
		//System.out.println("UserPrincipal:"+nameUserPrincipal);
		//if(nameUserPrincipal !=null)
			//System.out.println("UserPrincipal:"+nameUserPrincipal.getName());
		return ss;
	}
	
	/**
	 * 15.09.2015 получение списка ACL
	 * @param loginName
	 * @param serverName
	 * @return
	 */
	public List<AclPermissionProxy> getAclPermissionList(String loginName, String serverName){
		List<AclPermissionProxy> list = new ArrayList<AclPermissionProxy>();
		
		params.clear();
		params.put("param1", loginName);
		List<Object[]> list2 = runHibernateNativedQuery(queryNativeAclPermissionBylogin, params, true, serverName);
		
		for (Object[] o : list2) {
			list.add(new AclPermissionProxy(o));
		}
		
		return list;
	}
	
	public void addRole(Permission obj, Integer appId, String serverName){
		Session session = null;
		try {
			SessionFactory factory = getSessionFactoryByServer(serverName);
			session = factory.getCurrentSession();

			session.beginTransaction();

			Application application = (Application) session.get(Application.class, appId);
			
			Permission permission = new Permission();
			permission.setName(obj.getName());
			permission.setDescription(obj.getDescription());
			permission.setApplication(application);
	
			session.save(permission);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
				System.err.println("error in addEntity: Permission " + obj.getName()
						+ " has already been added!");
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

	}
	
	public void addApplicaion(Application obj, String serverName){
		addEntityPm(obj, serverName);
	}
	
	public void mergeUserPm(User userProxy, Integer userId, String serverName){
		mergeUserPm(userProxy, userId.longValue(), serverName);
	}
	
	/**
	 * Удаление ACLpermission у пользователя JBoss
	 * @param userName
	 */
	private void deleteUserPmAclPermissionNative(String userName, String serverName){
		
		SessionFactory sessionFactory = null;
		Session session = null;
		try {
			sessionFactory = getSessionFactoryByServer(serverName);
			session = sessionFactory.getCurrentSession();
			final Transaction transaction = session.beginTransaction();

			try {
				String nativeQuery = "DELETE FROM ACL_PERMISSION WHERE PRINCIPAL = :param1";									
				org.hibernate.Query query = session.createSQLQuery(nativeQuery).setParameter("param1", userName);
				query.executeUpdate();
		
				transaction.commit();
			   
			  } catch (Exception ex) {
				  transaction.rollback();
				  ex.printStackTrace();
				  System.err.println("deleteUserPmAclPermission: delete ACL native: " + ex.getMessage());
			}
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}
	
	/**
	 * Удаление пользователя из схемы JBoss, c Aclpermission (native)
	 * 10.09.2015
	 * @param userId
	 * @param login
	 */
	public void deleteUserPm(Integer userId, String userName, String serverName){
		deleteUserPmAclPermissionNative(userName, serverName);
		deleteEntityPm(new User(), userId.longValue(), serverName);
	}
	
	public void addUserPm(User user, String serverName){
		addEntityPm(user, serverName);
	}
	
	public void addUserPm2(String userName, String fio, String employeeID, String phone, String serverName) {
		User user = new User();
		user.setLoginName(userName);
		user.setFullName(fio);
		user.setEmployeeID(employeeID);;
		user.setWorkPhone(phone);

		addEntityPm(user, serverName);
	}
	

	
	/**
	 * 19.11.2015 updated, add - sessionFactoryIDS, remove - servername  
	 * 05.10.2015
	 * Копирование пользователя в табл. ИДС УЖДТ
	 * @param userNameNew
	 * @param userNameOld
	 * @param sessionFactoryIDS
	 * @param userNameCreated 
	 * @return
	 */
	private boolean copyUserIds(String userNameNew, String userNameOld, SessionFactory sessionFactoryIDS, String userNameCreated){

		Session session = null;
		boolean result = false;
		try {
			params.clear();
			params.put("param1", userNameOld);
			List<Users> resultList = runHibernateNamedQuery(getUserIdsByLoginFetch, null, params, true, sessionFactoryIDS.getCurrentSession());
		
		
			if(resultList.isEmpty()){
				System.err.println("copy UserIds: " + userNameOld + ", user is not found!!!");
				return result;
			}
			
			//check for dublicate user by userNameNew
			params.clear();
			params.put("param1", userNameNew);
			List<Users> resultList2 = runHibernateNamedQuery(getUserIdsByLoginFetch, null, params, true, sessionFactoryIDS.getCurrentSession());
			if(!resultList2.isEmpty()){
				System.err.println("copy UserIds: found userNameNew=" +resultList2.get(0).getName() +" in IDS. User:" + userNameNew + " not will be added to IDS!!!");
				return result;
			}
			//--
			
			Users userOld = resultList.get(0);
	
			session = sessionFactoryIDS.getCurrentSession();

			session.beginTransaction();

			Users users = new Users();
			users.setName(userNameNew);
			users.setFio(userOld.getFio());
			users.setUsername(userNameCreated);
			users.getRailwayGroup().addAll(userOld.getRailwayGroup());
			users.getUsersDepartment().addAll(userOld.getUsersDepartment());
			users.getUsersDocumentPermitions().addAll(userOld.getUsersDocumentPermitions());
			users.getUsersEntityPermitions().addAll(userOld.getUsersEntityPermitions());
			users.getUsersDepartmentUz().addAll(userOld.getUsersDepartmentUz());
			
			session.save(users);
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
			System.err.println("error in copy UserIds userOld:" + userNameOld +" to new user: "+userNameNew);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return result;

	}


	
	/**
	 * @param sessionFactoryIDS 
	 */
	/**
	 * update 19.11.2015
	 * 28.09.2015
	 * Копирование пользователя
	 * @param userNameNew
	 * @param userNameOld
	 * @param serverName
	 * @param sessionFactoryPm
	 * @param sessionFactoryIDS
	 * @param userNameCreated 
	 * @return
	 */
	private String addUserCopyPm(String userNameNew, String userNameOld, String serverName, SessionFactory sessionFactoryPm, SessionFactory sessionFactoryIDS, String userNameCreated) {
						
		params.clear();
		params.put("param1", userNameOld);
		List<User> list = runHibernateNamedQuery(getUserPMbynameFetch, null, params, false, sessionFactoryPm.getCurrentSession()); //executeHibernateNamedQueryAll(getUserPMbynameFetch, null, params, serverName);		

		if (list.isEmpty()) {
			boolean copyUserIds = false;
			if(serverName.equals(Constants.server_name_jboss_5) || serverName.equals(Constants.server_name_jboss_01)){
				copyUserIds = copyUserIds(userNameNew, userNameOld, sessionFactoryIDS, userNameCreated);
			}
			return "addUserCopyPm: ----- add to IDS = " + copyUserIds;
		};
		
		if (!list.isEmpty()) {
			//check for dublicate user by userNameNew
			params.clear();
			params.put("param1", userNameNew);
			List<User> list2 = runHibernateNamedQuery(getUserPMbyName, null, params, false, sessionFactoryPm.getCurrentSession());
			if (!list2.isEmpty()) {
				return "addUserCopyPm: found userNameNew=" +list2.get(0).getLoginName() +" in PM. User:" + userNameNew + " not will be added to " + serverName + " !!!";
			};
			//--
			
	
			User userOld = list.get(0);
	
			User userNew = new User();
			userNew.setLoginName(userNameNew);
			userNew.setFullName(userOld.getFullName());
			userNew.setWorkPhone(userOld.getWorkPhone());
			userNew.setEmployeeID(userOld.getEmployeeID());
			userNew.getProfiles().addAll(userOld.getProfiles());
		
			addUserPm(userOld, userNew, sessionFactoryPm);
	
			
			if(!userOld.getAclPermissions().isEmpty()){
				addUserPmAclPermissionNative(userNameNew, userOld, sessionFactoryPm.getCurrentSession());
			}
			
			//copy user IDS
			if(serverName.equals(Constants.server_name_jboss_5) || serverName.equals(Constants.server_name_jboss_01)){
				copyUserIds(userNameNew, userNameOld, sessionFactoryIDS, userNameCreated);
			}	
			//--
		}
		
		return "1";
	}

	/**
	 * 19.11.2015 updated add - userNameCreated
	 * 08.09.2015
	 * Копирование пользователя
	 * @param userNameNew
	 * @param fio
	 * @param phone
	 * @param employeeId
	 * @param userNameOld
	 * @param serverName
	 * @param userNameCreated
	 * @return
	 */
	public String addUserCopyPm(String userNameNew, String fio, String phone, String employeeId, 
			String userNameOld, String serverName, String userNameCreated) {
		
		SessionFactory sessionFactoryPM = getSessionFactoryByServer(serverName);
		SessionFactory sessionFactoryIDS = getSessionFactoryIDSByServer(serverName);
		

		params.clear();
		params.put("param1", userNameOld);
		List<User> list = runHibernateNamedQuery(getUserPMbynameFetch, null, params, false, sessionFactoryPM.getCurrentSession()); 
		


		if (list.isEmpty()) {
			boolean copyUserIds = false;
			if(serverName.equals(Constants.server_name_jboss_5) || serverName.equals(Constants.server_name_jboss_01)){
				copyUserIds = copyUserIds(userNameNew, userNameOld, sessionFactoryIDS, userNameCreated);
			}

			return "1";
		};

		if (!list.isEmpty()) {
			
			User userOld = list.get(0);
	
			User userNew = new User();
			userNew.setLoginName(userNameNew);
			userNew.setFullName(fio);
			userNew.setWorkPhone(phone);
			userNew.setEmployeeID(employeeId);
			userNew.getProfiles().addAll(userOld.getProfiles());
			//userNew.getPermissions().addAll(userOld.getPermissions());
		
			addUserPm(userOld, userNew, sessionFactoryPM);
	
			
			if(!userOld.getAclPermissions().isEmpty()){
				addUserPmAclPermissionNative(userNameNew, userOld, sessionFactoryPM.getCurrentSession());
			}
			
			//IDS
			
			if(serverName.equals(Constants.server_name_jboss_5) || serverName.equals(Constants.server_name_jboss_01)){
				copyUserIds(userNameNew, userNameOld, sessionFactoryIDS, userNameCreated);
			}	
			//--
		}

		return "1";
	}
	
	/**
	 * 09.09.2015
	 * Добавление пользователю AclPermission
	 * @param userNameNew
	 * @param userOld
	 */
	private void addUserPmAclPermissionNative(String userNameNew, User userOld, Session session) {
		
		try {
			final Transaction transaction = session.beginTransaction();
			try {
			    // The real work is here
				for (AclPermission aclPermission : userOld.getAclPermissions()) {
					String nativeQuery = "INSERT INTO ACL_PERMISSION (principal, entity_id, app_id, access_type, property, operation_type, permissiom_value) "
									+ "VALUES (:param1, :param2, :param3, :param4, :param5, :param6, :param7)";
					
					String accessType = aclPermission.getId().getAccessType().toString();

					String opertationType = aclPermission.getOperationType().toString();
					 
					org.hibernate.Query query = session.createSQLQuery(nativeQuery);
						query.setParameter("param1", userNameNew);
						query.setParameter("param2", aclPermission.getId().getEntityId());
						query.setParameter("param3", aclPermission.getId().getAppId());
						query.setParameter("param4", accessType.trim());
						query.setParameter("param5", aclPermission.getId().getProperty());
						query.setParameter("param6", opertationType.trim());
						query.setParameter("param7", aclPermission.getPermissionValue());
						query.executeUpdate();
	
				}
			    transaction.commit();
			   
			  } catch (Exception ex) {
				  transaction.rollback();
				  ex.printStackTrace();
				  System.err.println("update ACL native: " + ex.getMessage());
			}
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}
	
	/**
	 * 13.10.2015
	 * @param userOld
	 * @param userNew
	 * @param session
	 */
	private void addUserPm(User userOld, User userNew, SessionFactory sessionFactory) {
		List<ApplicationProxy> userAppList = getApplicationPmList(userOld.getLoginName(), sessionFactory.getCurrentSession());
		
		Session session = sessionFactory.getCurrentSession();
		try{
			session.beginTransaction();
		
			List<Integer> userAppLongList = new ArrayList<Integer>(userAppList.size());
			for(ApplicationProxy pr: userAppList){
				userAppLongList.add(pr.getId());
			}

			
			for(Integer id: userAppLongList){
				Application application = (Application) session.get(Application.class, id);

				for(Permission p : application.getPermissions()){
				
					if(userOld.getPermissions().contains(p)){
						userNew.getPermissions().add(p);
					}
				}
			}
			session.save(userNew);
			session.getTransaction().commit();
	
		} catch (Exception e) {

			e.printStackTrace();
		}finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	/**
	 * use addUserPm(User userOld, User userNew, Session session)
	 * @param userOld
	 * @param userNew
	 * @param serverName
	 */
	@Deprecated
	private void addUserPm(User userOld, User userNew, String serverName) {
		
		List<ApplicationProxy> userAppList = getApplicationPmList(userOld.getLoginName(), serverName);

		SessionFactory factory = getSessionFactoryByServer(serverName);
		Session session = factory.getCurrentSession();
		try{
			session.beginTransaction();
		
			List<Integer> userAppLongList = new ArrayList<Integer>(userAppList.size());
			for(ApplicationProxy pr: userAppList){
				userAppLongList.add(pr.getId());
			}

			
			for(Integer id: userAppLongList){
				Application application = (Application) session.get(Application.class, id);

				for(Permission p : application.getPermissions()){
				
					if(userOld.getPermissions().contains(p)){
		
						userNew.getPermissions().add(p);
					}
				}
			}
			session.save(userNew);
			session.getTransaction().commit();
	
		} catch (Exception e) {

			e.printStackTrace();
		}finally {
			if (session != null && session.isOpen()) {
				session.close();

			}
		}
		
	}
	
	/**
	 * Добавление приложения пользователю 
	 */
	public String addUserApplication(String login, String shortName, List<ApplicationProxy> list, String serverName){
		//TODO переделать инициализацию SessionFactory
		params.clear();
		params.put("param1", login);
		User user = (User) executeHibernateNamedQueryAll(getUserPMbyname, null, params, serverName).get(0);

		if(user == null){
			return "-1";
		}
		
		List<ApplicationProxy> userAppList = getApplicationPmList(login, serverName);
		
		List<Integer> userAppLongList = new ArrayList<Integer>(userAppList.size());
		for(ApplicationProxy pr: userAppList){
			userAppLongList.add(pr.getId());
		}
		
		List<Integer> proxyIdList = new ArrayList<Integer>(list.size());
		for(ApplicationProxy p:list){
			proxyIdList.add(p.getId());
		}
		
	try{
		SessionFactory factory = getSessionFactoryByServer(serverName);
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		
		User user2 = (User) session.get(User.class, user.getId());
		
		List<Integer> toAdd = new ArrayList<Integer>();
		if(proxyIdList.size() > userAppLongList.size()){
			toAdd = getDiffList(proxyIdList, userAppLongList);
			
			for(Integer id: toAdd){
				Application application = (Application) session.get(Application.class, id);
				user2.getPermissions().addAll(application.getPermissions());
			}

		}else if(userAppLongList.size() > proxyIdList.size()){
			toAdd = getDiffList(userAppLongList, proxyIdList);

			for(Integer id: toAdd){
				Application application = (Application) session.get(Application.class, id);
				user2.getPermissions().remove(application.getPermissions());
			}

		}
		

		if(!toAdd.isEmpty()){
			session.flush();
			session.getTransaction().commit();

		}
		
		if (session != null && session.isOpen()) {
			session.close();
		}

		
	} catch (HibernateException e) {
		e.printStackTrace();
	}	
		
		return "1";
	}
	
	/**
	 * Добавление ролей приложению  
	 */
	public String addUserPermision(String login, String shortName, List<PermissionProxy> list, String serverName){
		
		params.clear();
		params.put("param1", login);
		User user = (User) executeHibernateNamedQueryAll(getUserPMbyname, null, params, serverName).get(0);
		if(user == null){
			return "-1";
		}

		Collection<Permission> permissions = user.getPermissions();
		List<Integer> userPIdList = new ArrayList<Integer>(permissions.size());
		for(Permission p: permissions){
			if(p.getApplication().getShortName().equals(shortName))
				userPIdList.add(p.getId());
		}
	
		List<Integer> proxyIdList = new ArrayList<Integer>(list.size());
		for(PermissionProxy p:list){
			proxyIdList.add(p.getId());
			
		}

		try {
			SessionFactory factory = getSessionFactoryByServer(serverName);
			Session session = factory.getCurrentSession();
			session.beginTransaction();
			
			User user2 = (User) session.get(User.class, user.getId());

			List<Integer> toAdd = new ArrayList<Integer>();
			if(proxyIdList.size() > userPIdList.size()){
				toAdd = getDiffList(proxyIdList, userPIdList);
				for(Integer id: toAdd){
					Permission permission = (Permission) session.get(Permission.class, id);
					user2.getPermissions().add(permission);
				}
				
			}else if(userPIdList.size() > proxyIdList.size()){
				toAdd = getDiffList(userPIdList, proxyIdList);

				for(Integer id: toAdd){
					Permission permission = (Permission) session.get(Permission.class, id);
					user2.getPermissions().remove(permission);
				}

			}else if(!toAdd.isEmpty()){
				//remove All, then add All
				for(Integer id: userPIdList){
					Permission permission = (Permission) session.get(Permission.class, id);
					user2.getPermissions().remove(permission);
				}
				for(Integer id: proxyIdList){
					Permission permission = (Permission) session.get(Permission.class, id);
					user2.getPermissions().add(permission);
				}
			}
			if(!toAdd.isEmpty()){
				
				session.flush();
				session.getTransaction().commit();

			}
			
			if (session != null && session.isOpen()) {
				session.close();
			}

		} catch (HibernateException e) {
			e.printStackTrace();
		}		
		return "1";
	}
	
	
	
	/**
	 * 
	 * лист1 должен быть > лист2
	 * @param list1 
	 * @param list2
	 * @return
	 */
	private<T> List<T> getDiffList(List<T> list1, List<T> list2){
		List<T> list = new ArrayList<T>();
		for(T pr: list1){
			if(!list2.contains(pr))
				list.add(pr);
		}
		return list;
	}
	
	public ApplicationProxyFull getApplicationPmFull(String login, String serverName){
		
		List<ApplicationProxy> listUserApp = getApplicationPmList(login, serverName);
	
		List<Integer> listLong = new ArrayList<Integer>();
		listLong.add(-1);
		for(ApplicationProxy pr: listUserApp){
			listLong.add(pr.getId());
		}
		
		params.clear();
		params.put("param1", listLong);
		List<ApplicationProxy> listAppAll = new ArrayList<ApplicationProxy>();
		listAppAll = executeHibernateNamedQueryAll(getApplicationPmAllUser, ApplicationProxy.class, params, serverName);
		
		return new ApplicationProxyFull(listAppAll, listUserApp);
	}
	
	
	public PermissionProxyFull getApplicationPmPermissionFull(String login, String shortName, String serverName){
		
		List<PermissionProxy> list1 = new ArrayList<PermissionProxy>();
		List<PermissionProxy> list2 = new ArrayList<PermissionProxy>();
		if(shortName == null){
			return new PermissionProxyFull(list1, list2);
		}
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("param1", login);
		params.put("param2", shortName);
		
		list2 = executeHibernateNamedQueryAll(getApplicationPmPermission, PermissionProxy.class, params, serverName);
		
		List<Integer> listLong = new ArrayList<Integer>();
		listLong.add(-1);
		for(PermissionProxy pr: list2){
			listLong.add(pr.getId());
		}
		
		params = new HashMap<String, Object>(2);
		params.put("param1", shortName);
		params.put("param2", listLong);
		
		list1 = executeHibernateNamedQueryAll(getApplicationPmPermissionAll, PermissionProxy.class, params, serverName);
		
		return new PermissionProxyFull(list1, list2);
	}
	
	public List<PermissionProxy> getApplicationPmPermissionAll(String shortName, String serverName){
		params.clear(); 
		params.put("param1", shortName);
		List<PermissionProxy> list = new ArrayList<PermissionProxy>();
		list = executeHibernateNamedQueryAll(getApplicationPmPermissionAll, PermissionProxy.class, params, serverName);
		return list;
	}
	
	/**список разрешений у пользователя по приложению */
	public List<PermissionProxy> getApplicationPmPermission(String login, String shortName, String serverName){
		params.clear(); 
		params.put("param1", login);
		params.put("param2", shortName);
		List<PermissionProxy> list = new ArrayList<PermissionProxy>();
		list = executeHibernateNamedQueryAll(getApplicationPmPermission, PermissionProxy.class, params, serverName);
		return list;
	}
	
	public List<ApplicationProxy> getApplicationPmList2(String serverName){
		params.clear();
		List<ApplicationProxy> list = new ArrayList<ApplicationProxy>();

		list = executeHibernateNamedQueryAll(getApplicationPmAll, ApplicationProxy.class, null, serverName);
		return list;
	}
	
	/**
	 * 13.10.2015
	 * @param login
	 * @param session
	 * @return
	 */
	private List<ApplicationProxy> getApplicationPmList(String login, Session session){
		params.clear();
		params.put("param1", login);
		List<ApplicationProxy> list = new ArrayList<ApplicationProxy>();
		list = runHibernateNamedQuery(getApplicationPm, ApplicationProxy.class, params, false, session); //executeHibernateNamedQueryAll(getApplicationPm, ApplicationProxy.class, params, serverName);
		return list;
	}
	
	public List<ApplicationProxy> getApplicationPmList(String login, String serverName){
		params.clear();
		params.put("param1", login);
		List<ApplicationProxy> list = new ArrayList<ApplicationProxy>();
		list = executeHibernateNamedQueryAll(getApplicationPm, ApplicationProxy.class, params, serverName);
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<UsersDepartmentProxy> getUserDepartmentList(String login, String serverName){
		List<UsersDepartment> list = new ArrayList<UsersDepartment>();
		List<UsersDepartmentProxy> list2 = new ArrayList<UsersDepartmentProxy>();
		EntityManager em = getEntityManagerByServerName(serverName);
		list = em.createNamedQuery("getUserDepartmentByLogin").setParameter(1, login).setMaxResults(maxResults).getResultList();
		for(UsersDepartment o: list){
			list2.add(new UsersDepartmentProxy(o));
		}
		
		return list2;
	}
	
	@SuppressWarnings("unchecked")
	public List<DocumentPermission> getUserDocumentList(String login, String serverName){
		EntityManager em = getEntityManagerByServerName(serverName);
		return em.createNamedQuery("getUserDocumentByLogin").setParameter(1, login).setMaxResults(maxResults).getResultList();
	}

	/**
	 * Получение списка справочников по правам по ИДС УЖДТ
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EntityDictionary> getEntityDictionaryList(String serverName){
		EntityManager em = getEntityManagerByServerName(serverName);
		return em.createNamedQuery("getAllEntityDictionary").getResultList();
	}
	
	/**
	 * Получение списка подразделений по ИДС УЖДТ
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DepartmentProxy> getDepartmentList(String serverName){
		EntityManager em = getEntityManagerByServerName(serverName);
		List<Department> resultList2 = em.createNamedQuery("getAllDepartment").getResultList();
		List<DepartmentProxy> resultList = new ArrayList<DepartmentProxy>();
		for (Department department : resultList2) {
			resultList.add(new DepartmentProxy(department.getId(), department.getFullName()));
		}		
		return resultList;
	}
	
	/**
	 * Получение списка форм по правам по ИДС УЖДТ
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DocumentDictionary> getDocumentDictionaryList(String serverName){
		EntityManager em = getEntityManagerByServerName(serverName);
		return em.createNamedQuery("getAllDocumentDictionary").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<EntityPermission> getUserEntityList(String login, String serverName){
		EntityManager em = getEntityManagerByServerName(serverName);
		return em.createNamedQuery("getUserEntityByLogin").setParameter(1, login).setMaxResults(maxResults).getResultList();
	}
	
	
	/**
	 * Получение объектов RailwayGroup по syscode из {@link Constants}
	 * @param syscode
	 * @param usersList - пользовательский список
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RailwayGroup> getStationList(String syscode, List<Long> usersList, String serverName){
		List<RailwayGroup> list = new ArrayList<RailwayGroup>();
		EntityManager em = getEntityManagerByServerName(serverName);
		if(usersList.isEmpty()){
			
			list = em.createNamedQuery("getAllRailwayGroups").setParameter("param1", syscode).getResultList();
		}else{
			list = em.createNamedQuery("getAllRailwayGroups.notInUser")
						.setParameter("param1", syscode)
						.setParameter("param2", usersList)
						.getResultList();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<RailwayGroupProxy> getUserStationList(String login, String serverName){
		List<Object[]> list = new ArrayList<Object[]>();
		List<RailwayGroupProxy> list2 = new ArrayList<RailwayGroupProxy>();
		EntityManager em = getEntityManagerByServerName(serverName);
		list = em.createNamedQuery("getUserStationByLogin").setParameter(1, login).setMaxResults(maxResults).getResultList();
		for(Object[] o: list){
			list2.add(new RailwayGroupProxy(o));
		}
		
		return list2;
	}
	
	@SuppressWarnings("unchecked")
	public List<UsersProxy> getUserIdsList(String login, String serverName){
		List<Users> list = new ArrayList<Users>();
		List<UsersProxy> list2 = new ArrayList<UsersProxy>();
		if(login == null || login.isEmpty()){
			return list2;
		}	
		EntityManager em = getEntityManagerByServerName(serverName);
		list = em.createNamedQuery("getUserIdsByLogin").setParameter(1, login).setMaxResults(maxResults).getResultList();
		for (Users u : list) {
			list2.add(new UsersProxy(u));
		}
		return list2;
	}
	
	public List<UserProxy> getUserPmList(String login, int param2, String serverName){
		List<UserProxy> list = new ArrayList<UserProxy>();
		params.clear();

		if(param2 == 0){
			list =	executeHibernateNamedQueryAll(getUserPMLite, UserProxy.class, params, serverName);
		}else{
			params.put("param1", "%" + login.toUpperCase() + "%");
			String query = "";
			if(param2 == Constants.CODE_LOGIN_NAME){		
				query = getUserPMLitebyname;
			}else if(param2 ==  Constants.CODE_FULL_NAME){
				query = getUserPMLitebyFullName;
			}

			list = executeHibernateNamedQueryAll(query, UserProxy.class, params, serverName, TypeSessionFactory.FACTORY_PM);

		}
		return list;
	}
	

	@Override
	public List<Country> getCountryList(String codeNUM, String serverName) throws IllegalArgumentException {
		List<Country> list = new ArrayList<Country>();
		EntityManager em = getEntityManagerByServerName(serverName);
		if(codeNUM == null || codeNUM.isEmpty())
			list =	em.createNamedQuery("getCountryAll").setMaxResults(maxResults).getResultList();
		else
			list = em.createNamedQuery("getCountryByCodeNUM").setParameter(1, codeNUM).setMaxResults(maxResults).getResultList();
		return list;
	}
	
	/**
	 * 15.09.2015
	 * @param queryNative
	 * @param clazz
	 * @param parameters
	 * @param isMaxResult
	 * @param serverName - схема JBoss
	 * @return
	 */
	private List<Object[]> runHibernateNativedQuery(String queryNative,
			 Map<String, Object> parameters, boolean isMaxResult, String serverName) {
	
		List<Object[]> list = new ArrayList<Object[]>();
		SessionFactory sessionFactory = null;
		Session session = null;
		try {
			
			sessionFactory = getSessionFactoryByServer(serverName);

			session = sessionFactory.getCurrentSession();
			final Transaction transaction = session.beginTransaction();
			try {
			    // The real work is here
				org.hibernate.Query query = session.createSQLQuery(queryNative);
				
				if(parameters != null && !parameters.isEmpty()){
					for (String p : parameters.keySet()) {

						if (parameters.get(p) instanceof Collection<?>) {
							query.setParameterList(p, (Collection<?>) parameters.get(p));
						} else if (parameters.get(p) instanceof Date) {
							query.setTimestamp(p, (Date) parameters.get(p));
						} else {
							query.setParameter(p, parameters.get(p));
						}
					}
				}
								
				if (isMaxResult)
					query.setMaxResults(maxResults);
				
				list = query.list();
				
			    transaction.commit();
			  } catch (Exception ex) {
				  transaction.rollback();
				  ex.printStackTrace();
				  System.err.println("runHibernateNamedQuery: " + ex.getMessage());
			}
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

		return list;
	}
	
	/**
	 * 11.09.2015
	 * @param namedQuery
	 * @param clazz
	 * @param parameters
	 * @param serverName схема JBoss
	 * @return
	 */
	private <T> List<T> executeHibernateNamedQueryAll(String namedQuery,
			Class<T> clazz, Map<String, Object> parameters, String serverName) {
		return runHibernateNamedQuery(namedQuery, clazz, parameters, false, serverName, TypeSessionFactory.FACTORY_PM);
	}

	/**
	 * 05.10.2015
	 * @param query
	 * @param clazz
	 * @param parameters
	 * @param serverName схема JBoss
	 * @return
	 */
	private <T> List<T> executeHibernateNamedQueryAll(String query,
			Class<T> clazz, Map<String, Object> parameters, String serverName, TypeSessionFactory typFactory) {
		return runHibernateNamedQuery(query, clazz, parameters, false, serverName, typFactory);
	}
	
	
	private enum TypeSessionFactory{
		FACTORY_IDS,
		FACTORY_PM
	}
	
	
	/**
	 * 13.10.2015
	 * @param namedQuery
	 * @param clazz
	 * @param parameters
	 * @param isMaxResult
	 * @param session
	 * @return
	 */
	private <T> List<T> runHibernateNamedQuery(String namedQuery,
			Class<T> clazz, Map<String, Object> parameters, boolean isMaxResult, Session session) {
		List<T> list = new ArrayList<T>();

			final Transaction transaction = session.beginTransaction();
			try {
			    // The real work is here
				org.hibernate.Query query = session.createQuery(namedQuery);
				
				if(parameters != null && !parameters.isEmpty()){
					for (String p : parameters.keySet()) {

						if (parameters.get(p) instanceof Collection<?>) {
							query.setParameterList(p, (Collection<?>) parameters.get(p));
						} else if (parameters.get(p) instanceof Date) {
							query.setTimestamp(p, (Date) parameters.get(p));
						} else {
							query.setParameter(p, parameters.get(p));
						}
					}
				}
				if(clazz != null)
					query.setResultTransformer(Transformers.aliasToBean(clazz));
				
				if (isMaxResult)
					query.setMaxResults(maxResults);
				
				list = query.list();
				
			    transaction.commit();
			  } catch (Exception ex) {
				  transaction.rollback();
				  ex.printStackTrace();
				  System.err.println("runHibernateNamedQuery: " + ex.getMessage());
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}	
		
		return list;
	}
	
	/**
	 * 11.09.2015
	 * @param namedQuery
	 * @param clazz
	 * @param parameters
	 * @param isMaxResult
	 * @param serverName - схема JBoss
	 * @return
	 */
	private <T> List<T> runHibernateNamedQuery(String namedQuery,
			Class<T> clazz, Map<String, Object> parameters, boolean isMaxResult, String serverName, TypeSessionFactory typeFactory) {
	
		List<T> list = new ArrayList<T>();
		SessionFactory sessionFactory = null;
		Session session = null;
		try {
			
			switch (typeFactory) {
			case FACTORY_IDS:
				sessionFactory = getSessionFactoryIDSByServer(serverName);
				break;
			case FACTORY_PM:
				sessionFactory = getSessionFactoryByServer(serverName);
				break;
			default:
				break;
			}
				

			session = sessionFactory.getCurrentSession();
			final Transaction transaction = session.beginTransaction();
			try {
			    // The real work is here
				org.hibernate.Query query = session.createQuery(namedQuery);
				
				if(parameters != null && !parameters.isEmpty()){
					for (String p : parameters.keySet()) {

						if (parameters.get(p) instanceof Collection<?>) {
							query.setParameterList(p, (Collection<?>) parameters.get(p));
						} else if (parameters.get(p) instanceof Date) {
							query.setTimestamp(p, (Date) parameters.get(p));
						} else {
							query.setParameter(p, parameters.get(p));
						}
					}
				}
				if(clazz != null)
					query.setResultTransformer(Transformers.aliasToBean(clazz));
				
				if (isMaxResult)
					query.setMaxResults(maxResults);
				
				list = query.list();
				
			    transaction.commit();
			  } catch (Exception ex) {
				  transaction.rollback();
				  ex.printStackTrace();
				  System.err.println("runHibernateNamedQuery: " + ex.getMessage());
			}
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

		return list;
	}
	
	/**
	 * 11.09.2015 
	 * @param serverName
	 * @return
	 */
	private SessionFactory getSessionFactoryByServer(String serverName) {
		SessionFactory sessionFactory;
		switch (serverName) {
		case Constants.server_name_jboss:
			sessionFactory = HibernateUtil.getSessionFactoryJboss();
			break;
		case Constants.server_name_jboss_5:
			sessionFactory = HibernateUtil.getSessionFactoryJboss5();
			break;
		case Constants.server_name_jboss_01:
			sessionFactory = HibernateUtil.getSessionFactoryJboss01();
			break;

		default:
			sessionFactory = HibernateUtil.getSessionFactoryJboss();
			break;
		}
		return sessionFactory;
	}

	/**
	 * 29.09.2015 
	 * @param serverName
	 * @return
	 */
	private SessionFactory getSessionFactoryIDSByServer(String serverName) {
		SessionFactory sessionFactory;
		switch (serverName) {
		case Constants.server_name_jboss_5:
			sessionFactory = HibernateUtil.getSessionFactoryIDS();
			break;
		case Constants.server_name_jboss_01:
			sessionFactory = HibernateUtil.getSessionFactoryIDS_test();
			break;
		default:
			sessionFactory = HibernateUtil.getSessionFactoryIDS();
			break;
		}
		return sessionFactory;
	}
	
	/**
	 * 13.10.2015
	 * 
	 * @param entity
	 * @param id
	 * @param session
	 */
	private <T> void mergeUserPm(User entity, Long id, Session session) {
		try {
			session.beginTransaction();

			User user = (User) session.get(entity.getClass(), id.intValue());
			user.setFullName(entity.getFullName());
			user.setLoginName(entity.getLoginName());
			user.setWorkPhone(entity.getWorkPhone());
			user.setEmployeeID(entity.getEmployeeID());
			session.flush();

			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}
	
	private <T> void mergeUserPm(User entity, Long id, String serverName) {
		Session session = null;
		try {
			SessionFactory factory = getSessionFactoryByServer(serverName);
			session = factory.getCurrentSession();
			
			session.beginTransaction();
			
			User user = (User) session.get(entity.getClass(), id.intValue());
			user.setFullName(entity.getFullName());
			user.setLoginName(entity.getLoginName());
			user.setWorkPhone(entity.getWorkPhone());
			user.setEmployeeID(entity.getEmployeeID());
			session.flush();
			
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}
	
	private <T> void addEntityPm(T entity, String serverName) {
		Session session = null;
		try {
			SessionFactory factory = getSessionFactoryByServer(serverName);
			session = factory.getCurrentSession();

			session.beginTransaction();
			session.save(entity);
			session.getTransaction().commit();
		} catch (Exception e) {
			if(entity instanceof User){
				User user = (User) entity;
				System.err.println("error in addEntity: user " + user.getLoginName()
						+ " has already been added!");
				
				user = null;
			}
			if(entity instanceof Application){
				Application user = (Application) entity;
				System.err.println("error in addEntity: Application " + user.getShortName()
						+ " has already been added!");
				user = null;
			}
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}
	
	private <T> void deleteEntityPm(T obj, Long id, String serverName) {
		Session session = null;
		try {
			session = getSessionFactoryByServer(serverName).getCurrentSession();
			session.beginTransaction();
			Object object = null;
			if(obj.getClass().getName().equals(User.class.getName())){
				 object = session.get(obj.getClass(), id.intValue());
			}else{
				 object = session.get(obj.getClass(), id);
			}
			
			session.delete(object);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.err.println("error in deleteEntity: " + obj.getClass());
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}


	@SuppressWarnings("unchecked")
	private <T> T findEntityPm(Class<?> clazz, Integer id, String serverName){
		Session  session = getSessionFactoryByServer(serverName).getCurrentSession();
		T obj = null;
        try{
			obj =  (T) session.get(clazz, id);
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	        if (session != null && session.isOpen()) {
	            session.close();
	        }
	    }
        return obj;
	}
	
	public Application findEntityApplication(Integer id, String serverName){
		return findEntityPm(Application.class, id, serverName);
	}
	
		
	/**
	 * возвращает имя пользователя из AD
	 * @param loginName
	 * @return
	 */
	public String autorizationByLoginName(Map<String, String> environment, String loginName, String serverName){
		StringBuilder sb = new StringBuilder();
		
		String filterBase = environment.get(COM_RNB2_FILTER_BASE_KEY);
		
		List<UserProxy> searchUserAdList = searchUserAdAuth(environment, loginName, filterBase);//ad_Azovstal, filterBaseAzovstal);
		
		if(searchUserAdList.isEmpty()){
			System.out.println("autorizationByLoginName: return 0 in AD, for: " + loginName);
			return null;
		}
		
		if(searchUserAdList.size() > 1){
			System.out.println("autorizationByLoginName: return > 1 in AD, for: " + loginName);
			return null;
		}	
		
		
		loginName = searchUserAdList.get(0).getLoginName();
		
		sb.append(searchUserAdList.get(0).getFullName());
		sb.append("(");
		sb.append(loginName);				
		sb.append(")");
		
		if(sb.length() > 0)
			return sb.toString();
		
		return null;
	}
	
	/**
	 * Поиск пользователя в AD
	 * 11.08.2016
	 * @param environment
	 * @param loginName
	 * @param filterBase
	 * @return
	 */
	private List<UserProxy> searchUserAdAuth(Map<String, String> environment2, String loginName, String filterBase) {
		List<UserProxy> result = new ArrayList<UserProxy>();
		try {
			Hashtable<String, String> environment  = new Hashtable<>(environment2);
			
     		LdapContext ldap = new InitialLdapContext(environment, null);
     		String filter = "(&(objectclass=user) (sAMAccountName="+loginName+"))";   		
			feelUserProxy(result, ldap, filter, filterBase);
     	} catch (Exception e) {
     		e.printStackTrace();
     	} 

		return result;
	}
	
	/**
	 * Поиск пользователей в AD
	 * 11.08.2016
	 * @param environment2
	 * @param loginName
	 * @param fio
	 * @param employeeID
	 * @return
	 */
	@Override
	public List<UserProxy> searchUserAd(Map<String, String> environment2, String loginName, String fio, String employeeID) {
		List<UserProxy> result = new ArrayList<UserProxy>();
		Hashtable<String, String> environment = new Hashtable<>(environment2); //= getEnvironment(ad_Azovstal);
		
		String filterBase = environment.get(COM_RNB2_FILTER_BASE_KEY);
		
		try {
			LdapContext ldap = new InitialLdapContext(environment, null);
			
			String filter = "(&(objectclass=user) (sAMAccountName=*"+loginName+"*))";
			
			//3 по фио
			if(fio != null){
				filter = "(&(objectclass=user) (displayName="+fio+"*))";
			}
			
			if(employeeID != null){
				filter = "(&(objectclass=user) (employeeID="+employeeID+"*))";
			}
			
			feelUserProxy(result, ldap, filter, filterBase);
				
			/*if(!result.isEmpty()){	
				UserProxy principal2 = result.get(0);				
				System.out.println("------------------------");
				System.out.println("principal2.getEmployeeID():" + principal2.getEmployeeID());
				System.out.println("principal2.getFullName():" + principal2.getFullName());
				System.out.println("principal2.getLoginName():" + principal2.getLoginName());
				System.out.println("principal2.getWorkPhone():" + principal2.getWorkPhone());
				System.out.println("principal2.getMemberOf():" + principal2.getMemberOf());
				
			}*/	
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return result;
	}
		
	
	/**
	 * 08.07.2015 
	 * @param result
	 * @param ldap
	 * @param filter
	 * @param filterBase
	 * @throws NamingException
	 */
	private void feelUserProxy(List<UserProxy> result, LdapContext ldap,
			String filter, String filterBase) throws NamingException {
		LdapPrincipal principal2;
		NamingEnumeration<SearchResult> search = ldap.search(filterBase, filter, getSimpleSearchControls());
		
		while(search.hasMoreElements()){

			SearchResult result2 = (SearchResult) search.next ();
			principal2 = LdapUtil.internalCreatePrincipal(result2);
			
			UserProxy userProxy = new UserProxy();
			userProxy.setFullName(principal2.getDisplayName());
			userProxy.setLoginName(principal2.getName());
			userProxy.setWorkPhone(principal2.getTelephoneNumber());
			userProxy.setFromAD(true);
			userProxy.setMemberOf(principal2.getMemberOf());
			userProxy.setEmployeeID(principal2.getEmployeeID());
			userProxy.setCompany(principal2.getCompany());
			userProxy.setDepartment(principal2.getDepartment());
			
			result.add(userProxy);
		}
	}
	
	/**
	 * Добавление станции у пользователя ИДС УЖДТ
	 * @param idUser
	 * @param railwayGroup
	 * @return
	 */
	public boolean addStationUserIds(Long idUser, RailwayGroup railwayGroup, String serverName){
		Session session = null;
		boolean result = false;
		try {
			SessionFactory factory = getSessionFactoryIDSByServer(serverName);//HibernateUtil.getSessionFactoryIDS();
			session = factory.getCurrentSession();

			session.beginTransaction();
	
			Users users = (Users) session.get(Users.class, idUser);
				
			RailwayGroup railwayGroup2 = (RailwayGroup) session.get(RailwayGroup.class, railwayGroup.getId());
			result = users.getRailwayGroup().add(railwayGroup2);
				
			session.save(users);
			session.getTransaction().commit();
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
				System.err.println("error in addStationUserIds idUser " + idUser);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	
		return result;
	}
	
	/**
	 * Удаление станции у пользователя ИДС УЖДТ
	 * @param idUser
	 * @param idStation
	 * @return
	 */
	public boolean deleteStationIds(Long idUser, Long idStation, String serverName){
		boolean result = false;
		Session session = null;
		
		try {
			SessionFactory factory =  getSessionFactoryIDSByServer(serverName);//HibernateUtil.getSessionFactoryIDS();
			session = factory.getCurrentSession();

			session.beginTransaction();
				
			Users users = (Users) session.get(Users.class, idUser);
				
			RailwayGroup railwayGroup2 = (RailwayGroup) session.get(RailwayGroup.class, idStation);
			result = users.getRailwayGroup().remove(railwayGroup2);
				
			session.save(users);
			session.getTransaction().commit();
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
				System.err.println("error in -deleteStationIds idUser " + idUser);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		
		return result;
	}
	
	/**
	 * Добавление Доступн. форм пользователю ИДС УЖДТ 
	 * @param idUser
	 * @param documentDictionary
	 * @param documentRoles
	 * @return
	 */
	public boolean addDocumentsPermission(Long idUser, DocumentDictionary documentDictionary, DocumentRoles documentRoles, String userName, String serverName){
		Session session = null;
		boolean result = false;
		try {
			SessionFactory factory = getSessionFactoryIDSByServer(serverName);//HibernateUtil.getSessionFactoryIDS();
			session = factory.getCurrentSession();

			session.beginTransaction();

			Users users = (Users) session.get(Users.class, idUser);
				
			DocumentPermission documentPermission = new DocumentPermission();
			documentPermission.setDictionary(documentDictionary);
			documentPermission.setDocumentRoles(documentRoles);
			documentPermission.setUsername(userName);
			result = users.getUsersDocumentPermitions().add(documentPermission);
	
			session.save(users);
			session.getTransaction().commit();
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
				System.err.println("error in addDocumentsPermission idUser " + idUser);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	
		return result;
	}
	
	/**
	 * Удаление Доступн. форм пользователю ИДС УЖДТ 
	 * @param idUser
	 * @param idDocumentPermiss
	 * @return
	 */
	public boolean deleteDocumentPermission(Long idUser, Long idDocumentPermiss, String serverName){
		boolean result = false;
		Session session = null;
		
		try {
			SessionFactory factory = getSessionFactoryIDSByServer(serverName);//HibernateUtil.getSessionFactoryIDS();
			session = factory.getCurrentSession();

			session.beginTransaction();

			Users users = (Users) session.get(Users.class, idUser);
				
			DocumentPermission documentPermission = (DocumentPermission) session.get(DocumentPermission.class, idDocumentPermiss);
			result = users.getUsersDocumentPermitions().remove(documentPermission);
				
			session.save(users);
			session.getTransaction().commit();
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
				System.err.println("error in -deleteDocumentPermission idUser " + idUser);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		
		return result;
	}
	
	
	
	/**
	 * Добавление пользователя в табл. ИДС УЖДТ
	 * @param name
	 * @param fio
	 * @return
	 */
	public boolean addUserIds(String name, String fio, String userName, String serverName){
		//addUserPermision
		Session session = null;
		boolean result = false;
		try {
			EntityManager em = getEntityManagerByServerName(serverName);
			List<?> resultList = em.createNamedQuery("getUserIdsByLogin").setParameter(1, name).setMaxResults(maxResults).getResultList();
			if(!resultList.isEmpty()){
				System.err.println("addUserIds: " + name + ", user is allready added!");
				return result;
			}
			
			SessionFactory factory = getSessionFactoryIDSByServer(serverName);//HibernateUtil.getSessionFactoryIDS();
			session = factory.getCurrentSession();

			session.beginTransaction();

			Users users = new Users();
			users.setName(name);
			users.setFio(fio);
			users.setUsername(userName);
		
			session.save(users);
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
				System.err.println("error in addUserIds name:" + name);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	
		return result;

	}
	
	/**
	 * Удаление пользователя из ИДС УЖДТ
	 * @param name
	 * @param fio
	 * @return
	 */
	public boolean deleteUserIds(Long idUser, String serverName){
		Session session = null;
		boolean result = false;
		try {
			SessionFactory factory = getSessionFactoryIDSByServer(serverName);//HibernateUtil.getSessionFactoryIDS();
			session = factory.getCurrentSession();

			session.beginTransaction();
			Users users = (Users) session.get(Users.class, idUser);

			session.delete(users);
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
				System.err.println("error in deleteUserIds for id:" + idUser);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	
		return result;

	}
	
	/**
	 * Добавление Доступн. справочников пользователю ИДС УЖДТ 
	 * @param idUser
	 * @param entityDictionary
	 * @param entityRoles
	 * @return
	 */
	public boolean addEntityPermission(Long idUser, EntityDictionary entityDictionary, EntityRoles entityRoles, String userName, String serverName){
		Session session = null;
		boolean result = false;
		try {
			SessionFactory factory = getSessionFactoryIDSByServer(serverName);//HibernateUtil.getSessionFactoryIDS();
			session = factory.getCurrentSession();

			session.beginTransaction();
					

				Users users = (Users) session.get(Users.class, idUser);
				
				EntityPermission entityPermission = new EntityPermission();
				entityPermission.setDictionary(entityDictionary);
				entityPermission.setEntityRoles(entityRoles);
				entityPermission.setUsername(userName);
				result = users.getUsersEntityPermitions().add(entityPermission);
	
			session.save(users);
			session.getTransaction().commit();
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
				System.err.println("error in addEntityPermission idUser " + idUser);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	
		return result;
	}
	
	/**
	 * Удаление Доступн. справочников пользователю ИДС УЖДТ 
	 * @param idUser
	 * @param idEntityPermiss
	 * @return
	 */
	public boolean deleteEntityPermission(Long idUser, Long idEntityPermiss, String serverName){
		boolean result = false;
		Session session = null;
		
		try {
			SessionFactory factory = getSessionFactoryIDSByServer(serverName);//HibernateUtil.getSessionFactoryIDS();
			session = factory.getCurrentSession();

			session.beginTransaction();
				
			Users users = (Users) session.get(Users.class, idUser);
				
			EntityPermission permission = (EntityPermission) session.get(EntityPermission.class, idEntityPermiss);
			result = users.getUsersEntityPermitions().remove(permission);
				
			session.save(users);
			session.getTransaction().commit();
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
				System.err.println("error in -deleteEntityPermission idUser " + idUser);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		
		return result;
	}
	
	/**
	 * Удаление Доступн. подразделений ЭГД у пользователя ИДС УЖДТ 
	 * @param idUser
	 * @param idDepartmentPermiss
	 * @return
	 */
	public boolean deleteDepartmentPermission(Long idUser, Long idDepartmentPermiss, String serverName){
		boolean result = false;
		Session session = null;
		
		try {
			SessionFactory factory = getSessionFactoryIDSByServer(serverName);//HibernateUtil.getSessionFactoryIDS();
			session = factory.getCurrentSession();

			session.beginTransaction();
				
			Users users = (Users) session.get(Users.class, idUser);
				
			UsersDepartment documentPermission = (UsersDepartment) session.get(UsersDepartment.class, idDepartmentPermiss);
			result = users.getUsersDepartment().remove(documentPermission);
				
			session.save(users);
			session.getTransaction().commit();
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
				System.err.println("error in -deleteDepartmentPermission idUser " + idUser);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		
		return result;
	}
	
	/**
	 * Добавление Доступн. подразделений ЭГД у пользователя ИДС УЖДТ  
	 * @param idUser
	 * @param department
	 * @param edcRoles
	 * @return
	 */
	public boolean addDepartmentPermission(Long idUser, Long idDepartment, EdcPermissionRoles edcRoles, String serverName){
		Session session = null;
		boolean result = false;
		try {
			SessionFactory factory = getSessionFactoryIDSByServer(serverName);//HibernateUtil.getSessionFactoryIDS();
			session = factory.getCurrentSession();

			session.beginTransaction();

			Users users = (Users) session.get(Users.class, idUser);
				
			Department department = (Department) session.get(Department.class, idDepartment);
				
			UsersDepartment usersDepartment = new UsersDepartment();
			usersDepartment.setDepartment(department);
			usersDepartment.setRoles(edcRoles);
			result = users.getUsersDepartment().add(usersDepartment);
				
			session.save(users);
			session.getTransaction().commit();
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
				System.err.println("error in addDepartmentPermission idUser " + idUser);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	
		return result;
	}
	
	private SearchControls getSimpleSearchControls() {
	    SearchControls searchControls = new SearchControls();
	    searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	    searchControls.setTimeLimit(30000);
	    //String[] attrIDs = {"objectGUID"};
	    //searchControls.setReturningAttributes(attrIDs);
	    return searchControls;
	}
}
