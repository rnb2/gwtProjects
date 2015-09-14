package com.rnb2.gwt1.client.widgets.window;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rnb2.gwt1.client.Mainwidget2;
import com.rnb2.gwt1.client.ManageService;
import com.rnb2.gwt1.client.ManageServiceAsync;
import com.rnb2.gwt1.client.images.Images;
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
 * Поиск пользователей в AD
 * @author budukh-rn
 * 15.11.2013
 *
 */
public class WindowUserPmSearch extends Window {

	private final ManageServiceAsync manageService = GWT
			.create(ManageService.class);
	private MyMessages messages;
	private boolean isOkclicked = false;
	
		
	/**
	 *  Поиск пользователей в AD
	 */
	public WindowUserPmSearch(String title, MyMessages messages) {
		this.messages = messages;
				
		setModal(true);
		setBlinkModal(true);
		setHeadingText(title);
		setBodyStyle("padding: 5px");
		setWidth(350);
		
		String string = "";
		

		
		VerticalLayoutContainer p = new VerticalLayoutContainer();
		add(p);
		 
	    final TextField loginName = new TextField();
	    final TextField fullName = new TextField();
	    final TextField employeID = new TextField();
	    
	    loginName.setAllowBlank(true);
	    loginName.setEmptyText(messages.inputValue());
	    loginName.setValue(string);
	    
	    fullName.setEmptyText(messages.inputValue());
	    fullName.setAllowBlank(true);
	    fullName.setValue(string);

	    employeID.setEmptyText(messages.inputValue());
	    employeID.setAllowBlank(true);
	    
	    
	    p.add(new FieldLabel(loginName, messages.loginName()), new VerticalLayoutData(1, -1));
	    p.add(new FieldLabel(fullName, messages.fullName()), new VerticalLayoutData(1, -1));
	    p.add(new FieldLabel(employeID, messages.employeID()), new VerticalLayoutData(1, -1));
	      
	   
		TextButton bClose = new TextButton(messages.close());
		bClose.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				isOkclicked = false;
				hide();
			}
		});
		
		
		TextButton bFind = new TextButton(messages.find());
		bFind.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				
				String login = null, fio = null, employeeID = null;
				if(loginName.getCurrentValue() != null && !loginName.getCurrentValue().isEmpty()){
					login = loginName.getCurrentValue();
				}
				if(fullName.getCurrentValue() != null && !fullName.getCurrentValue().isEmpty()){
					fio = fullName.getCurrentValue();
				}
				
				if(employeID.getCurrentValue() != null && !employeID.getCurrentValue().isEmpty()){
					employeeID = employeID.getCurrentValue();
				}
				
				if(login !=null || fio !=null)
					CustomWidgets.showWaitCursor(); 
				
				manageService.searchUserAd(login, fio, employeeID, callbackSearch());
				
			}
		});
		bFind.setIcon(Images.INSTANCE.search());
		
		addButton(bFind);
		addButton(bClose);
	}

	protected AsyncCallback<List<UserProxy>> callbackSearch() {
		return new AsyncCallback<List<UserProxy>>() {
			@Override
			public void onFailure(Throwable caught) {
				CustomWidgets.showDefaultCursor();
				Info.display(messages.error(), messages.error() + caught.getLocalizedMessage());
			}
			@Override
			public void onSuccess(List<UserProxy> result) {
				isOkclicked = true;

				Mainwidget2 mainwidget2 = Mainwidget2.getInstance();
				mainwidget2.feelWidgetTableUserPM(result, true);
				
				if(!result.isEmpty()){
					CustomWidgets.showDefaultCursor();
					Info.display(messages.findUser(), messages.foundUsers() + ": " + result.size());
					hide();
				}
			}
		};
	}

	public boolean isOkclicked() {
		return isOkclicked;
	}

	
}
