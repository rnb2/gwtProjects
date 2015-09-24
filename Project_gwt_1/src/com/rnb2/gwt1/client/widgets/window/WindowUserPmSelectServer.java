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
 * Выбор сервера JBOSS 
 * @author budukh-rn
 * 28 сент. 2015 г.	
 *
 */
public class WindowUserPmSelectServer extends Window {

	private final ManageServiceAsync manageService = GWT
			.create(ManageService.class);
	private MyMessages messages;
	private boolean isOkclicked = false;
	private ComboBox<ServerProxy> comboServer;
	
	public WindowUserPmSelectServer() {
	}

	/**
	 * Выбор сервера JBOSS 
	 * @author budukh-rn
	 * 28 сент. 2015 г.	
	 *
	 */
	public WindowUserPmSelectServer(String title, final MyMessages messages, final List<UserProxy> list) {
		this.messages = messages;
			
		setModal(true);
		setBlinkModal(true);
		setHeadingText(title);
		setBodyStyle("padding: 5px");
		setWidth(350);
		
		
		VerticalLayoutContainer p = new VerticalLayoutContainer();
		add(p);
		
		List<ServerProxy> serversList = new ArrayList<ServerProxy>();
		int i=1;
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
				
				if(comboServer.getCurrentValue() == null){
					CustomWidgets.createAlert(messages.error(), messages.errorSelectServer());
					return;
				}
				CustomWidgets.showWaitCursor();
				manageService.addUserCopyPmAll(list, comboServer.getCurrentValue().getShortName(), callbackCopyAll());

				isOkclicked = true;
				
			}
		});
		addButton(bSave);
		addButton(bClose);
	}

	protected AsyncCallback<String> callbackCopyAll() {
		return new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				CustomWidgets.showDefaultCursor();
				CustomWidgets.createAlert(messages.copyAllUsers2(), messages.errorCopyUser());
			}

			@Override
			public void onSuccess(String result) {
				CustomWidgets.showDefaultCursor();
				if(result == "-1"){
					CustomWidgets.createAlert(messages.copyAllUsers2(), messages.errorCopyUser());
				}else{
					Info.display(messages.copyAllUsers2(), messages.usersCopied());
					hide();
				}
			}
			
		};
	}

	public boolean isOkclicked() {
		return isOkclicked;
	}


}
