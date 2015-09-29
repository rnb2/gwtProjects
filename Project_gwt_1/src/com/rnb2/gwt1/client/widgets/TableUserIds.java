package com.rnb2.gwt1.client.widgets;

import java.util.ArrayList;
import java.util.Date;
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
import com.rnb2.gwt1.client.model.UserIdsProperties;
import com.rnb2.gwt1.client.utils.UtilString;
import com.rnb2.gwt1.client.widgets.window.DialogDelete2;
import com.rnb2.gwt1.client.widgets.window.WindowIdsAddUser;
import com.rnb2.gwt1.data.idsugdt.proxy.UsersProxy;
import com.rnb2.gwt1.data.pm.proxy.UserProxy;
import com.sencha.gxt.core.client.resources.ThemeStyles;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Status;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.tips.QuickTip;
import com.sencha.gxt.widget.core.client.toolbar.FillToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

/**
 * Отображение пользователей ИДС УЖДТ
 * @author budukh-rn
 *
 */
public class TableUserIds implements IsWidget {

	private ContentPanel root;
	private final UserIdsProperties props = GWT.create(UserIdsProperties.class);
	private Status status;
	private final MyMessages messages = GWT.create(MyMessages.class);
	private List<UsersProxy> items;
	private UserProxy pmUserProxy;
	private final ManageServiceAsync manageService = GWT
			.create(ManageService.class);
	
	DialogDelete2 dialogDelete;
	private String serverName;

	/**
	 * Отображение пользователей ИДС УЖДТ
	 * @author budukh-rn
	 *
	 */
	public TableUserIds(List<UsersProxy> result, UserProxy pmUserProxy2, String serverName) {
		items = result;
		status = new Status();
		pmUserProxy = pmUserProxy2;
		this.serverName = serverName;
	}
	
	@Override
	public Widget asWidget() {
		
		if(root == null){
			
			root = new ContentPanel();
			root.setId("TableUserIds");
		    root.setHeadingText(messages.userIds());
		    root.setBorders(true);
		    root.addStyleName("margin-10");
		    root.setResize(true);
		    root.getHeader().setIcon(Images.INSTANCE.table());
		 
			List<ColumnConfig<UsersProxy, ?>> configList = getConfigColumnList(props);
			 
			ColumnModel<UsersProxy> columnModel = new ColumnModel<UsersProxy>(configList);
			
			ListStore<UsersProxy> listStore = new ListStore<UsersProxy>(props.key());
			
			listStore.addAll(items);
			
			grid = new Grid<UsersProxy>(listStore, columnModel);

			grid.getView().setAutoExpandColumn(configList.get(0));
			grid.getView().setStripeRows(true);
			grid.getView().setColumnLines(true);
			grid.setBorders(false);
			grid.setColumnReordering(true);
			grid.setStateful(true);
			grid.setStateId("gridExample");

			grid.getSelectionModel().addSelectionHandler(selection);
			
			
		    Menu contextMenu = new Menu();
		    
		    MenuItem insert = new MenuItem();
		    insert.setText(messages.addRecord());
		    insert.setIcon(Images.INSTANCE.add());
		    insert.addSelectionHandler(new SelectionHandler<Item>() {
		 
		      @Override
		      public void onSelection(SelectionEvent<Item> event) {
		        
		    	  WindowIdsAddUser addUser = new WindowIdsAddUser(messages.userAdd(), pmUserProxy, messages, serverName);
		    	  addUser.show();
		      }
		    });
		 
		    boolean enabled = (pmUserProxy != null && items.isEmpty());
			insert.setEnabled(enabled);
		    contextMenu.add(insert);
		 
		    MenuItem remove = new MenuItem();
		    remove.setText(messages.deleteRecord());
		    remove.setIcon(Images.INSTANCE.remove());
		    remove.addSelectionHandler(new SelectionHandler<Item>() {
		 
		      @Override
		      public void onSelection(SelectionEvent<Item> event) {
		       UsersProxy sel = grid.getSelectionModel().getSelectedItem();
		       	if (sel != null) {
		       		
		       		try{
						dialogDelete = new DialogDelete2(messages.deleteRecord(), UtilString.getMessageForDelete(sel.getFio(), messages),
							messages,	
							handlerDeleteUserDo(sel));
					
					dialogDelete.show();
					}catch(Exception ex){
						dialogDelete.hide();
						new MessageBox("Exc", ex.getLocalizedMessage()).show();
					}
		       		
		       	}
		      }
		 
		    });
		    remove.setEnabled(!items.isEmpty());
		    contextMenu.add(remove);
			grid.setContextMenu(contextMenu);
		    
			container = new VerticalLayoutContainer();
		    
		    ToolBar toolBar = new ToolBar();
		    toolBar.addStyleName(ThemeStyles.getStyle().borderTop());
		    
		    status.setText(messages.count() + ": " + items.size());
		    status.setWidth(250);
		    toolBar.add(status);
		    
		    toolBar.add(new FillToolItem());
		    
			VerticalLayoutData layoutData = new VerticalLayoutData();
			layoutData.setMargins(new Margins(0, 0, 5, 0));
			layoutData.setHeight(1);
			layoutData.setWidth(1);
			
		    VerticalLayoutData layoutData2 = new VerticalLayoutData();
		    layoutData2.setMargins(new Margins(5));


		    container.add(toolBar, new VerticalLayoutData(1,-1));
		    container.add(grid, layoutData);
		    
		    
		   /* ToolBar toolBar2 = new ToolBar();
		    toolBar2.addStyleName(ThemeStyles.getStyle().borderBottom());
		 
		    Status status2 = new Status();
		    status2.setText("Footer");
		    status2.setWidth(150);
		    toolBar2.add(status2);
		    
		    container.add(toolBar2, new VerticalLayoutData(1,-1));*/
		   
		    root.setWidget(container);

		    
		    new QuickTip(grid);		    
		}		
		return root;
	}
	
