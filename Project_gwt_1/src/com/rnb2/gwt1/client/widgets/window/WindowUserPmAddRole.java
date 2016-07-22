/**
 * 
 */
package com.rnb2.gwt1.client.widgets.window;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.rnb2.gwt1.client.Mainwidget2;
import com.rnb2.gwt1.client.ManageService;
import com.rnb2.gwt1.client.ManageServiceAsync;
import com.rnb2.gwt1.client.messages.MyMessages;
import com.rnb2.gwt1.client.model.combo.ApplicationProperties;
import com.rnb2.gwt1.client.widgets.LabelPanel;
import com.rnb2.gwt1.data.pm.proxy.ApplicationProxy;
import com.rnb2.gwt1.data.pm.proxy.PermissionProxy;
import com.rnb2.gwt1.data.pm.proxy.PermissionProxyFull;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.DualListField;
import com.sencha.gxt.widget.core.client.form.DualListField.Mode;
import com.sencha.gxt.widget.core.client.form.validator.EmptyValidator;
import com.sencha.gxt.widget.core.client.info.Info;

/**
 * Добавление ролей пользователю
 * @author budukh-rn
 *
 */
public class WindowUserPmAddRole extends Window {

	private static final String IDS_UZDT = "IDS_UZDT";
	private final ManageServiceAsync manageService = GWT
			.create(ManageService.class);
	protected ListStore<PermissionProxy> store2;
	private ListStore<PermissionProxy> store1;
	private ListStore<ApplicationProxy> storeApp;
	private MyMessages messages;
	private String login, shortName;
	protected ApplicationProxy selectedApp;

			
	interface PermisionProperties extends PropertyAccess<PermissionProxy> {
		@Path("id")
		ModelKeyProvider<PermissionProxy> key();

		@Path("fullName")
		ValueProvider<PermissionProxy, String> fullName();
	}
	
