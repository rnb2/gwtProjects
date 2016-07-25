/**
 * 
 */
package com.rnb2.gwt1.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.rnb2.gwt1.client.images.Images;
import com.rnb2.gwt1.client.messages.MyMessages;
import com.rnb2.gwt1.client.model.combo.ServerProperties;
import com.rnb2.gwt1.client.utils.Constants;
import com.rnb2.gwt1.client.utils.CustomWidgets;
import com.rnb2.gwt1.client.utils.UtilString;
import com.rnb2.gwt1.client.widgets.TableAclPerm;
import com.rnb2.gwt1.client.widgets.TableApplicationPm;
import com.rnb2.gwt1.client.widgets.TableDocumentPermis;
import com.rnb2.gwt1.client.widgets.TableEntityPermis;
import com.rnb2.gwt1.client.widgets.TableRailwayGroup;
import com.rnb2.gwt1.client.widgets.TableUserIds;
import com.rnb2.gwt1.client.widgets.TableUserPm;
import com.rnb2.gwt1.client.widgets.TableUsersDepartment;
import com.rnb2.gwt1.client.widgets.TableUsersXls;
import com.rnb2.gwt1.client.widgets.TestTabPanel;
import com.rnb2.gwt1.client.widgets.window.DialogDelete2;
import com.rnb2.gwt1.client.widgets.window.WindowUserPmSearch;
import com.rnb2.gwt1.client.widgets.window.WindowUserPmXlsImport;
import com.rnb2.gwt1.data.idsugdt.DocumentPermission;
import com.rnb2.gwt1.data.idsugdt.EntityPermission;
import com.rnb2.gwt1.data.idsugdt.proxy.RailwayGroupProxy;
import com.rnb2.gwt1.data.idsugdt.proxy.UsersDepartmentProxy;
import com.rnb2.gwt1.data.idsugdt.proxy.UsersProxy;
import com.rnb2.gwt1.data.pm.proxy.AclPermissionProxy;
import com.rnb2.gwt1.data.pm.proxy.ApplicationProxy;
import com.rnb2.gwt1.data.pm.proxy.UserProxy;
import com.rnb2.gwt1.shared.ServerProxy;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Status;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.FileUploadField;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.menu.SeparatorMenuItem;
import com.sencha.gxt.widget.core.client.toolbar.FillToolItem;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;
import com.sencha.gxt.widget.core.client.toolbar.SeparatorToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

/**
 * Раздача прав на JBOSS'ах, и приложениях
 * @author budukh-rn
 *
 */
public class Mainwidget2 implements IsWidget{

	private static final String M_CONTAINER_V = "m.containerV";
	private final ManageServiceAsync manageService = GWT.create(ManageService.class);
	private final MyMessages messages = GWT.create(MyMessages.class);
	private TextField textField;
	private ContentPanel center;
	private ContentPanel left;
	private ContentPanel wright;
	private TextButton buttonFind, buttonSearchAd;
	private TableUserPm widgetTableUserPM;
	private VerticalLayoutContainer containerV;
	private VerticalLayoutContainer containerApplication = new VerticalLayoutContainer();
	private HorizontalLayoutContainer containerHm = new HorizontalLayoutContainer();
	private HorizontalLayoutContainer containerHtop;
	private DialogDelete2 dialogDelete;
	private static Mainwidget2 instance;
	private String userName, loginName;
	private VerticalLayoutData layoutDataFull;
	private TestTabPanel testTabPanel;
	private UsersProxy usersProxy;
	private ComboBox<ServerProxy> comboServer;
	private FormPanel fileformPanel;
	private FileUploadField file;
	protected List<UserProxy> usersFomXls = new ArrayList<UserProxy>();
	private Status statusBusy;
	
	public Mainwidget2(String userName2, String loginName2) {
		this.userName = userName2;
		this.loginName = loginName2;
		
		if(applicationProxyAllList.isEmpty())
			manageService.getApplicationPmList2(getSelectedServerName(), callbackDetailUserPmApplications);
		
		instance = this;
		
		layoutDataFull = new VerticalLayoutData();
		layoutDataFull.setHeight(1);
		layoutDataFull.setWidth(1);
	}
	
	@Override
	public Widget asWidget() {
		final BorderLayoutContainer con = new BorderLayoutContainer();
		addTop(con);
		//addLeft(con);
		//addWright(con);
		addCenter(con);
		
		SimpleContainer simple = new SimpleContainer();
	    simple.add(con, new MarginData(15));
		return con;
	}
	
