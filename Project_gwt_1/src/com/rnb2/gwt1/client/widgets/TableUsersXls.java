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
import com.rnb2.gwt1.client.model.UserXlsProperties;
import com.rnb2.gwt1.client.widgets.window.WindowUserPmAddCopy;
import com.rnb2.gwt1.client.widgets.window.WindowUserPmSelectServer;
import com.rnb2.gwt1.data.pm.proxy.UserProxy;
import com.sencha.gxt.core.client.resources.ThemeStyles;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
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
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.tips.QuickTip;
import com.sencha.gxt.widget.core.client.toolbar.SeparatorToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;


/**
 * 
 * Отображение Пользователей прочитанных из Excel
 * @author budukh-rn
 * 22 сент. 2015 г.	
 *
 */
public class TableUsersXls implements IsWidget {

	private ContentPanel root;
	private final UserXlsProperties props = GWT.create(UserXlsProperties.class);
	private Collection<? extends UserProxy> items;
	private Status status, statusBusy;
	private UserProxy selectedElement;
	private MyMessages messages;
	private VerticalLayoutContainer container;
	private Grid<UserProxy> grid;
	private HorizontalLayoutContainer containerH;
	private TextButton buttonCopy, buttonCopyAll;
	private String headingText;
	

	/**
	 * Отображение Пользователей прочитанных из Excel
	 * @param result
	 * @param messages2
	 */
	public TableUsersXls(List<UserProxy> result, MyMessages messages2) {
		this.items = result;
		this.messages = messages2;
		this.headingText = messages.titleUsersXls();
		
		containerH = new HorizontalLayoutContainer();
		containerH.setId("ContainerIds");
		
		buttonCopy = new TextButton(messages2.copy());
	    buttonCopyAll = new TextButton(messages2.copyAll());
	    
	    buttonCopy.setToolTip(messages2.copyUser());
	    buttonCopyAll.setToolTip(messages2.copyAllUsers());
	}

	@Override
	public Widget asWidget() {
		
		if(root == null){
			root = new ContentPanel();
		    root.setHeadingText(this.headingText);
		    root.setBorders(true);
		    root.addStyleName("margin-10");
		    root.getHeader().setIcon(Images.INSTANCE.table());
		    root.setCollapsible(true);
		 
			List<ColumnConfig<UserProxy, ?>> configList = getConfigColumnList(props);
			 
			ColumnModel<UserProxy> columnModel = new ColumnModel<UserProxy>(configList);
			//statusBusy = new Status();
			
			ListStore<UserProxy> listStore = new ListStore<UserProxy>(props.key());
			listStore.addAll(items);
			  
			grid = new Grid<UserProxy>(listStore, columnModel);
			grid.getView().setStripeRows(true);
			grid.getView().setAutoFill(true);
			grid.getView().setColumnLines(true);
			grid.setBorders(false);
			grid.setColumnReordering(false);
			grid.setStateful(true);
			grid.setStateId("gridExample");
			grid.setLoadMask(true);
			grid.getSelectionModel().addSelectionHandler(selection);
			grid.getView().setEmptyText(messages.noDataFound());
		    
			container = new VerticalLayoutContainer();
			
		    ToolBar toolBar = new ToolBar();
		    toolBar.addStyleName(ThemeStyles.getStyle().borderTop());
		 
		    status = new Status();
		    status.setText(messages.records() + ": " + items.size());
		    status.setWidth(250);
		    
		    //statusBusy.setText("");

		    toolBar.add(status);
		    toolBar.add(new SeparatorToolItem());
		    //toolBar.add(statusBusy);
			toolBar.forceLayout();
		 
			VerticalLayoutData layoutData = new VerticalLayoutData();
			layoutData.setMargins(new Margins(0, 0, 5, 0));
			layoutData.setHeight(1);
			layoutData.setWidth(1);
			 
		    		    
		    ToolBar toolBar2 = new ToolBar();
		    toolBar2.setSpacing(3);
		    
		    buttonCopy.setIcon(Images.INSTANCE.user());
		    buttonCopy.addSelectHandler(handlerCopy());
		   
		    buttonCopyAll.setIcon(Images.INSTANCE.user_group());
		    buttonCopyAll.addSelectHandler(handlerCopyAll());
		    		    		    		    
		    toolBar2.add(buttonCopy);
		    toolBar2.add(buttonCopyAll);
		    
		    buttonCopy.setEnabled(false);
		    buttonCopyAll.setEnabled(true);
		 		    
		    container.add(toolBar2, new VerticalLayoutData(1,-1));
		    container.add(grid, layoutData);
		    container.add(toolBar, new VerticalLayoutData(1,-1));
		    container.add(containerH);
		    
		    root.setWidget(container);
		  		    
		    new QuickTip(grid);		    
		}		
		return root;
	}
	
	//public void setStatusBusy(){
		//statusBusy.setBusy(messages.loadData());
	//}
	
	/**
	 * Копирование пользователя
	 * @return
	 */
	private SelectHandler handlerCopy() {
		SelectHandler handler = new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				if(selectedElement == null){
					Info.display(messages.error(), messages.selectRecord());
					return;
				}
				
				WindowUserPmAddCopy user = new WindowUserPmAddCopy(messages.copyUser(), selectedElement, messages, null);
				user.show();
			}
		};
		return handler;
	}
	
	/**
	 * Копирование пользователей
	 * @return
	 */
	private SelectHandler handlerCopyAll() {
		SelectHandler handler = new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				WindowUserPmSelectServer user = new WindowUserPmSelectServer(messages.copyAllUsers2(), messages, new ArrayList<UserProxy>(items));
				user.show();
				
			}
		};
		return handler;
	}
	
		
	private SelectionHandler<UserProxy> selection = new SelectionHandler<UserProxy>() {
		@Override
		public void onSelection(SelectionEvent<UserProxy> event) {
			selectedElement = event.getSelectedItem();
			buttonCopy.setEnabled(true);
		}
	};
	
	
	private<T> List<ColumnConfig<UserProxy, ?>> getConfigColumnList(
			UserXlsProperties props2) {
	
		ColumnConfig<UserProxy, String> col1 = new ColumnConfig<UserProxy, String>(props2.loginName(), 80, messages.loginNameOld());
		ColumnConfig<UserProxy, String> col2 = new ColumnConfig<UserProxy, String>(props2.fullName(), 80, messages.loginNameNew());
		 
		List<ColumnConfig<UserProxy, ?>> configList =  new ArrayList<ColumnConfig<UserProxy,?>>();
		configList.add(col1);
		configList.add(col2);
		
		return configList;
	}

		
	public UserProxy getReturnedvalue(){
		return selectedElement;
	}
	
	
	public void  addSelectionHandler(SelectionHandler<UserProxy> handler){
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
		return buttonCopy;
	}

	public TextButton getButtonEdit() {
		return buttonCopyAll;
	}

	public Collection<? extends UserProxy> getItems() {
		return items;
	}

}
