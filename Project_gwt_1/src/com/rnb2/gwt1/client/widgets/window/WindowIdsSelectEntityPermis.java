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
import com.rnb2.gwt1.client.model.combo.EntityDictionaryProperties;
import com.rnb2.gwt1.client.model.combo.EntityRolesProperties;
import com.rnb2.gwt1.data.idsugdt.EntityDictionary;
import com.rnb2.gwt1.data.idsugdt.EntityRoles;
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
 * Выбор доступа к справочникам ИДС УЖДТ
 * @author budukh-rn
 *
 */
public class WindowIdsSelectEntityPermis extends Window {

	private final MyMessages messages = GWT.create(MyMessages.class);
	private VerticalLayoutContainer container;
	private UsersProxy usersProxy;
	private ManageServiceAsync manageService;
	private ListStore<EntityDictionary> listStoreEntityPermis;
	private ListStore<EntityRoles> listStoreEntityRoles;
	private EntityDictionary selectedItemEntityPermis = null;
	private EntityRoles selectedItemEntityRoles = null;
	
	/**
	 * Выбор доступа к справочникам ИДС УЖДТ
	 * @param manageService
	 * @param usersProxy
	 */
	public WindowIdsSelectEntityPermis(ManageServiceAsync manageService,  UsersProxy usersProxy) {
		this.usersProxy = usersProxy;
		this.manageService = manageService;
	
		setModal(true);
		setBlinkModal(true);
		setHeadingText(messages.addRecord());
		setBodyStyle("padding: 5px");
		setWidth(350);
			
		container = new VerticalLayoutContainer();
		add(container);
		
		EntityDictionaryProperties properties = GWT.create(EntityDictionaryProperties.class);
		listStoreEntityPermis = new ListStore<EntityDictionary>(properties.key());
		this.manageService.getEntityDictionaryList(callbackEntityDictionaryList());
		
		ComboBox<EntityDictionary> combo1 = new ComboBox<EntityDictionary>(listStoreEntityPermis, properties.dictionary());
		addHandlersForCombo1(combo1, properties.dictionary());
	    combo1.setEmptyText(messages.selectRecord()+"...");
	    combo1.setTypeAhead(true);
	    combo1.setTriggerAction(TriggerAction.ALL);
	    
	    EntityRolesProperties properties2 = GWT.create(EntityRolesProperties.class);
	    listStoreEntityRoles = new ListStore<EntityRoles>(properties2.key());
	    for(EntityRoles r : EntityRoles.values()){
	    	listStoreEntityRoles.add(r);	
	    }	    
		
		ComboBox<EntityRoles> combo2 = new ComboBox<EntityRoles>(listStoreEntityRoles, properties2.nameLabel());
		addHandlersForCombo2(combo2, properties2.nameLabel());
	    combo2.setEmptyText(messages.selectRecord()+"...");
	    combo2.setTypeAhead(true);
	    combo2.setTriggerAction(TriggerAction.ALL);
	    
		
    	VerticalLayoutData layoutData = new VerticalLayoutData();
		layoutData.setMargins(new Margins(5));
		layoutData.setHeight(-1);
		layoutData.setWidth(1);
		
		container.add(new FieldLabel(combo1, messages.accessSpr()), layoutData);
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
	
	
	private void addHandlersForCombo2(ComboBox<EntityRoles> comboApp,	LabelProvider<EntityRoles> fullName) {
		comboApp.addSelectionHandler(new SelectionHandler<EntityRoles>() {
			@Override
			public void onSelection(SelectionEvent<EntityRoles> event) {
				selectedItemEntityRoles = event.getSelectedItem();
			}
		});
	}
	
	private void addHandlersForCombo1(ComboBox<EntityDictionary> comboApp,	LabelProvider<EntityDictionary> fullName) {
		comboApp.addSelectionHandler(new SelectionHandler<EntityDictionary>() {
			@Override
			public void onSelection(SelectionEvent<EntityDictionary> event) {
				selectedItemEntityPermis = event.getSelectedItem();
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
				Mainwidget2 mainwidget2 = Mainwidget2.getInstance();
				manageService.addEntityPermission(usersProxy.getId(), selectedItemEntityPermis, selectedItemEntityRoles, mainwidget2.getLoginName(), callbackAddEntityPermission());
			}
		};
		return handler;
	}
	
	/**
	 * Обновление инф. на главной форме
	 * @return
	 */
	protected AsyncCallback<Boolean> callbackAddEntityPermission() {
		return new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				if(result){
					Mainwidget2 mainwidget2 = Mainwidget2.getInstance();
					mainwidget2.updateIdsUserEntityByUser(usersProxy.getName());
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
	 * Получение справочника - списка всех справочников
	 * @return
	 */
	private AsyncCallback<List<EntityDictionary>> callbackEntityDictionaryList() {
		return new AsyncCallback<List<EntityDictionary>>() {
			
			@Override
			public void onSuccess(List<EntityDictionary> result) {
				listStoreEntityPermis.clear();
				listStoreEntityPermis.addAll(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				new MessageBox("callbackApp: error manageService: " 
						+ caught.getLocalizedMessage()).show();
				
			}
		};
	}
	
	
	
	
	
}
