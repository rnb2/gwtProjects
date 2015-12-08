package com.rnb2.gwt1.client.widgets;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.rnb2.gwt1.client.images.Images;
import com.rnb2.gwt1.client.messages.MyMessages;
import com.rnb2.gwt1.client.model.ApplicationPmProperties;
import com.rnb2.gwt1.client.widgets.window.WindowUserPmAddApp;
import com.rnb2.gwt1.client.widgets.window.WindowUserPmAddAppNew;
import com.rnb2.gwt1.client.widgets.window.WindowUserPmAddRole;
import com.rnb2.gwt1.client.widgets.window.WindowUserPmAddRoleNew;
import com.rnb2.gwt1.data.pm.proxy.ApplicationProxy;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.tips.QuickTip;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;


/**
 * Приложения у пользователя
 * @author budukh-rn
 *
 */
public class TableApplicationPm implements IsWidget {

	public static final String ID = "TableApplicationProxy";
	private ContentPanel root;
	private final ApplicationPmProperties props = GWT.create(ApplicationPmProperties.class);

	private final MyMessages messages;
	private List<ApplicationProxy> items;
	private VerticalLayoutContainer container;
	
	private String login = null;
	private String shortname = null;
	private String serverName, fio;
	
	
	private SelectionHandler<ApplicationProxy> selectionHandler = new SelectionHandler<ApplicationProxy>() {
		
		@Override
		public void onSelection(SelectionEvent<ApplicationProxy> event) {
			shortname = event.getSelectedItem().getShortName();
			//new MessageBox("0 select record", "shortname = " + shortname + " login="+ login).show();
		}
	};


	/**
	 * 
	 * Приложения у пользователя
	 * @param result
	 * @param login
	 * @param messages
	 * @param serverName
	 * @param fio пользователя
	 */
	public TableApplicationPm(List<ApplicationProxy> result, String login,  MyMessages messages,  String serverName, String fio) {
		this.items = result;
		this.messages = messages;
		this.login = login;
		this.serverName  = serverName;
		this.fio = fio;
	}
	
	@Override
	public Widget asWidget() {
		
		if(root == null){
			
			root = new ContentPanel();
			root.setId(ID);
		    root.setHeadingText(messages.application());
		    root.setBorders(true);
		   // root.addStyleName("margin-10");
		    root.setResize(true);
		    root.getHeader().setIcon(Images.INSTANCE.table());
		 
			List<ColumnConfig<ApplicationProxy, ?>> configList = getConfigColumnList(props);
			 
			ColumnModel<ApplicationProxy> columnModel = new ColumnModel<ApplicationProxy>(configList);
			
			ListStore<ApplicationProxy> listStore = new ListStore<ApplicationProxy>(props.key());
			
			listStore.addAll(items);
			
			Grid<ApplicationProxy> grid = new Grid<ApplicationProxy>(listStore, columnModel);

			grid.getView().setAutoExpandColumn(configList.get(0));
			grid.getView().setStripeRows(true);
			grid.getView().setColumnLines(true);
			grid.getView().setAutoFill(true);
			grid.setBorders(false);
			grid.setColumnReordering(true);
			grid.setStateful(true);
			//grid.setStateId("gridApplicationProxy");
					

			grid.getSelectionModel().addSelectionHandler(selectionHandler);

		    
			container = new VerticalLayoutContainer();
			
			ToolBar toolBar = new ToolBar();
			toolBar.setSpacing(3);
			    
		    TextButton buttonRoles = new TextButton(messages.permisions());
		    buttonRoles.setIcon(Images.INSTANCE.script());
		    buttonRoles.addSelectHandler(handlerBtnPermissionAdd());
		    
		    
		    TextButton buttonAppl = new TextButton(messages.application());
		    buttonAppl.setIcon(Images.INSTANCE.script());
		    buttonAppl.addSelectHandler(handlerBtnAppAdd());
		    
	    
		    /*TextButton buttonPermission = new TextButton(messages.permision());
		    buttonPermission.setIcon(Images.INSTANCE.script());
		    buttonPermission.addSelectHandler(handlerBtnPermission());*/
		    		   
		    TextButton buttonAddApp = new TextButton(messages.addApplication());
		    buttonAddApp.setIcon(Images.INSTANCE.table_add());
		    buttonAddApp.addSelectHandler(handlerBtnAppAddNew());
		    
		    TextButton buttonAddPermission = new TextButton(messages.addRole());
		    buttonAddPermission.setIcon(Images.INSTANCE.table_add());
		    buttonAddPermission.addSelectHandler(handlerBtnRoleAddNew());
		    
		    
		    //toolBar.add(buttonAppl);
		    toolBar.add(buttonRoles);
		    //toolBar.add(buttonPermission);
		    toolBar.add(buttonAddApp);
		    toolBar.add(buttonAddPermission);
			    
			
		    
			VerticalLayoutData layoutData = new VerticalLayoutData();
			layoutData.setHeight(1);
			layoutData.setWidth(1);
			
		    VerticalLayoutData layoutData2 = new VerticalLayoutData();
		    layoutData2.setMargins(new Margins(5));

	    
		    container.add(grid, layoutData);
		    container.add(toolBar, new VerticalLayoutData(1,-1));
		   
		    root.setWidget(container);
		    
		    new QuickTip(grid);		    
		}		
		return root;
	}
	
