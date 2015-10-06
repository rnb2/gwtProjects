package com.rnb2.gwt1.client.widgets.window;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rnb2.gwt1.client.Mainwidget2;
import com.rnb2.gwt1.client.ManageService;
import com.rnb2.gwt1.client.ManageServiceAsync;
import com.rnb2.gwt1.client.images.Images;
import com.rnb2.gwt1.client.messages.MyMessages;
import com.rnb2.gwt1.client.model.combo.ServerProperties;
import com.rnb2.gwt1.client.utils.Constants;
import com.rnb2.gwt1.client.utils.CustomWidgets;
import com.rnb2.gwt1.client.widgets.TableUserPm;
import com.rnb2.gwt1.data.pm.User;
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
 * Добавление/Редактирование пользователей PM (jboss5-test)
 * @author budukh-rn
 *
 */
public class WindowUserPmAdd extends Window {

	private final ManageServiceAsync manageService = GWT
			.create(ManageService.class);
	private MyMessages messages;
	private boolean isAdd = false;
	private boolean isOkclicked = false;
	private ComboBox<ServerProxy> comboServer;	
	
	/**
	 * Добавление пользователя PM (jboss5-test)
	 */
	public WindowUserPmAdd(String title, MyMessages messages, String serverName) {
		this(title, null, messages, serverName);
	}
	
	/**
	 * Редактирование пользователя PM (jboss5-test)
	 */
	public WindowUserPmAdd(String title, final UserProxy userProxy,  final MyMessages messages, final String serverName) {
		this.messages = messages;
	
			
		isAdd = userProxy == null;
		if(userProxy != null && userProxy.isFromAD()){
			isAdd = true;
		}
		
		setModal(true);
		setBlinkModal(true);
		setHeadingText(title);
		setBodyStyle("padding: 5px");
		setWidth(350);
		
		String loginNameS = "";
		String fullNameS = "";
		String phoneS = "";
		String employeeIDS = "";
		if(userProxy != null){
			 loginNameS = userProxy.getLoginName();
			 fullNameS = userProxy.getFullName();
			 phoneS = userProxy.getWorkPhone();
			 employeeIDS = userProxy.getEmployeeID();
		}
		VerticalLayoutContainer p = new VerticalLayoutContainer();

		if(isAdd){
			List<ServerProxy> serversList = new ArrayList<ServerProxy>();
			
			int i=0;
			for(String server : Constants.serverList){
				serversList.add(new ServerProxy(i, server));
				i++;
			}
		 
			 ServerProperties propsServers = GWT.create(ServerProperties.class);
			 ListStore<ServerProxy> storeServer = new ListStore<ServerProxy>(propsServers.key());
			 storeServer.addAll(serversList);			 
			 comboServer = new ComboBox<ServerProxy>(storeServer, propsServers.fullName());
			 comboServer.setTriggerAction(TriggerAction.ALL);
			 comboServer.setForceSelection(true);
			 comboServer.setAllowBlank(false);
			 p.add(new FieldLabel(comboServer, messages.server()), new VerticalLayoutData(1, -1));
		} 
		 
	    final TextField loginName = new TextField();
	    loginName.setAllowBlank(false);
	    loginName.setEmptyText(messages.inputValue());
	    loginName.setValue(loginNameS);
	    
	    final TextField fullName = new TextField();
	    fullName.setEmptyText(messages.inputValue());
	    fullName.setAllowBlank(false);
	    fullName.setValue(fullNameS);

	    final TextField emploeeId = new TextField();
	    emploeeId.setEmptyText(messages.inputValue());
	    emploeeId.setAllowBlank(true);
	    emploeeId.setValue(employeeIDS);
	
	    
	    final TextField phone = new TextField();
	    phone.setAllowBlank(true);
	    phone.setValue(phoneS);
	    
	    p.add(new FieldLabel(loginName, messages.loginName()), new VerticalLayoutData(1, -1));
	    p.add(new FieldLabel(fullName, messages.fullName()), new VerticalLayoutData(1, -1));
	    p.add(new FieldLabel(emploeeId, messages.employeID()), new VerticalLayoutData(1, -1));
	    p.add(new FieldLabel(phone, messages.phone()), new VerticalLayoutData(1, -1));
		
	    add(p);
		
		TextButton bClose = new TextButton(messages.close());
		bClose.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				isOkclicked = false;
				hide();
			}
		});
		TextButton bSave = new TextButton(messages.save());
		bSave.setIcon(Images.INSTANCE.save());
		bSave.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				TableUserPm.getInstance().setStatusBusy();
					User user = new User();
					user.setLoginName(loginName.getCurrentValue());
					user.setFullName(fullName.getCurrentValue());
					user.setWorkPhone(phone.getCurrentValue());
					user.setEmployeeID(emploeeId.getCurrentValue());
					
				if(isAdd){
					if(comboServer.getCurrentValue() == null){
						CustomWidgets.createAlert(messages.error(), messages.errorSelectServer());
						return;
					}
					String serverName = comboServer.getCurrentValue().getShortName();					
					manageService.addUserPm2(loginName.getCurrentValue(), fullName.getCurrentValue(), emploeeId.getCurrentValue(), phone.getCurrentValue(), serverName, callbackAddUser());
				}else{
					manageService.mergeUserPm(user, userProxy.getId(), serverName, callbackAddUser());
				}
				isOkclicked = true;
				Mainwidget2 mainwidget2 = Mainwidget2.getInstance();
				mainwidget2.setFindFieldText(loginName.getValue());
				if(!serverName.equals(Constants.server_name_AD))
					mainwidget2.handlerFindUsersPm().onSelect(event);
			}
		});
		addButton(bSave);
		addButton(bClose);
	}

	protected AsyncCallback<Void> callbackAddUser() {
		return new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				TableUserPm.getInstance().setStatusClear();
				Info.display(messages.error(), messages.userAddedAlreadey());
			}

			@Override
			public void onSuccess(Void result) {
				TableUserPm.getInstance().setStatusClear();
				if(isAdd)
					Info.display("", messages.userAdded());
				else
					Info.display("", messages.userEdited());
				
				hide();
			}
			
		};
	}

	public boolean isOkclicked() {
		return isOkclicked;
	}
	
}
