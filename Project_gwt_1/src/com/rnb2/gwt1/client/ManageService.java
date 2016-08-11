package com.rnb2.gwt1.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
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
import com.rnb2.gwt1.server.utils.Constants;

/**
 * 
 * @author budukh-rn
 *
 */
@RemoteServiceRelativePath("manage")
public interface ManageService extends RemoteService {
	
	/**
	 * Поиск пользователей в AD
	 * 11.08.2016
	 * @param environment2
	 * @param loginName
	 * @param fio
	 * @param employeeID
	 * @return
	 */
	public List<UserProxy> searchUserAd(Map<String, String> environment2, String loginName, String fio, String employeeID);
	/**
	 * 11.08.2016
	 * возвращает имя пользователя из AD
	 * @param loginName
	 * @return
	 */
	public String autorizationByLoginName(Map<String, String> environment, String loginName, String serverName);
	
	/**
	 * 20.10.2015
	 * Синхранизация полей пользователя из AD(в контектсном)
	 * @param loginName
	 * @param serverName
	 * @return
	 */
	public String syncUsersFromAD(Map<String, String> environment, String loginName, String serverName);
	
	/**
	 * Синхранизация полей пользователtй из AD
	 * 06.10.2015
	 * @param serverName
	 * @return
	 */
	public String syncUsersFromAD(Map<String, String> environment, String serverName);
	
	/**
	 * 
	 * Копирование пользователей
	 * @param list
	 * @param serverName
	 * @param userNameCreated - пользователь выполняющий операцию
	 * @return
	 */
	public String addUserCopyPmAll(List<UserProxy> list, String serverName, String userNameCreated);		

	/**
	 * 
	 * @param fileName
	 * @param rangeBegin
	 * @param rangeEnd
	 * @param columnIndexLoginNameOld
	 * @param columnIndexLoginNameNew
	 */
	public List<UserProxy> readFileXls(String fileName, int rangeBegin, int rangeEnd, 
			int columnIndexLoginNameOld, int columnIndexLoginNameNew);
	
	/**
	 * 15.09.2015 получение списка ACL
	 * @param loginName
	 * @param serverName
	 * @return
	 */
	List<AclPermissionProxy> getAclPermissionList(String loginName, String serverName);
	
	/**
	 * Удаление пользователя из схемы JBoss, c Aclpermission (native)
	 * 10.09.2015
	 * @param userId
	 * @param login
	 */
	public void deleteUserPm(Integer userId, String userName, String serverName);
	
	
	/**
	 * 
	 * Копирование пользователя
	 * @param userNameNew - новый логин пользователя
	 * @param fio
	 * @param phone
	 * @param employeeId
	 * @param userNameOld - старый логин пользователя
	 * @param serverName
	 * @param userNameCreated - пользователь выполняющий операцию
	 * @return
	 */
	public String addUserCopyPm(String userNameNew, String fio, String phone, String employeeId, String userNameOld, String serverName, String userNameCreated);
	
	/**
	 * Удаление пользователя из ИДС УЖДТ
	 * @param name
	 * @param fio
	 * @return
	 */
	public boolean deleteUserIds(Long idUser, String serverName);
	
	/**
	 * Авторизация пользователя в AD, затем поиск роли в "PM"
	 * @param loginName
	 * @return
	 */
	/*public String autorizationByLoginName(String loginName, String serverName);*/
	
	/**
	 * Добавление Доступн. подразделений ЭГД у пользователя ИДС УЖДТ  
	 * @param idUser
	 * @param department
	 * @param edcRoles
	 * @return
	 */
	public boolean addDepartmentPermission(Long idUser, Long department, EdcPermissionRoles edcRoles, String serverName);
	
	/**
	 * Получение списка подразделений по ИДС УЖДТ
	 * @return
	 */
	public List<DepartmentProxy> getDepartmentList(String serverName);
	
	boolean deleteDepartmentPermission(Long idUser, Long idDepartmentPermiss, String serverName);
	
	/**
	 * Удаление Доступн. справочников пользователю ИДС УЖДТ 
	 * @param idUser
	 * @param idEntityPermiss
	 * @return
	 */
	public boolean deleteEntityPermission(Long idUser, Long idEntityPermiss, String serverName);
	
	boolean addEntityPermission(Long idUser, EntityDictionary entityDictionary,
			EntityRoles entityRoles, String userName, String serverName);
	
	/**
	 * Получение списка справочников по правам по ИДС УЖДТ
	 * @return
	 */
	public List<EntityDictionary> getEntityDictionaryList(String serverName);
	