	protected SelectHandler handlerDeleteUserDo(final UsersProxy sel) {
		SelectHandler handler = new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				manageService.deleteUserIds(sel.getId(), serverName, deleteUserIdsCallback());
			}
		};
		return handler;
	}

	protected AsyncCallback<Boolean> deleteUserIdsCallback() {
		return new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				if(result){
					dialogDelete.hide();
					Mainwidget2.getInstance().updateIdsUser(pmUserProxy.getLoginName());
				}	
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Info.display(messages.error(), messages.error() + caught.getLocalizedMessage());
			}
		};
	}

	private SelectionHandler<UsersProxy> selection;
	
	public void addSelection(SelectionHandler<UsersProxy> selection){
		this.selection = selection;
	}
	
	public void changeStatus(String string){
		status.setText(string);
	}
	
	private VerticalLayoutContainer container;
	private Grid<UsersProxy> grid;
	
	private<T> List<ColumnConfig<UsersProxy, ?>> getConfigColumnList(
			UserIdsProperties props2) {
		 
		ColumnConfig<UsersProxy, String> col1 = new ColumnConfig<UsersProxy, String>(props2.fio(), 150, messages.fullName());
		ColumnConfig<UsersProxy, String> col2 = new ColumnConfig<UsersProxy, String>(props2.name(), 100, messages.loginName());
		ColumnConfig<UsersProxy, Date> col3 = new ColumnConfig<UsersProxy, Date>(props2.dateInput(), 100, messages.dateInput());
		ColumnConfig<UsersProxy, String> col4 = new ColumnConfig<UsersProxy, String>(props2.userInput(), 100, messages.loginNameAdded());

		List<ColumnConfig<UsersProxy, ?>> configList =  new ArrayList<ColumnConfig<UsersProxy,?>>();
		configList.add(col1);
		configList.add(col2);
		configList.add(col3);
		configList.add(col4);
		return configList;
	}

}
