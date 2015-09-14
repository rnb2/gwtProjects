package com.rnb2.gwt1.client.widgets.window;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rnb2.gwt1.client.Mainwidget2;
import com.rnb2.gwt1.client.ManageService;
import com.rnb2.gwt1.client.ManageServiceAsync;
import com.rnb2.gwt1.client.images.Images;
import com.rnb2.gwt1.client.messages.MyMessages;
import com.rnb2.gwt1.data.pm.User;
import com.rnb2.gwt1.data.pm.proxy.UserProxy;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
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
		
	
	/**
	 * Добавление пользователя PM (jboss5-test)
	 */
	public WindowUserPmAdd(String title, MyMessages messages, String serverName) {
		this(title, null, messages, serverName);
	}
	
	/**
	 * Редактирование пользователя PM (jboss5-test)
	 */
	public WindowUserPmAdd(String title, final UserProxy userProxy,  MyMessages messages, final String serverName) {
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
		if(userProxy != null){
			 loginNameS = userProxy.getLoginName();
			 fullNameS = userProxy.getFullName();
			 phoneS = userProxy.getWorkPhone();
		}

		VerticalLayoutContainer p = new VerticalLayoutContainer();
		add(p);
		 
	    final TextField loginName = new TextField();
	    loginName.setAllowBlank(false);
	    loginName.setEmptyText(messages.inputValue());
	    loginName.setValue(loginNameS);
	    p.add(new FieldLabel(loginName, messages.loginName()), new VerticalLayoutData(1, -1));
	    
	    final TextField fullName = new TextField();
	    fullName.setEmptyText(messages.inputValue());
	    fullName.setAllowBlank(false);
	    fullName.setValue(fullNameS);
	    p.add(new FieldLabel(fullName, messages.fullName()), new VerticalLayoutData(1, -1));
	    
	    
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
		bSave.setIcon(Images.INSTANCE.save());
		bSave.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
					User user = new User();
					user.setLoginName(loginName.getCurrentValue());
					user.setFullName(fullName.getCurrentValue());
					user.setWorkPhone(phone.getCurrentValue());
				if(isAdd){
					manageService.addUserPm2(loginName.getCurrentValue(), fullName.getCurrentValue(), phone.getCurrentValue(), serverName, callbackAddUser());
				}else{
					manageService.mergeUserPm(user, userProxy.getId(), serverName, callbackAddUser());
				}
				isOkclicked = true;
				Mainwidget2 mainwidget2 = Mainwidget2.getInstance();
				mainwidget2.setFindFieldText(loginName.getValue());
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
				Info.display(messages.error(), messages.userAddedAlreadey());
			}

			@Override
			public void onSuccess(Void result) {
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
