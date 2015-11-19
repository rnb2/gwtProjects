package com.rnb2.gwt1.client.widgets.window;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rnb2.gwt1.client.Mainwidget2;
import com.rnb2.gwt1.client.ManageService;
import com.rnb2.gwt1.client.ManageServiceAsync;
import com.rnb2.gwt1.client.messages.MyMessages;
import com.rnb2.gwt1.client.model.combo.ServerProperties;
import com.rnb2.gwt1.client.utils.Constants;
import com.rnb2.gwt1.client.utils.CustomWidgets;
import com.rnb2.gwt1.client.widgets.TableUserPm;
import com.rnb2.gwt1.data.pm.proxy.UserProxy;
import com.rnb2.gwt1.shared.ServerProxy;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;


/**
 * Копирование пользователей PM (jboss5-test)
 * @author budukh-rn
 * 08 сент. 2015 г.	
 *
 */
public class WindowUserPmAddCopy extends Window {

	private final ManageServiceAsync manageService = GWT
			.create(ManageService.class);
	private MyMessages messages;
	private boolean isOkclicked = false;
	private ComboBox<ServerProxy> comboServer;
	
	public WindowUserPmAddCopy() {
	}

	/**
	 * Копирование пользователей PM (jboss5-test)
	 * @author budukh-rn
	 * 08 сент. 2015 г.	
	 *
	 */
	public WindowUserPmAddCopy(String title, final UserProxy userProxy,  final MyMessages messages, final String serverName, final String userNameCreated) {
		this.messages = messages;
			
		setModal(true);
		setBlinkModal(true);
		setHeadingText(title);
		setBodyStyle("padding: 5px");
		setWidth(350);
		
		String loginNameS = "";
		String fullNameS = "";
		String phoneS = "";
		String employeeIdS = "";
		if(userProxy != null){
			 loginNameS = userProxy.getLoginName();
			 fullNameS = userProxy.getFullName();
			 phoneS = userProxy.getWorkPhone();
			 employeeIdS = userProxy.getEmployeeID();
		}

		VerticalLayoutContainer p = new VerticalLayoutContainer();
		add(p);
		
		//для вызова формы из таблицы xls
		if(serverName == null){
			List<ServerProxy> serversList = new ArrayList<ServerProxy>();
			 serversList.add(new ServerProxy(1, Constants.server_name_jboss));
			 serversList.add(new ServerProxy(2, Constants.server_name_jboss_5));
			 serversList.add(new ServerProxy(3,  Constants.server_name_jboss_01));
			 
			 ServerProperties propsServers = GWT.create(ServerProperties.class);
			 ListStore<ServerProxy> storeServer = new ListStore<ServerProxy>(propsServers.key());
			 storeServer.addAll(serversList);			 
			 comboServer = new ComboBox<ServerProxy>(storeServer, propsServers.fullName());
			 comboServer.setTriggerAction(TriggerAction.ALL);
			 comboServer.setForceSelection(true);
			 comboServer.setAllowBlank(false);
			 p.add(new FieldLabel(comboServer, messages.server()), new VerticalLayoutData(1, -1));
		}
		 
	    final TextField loginNameOld = new TextField();
	    loginNameOld.setAllowBlank(false);
	    loginNameOld.setEmptyText(messages.inputValue());
	    loginNameOld.setValue(loginNameS);
	    loginNameOld.setEnabled(false);
	    p.add(new FieldLabel(loginNameOld, messages.loginNameOld()), new VerticalLayoutData(1, -1));

	    final TextField loginName = new TextField();
	    loginName.setAllowBlank(false);
	    loginName.setEmptyText(messages.inputValue());
	    p.add(new FieldLabel(loginName, messages.loginNameNew()), new VerticalLayoutData(1, -1));
	    
	    final TextField fullName = new TextField();
	    fullName.setEmptyText(messages.inputValue());
	    fullName.setAllowBlank(false);
	    fullName.setValue(fullNameS);
	    p.add(new FieldLabel(fullName, messages.fullName()), new VerticalLayoutData(1, -1));
	    
	    final TextField employeeId = new TextField();
	    employeeId.setAllowBlank(true);
	    employeeId.setValue(employeeIdS);
	    p.add(new FieldLabel(employeeId, messages.employeID()), new VerticalLayoutData(1, -1));
	    
	    final TextField phone = new TextField();
	    phone.setAllowBlank(true);
	    phone.setValue(phoneS);
	    p.add(new FieldLabel(phone, messages.phone()), new VerticalLayoutData(1, -1));
		
		
		TextButton bClose = new TextButton(messages.close());
		bClose.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				isOkclicked = false;
				hide();
			}
		});
		TextButton bSave = new TextButton(messages.save());
		bSave.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				
				TableUserPm.getInstance().setStatusBusy();
				if(serverName == null){
					if(comboServer.getCurrentValue() == null){
						CustomWidgets.createAlert(messages.error(), messages.errorSelectServer());
						return;
					}
					manageService.addUserCopyPm(loginName.getCurrentValue(), fullName.getCurrentValue(), phone.getCurrentValue(), employeeId.getCurrentValue(), loginNameOld.getValue(), comboServer.getCurrentValue().getShortName(), userNameCreated, callbackAddUser());
				}else{	
					manageService.addUserCopyPm(loginName.getCurrentValue(), fullName.getCurrentValue(), phone.getCurrentValue(), employeeId.getCurrentValue(), loginNameOld.getValue(), serverName,  userNameCreated, callbackAddUser());
				}	

				isOkclicked = true;
				Mainwidget2 mainwidget2 = Mainwidget2.getInstance();
				mainwidget2.setFindFieldText(loginName.getValue());
				//mainwidget2.handlerFindUsersPm().onSelect(event);
			}
		});
		addButton(bSave);
		addButton(bClose);
		setFocusWidget(loginName);
	}

	protected AsyncCallback<String> callbackAddUser() {
		return new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				TableUserPm.getInstance().setStatusClear();
				CustomWidgets.createAlert(messages.copyUser(), messages.errorCopyUser());
			}

			@Override
			public void onSuccess(String result) {
				TableUserPm.getInstance().setStatusClear();
				if(result == "-1"){
					CustomWidgets.createAlert(messages.copyUser(), messages.errorCopyUser());
				}else{
					Info.display(messages.copyUser(), messages.userAdded());
					hide();
				}
			}
			
		};
	}

	public boolean isOkclicked() {
		return isOkclicked;
	}


}