	/**
	 * Удаление Доступн. форм пользователю ИДС УЖДТ 
	 * @param idUser
	 * @param idDocumentPermiss
	 * @return
	 */
	public boolean deleteDocumentPermission(Long idUser, Long idDocumentPermiss, String serverName);
	
	/**
	 * Добавление пользователя в табл. ИДС УЖДТ
	 * @param name
	 * @param fio
	 * @return
	 */
	public boolean addUserIds(String name, String fio, String userName, String serverName);
	
	/**
	 * Получение списка форм по правам по ИДС УЖДТ
	 * @return
	 */
	public List<DocumentDictionary> getDocumentDictionaryList(String serverName);
	
	/**
	 * Добавление Доступн. форм пользователю ИДС УЖДТ 
	 * @param idUser
	 * @param documentDictionary
	 * @param documentRoles
	 * @param userName
	 * @return
	 */
	public boolean addDocumentsPermission(Long idUser, DocumentDictionary documentDictionary, DocumentRoles documentRoles, String userName, String serverName);
	 
	
	/**
	 * Удаление станции у пользователя ИДС УЖДТ
	 * @param idUser
	 * @param idStation
	 * @return
	 */
	public boolean deleteStationIds(Long idUser, Long idStation, String serverName);
	
	/**
	 * Добавление станции у пользователя ИДС УЖДТ
	 * @param idUser
	 * @param railwayGroup
	 * @return
	 */
	public boolean addStationUserIds(Long idUser, RailwayGroup railwayGroup, String serverName);
	
	/**
	 * Список станциий ИДС УЖДТ
	 * Получение объектов RailwayGroup по syscode из {@link Constants}
	 * @param syscode
	 * @param usersList - пользовательский список
	 * @return
	 */
	List<RailwayGroup> getStationList(String syscode, List<Long> usersList, String serverName);
	
	///** Поиск пользователей в AD*/
	//List<UserProxy> searchUserAd(String loginName, String fio, String employeeID); 
	
	/**разрешения у приложения*/
	PermissionProxyFull getApplicationPmPermissionFull(String login, String shortName, String serverName);
	
	/**список разрешений у приложения*/
	List<PermissionProxy> getApplicationPmPermissionAll(String shortName, String serverName);
	
	/**список разрешений у пользователя по приложению */
	List<PermissionProxy> getApplicationPmPermission(String login, String shortName, String serverName);
	
	/**список приложений у пользователя*/
	List<ApplicationProxy> getApplicationPmList2(String serverName);
	
	/**список приложений у пользователя*/
	List<ApplicationProxy> getApplicationPmList(String login, String serverName);
	
	/**список подразделений ЭГД у пользователя*/
	List<UsersDepartmentProxy> getUserDepartmentList(String login, String serverName);
	
	/**список форм ИДС у пользователя*/
	List<DocumentPermission> getUserDocumentList(String login, String serverName);
	
	/**список справочников ИДС у пользователя*/
	List<EntityPermission> getUserEntityList(String login, String serverName);
	
	/**список станций ИДС у пользователя*/
	List<RailwayGroupProxy> getUserStationList(String login, String serverName);
	
	/**список пользователей ИДС*/
	List<UsersProxy> getUserIdsList(String login, String serverName);
	
	/**список пользователей PM*/
	List<UserProxy> getUserPmList(String login, int param2, String serverName);
	
	
	List<Country> getCountryList(String codeNUM, String serverName)  throws IllegalArgumentException;


	/** Добавление пользователя PM*/
	void addUserPm(User user, String serverName);
	
	/**
	 *  Добавление пользователя PM
	 * @param userName
	 * @param fio
	 * @param employeeID
	 * @param phone
	 * @param serverName
	 */
	public void addUserPm2(String userName, String fio, String employeeID, String phone, String serverName);
	
	
	/** Ред. пользователя PM*/
	void mergeUserPm(User userProxy, Integer userId, String serverName);
	
		
	String addUserPermision(String login, String shortName, List<PermissionProxy> list, String serverName);
	
	/**приложения у пользователя*/
	ApplicationProxyFull getApplicationPmFull(String login, String serverName);
	
	String addUserApplication(String login, String shortName, List<ApplicationProxy> list, String serverName);
	
	/**
	 * Добавление нового приложения
	 * @param obj
	 */
	void addApplicaion(Application obj, String serverName);
	
	/**
	 * Добавление новой роли
	 * @param obj
	 */
	void addRole(Permission obj, Integer appId, String serverName);
	
	public Application findEntityApplication(Integer id, String serverName);
	
	public String getUserName();
}
