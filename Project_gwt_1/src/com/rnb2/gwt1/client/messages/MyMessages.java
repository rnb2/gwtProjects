package com.rnb2.gwt1.client.messages;

import com.google.gwt.i18n.client.Messages;
import com.sencha.gxt.widget.core.client.form.FieldLabel.FieldLabelAppearance;

public interface MyMessages extends Messages {

	/**
	 * ����
	 * @return
	 */
	String enterApp();
	
	/**
	 * ������������ �� ������!
	 * @return
	 */
	String userNotFound();
	
	/**
	 * ����������� ������ �������!
	 * @return
	 */
	String authorizationOk();
	
	/**
	 * �����������
	 * @return
	 */
	String authorization();
	
	/**
	 * �������
	 * @return
	 */
	String records();
	
	/**
	 * ������ ������������� �� Active Directory
	 * @return
	 */
	String titleUserAD();
	
	/**
	 * ������ ������������� JBoss5
	 * @return
	 */
	String titleUserJboss5(); 

	/**
	 * ������ ������������� �������:
	 * @return
	 */
	String titleUsers(); 
	
	/** �������� ������*/
	String addRecord();
	
	/** ��������� ���������� � ����������*/
	String appDetail();
		
	/** ������� �������������*/
	String foundUsers();
	
	String find();

	String country();

	String userPm();

	String loginName();

	String loginNameOld();

	String fullName();

	String workPhone();

	String idsInfo();

	String count();

	String userIds();

	String station();
	
	/**
	 * ��������� �����
	 * @return
	 */
	String employeID();

	/**
	 * ���.�
	 * @return
	 */
	String employeIDshort();

	/**
	 * �������. �����
	 * @return
	 */
	String accessForm();

	/**
	 * �������. �����������
	 * @return
	 */
	String accessSpr();

	String type();

	String dictionary();

	String permision();

	String add();

	String edit();

	/**�������*/
	String delete();
	
	/** ������� ������ */
	String deleteRecord();

	String login_Fio();

	String toolTipFindUserPm();
	
	/**����� ������������� � Active Directory*/
	String toolTipSearchUserAd();

	String description();

	String architect();

	String programmer();
	
	/**
	 * ����������
	 * @return
	 */
	String application();
	
	String name();
	
	String nameTask();
	
	String permisionsFor();
	
	/** ����*/
	String permisions();
	
	String departments();
	
	String egd();
	
	String form();
	
	/** �������� ������*/
	String selectRecord();
	
	/** ������*/
	String error();
	
	/**��������*/
	String detail();
	
	/**�������� ����������!*/
	String selectApplication();
	
	/**���������� ������������*/
	String userAdd();
	
	/**�������������� ������������*/
	String userEdit();

	/**�������*/
	String close();

	/**���������*/
	String save();

	/**�������*/
	String phone();

	/**������*/
	String cancel();

	/**������������ ��������!*/
	String userAdded();
	
	/**������������ ��� ��������!!!!*/
	String userAddedAlreadey();

	/** �� ������������� ������ ������� ������:*/
	String deleteRecordQuestion();
	
	/**������������ ������!*/
	String userDeleted();

	/**������������ ��������������!*/
	String userEdited();
	
	/**���� ������������*/
	String userRoles();

	/** �������� ����*/
	String addRole();
	
	/** �������� ����������*/
	String addApplication();
	
	/**���������� ������������*/
	String useraApps();
	
	/**��� ����*/
	String allRoles();
	
	/**��� ����������*/
	String allApps();
	
	/**
	 * ������ ���������!
	 * @return
	 */
	String recordAdded();
	
	/**
	 * ������� ��������...
	 * @return
	 */
	String inputValue();

	/**
	 * �����
	 * @return
	 */
	String neww();
	
	/**
	 * ����������
	 * @return
	 */
	String Applications();

	/**
	 * ���������� ����������
	 * @return
	 */
	String ApplicationAdd();
	
	/**
	 * ���������� ���������
	 * @return
	 */
	String ApplicationAdded();
	
	/**
	 * ������������ ����������
	 */
	String nameApp();

	/**
	 * ���� ���������
	 * @return
	 */
	String permisionAdded();

	/**
	 * ���������� ����
	 * @return
	 */
	String RoleAdd();

	/**
	 * ����� �������������
	 * @return
	 */
	String findUser();
	
	/**
	 * ������� ��������� �������!
	 * @return
	 */
	String infoAddStation();

	/**
	 * ������ �������!
	 * @return
	 */
	String recordDeleted();

	/**
	 * ��� ������� ���������!
	 * @return
	 */
	String allStationAdded();

	/**
	 * ����������
	 * @return
	 */
	String info();

	/**
	 * ����������
	 * @return
	 */
	String copy();

	/**
	 * ����������� ������������
	 * @return
	 */
	String copyUser();

	/**
	 * ������������ �����
	 * @return
	 */
	String loginNameNew();

	/**
	 * ������ ����������� ������������. ���������� ��� �����
	 * @return
	 */
	String errorCopyUser();

	/**
	 * ������ ����������� ������������. ���������� ��� �����
	 * @return
	 */
	String errorSelectServer();

	/**
	 * ���� �������� 
	 * @return
	 */
	String dateInput();

	/**
	 * ������� ������������
	 * @return
	 */
	String loginNameAdded();

	/**
	 * ������ Acl
	 * @return
	 */
	String detailAcl();

	/**
	 * ���� ������������ � ACL_PERMISSION
	 * @return
	 */
	String detailAclInfo();

	/**
	 * ��� ��������������� �������
	 * @return
	 */
	String entityType();

	/**
	 * ��� ����������
	 * @return
	 */
	String appType();

	/**
	 * ������
	 * @return
	 */
	String accessType();

	/**
	 * �������
	 * @return
	 */
	String property();

	/**
	 * �������� ���������
	 * @return
	 */
	String operationType();

	/**
	 * ��������
	 * @return
	 */
	String permissiomValue();

	/**
	 * ������ �� �������!
	 * @return
	 */
	String recrodsNoFound();
	
	/**
	 * ������
	 * @return
	 */
	String server();
	
	/**
	 *  ��������� ������� ������������� �� Excel
	 * @return
	 */
	String titleUsersXlsImport();
	
	/**
	 * ������������ �� Excel
	 * @return
	 */
	String titleUsersXls();

	/**
	 * ������
	 * @return
	 */
	String rowBegin();

	/**
	 * ���������
	 * @return
	 */
	String rowEnd();

	/**
	 * ����
	 * @return
	 */
	String file();

	/**
	 * ���������� ����
	 * @return
	 */
	String copyAll();

	/**
	 * ���������� ���� �������������
	 * @return
	 */
	String copyAllUsers();

	/**
	 * ����������� ���� �������������
	 * @return
	 */
	String copyAllUsers2();

	/**
	 * ������ �� �������
	 * @return
	 */
	String noDataFound();

	/**
	 * �������� ������
	 * @return
	 */
	String loadData();
	
	/**������������ �����������*/
	String usersCopied();
}