	public void setFindFieldText(String text){
		textField.setText(text);
		
	}

	public static Mainwidget2 getInstance() {
		if (instance == null) {
			instance = new Mainwidget2("instance name", "");
		}
		return instance;
	}
	
	private Widget toolBarWidget(){
		ToolBar t = new ToolBar();
	    final ToggleButton b1 = new ToggleButton("Toggle Size");
	    b1.addSelectHandler(new SelectHandler() {
	 
	      @Override
	      public void onSelect(SelectEvent event) {
	        if (b1.getValue()) {
	          center.setPixelSize(600, 400);
	        } else {
	          center.setPixelSize(400, 300);
	        }
	 
	      }
	    });
	    t.add(b1);
	    return t;
	}
	

	private void addTop(BorderLayoutContainer con) {

		BorderLayoutData layout = new BorderLayoutData(50);
		layout.setCollapsible(true);
		layout.setSplit(true);
		layout.setMinSize(50);
		layout.setMaxSize(500);
		
		con.setNorthWidget(findWidget2(), layout);
	}
	
	private ToolBar findWidget2(){
		toolBar = new ToolBar();
		statusBusy = new Status();
		
		LabelToolItem labelToolItem = new LabelToolItem(messages.login_Fio()+": ");
		labelToolItem.setStylePrimaryName("textBold");
		
		textField = new TextField();
		textField.setWidth(200);
		textField.setAllowTextSelection(true);
		textField.focus();
		
		 //combo server
		 List<ServerProxy> serversList = new ArrayList<ServerProxy>();
		 int i=0;
		 for(String server : Constants.serverListAll){
			serversList.add(new ServerProxy(i, server));
			i++;
		 }
		 
		 ServerProperties propsServers = GWT.create(ServerProperties.class);
		 ListStore<ServerProxy> storeServer = new ListStore<ServerProxy>(propsServers.key());
		 storeServer.addAll(serversList);			 
		 comboServer = new ComboBox<ServerProxy>(storeServer, propsServers.fullName());
		 comboServer.setTriggerAction(TriggerAction.ALL);
		 comboServer.setForceSelection(true);
		 //comboServer.addValueChangeHandler(handlerComboServer());
		 comboServer.addSelectionHandler(handlerComboServer());
		//---

		buttonFind = new TextButton(messages.find(), handlerFindUsersPm());
		buttonFind.setTitle(messages.toolTipFindUserPm());
		buttonFind.setIcon(Images.INSTANCE.search());
		
		toolBar.add(labelToolItem);
		toolBar.add(textField);
		toolBar.add(new SeparatorToolItem());
		toolBar.add(comboServer);
		toolBar.add(new SeparatorToolItem());
		toolBar.add(buttonFind);
		toolBar.add(new SeparatorToolItem());
		
	    
	    MenuItem menuItem1 = new MenuItem(messages.titleUsersXlsImportShort());
	    menuItem1.addSelectionHandler(handlerSettingUserXls());
	    menuItem1.setIcon(Images.INSTANCE.excel_imports());

	    MenuItem menuItem2 = new MenuItem(messages.syncWithAd());
	    menuItem2.addSelectionHandler(handlerSyncFromAD());
	    menuItem2.setIcon(Images.INSTANCE.tree());
	    menuItem2.setToolTip(messages.toolTipSyncWithAd());
	 
	    
	    Menu menu1 = new Menu();
	    menu1.add(menuItem1);
	    menu1.add(new SeparatorMenuItem());
	    menu1.add(menuItem2);
	
	 
	    TextButton buttonSettings = new TextButton(messages.settingsDop());
	    buttonSettings.setIcon(Images.INSTANCE.gear());
	    buttonSettings.setMenu(menu1);
	    
	    toolBar.add(buttonSettings);
	    toolBar.add(statusBusy);  
		
		toolBar.add(new FillToolItem());
		StringBuilder sb = new StringBuilder();
		sb.append(messages.loginName());
		sb.append(": ");
		sb.append(userName);
		toolBar.add(new Label(sb.toString()));		
		
		toolBar.forceLayout();
		return toolBar;
	}
	
	/**
	 * Отображение настроек импорта из Xsl	
	 * @return
	 */
	public SelectionHandler<Item> handlerSettingUserXls(){
		return new SelectionHandler<Item>() {
			@Override
			public void onSelection(SelectionEvent<Item> event) {
				WindowUserPmXlsImport pmSearch = new WindowUserPmXlsImport(messages.titleUsersXlsImport(), messages);
				pmSearch.show();
				
			}
		};
	}
	
