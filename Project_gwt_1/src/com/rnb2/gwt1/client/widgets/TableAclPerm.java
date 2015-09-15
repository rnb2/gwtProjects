/**
 * 
 */
package com.rnb2.gwt1.client.widgets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.rnb2.gwt1.client.images.Images;
import com.rnb2.gwt1.client.messages.MyMessages;
import com.rnb2.gwt1.client.model.AclPermProperties;
import com.rnb2.gwt1.data.pm.proxy.AclPermissionProxy;
import com.sencha.gxt.core.client.resources.ThemeStyles;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Status;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.tips.QuickTip;
import com.sencha.gxt.widget.core.client.toolbar.SeparatorToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

/**
 * 
 * Отображение /Добавление/редактирование/удаление Acl
 * @author budukh-rn
 * 15 сент. 2015 г.	
 *
 */
public class TableAclPerm implements IsWidget {

	private ContentPanel root;
	private final AclPermProperties props = GWT.create(AclPermProperties.class);
	private Collection<? extends AclPermissionProxy> items;
	private Status status;
	private AclPermissionProxy selectedElement;
	private MyMessages messages;
	private VerticalLayoutContainer container;
	private Grid<AclPermissionProxy> grid;
	private HorizontalLayoutContainer containerH;
	private TextButton buttonAdd, buttonEdit, buttonDelete;
	private String headingText, serverName;
	private boolean isFromAD = false;
	

	/**
	 * 15.09.2015 Отображение ролей в приложениях (AclPermission) 
	 * @param result
	 * @param messages2
	 * @param serverName
	 */
	public TableAclPerm(List<AclPermissionProxy> result, MyMessages messages2, String serverName) {
		this.items = result;
		this.messages = messages2;
		this.serverName = serverName;
		this.headingText = messages.detailAclInfo();
		
		containerH = new HorizontalLayoutContainer();
		containerH.setId("ContainerIds");
		
		buttonAdd = new TextButton(messages.add());
	    buttonEdit = new TextButton(messages.edit());
	    buttonDelete = new TextButton(messages.delete());
	    
	}

	@Override
	public Widget asWidget() {
		
		if(root == null){
			root = new ContentPanel();
		    root.setHeadingText(this.headingText);
		    root.setBorders(true);
		    root.addStyleName("margin-10");
		    root.getHeader().setIcon(Images.INSTANCE.table());
		    root.setCollapsible(false);
		 
			List<ColumnConfig<AclPermissionProxy, ?>> configList = getConfigColumnList(props, isFromAD);
			 
			ColumnModel<AclPermissionProxy> columnModel = new ColumnModel<AclPermissionProxy>(configList);
			
			ListStore<AclPermissionProxy> listStore = new ListStore<AclPermissionProxy>(new ModelKeyProvider<AclPermissionProxy>() {
			    @Override
			    public String getKey(AclPermissionProxy item) {
			      return item.getPrincipal() + item.getAppId() + item.getAppId();
			    }
			  });
			
			listStore.addAll(items);
			  
			grid = new Grid<AclPermissionProxy>(listStore, columnModel);

			grid.getView().setStripeRows(true);
			grid.getView().setAutoFill(true);
			grid.getView().setColumnLines(true);
			grid.setBorders(false);
			grid.setColumnReordering(true);
			grid.setStateful(true);
			grid.setStateId("gridExample");
			
			grid.getSelectionModel().addSelectionHandler(selection);
			grid.setLoadMask(true);
		    
			container = new VerticalLayoutContainer();
			
		    ToolBar toolBar = new ToolBar();
		    toolBar.addStyleName(ThemeStyles.getStyle().borderTop());
		 
		    status = new Status();
		    status.setText(messages.records() + ": " + items.size());
		    status.setWidth(250);
		    toolBar.add(status);
		    toolBar.add(new SeparatorToolItem());
			toolBar.forceLayout();
		 
			VerticalLayoutData layoutData = new VerticalLayoutData();
			layoutData.setMargins(new Margins(0, 0, 5, 0));
			layoutData.setHeight(1);
			layoutData.setWidth(1);
			 
		    		    
		    ToolBar toolBar2 = new ToolBar();
		    toolBar2.setSpacing(3);
		    
		    buttonAdd.setIcon(Images.INSTANCE.table_add());
		    buttonAdd.addSelectHandler(handlerAdd());
		    
		    buttonEdit.setIcon(Images.INSTANCE.table_edit());
		    buttonEdit.addSelectHandler(handlerEdit());
		    		    
		    buttonDelete.setIcon(Images.INSTANCE.table_row_delete());
		    		    
		    toolBar2.add(buttonAdd);
		    toolBar2.add(buttonEdit);
		    toolBar2.add(buttonDelete);
		    
		    buttonAdd.setEnabled(false);
		    buttonEdit.setEnabled(false);
		    buttonDelete.setEnabled(false);
		    
		    container.add(toolBar2, new VerticalLayoutData(1,-1));
		    container.add(grid, layoutData);
		    container.add(toolBar, new VerticalLayoutData(1,-1));
		    container.add(containerH);
		    
		    root.setWidget(container);
		  		    
		    new QuickTip(grid);		    
		}		
		return root;
	}
	
