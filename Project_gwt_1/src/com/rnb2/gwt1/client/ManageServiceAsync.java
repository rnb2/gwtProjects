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

	void getCountryList(String codeNUM, AsyncCallback<List<Country>> callback);

	void getUserIdsList(String login, AsyncCallback<List<UsersProxy>> callback);

	/** список пользователей PM */
	void getUserPmList(String login, int param2, String serverName,
			AsyncCallback<List<UserProxy>> callback);

	/** список станций ИДС у пользователя */
	void getUserStationList(String login,
			AsyncCallback<List<RailwayGroupProxy>> callback);

	/** список справочников ИДС у пользователя */
	void getUserEntityList(String login,
			AsyncCallback<List<EntityPermission>> callback);

	void getApplicationPmList(String login, String serverName,
			AsyncCallback<List<ApplicationProxy>> callback);

	void getApplicationPmPermission(String login, String shortName,
			String serverName, AsyncCallback<List<PermissionProxy>> callback);

	/** список форм ИДС у пользователя */
	void getUserDocumentList(String login,
			AsyncCallback<List<DocumentPermission>> callback);

	/** список подразделений ЭГД у пользователя */
	void getUserDepartmentList(String login,
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

	/** Поиск пользователей в AD */
	void searchUserAd(String loginName, String fio, String employeeID,
			AsyncCallback<List<UserProxy>> callback);

	/**
	 * Список станциий ИДС УЖДТ Получение объектов RailwayGroup по sysCode
	 * Constants}
	 * 
	 * @param syscode
	 * @param usersList
	 *            - пользовательский список
	 * @return
	 */
	void getStationList(String sysCode, List<Long> usersList,
			AsyncCallback<List<RailwayGroup>> callback);

	/**
	 * Добавление станции у пользователя ИДС УЖДТ
	 * 
	 * @param idUser
	 * @param railwayGroup
	 * @return
	 */
	void addStationUserIds(Long idUser, RailwayGroup railwayGroup,
			AsyncCallback<Boolean> callback);

	/**
	 * Удаление станции у пользователя ИДС УЖДТ
	 * 
	 * @param idUser
	 * @param idStation
	 * @return
	 */
	void deleteStationIds(Long idUser, Long idStation,
			AsyncCallback<Boolean> callback);

	/**
	 * Добавление Доступн. форм пользователю ИДС УЖДТ
	 * 
	 * @param idUser
	 * @param documentDictionary
	 * @param documentRoles
	 * @return
	 */
	void addDocumentsPermission(Long idUser,
			DocumentDictionary documentDictionary, DocumentRoles documentRoles,
			String userName, AsyncCallback<Boolean> callback);

	/**
	 * Получение списка форм по правам по ИДС УЖДТ
	 * 
	 * @return
	 */
	void getDocumentDictionaryList(
			AsyncCallback<List<DocumentDictionary>> callback);

	/**
	 * Добавление пользователя в табл. ИДС УЖДТ
	 * 
	 * @param name
	 * @param fio
	 * @param userName
	 * @return
	 */
	void addUserIds(String name, String fio, String userName,
			AsyncCallback<Boolean> callback);

	/**
	 * Удаление Доступн. форм пользователю ИДС УЖДТ
	 * 
	 * @param idUser
	 * @param idDocumentPermiss
	 * @return
	 */
	void deleteDocumentPermission(Long idUser, Long idDocumentPermiss,
			AsyncCallback<Boolean> callback);

	/**
	 * Получение списка справочников по правам по ИДС УЖДТ
	 * 
	 * @return
	 */
	void getEntityDictionaryList(AsyncCallback<List<EntityDictionary>> callback);

	/**
	 * Добавление Доступн. справочников пользователю ИДС УЖДТ
	 * 
	 * @param idUser
	 * @param entityDictionary
	 * @param entityRoles
	 * @return
	 */
	void addEntityPermission(Long idUser, EntityDictionary entityDictionary,
			EntityRoles entityRoles, String userName,
			AsyncCallback<Boolean> callback);

	/**
	 * Удаление Доступн. справочников пользователю ИДС УЖДТ
	 * 
	 * @param idUser
	 * @param idEntityPermiss
	 * @return
	 */
	void deleteEntityPermission(Long idUser, Long idEntityPermiss,
			AsyncCallback<Boolean> callback);

	/**
	 * Удаление Доступн. подразделений ЭГД у пользователя ИДС УЖДТ
	 * 
	 * @param idUser
	 * @param idDepartmentPermiss
	 * @return
	 */
	void deleteDepartmentPermission(Long idUser, Long idDepartmentPermiss,
			AsyncCallback<Boolean> callback);

	/**
	 * Получение списка подразделений по ИДС УЖДТ
	 * 
	 * @return
	 */
	void getDepartmentList(AsyncCallback<List<DepartmentProxy>> callback);

	/**
	 * Добавление Доступн. подразделений ЭГД у пользователя ИДС УЖДТ
	 * 
	 * @param idUser
	 * @param department
	 * @param edcRoles
	 * @return
	 */
	void addDepartmentPermission(Long idUser, Long department,
			EdcPermissionRoles edcRoles, AsyncCallback<Boolean> callback);

	void autorizationByLoginName(String loginName, String serverName,
			AsyncCallback<String> callback);

	/**
	 * Удаление пользователя из ИДС УЖДТ
	 * 
	 * @param name
	 * @param fio
	 * @return
	 */
	void deleteUserIds(Long idUser, AsyncCallback<Boolean> callback);

	void addUserCopyPm(String userNameNew, String fio, String phone,
			String employeeId, String userNameOld, String serverName,
			AsyncCallback<String> callback);

	/**
	 * Удаление пользователя из схемы JBoss, c Aclpermission (native) 10.09.2015
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
	 * 21.09.2015 получает список изменяемых пользователей
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
	 * Копирование пользователей
	 * @param list
	 * @param serverName
	 * @return
	 */
	void addUserCopyPmAll(List<UserProxy> list, String serverName,
			AsyncCallback<String> callback);

}
