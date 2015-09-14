package com.rnb2.gwt1.client.widgets.window;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rnb2.gwt1.client.Mainwidget2;
import com.rnb2.gwt1.client.ManageService;
import com.rnb2.gwt1.client.ManageServiceAsync;
import com.rnb2.gwt1.client.messages.MyMessages;
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
 * Добавление пользователей ИДС УЖДТ
 * @author budukh-rn
 * 14.01.2014
 *
 */
public class WindowIdsAddUser extends Window {

	private final ManageServiceAsync manageService = GWT
			.create(ManageService.class);
	private MyMessages messages;
	private boolean isOkclicked = false;
	
	
	
	/**
	 * Добавление пользователей ИДС УЖДТ
	 * @author budukh-rn
	 * 14.01.2014
	 *
	 */
	public WindowIdsAddUser(String title, MyMessages messages) {
		this(title, null, messages);
	}
	
	/**
	 * Добавление пользователей ИДС УЖДТ
	 * @author budukh-rn
	 * 14.01.2014
	 *
	 */
	public WindowIdsAddUser(String title, final UserProxy userProxy,  MyMessages messages) {
		this.messages = messages;
			
				
		setModal(true);
		setBlinkModal(true);
		setHeadingText(title);
		setBodyStyle("padding: 5px");
		setWidth(350);
		
		String loginNameS = "";
		String fullNameS = "";

		if(userProxy != null){
			 loginNameS = userProxy.getLoginName();
			 fullNameS = userProxy.getFullName();
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

				if(loginName.getCurrentValue() != null && loginName.getCurrentValue().isEmpty()){
					return;
				}
					
				manageService.addUserIds(loginName.getCurrentValue(), fullName.getCurrentValue(), Mainwidget2.getInstance().getLoginName(), addUserIdsCallback(userProxy));
				
				isOkclicked = true;
				Mainwidget2 mainwidget2 = Mainwidget2.getInstance();
				mainwidget2.setFindFieldText(loginName.getValue());
				mainwidget2.handlerFindUsersPm().onSelect(event);
			}
		});
		addButton(bSave);
		addButton(bClose);
	}

	
	protected AsyncCallback<Boolean> addUserIdsCallback(final UserProxy userProxy) {
		return new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				Info.display(messages.error(), messages.userAddedAlreadey());
			}

			@Override
			public void onSuccess(Boolean result) {
				if(result){
					Info.display(messages.userAdd(), messages.userAdded());
					Mainwidget2.getInstance().updateIdsUser(userProxy.getLoginName());
					hide();
				}else{
					Info.display(messages.userAdd(), messages.error());
					
				}
				
			}
			
		};
	}

	public boolean isOkclicked() {
		return isOkclicked;
	}

	
}
