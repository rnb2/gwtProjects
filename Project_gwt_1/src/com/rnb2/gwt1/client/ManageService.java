package com.rnb2.gwt1.client;

import java.util.List;

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
	 * Удаление пользователя из схемы JBoss, c Aclpermission (native)
	 * 10.09.2015
	 * @param userId
	 * @param login
	 */
	public void deleteUserPm(Integer userId, String userName, String serverName);
	
	/**
	 * Копирование пользователя
	 * @param userNameNew - новый логин пользователя
	 * @param fio
	 * @param phone
	 * @param userNameOld - старый логин пользователя
	 * @param serverName
	 * @return*/
	public String addUserCopyPm(String userNameNew, String fio, String phone, String userNameOld, String serverName);
	
	/**
	 * Удаление пользователя из ИДС УЖДТ
	 * @param name
	 * @param fio
	 * @return
	 */
	public boolean deleteUserIds(Long idUser);
	
	/**
	 * Авторизация пользователя в AD, затем поиск роли в "PM"
	 * @param loginName
	 * @return
	 */
	public String autorizationByLoginName(String loginName, String serverName);
	
	/**
	 * Добавление Доступн. подразделений ЭГД у пользователя ИДС УЖДТ  
	 * @param idUser
	 * @param department
	 * @param edcRoles
	 * @return
	 */
	public boolean addDepartmentPermission(Long idUser, Long department, EdcPermissionRoles edcRoles);
	
	/**
	 * Получение списка подразделений по ИДС УЖДТ
	 * @return
	 */
	public List<DepartmentProxy> getDepartmentList();
	
	/**
	 * Удаление Доступн. подразделений ЭГД у пользователя ИДС УЖДТ 
	 * @param idUser
	 * @param idDepartmentPermiss
	 * @return
	 */
	public boolean deleteDepartmentPermission(Long idUser, Long idDepartmentPermiss);
	
	/**
	 * Удаление Доступн. справочников пользователю ИДС УЖДТ 
	 * @param idUser
	 * @param idEntityPermiss
	 * @return
	 */
	public boolean deleteEntityPermission(Long idUser, Long idEntityPermiss);
	
	/**
	 * Добавление Доступн. справочников пользователю ИДС УЖДТ 
	 * @param idUser
	 * @param entityDictionary
	 * @param entityRoles
	 * @param userName
	 * @return
	 */
	public boolean addEntityPermission(Long idUser, EntityDictionary entityDictionary, EntityRoles entityRoles, String userName);
	
	/**
	 * Получение списка справочников по правам по ИДС УЖДТ
	 * @return
	 */
	public List<EntityDictionary> getEntityDictionaryList();
	
	/**
	 * Удаление Доступн. форм пользователю ИДС УЖДТ 
	 * @param idUser
	 * @param idDocumentPermiss
	 * @return
	 */
	public boolean deleteDocumentPermission(Long idUser, Long idDocumentPermiss);
	
	/**
	 * Добавление пользователя в табл. ИДС УЖДТ
	 * @param name
	 * @param fio
	 * @return
	 */
	public boolean addUserIds(String name, String fio, String userName);
	
	/**
	 * Получение списка форм по правам по ИДС УЖДТ
	 * @return
	 */
	public List<DocumentDictionary> getDocumentDictionaryList();
	
	/**
	 * Добавление Доступн. форм пользователю ИДС УЖДТ 
	 * @param idUser
	 * @param documentDictionary
	 * @param documentRoles
	 * @param userName
	 * @return
	 */
	public boolean addDocumentsPermission(Long idUser, DocumentDictionary documentDictionary, DocumentRoles documentRoles, String userName);
	 
	
	/**
	 * Удаление станции у пользователя ИДС УЖДТ
	 * @param idUser
	 * @param idStation
	 * @return
	 */
	public boolean deleteStationIds(Long idUser, Long idStation);
	
	/**
	 * Добавление станции у пользователя ИДС УЖДТ
	 * @param idUser
	 * @param railwayGroup
	 * @return
	 */
	public boolean addStationUserIds(Long idUser, RailwayGroup railwayGroup);
	
	/**
	 * Список станциий ИДС УЖДТ
	 * Получение объектов RailwayGroup по syscode из {@link Constants}
	 * @param syscode
	 * @param usersList - пользовательский список
	 * @return
	 */
	List<RailwayGroup> getStationList(String syscode, List<Long> usersList);
	
	/** Поиск пользователей в AD*/
	List<UserProxy> searchUserAd(String loginName, String fio, String employeeID); 
	
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
	List<UsersDepartmentProxy> getUserDepartmentList(String login);
	
	/**список форм ИДС у пользователя*/
	List<DocumentPermission> getUserDocumentList(String login);
	
	/**список справочников ИДС у пользователя*/
	List<EntityPermission> getUserEntityList(String login);
	
	/**список станций ИДС у пользователя*/
	List<RailwayGroupProxy> getUserStationList(String login);
	
	/**список пользователей ИДС*/
	List<UsersProxy> getUserIdsList(String login);
	
	/**список пользователей PM*/
	List<UserProxy> getUserPmList(String login, int param2, String serverName);
	
	
	List<Country> getCountryList(String codeNUM)  throws IllegalArgumentException;


	/** Добавление пользователя PM*/
	void addUserPm(User user, String serverName);
	
	public void addUserPm2(String userName, String fio, String phone, String serverName);
	
	
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
