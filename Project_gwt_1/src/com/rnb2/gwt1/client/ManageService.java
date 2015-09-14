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
	 * �������� ������������ �� ����� JBoss, c Aclpermission (native)
	 * 10.09.2015
	 * @param userId
	 * @param login
	 */
	public void deleteUserPm(Integer userId, String userName, String serverName);
	
	/**
	 * ����������� ������������
	 * @param userNameNew - ����� ����� ������������
	 * @param fio
	 * @param phone
	 * @param userNameOld - ������ ����� ������������
	 * @param serverName
	 * @return*/
	public String addUserCopyPm(String userNameNew, String fio, String phone, String userNameOld, String serverName);
	
	/**
	 * �������� ������������ �� ��� ����
	 * @param name
	 * @param fio
	 * @return
	 */
	public boolean deleteUserIds(Long idUser);
	
	/**
	 * ����������� ������������ � AD, ����� ����� ���� � "PM"
	 * @param loginName
	 * @return
	 */
	public String autorizationByLoginName(String loginName, String serverName);
	
	/**
	 * ���������� �������. ������������� ��� � ������������ ��� ����  
	 * @param idUser
	 * @param department
	 * @param edcRoles
	 * @return
	 */
	public boolean addDepartmentPermission(Long idUser, Long department, EdcPermissionRoles edcRoles);
	
	/**
	 * ��������� ������ ������������� �� ��� ����
	 * @return
	 */
	public List<DepartmentProxy> getDepartmentList();
	
	/**
	 * �������� �������. ������������� ��� � ������������ ��� ���� 
	 * @param idUser
	 * @param idDepartmentPermiss
	 * @return
	 */
	public boolean deleteDepartmentPermission(Long idUser, Long idDepartmentPermiss);
	
	/**
	 * �������� �������. ������������ ������������ ��� ���� 
	 * @param idUser
	 * @param idEntityPermiss
	 * @return
	 */
	public boolean deleteEntityPermission(Long idUser, Long idEntityPermiss);
	
	/**
	 * ���������� �������. ������������ ������������ ��� ���� 
	 * @param idUser
	 * @param entityDictionary
	 * @param entityRoles
	 * @param userName
	 * @return
	 */
	public boolean addEntityPermission(Long idUser, EntityDictionary entityDictionary, EntityRoles entityRoles, String userName);
	
	/**
	 * ��������� ������ ������������ �� ������ �� ��� ����
	 * @return
	 */
	public List<EntityDictionary> getEntityDictionaryList();
	
	/**
	 * �������� �������. ���� ������������ ��� ���� 
	 * @param idUser
	 * @param idDocumentPermiss
	 * @return
	 */
	public boolean deleteDocumentPermission(Long idUser, Long idDocumentPermiss);
	
	/**
	 * ���������� ������������ � ����. ��� ����
	 * @param name
	 * @param fio
	 * @return
	 */
	public boolean addUserIds(String name, String fio, String userName);
	
	/**
	 * ��������� ������ ���� �� ������ �� ��� ����
	 * @return
	 */
	public List<DocumentDictionary> getDocumentDictionaryList();
	
	/**
	 * ���������� �������. ���� ������������ ��� ���� 
	 * @param idUser
	 * @param documentDictionary
	 * @param documentRoles
	 * @param userName
	 * @return
	 */
	public boolean addDocumentsPermission(Long idUser, DocumentDictionary documentDictionary, DocumentRoles documentRoles, String userName);
	 
	
	/**
	 * �������� ������� � ������������ ��� ����
	 * @param idUser
	 * @param idStation
	 * @return
	 */
	public boolean deleteStationIds(Long idUser, Long idStation);
	
	/**
	 * ���������� ������� � ������������ ��� ����
	 * @param idUser
	 * @param railwayGroup
	 * @return
	 */
	public boolean addStationUserIds(Long idUser, RailwayGroup railwayGroup);
	
	/**
	 * ������ �������� ��� ����
	 * ��������� �������� RailwayGroup �� syscode �� {@link Constants}
	 * @param syscode
	 * @param usersList - ���������������� ������
	 * @return
	 */
	List<RailwayGroup> getStationList(String syscode, List<Long> usersList);
	
	/** ����� ������������� � AD*/
	List<UserProxy> searchUserAd(String loginName, String fio, String employeeID); 
	
	/**���������� � ����������*/
	PermissionProxyFull getApplicationPmPermissionFull(String login, String shortName, String serverName);
	
	/**������ ���������� � ����������*/
	List<PermissionProxy> getApplicationPmPermissionAll(String shortName, String serverName);
	
	/**������ ���������� � ������������ �� ���������� */
	List<PermissionProxy> getApplicationPmPermission(String login, String shortName, String serverName);
	
	/**������ ���������� � ������������*/
	List<ApplicationProxy> getApplicationPmList2(String serverName);
	
	/**������ ���������� � ������������*/
	List<ApplicationProxy> getApplicationPmList(String login, String serverName);
	
	/**������ ������������� ��� � ������������*/
	List<UsersDepartmentProxy> getUserDepartmentList(String login);
	
	/**������ ���� ��� � ������������*/
	List<DocumentPermission> getUserDocumentList(String login);
	
	/**������ ������������ ��� � ������������*/
	List<EntityPermission> getUserEntityList(String login);
	
	/**������ ������� ��� � ������������*/
	List<RailwayGroupProxy> getUserStationList(String login);
	
	/**������ ������������� ���*/
	List<UsersProxy> getUserIdsList(String login);
	
	/**������ ������������� PM*/
	List<UserProxy> getUserPmList(String login, int param2, String serverName);
	
	
	List<Country> getCountryList(String codeNUM)  throws IllegalArgumentException;


	/** ���������� ������������ PM*/
	void addUserPm(User user, String serverName);
	
	public void addUserPm2(String userName, String fio, String phone, String serverName);
	
	
	/** ���. ������������ PM*/
	void mergeUserPm(User userProxy, Integer userId, String serverName);
	
		
	String addUserPermision(String login, String shortName, List<PermissionProxy> list, String serverName);
	
	/**���������� � ������������*/
	ApplicationProxyFull getApplicationPmFull(String login, String serverName);
	
	String addUserApplication(String login, String shortName, List<ApplicationProxy> list, String serverName);
	
	/**
	 * ���������� ������ ����������
	 * @param obj
	 */
	void addApplicaion(Application obj, String serverName);
	
	/**
	 * ���������� ����� ����
	 * @param obj
	 */
	void addRole(Permission obj, Integer appId, String serverName);
	
	public Application findEntityApplication(Integer id, String serverName);
	
	public String getUserName();
}
