package com.rnb2.gwt1.client.widgets.window;

import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rnb2.gwt1.client.Mainwidget2;
import com.rnb2.gwt1.client.ManageServiceAsync;
import com.rnb2.gwt1.client.images.Images;
import com.rnb2.gwt1.client.messages.MyMessages;
import com.rnb2.gwt1.client.model.combo.DepartmentsProperties;
import com.rnb2.gwt1.client.model.combo.EdcPermissionRolesProperties;
import com.rnb2.gwt1.data.idsugdt.EdcPermissionRoles;
import com.rnb2.gwt1.data.idsugdt.proxy.DepartmentProxy;
import com.rnb2.gwt1.data.idsugdt.proxy.UsersProxy;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.util.Margins;
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
import com.sencha.gxt.widget.core.client.info.Info;

/**
 * 15.11.2013
 * Выбор доступа к подразделениям ЭГД
 * @author budukh-rn
 *
 */
public class WindowIdsSelectDepartmentPermis extends Window {

	private final MyMessages messages = GWT.create(MyMessages.class);
	private VerticalLayoutContainer container;
	private UsersProxy usersProxy;
	private ManageServiceAsync manageService;
	private ListStore<DepartmentProxy> listStoreDepartment;
	private ListStore<EdcPermissionRoles> listStoreEdcPermissionRoles;
	private DepartmentProxy selectedItemDepartment = null;
	private EdcPermissionRoles selectedItemEdcPermissionRoles = null;
	private String serverName;
	
	/**
	 * Выбор доступа к подразделениям ЭГД
	 * @param manageService
	 * @param usersProxy
	 */
	public WindowIdsSelectDepartmentPermis(ManageServiceAsync manageService,  UsersProxy usersProxy, String serverName) {
		this.usersProxy = usersProxy;
		this.manageService = manageService;
		this.serverName = serverName;
	
		setModal(true);
		setBlinkModal(true);
		setHeadingText(messages.addRecord());
		setBodyStyle("padding: 5px");
		setWidth(350);
			
		container = new VerticalLayoutContainer();
		add(container);
		
		DepartmentsProperties properties = GWT.create(DepartmentsProperties.class);
		listStoreDepartment = new ListStore<DepartmentProxy>(properties.key());
		this.manageService.getDepartmentList(serverName, callbackUserDocumentList());
		
		ComboBox<DepartmentProxy> combo1 = new ComboBox<DepartmentProxy>(listStoreDepartment, properties.fullName());
		addHandlersForCombo1(combo1, properties.fullName());
	    combo1.setEmptyText(messages.selectRecord()+"...");
	    combo1.setTypeAhead(true);
	    combo1.setTriggerAction(TriggerAction.ALL);
	    
	    EdcPermissionRolesProperties properties2 = GWT.create(EdcPermissionRolesProperties.class);
	    listStoreEdcPermissionRoles = new ListStore<EdcPermissionRoles>(properties2.key());
	    for(EdcPermissionRoles r : EdcPermissionRoles.values()){
	    	listStoreEdcPermissionRoles.add(r);	
	    }	    
		
		ComboBox<EdcPermissionRoles> combo2 = new ComboBox<EdcPermissionRoles>(listStoreEdcPermissionRoles, properties2.nameLabel());
		addHandlersForCombo2(combo2, properties2.nameLabel());
	    combo2.setEmptyText(messages.selectRecord()+"...");
	    combo2.setTypeAhead(true);
	    combo2.setTriggerAction(TriggerAction.ALL);
	    
		
    	VerticalLayoutData layoutData = new VerticalLayoutData();
		layoutData.setMargins(new Margins(5));
		layoutData.setHeight(-1);
		layoutData.setWidth(1);
		
		container.add(new FieldLabel(combo1, messages.departments()), layoutData);
		container.add(new FieldLabel(combo2, messages.permisions()), layoutData);
		
			    
	    TextButton buttonAdd = new TextButton(messages.save());
	    buttonAdd.setIcon(Images.INSTANCE.save());
	    buttonAdd.setTitle(messages.addRecord());
	    buttonAdd.addSelectHandler(handlerAddDocumentsPerm());
	   	   
	    TextButton buttonClose = new TextButton(messages.close());
		buttonClose.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				hide();
			}
		});
				
	    
	    addButton(buttonAdd);
	    addButton(buttonClose);
	}
	
	
	private void addHandlersForCombo2(ComboBox<EdcPermissionRoles> comboApp,	LabelProvider<EdcPermissionRoles> fullName) {
		comboApp.addSelectionHandler(new SelectionHandler<EdcPermissionRoles>() {
			@Override
			public void onSelection(SelectionEvent<EdcPermissionRoles> event) {
				selectedItemEdcPermissionRoles = event.getSelectedItem();
			}
		});
	}
	
	private void addHandlersForCombo1(ComboBox<DepartmentProxy> comboApp,	LabelProvider<DepartmentProxy> fullName) {
		comboApp.addSelectionHandler(new SelectionHandler<DepartmentProxy>() {
			@Override
			public void onSelection(SelectionEvent<DepartmentProxy> event) {
				selectedItemDepartment = event.getSelectedItem();
			}
		});
	}
	
	/**
	 * Добавление пользователю доступа к подразделениям ЭГД
	 * @return
	 */
	private SelectHandler handlerAddDocumentsPerm() {
		SelectHandler handler = new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				manageService.addDepartmentPermission(usersProxy.getId(), selectedItemDepartment.getId(), selectedItemEdcPermissionRoles, serverName, callbackAddDocumentsPerm());
			}
		};
		return handler;
	}
	
	/**
	 * Обновление инф. на главной форме
	 * @return
	 */
	protected AsyncCallback<Boolean> callbackAddDocumentsPerm() {
		return new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				if(result){
					Mainwidget2 mainwidget2 = Mainwidget2.getInstance();
					mainwidget2.updateIdsUserDepartmentListByUser(usersProxy.getName());
					Info.display(messages.idsInfo(), messages.recordAdded());
					hide();
				}
			}
			@Override
			public void onFailure(Throwable caught) {
				Info.display(messages.error(), messages.error() + caught.getLocalizedMessage());
				hide();
			}
		};
	}
	
	/**
	 * Получение справочника - списка всех подразделениям ЭГД
	 * @return
	 */
	private AsyncCallback<List<DepartmentProxy>> callbackUserDocumentList() {
		return new AsyncCallback<List<DepartmentProxy>>() {
			
			@Override
			public void onSuccess(List<DepartmentProxy> result) {
				listStoreDepartment.clear();
				listStoreDepartment.addAll(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				new MessageBox("callbackApp: error manageService: " 
						+ caught.getLocalizedMessage()).show();
				
			}
		};
	}
	
	
	
	
	
}
