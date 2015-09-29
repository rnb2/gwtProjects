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
import com.rnb2.gwt1.client.model.UsersDepartmentProperties;
import com.rnb2.gwt1.client.utils.UtilString;
import com.rnb2.gwt1.client.widgets.window.DialogDelete2;
import com.rnb2.gwt1.client.widgets.window.WindowIdsSelectDepartmentPermis;
import com.rnb2.gwt1.data.idsugdt.proxy.UsersDepartmentProxy;
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
 * Справочники подразделений ЭГД доступн. пользователю
 * @author budukh-rn
 *
 */
public class TableUsersDepartment implements IsWidget {
	
	private final UsersDepartmentProperties props = GWT.create(UsersDepartmentProperties.class);
	private final MyMessages messages = GWT.create(MyMessages.class);
	private List<UsersDepartmentProxy> items;
	private VerticalLayoutContainer container;
	
	private final ManageServiceAsync manageService = GWT
			.create(ManageService.class);
	private UsersProxy usersProxy;
	protected DialogDelete2 dialogDelete;
	private UsersDepartmentProxy selectedItem;
	private String serverName;

	/**
	 * Справочники подразделений ЭГД доступн. пользователю
	 * @param usersProxy
	 * @param list
	 */
	public TableUsersDepartment(UsersProxy usersProxy, List<UsersDepartmentProxy> list, String serverName) {
		this.items = list;
		this.usersProxy = usersProxy;
		this.serverName = serverName;
	}

	@Override
	public Widget asWidget() {
		if(container == null){
			container = new VerticalLayoutContainer();
			List<ColumnConfig<UsersDepartmentProxy, ?>> configList = getConfigColumnList(props);
			 
			ColumnModel<UsersDepartmentProxy> columnModel = new ColumnModel<UsersDepartmentProxy>(configList);
			
			ListStore<UsersDepartmentProxy> listStore = new ListStore<UsersDepartmentProxy>(props.key());
			
			listStore.addAll(items);
		
		
			Grid<UsersDepartmentProxy> grid = new Grid<UsersDepartmentProxy>(listStore, columnModel);
	
			grid.getView().setAutoExpandColumn(configList.get(0));
			grid.getView().setStripeRows(true);
			grid.getView().setColumnLines(true);
			grid.getView().setAutoFill(true);
			grid.setBorders(false);
			grid.setColumnReordering(true);
			grid.setStateful(true);
			grid.setStateId("gridUsersDepartmentProxy");
			
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
		    buttonAdd.addSelectHandler(handlerAddDepartmentPermis());
		    
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
	private SelectionHandler<UsersDepartmentProxy> selection = new SelectionHandler<UsersDepartmentProxy>() {
		@Override
		public void onSelection(SelectionEvent<UsersDepartmentProxy> event) {
			selectedItem = event.getSelectedItem();
		}
	};
	
	/**
	 * Удаление доступа к подразделениям ЭГД в ИДС УЖДТ
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
					sb.append(selectedItem.getName());
					sb.append(" (");
					sb.append(selectedItem.getRoles());
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
	 * Удаение доступа к подразделениям ЭГД в ИДС УЖДТ
	 * @return
	 */
	private SelectHandler handlerDeleteDo() {
		SelectHandler handler = new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				manageService.deleteDepartmentPermission(usersProxy.getId(), selectedItem.getId(), serverName, callbackDeleteDepartmentPermiss());				
			}
		};
		return handler;
	}
	
	/**
	 * Удаление доступа к подразделениям ЭГД в ИДС УЖДТ
	 * @return
	 */
	protected AsyncCallback<Boolean> callbackDeleteDepartmentPermiss() {
		return new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				if(result){
					dialogDelete.hide();
					Mainwidget2 mainwidget2 = Mainwidget2.getInstance();
					mainwidget2.updateIdsUserDepartmentListByUser(usersProxy.getName());
				}	
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Info.display(messages.error(), messages.error() + caught.getLocalizedMessage());
			}
		};
	}
	
	/**
	 * Добавление доступа к подразделениям ЭГД в ИДС УЖДТ
	 * @return
	 */
	private SelectHandler handlerAddDepartmentPermis() {
		SelectHandler handler = new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				WindowIdsSelectDepartmentPermis documentPermis = new WindowIdsSelectDepartmentPermis(manageService, usersProxy, serverName);
				documentPermis.show();
			}
		};
		return handler;
	}
	
	private<T> List<ColumnConfig<UsersDepartmentProxy, ?>> getConfigColumnList(
			UsersDepartmentProperties props2) {
		 
		ColumnConfig<UsersDepartmentProxy, String> col1 = new ColumnConfig<UsersDepartmentProxy, String>(props2.name(), 180, messages.departments());
		ColumnConfig<UsersDepartmentProxy, String> col2 = new ColumnConfig<UsersDepartmentProxy, String>(props2.roles(), 80, messages.permision());

		List<ColumnConfig<UsersDepartmentProxy, ?>> configList =  new ArrayList<ColumnConfig<UsersDepartmentProxy,?>>();
		configList.add(col1);
		configList.add(col2);
		return configList;
	}

}
