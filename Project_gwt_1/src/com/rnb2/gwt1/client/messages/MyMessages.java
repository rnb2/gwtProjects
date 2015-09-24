package com.rnb2.gwt1.client.messages;

import com.google.gwt.i18n.client.Messages;
import com.sencha.gxt.widget.core.client.form.FieldLabel.FieldLabelAppearance;

public interface MyMessages extends Messages {

	/**
	 * Вход
	 * @return
	 */
	String enterApp();
	
	/**
	 * Пользователь не найден!
	 * @return
	 */
	String userNotFound();
	
	/**
	 * Авторизация прошла успешно!
	 * @return
	 */
	String authorizationOk();
	
	/**
	 * Авторизация
	 * @return
	 */
	String authorization();
	
	/**
	 * Записей
	 * @return
	 */
	String records();
	
	/**
	 * Список пользователей из Active Directory
	 * @return
	 */
	String titleUserAD();
	
	/**
	 * Список пользователей JBoss5
	 * @return
	 */
	String titleUserJboss5(); 

	/**
	 * Список пользователей сервера:
	 * @return
	 */
	String titleUsers(); 
	
	/** Добавить запись*/
	String addRecord();
	
	/** Подробная информация о приложении*/
	String appDetail();
		
	/** Найдено пользователей*/
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
	 * Табельный номер
	 * @return
	 */
	String employeID();

	/**
	 * Таб.№
	 * @return
	 */
	String employeIDshort();

	/**
	 * Доступн. формы
	 * @return
	 */
	String accessForm();

	/**
	 * Доступн. справочники
	 * @return
	 */
	String accessSpr();

	String type();

	String dictionary();

	String permision();

	String add();

	String edit();

	/**Удалить*/
	String delete();
	
	/** Удалние записи */
	String deleteRecord();

	String login_Fio();

	String toolTipFindUserPm();
	
	/**Поиск пользователей в Active Directory*/
	String toolTipSearchUserAd();

	String description();

	String architect();

	String programmer();
	
	/**
	 * Приложение
	 * @return
	 */
	String application();
	
	String name();
	
	String nameTask();
	
	String permisionsFor();
	
	/** Роли*/
	String permisions();
	
	String departments();
	
	String egd();
	
	String form();
	
	/** Выберете запись*/
	String selectRecord();
	
	/** Ошибка*/
	String error();
	
	/**Подробно*/
	String detail();
	
	/**Выберете приложение!*/
	String selectApplication();
	
	/**Добавление пользователя*/
	String userAdd();
	
	/**Редактирование пользователя*/
	String userEdit();

	/**Закрыть*/
	String close();

	/**Сохранить*/
	String save();

	/**Телефон*/
	String phone();

	/**Отмена*/
	String cancel();

	/**Пользователь добавлен!*/
	String userAdded();
	
	/**Пользователь уже добавлен!!!!*/
	String userAddedAlreadey();

	/** Вы действительно хотите удалить запись:*/
	String deleteRecordQuestion();
	
	/**Пользователь удален!*/
	String userDeleted();

	/**Пользователь отредактирован!*/
	String userEdited();
	
	/**Роли пользователя*/
	String userRoles();

	/** Добавить роль*/
	String addRole();
	
	/** Добавить приложение*/
	String addApplication();
	
	/**Приложения пользователя*/
	String useraApps();
	
	/**Все роли*/
	String allRoles();
	
	/**Все приложения*/
	String allApps();
	
	/**
	 * Запись добавлена!
	 * @return
	 */
	String recordAdded();
	
	/**
	 * Введите значение...
	 * @return
	 */
	String inputValue();

	/**
	 * Новое
	 * @return
	 */
	String neww();
	
	/**
	 * Приложения
	 * @return
	 */
	String Applications();

	/**
	 * Добавление приложения
	 * @return
	 */
	String ApplicationAdd();
	
	/**
	 * Приложение добавлено
	 * @return
	 */
	String ApplicationAdded();
	
	/**
	 * Наименование приложения
	 */
	String nameApp();

	/**
	 * Роль добавлена
	 * @return
	 */
	String permisionAdded();

	/**
	 * Добавление роли
	 * @return
	 */
	String RoleAdd();

	/**
	 * Поиск пользователей
	 * @return
	 */
	String findUser();
	
	/**
	 * Станция добавлена успешно!
	 * @return
	 */
	String infoAddStation();

	/**
	 * Запись удалена!
	 * @return
	 */
	String recordDeleted();

	/**
	 * Все Станции добавлены!
	 * @return
	 */
	String allStationAdded();

	/**
	 * Информация
	 * @return
	 */
	String info();

	/**
	 * Копировать
	 * @return
	 */
	String copy();

	/**
	 * Копирование пользователя
	 * @return
	 */
	String copyUser();

	/**
	 * Пользователь новый
	 * @return
	 */
	String loginNameNew();

	/**
	 * Ошибка копирования пользователя. Попробуйте еще разок
	 * @return
	 */
	String errorCopyUser();

	/**
	 * Ошибка копирования пользователя. Попробуйте еще разок
	 * @return
	 */
	String errorSelectServer();

	/**
	 * Дата создания 
	 * @return
	 */
	String dateInput();

	/**
	 * Добавил пользователь
	 * @return
	 */
	String loginNameAdded();

	/**
	 * Доступ Acl
	 * @return
	 */
	String detailAcl();

	/**
	 * Роли пользователя в ACL_PERMISSION
	 * @return
	 */
	String detailAclInfo();

	/**
	 * Тип контролируемого объекта
	 * @return
	 */
	String entityType();

	/**
	 * Тип приложения
	 * @return
	 */
	String appType();

	/**
	 * Доступ
	 * @return
	 */
	String accessType();

	/**
	 * Атрибут
	 * @return
	 */
	String property();

	/**
	 * Операция сравнения
	 * @return
	 */
	String operationType();

	/**
	 * Значение
	 * @return
	 */
	String permissiomValue();

	/**
	 * Записи не найдены!
	 * @return
	 */
	String recrodsNoFound();
	
	/**
	 * Сервер
	 * @return
	 */
	String server();
	
	/**
	 *  Настройки импрота пользователей из Excel
	 * @return
	 */
	String titleUsersXlsImport();
	
	/**
	 * Пользователи из Excel
	 * @return
	 */
	String titleUsersXls();

	/**
	 * Начало
	 * @return
	 */
	String rowBegin();

	/**
	 * Окончание
	 * @return
	 */
	String rowEnd();

	/**
	 * Файл
	 * @return
	 */
	String file();

	/**
	 * Копировать всех
	 * @return
	 */
	String copyAll();

	/**
	 * Копировать всех пользователей
	 * @return
	 */
	String copyAllUsers();

	/**
	 * Копирование всех пользователей
	 * @return
	 */
	String copyAllUsers2();

	/**
	 * Данных не найдено
	 * @return
	 */
	String noDataFound();

	/**
	 * Загрузка данных
	 * @return
	 */
	String loadData();
	
	/**Пользователи скопированы*/
	String usersCopied();
}