	/**
	 * Добавление новой роли
	 * @return
	 */
	private SelectHandler handlerBtnRoleAddNew() {
		SelectHandler handler = new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				WindowUserPmAddRoleNew widget = new WindowUserPmAddRoleNew(messages, serverName);
				widget.show();
			}
		};
		return handler;
	}
	
	/**
	 * Добавление нового приложения
	 * @return
	 */
	private SelectHandler handlerBtnAppAddNew() {
		SelectHandler handler = new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				WindowUserPmAddAppNew widget = new WindowUserPmAddAppNew(messages, serverName);
				widget.show();
			}
		};
		return handler;
	}
	
	private SelectHandler handlerBtnAppAdd() {
		SelectHandler handler = new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				if(login == null){
					new MessageBox(messages.error(), messages.selectRecord()).show();
					return;
				}
				WindowUserPmAddApp widget = new WindowUserPmAddApp(login, shortname, messages, serverName);
				widget.show();
			}
			
		};
		return handler;
	}

	public void addSelection(SelectionHandler<ApplicationProxy> selection){
		this.selectionHandler = selection;
	}

	private SelectHandler handlerBtnPermissionAdd() {
		SelectHandler handler = new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {

				WindowUserPmAddRole widget = new WindowUserPmAddRole(login, shortname, fio, messages, serverName);
				widget.show();
			}
		};
		return handler;
	}
	
	/*private SelectHandler handlerBtnPermission() {
		SelectHandler handler = new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				if(shortname == null){
					new MessageBox(messages.error(), messages.selectRecord()).show();
					return;
				}
				WindowPermission widget = new WindowPermission(login, shortname, messages, serverName);
				widget.show();
			}
		};
		return handler;
	}*/

	private<T> List<ColumnConfig<ApplicationProxy, ?>> getConfigColumnList(
			ApplicationPmProperties props) {
		 
		ColumnConfig<ApplicationProxy, String> col1 = new ColumnConfig<ApplicationProxy, String>(props.fullName(), 150, messages.name());
		ColumnConfig<ApplicationProxy, String> col2 = new ColumnConfig<ApplicationProxy, String>(props.shortName(), 100, messages.nameTask());
		ColumnConfig<ApplicationProxy, String> col3 = new ColumnConfig<ApplicationProxy, String>(props.description(), 120, messages.description());
		ColumnConfig<ApplicationProxy, String> col4 = new ColumnConfig<ApplicationProxy, String>(props.programmer(), 120, messages.programmer());
		ColumnConfig<ApplicationProxy, String> col5 = new ColumnConfig<ApplicationProxy, String>(props.architect(), 120, messages.architect());
		ColumnConfig<ApplicationProxy, String> col6 = new ColumnConfig<ApplicationProxy, String>(props.idString(), 60, messages.type());

		List<ColumnConfig<ApplicationProxy, ?>> configList =  new ArrayList<ColumnConfig<ApplicationProxy,?>>();
		configList.add(col1);
		configList.add(col2);
		configList.add(col3);
		configList.add(col4);
		configList.add(col5);
		configList.add(col6);
		return configList;
	}

}