	/**
	 * Синхранизация полей пользователй из AD
	 * 06.10.2015
	 * @return
	 */
	private SelectionHandler<Item> handlerSyncFromAD() {
		return new SelectionHandler<Item>() {
			
			@Override
			public void onSelection(SelectionEvent<Item> event) {
				if(comboServer.getCurrentValue() == null){
					CustomWidgets.createAlert(messages.error(), messages.errorSelectServer());
					return;
				}
				
				statusBusy.setBusy(messages.loadData());
				manageService.syncUsersFromAD(getSelectedServerName(), callbackSyncUsersFromAD());
			}
		};
	}
	
		
	protected AsyncCallback<String> callbackSyncUsersFromAD() {
		return new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				Info.display(messages.syncWithAd(), "Ok");
				statusBusy.clearStatus("");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				statusBusy.clearStatus("");
			}
		};
	}

	private SelectionHandler<ServerProxy> handlerComboServer() {
		
		return new SelectionHandler<ServerProxy>() {
			
			@Override
			public void onSelection(SelectionEvent<ServerProxy> event) {
			
				HorizontalLayoutData layoutData = new HorizontalLayoutData();
				layoutData.setHeight(1);
				layoutData.setWidth(1);
				containerHm.clear();
				containerHm.setLayoutData(layoutData);
				containerHm.forceLayout();
				
				containerApplication.clear();
				
				if(containerHtop != null){
					containerHtop.clear();
					containerHtop.forceLayout();
				}
				
				if(containerV != null){
					containerV.clear();
					containerV.forceLayout();
				}
				
				
					
				
			}
		};
	}

	
	/*private Widget findWidget(){
		HBoxLayoutContainer container = new HBoxLayoutContainer();
		//container.setHBoxLayoutAlign(HBoxLayoutAlign.STRETCH);
		BoxLayoutData layoutData = new BoxLayoutData(new Margins(5, 0, 0, 5));
		
			
		buttonFind = new TextButton(messages.find(), handlerFindUsersPm());
		buttonFind.setTitle(messages.toolTipFindUserPm());
		
		
		textField = new TextField();
		textField.setWidth(200);
		textField.setAllowTextSelection(true);
		textField.focus();
		
		FieldLabel fieldLabel = new FieldLabel(textField, messages.login_Fio());
		fieldLabel.setStylePrimaryName("textBold");
		
		buttonSearchAd = new TextButton(messages.find(), handlerSearchAD());
		buttonSearchAd.setTitle(messages.toolTipSearchUserAd());
		buttonSearchAd.setIcon(Images.INSTANCE.search());
		
		container.add(fieldLabel, layoutData);
		container.add(textField, layoutData);
	    container.add(buttonFind, layoutData);
		
	    Label label = new Label(this.userName);
	    container.add(label, layoutData);
	    
	    return container.asWidget();
	}*/
	
	private native String getUserName() /*-{
		//var wshell = new ActiveXObject("WScript.Shell");
		//return wshell.ExpandEnvironmentStrings("%USERNAME%");
		return $doc.getUsr();
	}-*/;
	
	
	private void addCenter(BorderLayoutContainer con) {
		center = new ContentPanel();
		center.setHeaderVisible(false);
		BorderLayoutData layout = new BorderLayoutData();
		layout.setMargins(new Margins(5));
		layout.setCollapsible(true);
		layout.setSplit(true);
		layout.setMinSize(50);
		layout.setMaxSize(500);
		con.setCenterWidget(center, layout);
	}

	private void addLeft(BorderLayoutContainer con) {
		left = new ContentPanel();
		
		left.setResize(true);

	    BorderLayoutData westData = new BorderLayoutData(150);
		westData.setCollapsible(true);
		westData.setFloatable(true);
		westData.setSplit(true);
		westData.setMargins(new Margins(5, 0, 5, 5));
		
	    con.setWestWidget(left, westData);
	}
	
	private void addWright(BorderLayoutContainer con) {
		wright = new ContentPanel();
		wright.setResize(true);

	    BorderLayoutData layoutData = new BorderLayoutData(150);
		layoutData.setCollapsible(true);
		layoutData.setFloatable(true);
		layoutData.setSplit(true);
		layoutData.setMargins(new Margins(5, 5, 5, 5));
		
	    con.setWestWidget(wright, layoutData);
	}

	private void addToPanel(ContentPanel panel, Widget c, String headerText) {
		//panel.rem
	    panel.clear();
	    panel.add(c);
	    panel.setHeadingText(headerText);
	    panel.forceLayout();
	}
	
	private void addToCenter(Widget c) {
	    center.clear();
	    center.add(c);
	    center.forceLayout();
	}
	

	/**
	 * получение информации по пользователю в ИДС УЖДТ (доступные станции, справочники, подразделения и т.д.) 
	 * @param tableUserIds
	 * @return
	 */
	private SelectionHandler<UsersProxy> getSelectionHandlerTableUserIds(
			final TableUserIds tableUserIds) {
		return new SelectionHandler<UsersProxy>() {
			@Override
			public void onSelection(SelectionEvent<UsersProxy> event) {
				usersProxy = event.getSelectedItem();
				
				manageService.getUserStationList(usersProxy.getName(), getSelectedServerName(), callbackSelectionTableUserIds);
				
				manageService.getUserEntityList(usersProxy.getName(), getSelectedServerName(), callbackSelectionGetUserEntity);
				
				manageService.getUserDocumentList(usersProxy.getName(), getSelectedServerName(), callbackSelectionGetUserDocuments);
				
				manageService.getUserDepartmentList(usersProxy.getName(), getSelectedServerName(), callbackSelectionGetUserDepartment);
				
			}
		};
	}
	
	/**
	 * Обновление списка подразделений ЭГД у пользователя ИДС при добавлении/удалении
	 * @param userName
	 */
	public void updateIdsUserDepartmentListByUser(String userName){
		manageService.getUserDepartmentList(usersProxy.getName(), getSelectedServerName(), callbackSelectionGetUserDepartment);
	}
	
	/**
	 * Обновление списка доступн. справочников у пользователя ИДС при добавлении/удалении
	 * @param userName
	 */
	public void updateIdsUserEntityByUser(String userName){
		manageService.getUserEntityList(usersProxy.getName(), getSelectedServerName(), callbackSelectionGetUserEntity);
	}
	
	/**
	 * Обновление списка доступн. форм у пользователя ИДС при добавлении/удалении
	 * @param userName
	 */
	public void updateIdsUserDocumentByUser(String userName){
		manageService.getUserDocumentList(usersProxy.getName(), getSelectedServerName(), callbackSelectionGetUserDocuments);
	}
	
	/**
	 * Обновление списка станций у пользователя ИДС при добавлении/удалении
	 * @param userName
	 */
	public void updateIdsStationByUser(String userName){
		manageService.getUserStationList(userName, getSelectedServerName(), callbackSelectionTableUserIds);
	}
	
	/**
	 * Обновление списка пользователей в ИДС при добавлении/удалении
	 * @param userName
	 */
	public void updateIdsUser(String userName){
		manageService.getUserIdsList(userName, getSelectedServerName(), callbackUserIdsByname);
	}

	/**
	 * Обновление списка пользователей в ACL
	 * @param userName
	 */
	public void updateUserInfoAcl(String userName){
		manageService.getAclPermissionList(userName, getSelectedServerName(), callbackUserAclByname);
	}
	
