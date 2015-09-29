package com.rnb2.gwt1.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rnb2.gwt1.data.idsugdt.Country;
import com.rnb2.gwt1.data.idsugdt.DocumentDictionary;
import com.rnb2.gwt1.data.idsugdt.DocumentPermission;
import com.rnb2.gwt1.data.idsugdt.DocumentRoles;
import com.rnb2.gwt1.data.idsugdt.EdcPermissionRoles;
import com.rnb2.gwt1.data.idsugdt.EntityDictionary;
import com.rnb2.gwt1.data.idsugdt.EntityPermission;
import com.rnb2.gwt1.data.idsugdt.EntityRoles;
import com.rnb2.gwt1.data.idsugdt.RailwayGroup;
import com.rnb2.gwt1.data.idsugdt.proxy.DepartmentProxy;
import com.rnb2.gwt1.data.idsugdt.proxy.RailwayGroupProxy;
import com.rnb2.gwt1.data.idsugdt.proxy.UsersDepartmentProxy;
import com.rnb2.gwt1.data.idsugdt.proxy.UsersProxy;
import com.rnb2.gwt1.data.pm.Application;
import com.rnb2.gwt1.data.pm.Permission;
import com.rnb2.gwt1.data.pm.User;
import com.rnb2.gwt1.data.pm.proxy.AclPermissionProxy;
import com.rnb2.gwt1.data.pm.proxy.ApplicationProxy;
import com.rnb2.gwt1.data.pm.proxy.ApplicationProxyFull;
import com.rnb2.gwt1.data.pm.proxy.PermissionProxy;
import com.rnb2.gwt1.data.pm.proxy.PermissionProxyFull;
import com.rnb2.gwt1.data.pm.proxy.UserProxy;

public interface ManageServiceAsync {

	void getCountryList(String codeNUM, String serverName,
			AsyncCallback<List<Country>> callback);

	void getUserIdsList(String login, String serverName,
			AsyncCallback<List<UsersProxy>> callback);

	/** список пользователей PM */
	void getUserPmList(String login, int param2, String serverName,
			AsyncCallback<List<UserProxy>> callback);

	void getUserStationList(String login, String serverName,
			AsyncCallback<List<RailwayGroupProxy>> callback);

	void getUserEntityList(String login, String serverName,
			AsyncCallback<List<EntityPermission>> callback);

	void getApplicationPmList(String login, String serverName,
			AsyncCallback<List<ApplicationProxy>> callback);

	void getApplicationPmPermission(String login, String shortName,
			String serverName, AsyncCallback<List<PermissionProxy>> callback);

	void getUserDocumentList(String login, String serverN,
			AsyncCallback<List<DocumentPermission>> callback);

	void getUserDepartmentList(String login, String serverName,
			AsyncCallback<List<UsersDepartmentProxy>> callback);

	void getApplicationPmList2(String serverName,
			AsyncCallback<List<ApplicationProxy>> callback);

	void addUserPm(User user, String serverName, AsyncCallback<Void> callback);

	void addUserPm2(String userName, String fio, String employeeID,
			String phone, String serverName, AsyncCallback<Void> callback);

	void mergeUserPm(User userProxy, Integer userId, String serverName,
			AsyncCallback<Void> callback);

	void getApplicationPmPermissionAll(String shortName, String serverName,
			AsyncCallback<List<PermissionProxy>> callback);

	void getApplicationPmPermissionFull(String login, String shortName,
			String serverName, AsyncCallback<PermissionProxyFull> callback);

	void addUserPermision(String login, String shortName,
			List<PermissionProxy> list, String serverName,
			AsyncCallback<String> callback);

	void getApplicationPmFull(String login, String serverName,
			AsyncCallback<ApplicationProxyFull> callback);

	void addUserApplication(String login, String shortName,
			List<ApplicationProxy> list, String serverName,
			AsyncCallback<String> callback);

