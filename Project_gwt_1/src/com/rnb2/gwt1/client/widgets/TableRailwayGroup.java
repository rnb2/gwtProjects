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
import com.rnb2.gwt1.client.model.RailwayGroupProperties;
import com.rnb2.gwt1.client.utils.Constants;
import com.rnb2.gwt1.client.utils.UtilString;
import com.rnb2.gwt1.client.widgets.window.DialogDelete2;
import com.rnb2.gwt1.data.idsugdt.RailwayGroup;
import com.rnb2.gwt1.data.idsugdt.proxy.RailwayGroupProxy;
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
 * Станции у пользователя
 * @author budukh-rn
 *
 */
public class TableRailwayGroup implements IsWidget {

	private final RailwayGroupProperties props = GWT.create(RailwayGroupProperties.class);
	private final MyMessages messages = GWT.create(MyMessages.class);
	private final ManageServiceAsync manageService = GWT
			.create(ManageService.class);
	private List<RailwayGroupProxy> items;

	private VerticalLayoutContainer container;
	private UsersProxy usersProxy;
	protected RailwayGroupProxy selectedItem = null;
	private DialogDelete2 dialogDelete;
	private String serverName;
	
	public TableRailwayGroup(UsersProxy usersProxy, List<RailwayGroupProxy> list, String serverName) {
		this.items = list;
		this.usersProxy = usersProxy;
		this.serverName = serverName;
	}

	@Override
	public Widget asWidget() {
		if(container == null){
			
			container = new VerticalLayoutContainer();
			List<ColumnConfig<RailwayGroupProxy, ?>> configList = getConfigColumnList(props);
			 
			ColumnModel<RailwayGroupProxy> columnModel = new ColumnModel<RailwayGroupProxy>(configList);
			
			ListStore<RailwayGroupProxy> listStore = new ListStore<RailwayGroupProxy>(props.key());
			
			listStore.addAll(items);
		
		
			Grid<RailwayGroupProxy> grid = new Grid<RailwayGroupProxy>(listStore, columnModel);
	
			grid.getView().setAutoExpandColumn(configList.get(0));
			grid.getView().setStripeRows(true);
			grid.getView().setColumnLines(true);
			grid.getView().setAutoFill(true);
			grid.setBorders(false);
			grid.setColumnReordering(true);
			grid.setStateful(true);
			grid.setStateId(Constants.GRID_RAILWAY_GROUP_PROXY);
			
			grid.getSelectionModel().addSelectionHandler(selection);
	
			//GridStateHandler<RailwayGroupProxy> state = new GridStateHandler<RailwayGroupProxy>(grid);
			//state.loadState();
		    
	    
			VerticalLayoutData layoutData = new VerticalLayoutData();
			layoutData.setMargins(new Margins(5));
			layoutData.setHeight(1);
			layoutData.setWidth(1);
		    
			
			ToolBar toolBar = new ToolBar();
		    toolBar.setSpacing(3);
		    
		    TextButton buttonAdd = new TextButton(messages.add());
		    buttonAdd.setIcon(Images.INSTANCE.table_add());
		    buttonAdd.setTitle(messages.addRecord());
		    buttonAdd.addSelectHandler(handlerAddStation());
		    
		    TextButton buttonDelete = new TextButton(messages.delete());
		    buttonDelete.setIcon(Images.INSTANCE.table_row_delete());
		    buttonDelete.setTitle(messages.deleteRecord());
		    buttonDelete.addSelectHandler(handlerDeleteStation());

		    
		    toolBar.add(buttonAdd);
		    toolBar.add(buttonDelete);
		    
		    container.add(toolBar, new VerticalLayoutData(1,-1));
		    container.add(grid, layoutData);
			
		    new QuickTip(grid);		
		  
			}	
		return container;
	}
	
	/**
	 * Удаение станции
	 * @return
	 */
	private SelectHandler handlerDeleteStation() {
		SelectHandler handler = new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				
				if(selectedItem == null){
					new MessageBox(messages.error(), messages.selectRecord()).show();
					return;
				}
				
				try{
					dialogDelete = new DialogDelete2(messages.deleteRecord(), UtilString.getMessageForDelete(selectedItem.getFullNameSt(), messages),
						messages,	
						handlerDeleteStationDo());
				
				dialogDelete.show();
				}catch(Exception ex){
					new MessageBox("Exc", ex.getLocalizedMessage()).show();
				}
			}
		};
		return handler;
	}
	
	/**
	 * Удаение станции
	 * @return
	 */
	private SelectHandler handlerDeleteStationDo() {
		SelectHandler handler = new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				manageService.deleteStationIds(usersProxy.getId(), selectedItem.getId(), serverName, callbackDeleteStation());
				
			}
		};
		return handler;
	}
	
	/**
	 * Удаление станции
	 * @return
	 */
	protected AsyncCallback<Boolean> callbackDeleteStation() {
		return new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				if(result){
					dialogDelete.hide();
					Mainwidget2 mainwidget2 = Mainwidget2.getInstance();
					mainwidget2.updateIdsStationByUser(usersProxy.getName());
					Info.display(messages.idsInfo(), messages.recordDeleted());
				}	
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Info.display(messages.error(), messages.error() + caught.getLocalizedMessage());
			}
		};
	}

	/**
	 * Выбор станции
	 */
	private SelectionHandler<RailwayGroupProxy> selection = new SelectionHandler<RailwayGroupProxy>() {
		@Override
		public void onSelection(SelectionEvent<RailwayGroupProxy> event) {
			selectedItem = event.getSelectedItem();
		}
	};
	
	/**
	 * Добавление станции
	 * @return
	 */
	private SelectHandler handlerAddStation() {
		SelectHandler handler = new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				if(items == null){
					items = new ArrayList<RailwayGroupProxy>();
				}
				List<Long> list = new ArrayList<Long>();
				for(RailwayGroupProxy pr :items){
					list.add(pr.getId());
				}
				manageService.getStationList(com.rnb2.gwt1.server.utils.Constants.SYS_CODE_STATION, list, serverName, callbackStationList());
				
			}
		};
		return handler;
	}
	
	
	protected AsyncCallback<List<RailwayGroup>> callbackStationList() {
		return new AsyncCallback<List<RailwayGroup>>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onSuccess(List<RailwayGroup> result) {
				
				if(result.isEmpty()){
					Info.display(messages.info(), messages.allStationAdded());
					return;
				}
				WindowSelectRailwayGroup pmSearch = new WindowSelectRailwayGroup(manageService, usersProxy, result, serverName);
				pmSearch.show();
			}
		};
	}

	private<T> List<ColumnConfig<RailwayGroupProxy, ?>> getConfigColumnList(
			RailwayGroupProperties props2) {
		 
		ColumnConfig<RailwayGroupProxy, String> col1 = new ColumnConfig<RailwayGroupProxy, String>(props2.fullNameType(), 80, messages.type());
		ColumnConfig<RailwayGroupProxy, String> col2 = new ColumnConfig<RailwayGroupProxy, String>(props2.fullNameSt(), 180, messages.station());

		List<ColumnConfig<RailwayGroupProxy, ?>> configList =  new ArrayList<ColumnConfig<RailwayGroupProxy,?>>();
		configList.add(col1);
		configList.add(col2);
		return configList;
	}
	
	public int getListSize(){
		if(items != null)
			return items.size();
		
		return 0;
	}

}