	/**
	 * Уд пользователя
	 * @return
	 */
	public void handlerDelete(SelectHandler handler) {
		if(buttonDelete != null)
			buttonDelete.addSelectHandler(handler);
	}
	
	/**
	 * Добавление пользователя
	 * @return
	 */
	private SelectHandler handlerAdd() {
		SelectHandler handler = new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				/*WindowUserPmAdd user = null;
				if(selectedElement !=null && selectedElement.isFromAD()){
					user = new WindowUserPmAdd(messages.userEdit(), selectedElement, messages, serverName);
				}else{
					user = new WindowUserPmAdd(messages.userAdd(), messages, serverName);
				}
				user.show();*/
			}
		};
		return handler;
	}
	
	/**
	 * Редактирование пользователя
	 * @return
	 */
	private SelectHandler handlerEdit() {
		SelectHandler handler = new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				//WindowUserPmAdd user = new WindowUserPmAdd(messages.userEdit(), selectedElement, messages, serverName);
				//user.show();
			}
		};
		return handler;
	}
	
		
	private SelectionHandler<AclPermissionProxy> selection = new SelectionHandler<AclPermissionProxy>() {
		@Override
		public void onSelection(SelectionEvent<AclPermissionProxy> event) {
			selectedElement = event.getSelectedItem();
		}
	};
	

	
	private<T> List<ColumnConfig<AclPermissionProxy, ?>> getConfigColumnList(
			AclPermProperties props2, final boolean isFromAD) {
	 
		ColumnConfig<AclPermissionProxy, String> col1 = new ColumnConfig<AclPermissionProxy, String>(props2.principal(), 80, messages.loginName());
		ColumnConfig<AclPermissionProxy, String> col2 = new ColumnConfig<AclPermissionProxy, String>(props2.entityId(), 80, messages.entityType());
		ColumnConfig<AclPermissionProxy, String> col3 = new ColumnConfig<AclPermissionProxy, String>(props2.appId(), 80, messages.appType());
		ColumnConfig<AclPermissionProxy, String> col4 = new ColumnConfig<AclPermissionProxy, String>(props2.accessType(), 80, messages.accessType());
		ColumnConfig<AclPermissionProxy, String> col5 = new ColumnConfig<AclPermissionProxy, String>(props2.property(), 80, messages.property());
		ColumnConfig<AclPermissionProxy, String> col6 = new ColumnConfig<AclPermissionProxy, String>(props2.operationType(), 80, messages.operationType());
		ColumnConfig<AclPermissionProxy, String> col7 = new ColumnConfig<AclPermissionProxy, String>(props2.permissiomValue(), 80, messages.permissiomValue());
		
			
		/*col1.setCell(new AbstractCell<String>(){
			@Override
			public void render(com.google.gwt.cell.client.Cell.Context context,
					String value, SafeHtmlBuilder sb) {
				  final String style = "style='color: " + (isFromAD ? "red" : "green") + "'";
			  sb.appendHtmlConstant("<span " + style + " qtitle='Change' qtip='" + value + "'>" + value + "</span>");
	        
			}
		});*/
		 
		List<ColumnConfig<AclPermissionProxy, ?>> configList =  new ArrayList<ColumnConfig<AclPermissionProxy,?>>();
		configList.add(col1);
		configList.add(col2);
		configList.add(col3);
		configList.add(col4);
		configList.add(col5);
		configList.add(col6);
		configList.add(col7);
		return configList;
	}

		
	public AclPermissionProxy getReturnedvalue(){
		return selectedElement;
	}
	
	
	public void  addSelectionHandler(SelectionHandler<AclPermissionProxy> handler){
		if(grid !=null)
			grid.getSelectionModel().addSelectionHandler(handler);
	}
	

	public VerticalLayoutContainer getContainer() {
		return container;
	}

	public ContentPanel getRoot() {
		return root;
	}

	public HorizontalLayoutContainer getContainerH() {
		return containerH;
	}

	public TextButton getButtonAdd() {
		return buttonAdd;
	}

	
	public TextButton getButtonDelete() {
		return buttonDelete;
	}

	public TextButton getButtonEdit() {
		return buttonEdit;
	}

	public Collection<? extends AclPermissionProxy> getItems() {
		return items;
	}

}
