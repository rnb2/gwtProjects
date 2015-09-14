/**
 * 
 */
package com.rnb2.gwt1.client.widgets.window;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rnb2.gwt1.client.ManageService;
import com.rnb2.gwt1.client.ManageServiceAsync;
import com.rnb2.gwt1.client.images.Images;
import com.rnb2.gwt1.client.messages.MyMessages;
import com.rnb2.gwt1.client.model.combo.ApplicationProperties;
import com.rnb2.gwt1.data.pm.Application;
import com.rnb2.gwt1.data.pm.Permission;
import com.rnb2.gwt1.data.pm.proxy.ApplicationProxy;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.MessageBox;
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
 * @author budukh-rn
 *
 */
public class WindowUserPmAddRoleNew extends Window {
	

	private final ManageServiceAsync manageService = GWT
			.create(ManageService.class);
	private MyMessages messages;
	private ListStore<ApplicationProxy> storeApp;
	private ApplicationProxy selectedApp;
	protected Application toAddApplication;
		
	/**
	 *  Добавление новой роли
	 */
	public WindowUserPmAddRoleNew() {
	}
	
	/**
	 *  Добавление новой роли
	 */
	public WindowUserPmAddRoleNew(MyMessages messages2, final String serverName) {
		this.messages = messages2;
		
		setModal(true);
		setBlinkModal(true);
		setHeadingText(messages.RoleAdd());
		setBodyStyle("padding: 5px");
		setWidth(350);
		
		VerticalLayoutContainer p = new VerticalLayoutContainer();
		add(p);
		
		ApplicationProperties propsApp = GWT.create(ApplicationProperties.class);
		storeApp = new ListStore<ApplicationProxy>(propsApp.key());
		manageService.getApplicationPmList2(serverName, callbackApp());
		
		ComboBox<ApplicationProxy> comboApp = new ComboBox<ApplicationProxy>(storeApp, propsApp.fullName());
	    addHandlersForEventObservation(comboApp, propsApp.fullName());
	    comboApp.setEmptyText(messages.selectRecord()+"...");
	    //comboApp.setWidth(300);
	    comboApp.setTypeAhead(true);
	    comboApp.setTriggerAction(TriggerAction.ALL);
	    comboApp.setAllowBlank(false);
	    
	    p.add(new FieldLabel(comboApp, messages.application()), new VerticalLayoutData(1, -1));
	    
		final TextField shortName = new TextField();
	    shortName.setAllowBlank(true);
	    p.add(new FieldLabel(shortName, messages.name()), new VerticalLayoutData(1, -1));
	    
	    
	    final TextField description = new TextField();
	    description.setAllowBlank(true);
	    p.add(new FieldLabel(description, messages.description()), new VerticalLayoutData(1, -1));
	    
		TextButton bClose = new TextButton(messages.close());
		bClose.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				hide();
			}
		});
		TextButton bSave = new TextButton(messages.save());
		bSave.setIcon(Images.INSTANCE.save());
		bSave.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
					Permission obj = new Permission();
					obj.setName(shortName.getValue());
					obj.setDescription(description.getValue());
					
										
					manageService.addRole(obj, selectedApp.getId(), serverName, callback());
				
			}
		});
		addButton(bSave);
		addButton(bClose);
	}

	protected AsyncCallback<Application> callbackFind() {
		
		return new AsyncCallback<Application>() {

			@Override
			public void onFailure(Throwable caught) {
				new MessageBox("callbackFind: error manageService: " 
						+ caught.getLocalizedMessage()).show();
				
			}

			@Override
			public void onSuccess(Application result) {
				if(result != null)
					toAddApplication = result;
			}
						
		};
	}

	protected AsyncCallback<Void> callback() {

		return new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				Info.display("", messages.permisionAdded());
				hide();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				new MessageBox("callback save: error manageService: " 
						+ caught.getLocalizedMessage()).show();
			}
		};
	}
	
	private AsyncCallback<List<ApplicationProxy>> callbackApp() {
		return new AsyncCallback<List<ApplicationProxy>>() {
			
			@Override
			public void onSuccess(List<ApplicationProxy> result) {
				storeApp.clear();
				storeApp.addAll(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				new MessageBox("callbackApp: error manageService: " 
						+ caught.getLocalizedMessage()).show();
				
			}
		};
	}
	
	private void addHandlersForEventObservation(ComboBox<ApplicationProxy> comboApp,	LabelProvider<ApplicationProxy> fullName) {
		comboApp.addSelectionHandler(new SelectionHandler<ApplicationProxy>() {

			@Override
			public void onSelection(SelectionEvent<ApplicationProxy> event) {
				selectedApp =  event.getSelectedItem();
			}
		});
	}
}