//	----------	AsyncCallback	----------------
	private AsyncCallback<List<UsersDepartmentProxy>> callbackSelectionGetUserDepartment = new AsyncCallback<List<UsersDepartmentProxy>>() {
		
		@Override
		public void onSuccess(List<UsersDepartmentProxy> result) {

			VerticalLayoutContainer container = testTabPanel.getContainerDepartments();
			container.clear();

			container.add(new TableUsersDepartment(usersProxy, result, getSelectedServerName()), layoutDataFull);
			container.forceLayout();
		}
		
		@Override
		public void onFailure(Throwable caught) {
			caught.printStackTrace();
			new MessageBox("Detail click", "get UsersDepartment error !!!! " + caught.getLocalizedMessage()).show();
		}
	};
	
	private AsyncCallback<List<DocumentPermission>> callbackSelectionGetUserDocuments = new AsyncCallback<List<DocumentPermission>>() {
		
		@Override
		public void onSuccess(List<DocumentPermission> result) {
			
			VerticalLayoutContainer container = testTabPanel.getContainerForm();
			container.clear();

			container.add(new TableDocumentPermis(usersProxy, result, getSelectedServerName()), layoutDataFull);
			container.forceLayout();
		}
		
		@Override
		public void onFailure(Throwable caught) {
			new MessageBox("Detail click", "get User Document error !!!! " + caught.getLocalizedMessage()).show();
		}
	};
	
	private AsyncCallback<List<EntityPermission>> callbackSelectionGetUserEntity = new AsyncCallback<List<EntityPermission>>() {
		
		@Override
		public void onSuccess(List<EntityPermission> result) {
			
			VerticalLayoutContainer container = testTabPanel.getContainerSpr();
			container.clear();

			container.add(new TableEntityPermis(usersProxy, result, getSelectedServerName()), layoutDataFull);
			container.forceLayout();
			
		}
		
		@Override
		public void onFailure(Throwable caught) {
			new MessageBox("Detail click", "get User Entity error !!!! " + caught.getLocalizedMessage()).show();
		}
	};

	private AsyncCallback<List<RailwayGroupProxy>> callbackSelectionTableUserIds = new AsyncCallback<List<RailwayGroupProxy>>() {

		@Override
		public void onSuccess(List<RailwayGroupProxy> result) {
			
			VerticalLayoutContainer containerStation = testTabPanel.getContainerStation();
			containerStation.clear();

				
			containerStation.add(new TableRailwayGroup(usersProxy, result, getSelectedServerName()), layoutDataFull);
			containerStation.forceLayout();
		}
		
		@Override
		public void onFailure(Throwable caught) {
			new MessageBox("callbackSelectionTableUserIds:", " !!!! s=" + caught.getLocalizedMessage()).show();
		}
	};
	
	

	private void addAclDetailInfo(List<AclPermissionProxy> result) {
		try {

			HorizontalLayoutData layoutData = new HorizontalLayoutData();
			layoutData.setHeight(1);
			layoutData.setWidth(1);
			
			containerHm.clear();
			containerHm.setLayoutData(layoutData);
			
			final TableAclPerm widget = new TableAclPerm(result, messages, getSelectedServerName());
			HorizontalLayoutData layoutData2 = new HorizontalLayoutData(0.5, 1, new Margins(5));
			//tableUserIds.addSelection(getSelectionHandlerTableUserIds(tableUserIds));
			containerHm.add(widget, layoutData2);
						
			containerHm.forceLayout();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			new MessageBox("addIdsDetailInfo", "error !!!! " + e.getLocalizedMessage()).show();
		}
	}
	
	private void addIdsDetailInfo(List<UsersProxy> result) {

		try {

			HorizontalLayoutData layoutData = new HorizontalLayoutData();
			layoutData.setHeight(1);
			layoutData.setWidth(1);
			
			
			//HorizontalLayoutContainer containerH = new HorizontalLayoutContainer();//widgetTableUserPM.getContainerH();
			
			containerHm.clear();
			containerHm.setLayoutData(layoutData);
			
			//Table Users IDS
			final TableUserIds tableUserIds = new TableUserIds(result, widgetTableUserPM.getReturnedvalue(), getSelectedServerName());
			HorizontalLayoutData layoutData2 = new HorizontalLayoutData(0.5, 1, new Margins(5));
			tableUserIds.addSelection(getSelectionHandlerTableUserIds(tableUserIds));
			containerHm.add(tableUserIds, layoutData2);
			
			//TabPanel Users IDS detail
			testTabPanel = new TestTabPanel(messages);
			HorizontalLayoutData layoutData3 = new HorizontalLayoutData(0.5, 1, new Margins(5, 5, 5, 0));
			containerHm.add(testTabPanel, layoutData3);
			
			containerHm.forceLayout();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			new MessageBox("addIdsDetailInfo", "error !!!! " + e.getLocalizedMessage()).show();
		}
		
	}
	
	private AsyncCallback<List<UsersProxy>> callbackUserIdsByname = new AsyncCallback<List<UsersProxy>>() {
		
		@Override
		public void onSuccess(List<UsersProxy> result) {
			//new MessageBox("callbackUserIdsByname:", " !!!! s=" + result.size()).show();
			addIdsDetailInfo(result);
		}
		@Override
		public void onFailure(Throwable caught) {
			new MessageBox("Detail click", "error !!!! " + caught.getLocalizedMessage()).show();
		}
	};
	
	private AsyncCallback<List<AclPermissionProxy>> callbackUserAclByname = new AsyncCallback<List<AclPermissionProxy>>() {
		
		@Override
		public void onSuccess(List<AclPermissionProxy> result) {
			if(result.isEmpty()){
				CustomWidgets.createAlert(messages.detailAclInfo(), messages.recrodsNoFound());
			}				
			addAclDetailInfo(result);
		}
		@Override
		public void onFailure(Throwable caught) {
			new MessageBox("Detail click", "error !!!! " + caught.getLocalizedMessage()).show();
		}
	};
	
	protected List<ApplicationProxy> applicationProxyAllList = new ArrayList<ApplicationProxy>();
	


	public SelectHandler handlerFindUsersPm(){
		SelectHandler handler = new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				
				manageService.getUserName(null);
				
				if(comboServer.getCurrentValue() == null){
					CustomWidgets.createAlert(messages.error(), messages.errorSelectServer());
					return;
				}
				
				if(comboServer.getCurrentValue().getId() > 0){
					updatePmUserInfo(textField.getCurrentValue(), comboServer.getCurrentValue().getShortName());
				}else if(comboServer.getCurrentValue().getId() == 0){
					WindowUserPmSearch pmSearch = new WindowUserPmSearch(messages.findUser(), messages);
					pmSearch.show();
				}
			}
		};
		return handler;
	}
	
	/**
	 * Обновление информации о пользователе JBOSS
	 * @param currentValue
	 */
	public void updatePmUserInfo(String currentValue, String serverName){
		
		int param2 = 0;
		if(currentValue != null && !currentValue.isEmpty()){
		
			if(!Character.isDigit(currentValue.charAt(0))){
				currentValue = currentValue.trim();
				param2 = 1;
				if(UtilString.isLatinCharacter(currentValue)){
					param2 = Constants.CODE_LOGIN_NAME;
				}else{
					param2 = Constants.CODE_FULL_NAME;
				}
			}
		}

		manageService.getUserPmList(currentValue, param2, serverName, callBackFindUserPm());
	}
	
	/**
	 * Поиск пользователя в AD
	 * @return
	 */
	private SelectHandler handlerSearchAD() {
		SelectHandler handler = new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				
				WindowUserPmSearch pmSearch = new WindowUserPmSearch(messages.findUser(), messages);
				pmSearch.show();
			}
		};
		return handler;
	}
	
	private AsyncCallback<List<UserProxy>> callBackFindUserPm() {
		return new AsyncCallback<List<UserProxy>>() {



			@Override
			public void onFailure(Throwable caught) {
				new MessageBox("getUserList Err", "Error !!!! "
						+ caught.getLocalizedMessage()).show();
			}
			
			@Override
			public void onSuccess(List<UserProxy> result) {
				try {
					
					feelWidgetTableUserPM(result, false);
					
				} catch (Exception e) {
					e.printStackTrace();
					new MessageBox("build table", "Error !!!! "
							+ e.getLocalizedMessage()).show();
				}
			}

			

			
		};
	}
	
	protected SelectHandler handlerDeleteUserShowDialog() {
		SelectHandler handler = new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				if(widgetTableUserPM.getReturnedvalue() == null){
					new MessageBox(messages.deleteRecord(), messages.selectRecord()).show();
					return;
				}
							
				try{
					dialogDelete = new DialogDelete2(messages.deleteRecord(), UtilString.getMessageForDelete(widgetTableUserPM.getReturnedvalue().getFullName(), messages),
						messages,	
						handlerDeleteUserDo());
				
				dialogDelete.show();
				}catch(Exception ex){
					new MessageBox("Exc", ex.getLocalizedMessage()).show();
				}
			}
		};
			
	return handler;
	}
	
	protected SelectHandler handlerDeleteUserDo() {
		SelectHandler handler = new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				widgetTableUserPM.setStatusBusy();
				manageService.deleteUserPm(widgetTableUserPM.getReturnedvalue().getId(), widgetTableUserPM.getReturnedvalue().getLoginName(), getSelectedServerName(), deleteUserPmCallback());
				Info.display("", messages.userDeleted());
				dialogDelete.hide();
			}
		};
		return handler;	
	}
		
	/**
	 * Обновление, после удаления пользователя
	 * @return
	 */
	protected AsyncCallback<Void> deleteUserPmCallback() {
		return new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				widgetTableUserPM.getItems().remove(widgetTableUserPM.getReturnedvalue());
				widgetTableUserPM.setStatusClear();
				updatePmUserInfo(textField.getCurrentValue(), comboServer.getCurrentValue().getShortName());
			}
			
			@Override
			public void onFailure(Throwable caught) {
				widgetTableUserPM.setStatusClear();
			}
		};
	}
	
	
	/**
	 * Добавление виджета пользователей из xls
	 * @param result
	 */
	public void addUsersXlsDetail(List<UserProxy> result) {
		try {
			containerHm.clear();
			containerApplication.clear();
			
			HorizontalLayoutData layoutData = new HorizontalLayoutData();
			layoutData.setHeight(1);
			layoutData.setWidth(1);
			
			containerHtop = new HorizontalLayoutContainer();
			containerHtop.setLayoutData(layoutData);
					
			final TableUsersXls widget = new TableUsersXls(result, messages, loginName);
			//widget.setStatusBusy();
			
			HorizontalLayoutData layoutData2 = new HorizontalLayoutData(0.5, 1, new Margins(5));
			containerHtop.add(widget, layoutData2);
			
			VerticalLayoutData  verticalLayoutData = new VerticalLayoutData();
			verticalLayoutData.setHeight(0.5);
			verticalLayoutData.setWidth(1);
			
			containerV = new VerticalLayoutContainer();
			containerV.setId(M_CONTAINER_V);
			containerV.setBorders(false);
			containerV.setAdjustForScroll(true);
			containerV.setLayoutData(verticalLayoutData);
			
			addToContainerV(containerHtop);
			addToPanel(center, containerV, "");
			
		} catch (Exception e) {
			e.printStackTrace();
			new MessageBox("Users Xls DetailInfo", "error !!!! " + e.getLocalizedMessage()).show();
		}
	}

	/**
	 * Заполнение списка пользователей JBoss5/Active Directory
	 * @param result
	 * @param isFromAD
	 */
	public void feelWidgetTableUserPM(List<UserProxy> result, boolean isFromAD) {
		containerHm.clear();
		containerApplication.clear();
		
				
		widgetTableUserPM = new TableUserPm(result, applicationProxyAllList, messages, isFromAD, comboServer.getCurrentValue().getShortName(), manageService, loginName);
		widgetTableUserPM.addHandlerButtonDetail(handlerDetailUserIds());

		widgetTableUserPM.addHandlerButtonAcl(handlerDetailAcl());

		widgetTableUserPM.getButtonDetail().setEnabled(false);
		widgetTableUserPM.getButtonDelete().setEnabled(false);
		widgetTableUserPM.getButtonCopy().setEnabled(false);
		widgetTableUserPM.getButtonEdit().setEnabled(false);
				
		HorizontalLayoutData layoutData = new HorizontalLayoutData();
		layoutData.setHeight(1);
		layoutData.setWidth(1);
		
		containerHtop = new HorizontalLayoutContainer();
		containerHtop.clear();
		//containerHtop.setBorders(true);
		containerHtop.setLayoutData(layoutData);
		
				
		HorizontalLayoutData layoutData2 = new HorizontalLayoutData(0.5, 1, new Margins(5));
		containerHtop.add(widgetTableUserPM, layoutData2);
		
		
		HorizontalLayoutData layoutData3 = new HorizontalLayoutData(0.5, 1, new Margins(5, 5, 5, 0));
		TableApplicationPm applicationPm = new TableApplicationPm(new ArrayList<ApplicationProxy>(), null, messages, getSelectedServerName(), "");
		containerApplication.add(applicationPm, new VerticalLayoutData(1, 1));
		   
		containerHtop.add(containerApplication, layoutData3);
		
		
		VerticalLayoutData  verticalLayoutData = new VerticalLayoutData();
		verticalLayoutData.setHeight(0.5);
		verticalLayoutData.setWidth(1);
		
		containerV = new VerticalLayoutContainer();
		containerV.setId(M_CONTAINER_V);
		containerV.setBorders(false);
		containerV.setAdjustForScroll(true);
		containerV.setLayoutData(verticalLayoutData);
		
							
		addToContainerV(containerHtop);

		addToContainerV(containerHm);
		
		addToPanel(center, containerV, "");
		  
		//addToPanel(center, widgetTableUserPM.asWidget(), "");
		
		widgetTableUserPM.addSelectionHandler(handlerDetailUserPm());
		widgetTableUserPM.handlerDelete(handlerDeleteUserShowDialog());
		widgetTableUserPM.selectDefaultComboValue();
	}
	
	
	private void addToContainerV(HorizontalLayoutContainer container) {
		VerticalLayoutData layoutData4 = new VerticalLayoutData();
		layoutData4.setHeight(0.5);
		layoutData4.setWidth(1);
							
		containerV.add(container, layoutData4);
		containerV.forceLayout();
	}
	