	/**
	 * Добавление ролей пользователю
	 * 
	 */
	public WindowUserPmAddRole(final String login, final String shortname, final String fio, MyMessages messages, final String serverName) {
		
		this.messages = messages;
		this.login = login;
		this.shortName = shortname;
				
		setModal(true);
		setBlinkModal(true);
		setHeadingText(messages.permisions());
		setBodyStyle("padding: 5px");
		setWidth(700);
		setHeight(400);
		setResizable(true);
		
		VerticalLayoutContainer p = new VerticalLayoutContainer();
		add(p);
		
		ApplicationProperties propsApp = GWT.create(ApplicationProperties.class);
		storeApp = new ListStore<ApplicationProxy>(propsApp.key());
		manageService.getApplicationPmList2(serverName, callbackApp());
		
		ComboBox<ApplicationProxy> comboApp = new ComboBox<ApplicationProxy>(storeApp, propsApp.fullName());
	    addHandlersForEventObservation(comboApp, propsApp.fullName(), serverName);
	    comboApp.setEmptyText(messages.selectRecord()+"...");
	    comboApp.setWidth(670);
	    comboApp.setTypeAhead(true);
	    comboApp.setTriggerAction(TriggerAction.ALL);
	    
	    for (ApplicationProxy pr : storeApp.getAll()) {
			if(pr.getShortName().equals(shortName)){
				selectedApp = pr;
				break;
			}	
		}
	    if(selectedApp != null){
	    	//TODO выбранное приложение не отображается в комбо ??????
	    	ApplicationProxy findModelWithKey = comboApp.getStore().findModel(selectedApp);
			comboApp.setValue(findModelWithKey);
	    	Label label = new Label(selectedApp.getFullName());
	    	comboApp.setEnabled(false);
	    	p.add(label);
	    }
	    
	    p.add(comboApp);
	    
		
		PermisionProperties props = GWT.create(PermisionProperties.class);
		store1 = new ListStore<PermissionProxy>(props.key());
		store2 = new ListStore<PermissionProxy>(props.key());
		
		manageService.getApplicationPmPermissionFull(login, this.shortName, serverName, callbackGetPermisions1());
		
		p.add(new LabelPanel(messages.allRoles()+":", messages.userRoles()+":", 290));
        
		final DualListField<PermissionProxy, String> dualList = new DualListField<PermissionProxy, String>(store1, store2, props.fullName(), new TextCell());
	    dualList.addValidator(new EmptyValidator<List<PermissionProxy>>());
	    dualList.setEnableDnd(true);
	    dualList.setMode(Mode.INSERT);
	   
	  
	  /* ContentPanel contentPanel = new ContentPanel();
	   contentPanel.setHeadingText(messages.allRoles()+":");
	   contentPanel.add(dualList);*/
	  
	      
	    p.add(dualList, new VerticalLayoutData(1, 1));

		
		TextButton bClose = new TextButton(messages.close());
		bClose.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				store2.commitChanges();
				hide();
			}
		});
		TextButton bSave = new TextButton(messages.save());
		bSave.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				
			try{
				
				List<PermissionProxy> list = new ArrayList<PermissionProxy>(store2.getAll());
				manageService.addUserPermision(login, shortName, list, serverName, callbackSave());
				
				/**проверка на приложение IDS_UZDT у пользователя, 
				*если выбрано то добавляем польз в Users
				*/
				boolean isAddUserIds = false;
				for(PermissionProxy pm : list){
					pm.getShortName().equals(IDS_UZDT);
					isAddUserIds = true;
					break;
				}
				if(isAddUserIds){
					manageService.addUserIds(login, fio, Mainwidget2.getInstance().getLoginName(), serverName, null);
				}	
				//
								
			}catch(Exception e){
				new MessageBox("getModifiedRecords Exception", e.fillInStackTrace().toString())
				.show();
			}
				
			}
		});
		
		addButton(bSave);
		addButton(bClose);
	}
	
	private void addHandlersForEventObservation(ComboBox<ApplicationProxy> comboApp, LabelProvider<ApplicationProxy> fullName, final String serverName) {
		comboApp.addSelectionHandler(new SelectionHandler<ApplicationProxy>() {

			@Override
			public void onSelection(SelectionEvent<ApplicationProxy> event) {
				ApplicationProxy proxy = event.getSelectedItem();
				if(proxy != null){
					shortName = proxy.getShortName();
					manageService.getApplicationPmPermissionFull(login, shortName, serverName, callbackGetPermisions1());
				}
			}
		});
	}

	private AsyncCallback<List<ApplicationProxy>> callbackApp() {
		return new AsyncCallback<List<ApplicationProxy>>() {
			
			@Override
			public void onSuccess(List<ApplicationProxy> result) {
				storeApp.clear();
				storeApp.addAll(result);
				/*for (ApplicationProxy proxy : result) {
					if (proxy.getShortName().equals(shortName)) {
						selectedApp = proxy;
					}
				}*/
			}
			
			@Override
			public void onFailure(Throwable caught) {
				new MessageBox("callbackApp: error manageService: " 
						+ caught.getLocalizedMessage()).show();
				
			}
		};
	}

	protected AsyncCallback<String> callbackSave() {
		return new AsyncCallback<String>() {
			final Mainwidget2 mainwidget2 = Mainwidget2.getInstance();
			
				@Override
				public void onSuccess(String result) {
					if(result.equals("1")){
						mainwidget2.updatePmUserInfo(login, mainwidget2.getSelectedServerName());
						Info.display("", messages.recordAdded());
						hide();
					}
				}
				
				@Override
				public void onFailure(Throwable caught) {

					new MessageBox("addPermissionUser error manageService: " 
							+ caught.getLocalizedMessage()).show();
				}
			};
	}

	private AsyncCallback<PermissionProxyFull> callbackGetPermisions1() {
		return new AsyncCallback<PermissionProxyFull>() {

			@Override
			public void onSuccess(PermissionProxyFull result) {
				store1.clear();
				store1.addAll(result.getList1());
				
				store2.clear();
				store2.addAll(result.getList2());
				
			}

			@Override
			public void onFailure(Throwable caught) {
				new MessageBox("show permision 1", caught.getMessage())
						.show();
			}
		};
	}


}