	void addApplicaion(Application obj, String serverName,
			AsyncCallback<Void> callback);

	void addRole(Permission obj, Integer appId, String serverName,
			AsyncCallback<Void> callback);

	void findEntityApplication(Integer id, String serverName,
			AsyncCallback<Application> callback);

	void getUserName(AsyncCallback<String> callback);

	/** ѕоиск пользователей в AD */
	void searchUserAd(String loginName, String fio, String employeeID,
			AsyncCallback<List<UserProxy>> callback);

	void getStationList(String syscode, List<Long> usersList,
			String serverName, AsyncCallback<List<RailwayGroup>> callback);

	void addStationUserIds(Long idUser, RailwayGroup railwayGroup,
			String serverName, AsyncCallback<Boolean> callback);

	void deleteStationIds(Long idUser, Long idStation, String serverName,
			AsyncCallback<Boolean> callback);

	void addDocumentsPermission(Long idUser,
			DocumentDictionary documentDictionary, DocumentRoles documentRoles,
			String userName, String serverName, AsyncCallback<Boolean> callback);

	void getDocumentDictionaryList(String serverName,
			AsyncCallback<List<DocumentDictionary>> callback);

	void addUserIds(String name, String fio, String userName,
			String serverName, AsyncCallback<Boolean> callback);

	void deleteDocumentPermission(Long idUser, Long idDocumentPermiss,
			String serverName, AsyncCallback<Boolean> callback);

	void getEntityDictionaryList(String server,
			AsyncCallback<List<EntityDictionary>> callback);

	void addEntityPermission(Long idUser, EntityDictionary entityDictionary,
			EntityRoles entityRoles, String userName, String serverName,
			AsyncCallback<Boolean> callback);

	void deleteEntityPermission(Long idUser, Long idEntityPermiss,
			String serverName, AsyncCallback<Boolean> callback);

	void deleteDepartmentPermission(Long idUser, Long idDepartmentPermiss,
			String serverName, AsyncCallback<Boolean> callback);

	void getDepartmentList(String serverName,
			AsyncCallback<List<DepartmentProxy>> callback);

	void addDepartmentPermission(Long idUser, Long department,
			EdcPermissionRoles edcRoles, String serverName,
			AsyncCallback<Boolean> callback);

	void autorizationByLoginName(String loginName, String serverName,
			AsyncCallback<String> callback);

	void deleteUserIds(Long idUser, String serverName,
			AsyncCallback<Boolean> callback);

	void addUserCopyPm(String userNameNew, String fio, String phone,
			String employeeId, String userNameOld, String serverName,
			AsyncCallback<String> callback);

	/**
	 * ”даление пользовател€ из схемы JBoss, c Aclpermission (native) 10.09.2015
	 * 
	 * @param userId
	 * @param login
	 */
	void deleteUserPm(Integer userId, String userName, String serverName,
			AsyncCallback<Void> callback);

	/**
	 * 15.09.2015 получение списка ACL
	 * 
	 * @param loginName
	 * @param serverName
	 * @return
	 */
	void getAclPermissionList(String loginName, String serverName,
			AsyncCallback<List<AclPermissionProxy>> callback);
	
	/**
	 * 21.09.2015 получает список измен€емых пользователей
	 * @param fileName
	 * @param rangeBegin
	 * @param rangeEnd
	 * @param columnIndexLoginNameOld
	 * @param columnIndexLoginNameNew
	 * @param callback
	 */
	void readFileXls(String fileName, int rangeBegin, int rangeEnd,
			int columnIndexLoginNameOld, int columnIndexLoginNameNew,
			AsyncCallback<List<UserProxy>> callback);

	/**
	 * 
	 *  опирование пользователей
	 * @param list
	 * @param serverName
	 * @return
	 */
	void addUserCopyPmAll(List<UserProxy> list, String serverName,
			AsyncCallback<String> callback);

}
