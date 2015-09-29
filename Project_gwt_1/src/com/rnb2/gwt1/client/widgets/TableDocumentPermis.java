package com.rnb2.gwt1.client.widgets;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.rnb2.gwt1.client.Mainwidget2;
import com.rnb2.gwt1.client.ManageService;
import com.rnb2.gwt1.client.ManageServiceAsync;
import com.rnb2.gwt1.client.images.Images;
import com.rnb2.gwt1.client.messages.MyMessages;
import com.rnb2.gwt1.client.model.combo.DocumentPermisProperties;
import com.rnb2.gwt1.client.utils.UtilString;
import com.rnb2.gwt1.client.widgets.window.DialogDelete2;
import com.rnb2.gwt1.client.widgets.window.WindowIdsSelectDocumentPermis;
import com.rnb2.gwt1.data.idsugdt.DocumentPermission;
import com.rnb2.gwt1.data.idsugdt.proxy.UsersProxy;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.tips.QuickTip;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

/**
 * Формы доступн. пользователю
 * @author budukh-rn
 *
 */
public class TableDocumentPermis implements IsWidget {
	
	private final DocumentPermisProperties props = GWT.create(DocumentPermisProperties.class);
	private final MyMessages messages = GWT.create(MyMessages.class);
	private List<DocumentPermission> items;
	private VerticalLayoutContainer container;
	private final ManageServiceAsync manageService = GWT
			.create(ManageService.class);
	private UsersProxy usersProxy;
	protected DialogDelete2 dialogDelete;
	private DocumentPermission selectedItem;
	private String serverName;

	public TableDocumentPermis(UsersProxy usersProxy, List<DocumentPermission> list, String serverName) {
		this.items = list;
		this.usersProxy = usersProxy;
		this.serverName = serverName;
	}

	@Override
	public Widget asWidget() {
		if(container == null){
			container = new VerticalLayoutContainer();
			List<ColumnConfig<DocumentPermission, ?>> configList = getConfigColumnList(props);
			 
			ColumnModel<DocumentPermission> columnModel = new ColumnModel<DocumentPermission>(configList);
			
			ListStore<DocumentPermission> listStore = new ListStore<DocumentPermission>(props.key());
			
			listStore.addAll(items);
		
		
			Grid<DocumentPermission> grid = new Grid<DocumentPermission>(listStore, columnModel);
					
			grid.getView().setAutoExpandColumn(configList.get(0));
			grid.getView().setStripeRows(true);
			grid.getView().setColumnLines(true);
			grid.getView().setAutoFill(true);
			grid.setBorders(false);
			grid.setColumnReordering(true);
			grid.setStateful(true);
			grid.setStateId("gridEntityPermission");
			
			grid.getSelectionModel().addSelectionHandler(selection);
		    
	    
			VerticalLayoutData layoutData = new VerticalLayoutData();
			layoutData.setMargins(new Margins(5));
			layoutData.setHeight(1);
			layoutData.setWidth(1);
			
			ToolBar toolBarPerm = new ToolBar();
		    toolBarPerm.setSpacing(3);
		    
		    TextButton buttonAdd = new TextButton(messages.add());
		    buttonAdd.setIcon(Images.INSTANCE.table_add());
		    buttonAdd.setTitle(messages.addRecord());
		    buttonAdd.addSelectHandler(handlerAddDocumentPermis());
		    
		    TextButton buttonDelete = new TextButton(messages.delete());
		    buttonDelete.setIcon(Images.INSTANCE.table_row_delete());
		    buttonDelete.setTitle(messages.deleteRecord());
		    buttonDelete.addSelectHandler(handlerDelete());
		    
		    toolBarPerm.add(buttonAdd);
		    toolBarPerm.add(buttonDelete);
		    	
		    container.add(toolBarPerm, new VerticalLayoutData(1,-1));
		    container.add(grid, layoutData);
		    
		    
		    new QuickTip(grid);		
		  
			}	
		return container;
	}
	
	/**
	 * Выбор записи в таблице
	 */
	private SelectionHandler<DocumentPermission> selection = new SelectionHandler<DocumentPermission>() {
		@Override
		public void onSelection(SelectionEvent<DocumentPermission> event) {
			selectedItem = event.getSelectedItem();
		}
	};
	
	/**
	 * Удаление доступа к формам ИДС УЖДТ
	 * @return
	 */
	private SelectHandler handlerDelete() {
		SelectHandler handler = new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				
				if(selectedItem == null){
					new MessageBox(messages.error(), messages.selectRecord()).show();
					return;
				}
				
				try{
					StringBuilder sb = new StringBuilder();
					sb.append(selectedItem.getDictionaryString());
					sb.append(" (");
					sb.append(selectedItem.getEntityRolesString());
					sb.append(")");
					dialogDelete = new DialogDelete2(messages.deleteRecord(), UtilString.getMessageForDelete(sb.toString(), messages),
						messages,	
						handlerDeleteDo());
				
				dialogDelete.show();
				}catch(Exception ex){
					new MessageBox("Exc", ex.getLocalizedMessage()).show();
				}
			}
		};
		return handler;
	}
	
	/**
	 * Удаение доступа к формам ИДС УЖДТ
	 * @return
	 */
	private SelectHandler handlerDeleteDo() {
		SelectHandler handler = new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				manageService.deleteDocumentPermission(usersProxy.getId(), selectedItem.getId(), serverName, callbackDeleteDocumentPermiss());
				
			}
		};
		return handler;
	}
	
	/**
	 * Удаление доступа к формам ИДС УЖДТ
	 * @return
	 */
	protected AsyncCallback<Boolean> callbackDeleteDocumentPermiss() {
		return new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				if(result){
					dialogDelete.hide();
					Mainwidget2 mainwidget2 = Mainwidget2.getInstance();
					mainwidget2.updateIdsUserDocumentByUser(usersProxy.getName());
				}	
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Info.display(messages.error(), messages.error() + caught.getLocalizedMessage());
			}
		};
	}
	
	/**
	 * Добавление доступа к формам ИДС УЖДТ
	 * @return
	 */
	private SelectHandler handlerAddDocumentPermis() {
		SelectHandler handler = new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				WindowIdsSelectDocumentPermis documentPermis = new WindowIdsSelectDocumentPermis(manageService, usersProxy, serverName);
				documentPermis.show();
			}
		};
		return handler;
	}
	
	
	
	private<T> List<ColumnConfig<DocumentPermission, ?>> getConfigColumnList(
			DocumentPermisProperties props2) {
		 
		ColumnConfig<DocumentPermission, String> col1 = new ColumnConfig<DocumentPermission, String>(props2.dictionaryString(), 180, messages.form());
		ColumnConfig<DocumentPermission, String> col2 = new ColumnConfig<DocumentPermission, String>(props2.entityRolesString(), 80, messages.permision());

		List<ColumnConfig<DocumentPermission, ?>> configList =  new ArrayList<ColumnConfig<DocumentPermission,?>>();
		configList.add(col1);
		configList.add(col2);
		return configList;
	}

}
