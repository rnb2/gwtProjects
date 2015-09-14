package com.rnb2.gwt1.client.widgets.window;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rnb2.gwt1.client.Mainwidget2;
import com.rnb2.gwt1.client.ManageService;
import com.rnb2.gwt1.client.ManageServiceAsync;
import com.rnb2.gwt1.client.messages.MyMessages;
import com.rnb2.gwt1.client.utils.CustomWidgets;
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
	
	
	public WindowUserPmAddCopy() {
	}

	/**
	 * Копирование пользователей PM (jboss5-test)
	 * @author budukh-rn
	 * 08 сент. 2015 г.	
	 *
	 */
	public WindowUserPmAddCopy(String title, final UserProxy userProxy,  MyMessages messages, final String serverName) {
		this.messages = messages;
			
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
				
				manageService.addUserCopyPm(loginName.getCurrentValue(), fullName.getCurrentValue(), phone.getCurrentValue(), loginNameOld.getValue(), serverName, callbackAddUser());

				isOkclicked = true;
				Mainwidget2 mainwidget2 = Mainwidget2.getInstance();
				mainwidget2.setFindFieldText(loginName.getValue());
				//mainwidget2.handlerFindUsersPm().onSelect(event);
			}
		});
		addButton(bSave);
		addButton(bClose);
	}

	protected AsyncCallback<String> callbackAddUser() {
		return new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				CustomWidgets.createAlert(messages.copyUser(), messages.errorCopyUser());
			}

			@Override
			public void onSuccess(String result) {
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
