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
import com.rnb2.gwt1.client.model.combo.DocumentDictionaryProperties;
import com.rnb2.gwt1.client.model.combo.DocumentRolesProperties;
import com.rnb2.gwt1.data.idsugdt.DocumentDictionary;
import com.rnb2.gwt1.data.idsugdt.DocumentRoles;
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
 * Выбор доступа к выбранной форме
 * @author budukh-rn
 *
 */
public class WindowIdsSelectDocumentPermis extends Window {

	private final MyMessages messages = GWT.create(MyMessages.class);
	private VerticalLayoutContainer container;
	private UsersProxy usersProxy;
	private ManageServiceAsync manageService;
	private ListStore<DocumentDictionary> listStoreDocumentPerm;
	private ListStore<DocumentRoles> listStoreDocumentRoles;
	private DocumentDictionary selectedItemDocumentPerm = null;
	private DocumentRoles selectedItemDocumentRoles = null;
	private String serverName;
	
	public WindowIdsSelectDocumentPermis(ManageServiceAsync manageService,  UsersProxy usersProxy, String serverName) {
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
		
		DocumentDictionaryProperties properties = GWT.create(DocumentDictionaryProperties.class);
		listStoreDocumentPerm = new ListStore<DocumentDictionary>(properties.key());
		this.manageService.getDocumentDictionaryList(serverName, callbackUserDocumentList());
		
		ComboBox<DocumentDictionary> combo1 = new ComboBox<DocumentDictionary>(listStoreDocumentPerm, properties.dictionary());
		addHandlersForCombo1(combo1, properties.dictionary());
	    combo1.setEmptyText(messages.selectRecord()+"...");
	    combo1.setTypeAhead(true);
	    combo1.setTriggerAction(TriggerAction.ALL);
	    
	    DocumentRolesProperties properties2 = GWT.create(DocumentRolesProperties.class);
	    listStoreDocumentRoles = new ListStore<DocumentRoles>(properties2.key());
	    for(DocumentRoles r : DocumentRoles.values()){
	    	listStoreDocumentRoles.add(r);	
	    }	    
		
		ComboBox<DocumentRoles> combo2 = new ComboBox<DocumentRoles>(listStoreDocumentRoles, properties2.nameLabel());
		addHandlersForCombo2(combo2, properties2.nameLabel());
	    combo2.setEmptyText(messages.selectRecord()+"...");
	    combo2.setTypeAhead(true);
	    combo2.setTriggerAction(TriggerAction.ALL);
	    
		
    	VerticalLayoutData layoutData = new VerticalLayoutData();
		layoutData.setMargins(new Margins(5));
		layoutData.setHeight(-1);
		layoutData.setWidth(1);
		
		container.add(new FieldLabel(combo1, messages.accessForm()), layoutData);
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
	
	
	private void addHandlersForCombo2(ComboBox<DocumentRoles> comboApp,	LabelProvider<DocumentRoles> fullName) {
		comboApp.addSelectionHandler(new SelectionHandler<DocumentRoles>() {
			@Override
			public void onSelection(SelectionEvent<DocumentRoles> event) {
				selectedItemDocumentRoles = event.getSelectedItem();
			}
		});
	}
	
	private void addHandlersForCombo1(ComboBox<DocumentDictionary> comboApp,	LabelProvider<DocumentDictionary> fullName) {
		comboApp.addSelectionHandler(new SelectionHandler<DocumentDictionary>() {
			@Override
			public void onSelection(SelectionEvent<DocumentDictionary> event) {
				selectedItemDocumentPerm = event.getSelectedItem();
			}
		});
	}
	
	/**
	 * Добавление пользователю доступа к выбранной форме
	 * @return
	 */
	private SelectHandler handlerAddDocumentsPerm() {
		SelectHandler handler = new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				manageService.addDocumentsPermission(usersProxy.getId(), selectedItemDocumentPerm, selectedItemDocumentRoles, Mainwidget2.getInstance().getLoginName(), serverName, callbackAddDocumentsPerm());
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
					mainwidget2.updateIdsUserDocumentByUser(usersProxy.getName());
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
	 * Получение справочника - списка всех форм
	 * @return
	 */
	private AsyncCallback<List<DocumentDictionary>> callbackUserDocumentList() {
		return new AsyncCallback<List<DocumentDictionary>>() {
			
			@Override
			public void onSuccess(List<DocumentDictionary> result) {
				listStoreDocumentPerm.clear();
				listStoreDocumentPerm.addAll(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				new MessageBox("callbackApp: error manageService: " 
						+ caught.getLocalizedMessage()).show();
				
			}
		};
	}
	
	
	
	
	
}