//	----------	AsyncCallback	----------------
	private AsyncCallback<Void> callbackDeleteUserPm(){
		return new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(Void result) {
				
			}
			
		};
	}
	
	/**
	 * 15.09.2015 отображение AclPermission
	 * @return
	 */
	private SelectHandler handlerDetailAcl(){
		SelectHandler handler = new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				
				if(widgetTableUserPM.getSelectedApplication() == null){
					new MessageBox(messages.error(), messages.selectRecord()).show();
					return;
				}				
				
				containerHm.clear();
				containerHm.forceLayout();
				
				updateUserInfoAcl(widgetTableUserPM.getReturnedvalue().getLoginName());
			}
			
		};
		return handler;
	}
	
	private SelectHandler handlerDetailUserIds(){
		SelectHandler handler = new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				
				/*if( widgetTableUserPM.getSelectedApplication() != null)
					new MessageBox("handlerDetail 1", "widgetTableUserPM.getSelectedApplication() - " + widgetTableUserPM.getSelectedApplication().getId()
							+ " - " + Constants.ID_APPLICATION_IDS.toString()).show();*/

				containerHm.clear();
				containerHm.forceLayout();
				
				if(widgetTableUserPM != null ){
					
					if(widgetTableUserPM.getSelectedApplication() == null){
						new MessageBox(messages.error(), messages.selectApplication()).show();
						return;
					}
					
					if(widgetTableUserPM.getSelectedApplication().getId().equals(Constants.ID_APPLICATION_IDS))
							updateIdsUser(widgetTableUserPM.getReturnedvalue().getLoginName());
							
				}
			}
			
		};
	return handler;	
	}
	
	public SelectionHandler<UserProxy> handlerDetailUserPm() {
		SelectionHandler<UserProxy> handler = new SelectionHandler<UserProxy>() {
			
			@Override
			public void onSelection(SelectionEvent<UserProxy> event) {
				if(Constants.server_name_AD.equals(getSelectedServerName()))
					return;
					
				manageService.getApplicationPmList(widgetTableUserPM.getReturnedvalue().getLoginName(), getSelectedServerName(), callbackDetailUserPm);
			}
		};
		return handler;
	}
	
	private AsyncCallback<List<ApplicationProxy>> callbackDetailUserPmApplications = new AsyncCallback<List<ApplicationProxy>>() {
		@Override
		public void onSuccess(List<ApplicationProxy> result) {
			applicationProxyAllList  = result;
		}
		
		@Override
		public void onFailure(Throwable caught) {
			new MessageBox("callbackDetailUserPmApplications", "error - " + caught.getLocalizedMessage()).show();
		}
	};
	
	private AsyncCallback<List<ApplicationProxy>> callbackDetailUserPm = new AsyncCallback<List<ApplicationProxy>>() {
		
		@Override
		public void onSuccess(List<ApplicationProxy> result) {
			containerApplication.clear();
			
			/** disable если пользователь уже добавлен (т.е. у него есть приложения)*/
			widgetTableUserPM.getButtonAdd().setEnabled(result.isEmpty());
			
			widgetTableUserPM.getButtonDetail().setEnabled(true);
			widgetTableUserPM.getButtonDelete().setEnabled(true);
			widgetTableUserPM.getButtonCopy().setEnabled(true);
			widgetTableUserPM.getButtonEdit().setEnabled(true);
			
			TableApplicationPm applicationPm = new TableApplicationPm(result, widgetTableUserPM.getReturnedvalue().getLoginName(), messages, getSelectedServerName(),  widgetTableUserPM.getReturnedvalue().getFullName());
			containerApplication.add(applicationPm, new VerticalLayoutData(1, 1));
			containerApplication.forceLayout();
			////applicationPm.addSelectionToTable();
			
			containerHm.clear();
			containerHm.forceLayout();
			addToContainerV(containerHm);
			}
		
		@Override
		public void onFailure(Throwable caught) {
			new MessageBox("callbackDetailUserPm", "error - " + caught.getLocalizedMessage()).show();
		}
	};
	private ToolBar toolBar;



	protected SelectionHandler<ApplicationProxy> selectionApplication() {
		SelectionHandler<ApplicationProxy> handler = new SelectionHandler<ApplicationProxy>() {
			
			@Override
			public void onSelection(SelectionEvent<ApplicationProxy> event) {
				new MessageBox("selectionApplication", "event - " + event.getSelectedItem().getFullName()).show();
			}
		};
		return handler;
	}

	public TextButton getButtonFind() {
		return buttonFind;
	}

	public String getLoginName() {
		return loginName;
	}

	public String getSelectedServerName() {
		if(comboServer.getCurrentValue() != null)
			return comboServer.getCurrentValue().getShortName();
		
		return Constants.server_name_jboss;
	}
}
