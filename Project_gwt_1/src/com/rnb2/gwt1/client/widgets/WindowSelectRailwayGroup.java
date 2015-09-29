package com.rnb2.gwt1.client.widgets;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rnb2.gwt1.client.Mainwidget2;
import com.rnb2.gwt1.client.ManageServiceAsync;
import com.rnb2.gwt1.client.images.Images;
import com.rnb2.gwt1.client.messages.MyMessages;
import com.rnb2.gwt1.client.model.RailwayGroupProperties2;
import com.rnb2.gwt1.client.utils.Constants;
import com.rnb2.gwt1.data.idsugdt.RailwayGroup;
import com.rnb2.gwt1.data.idsugdt.proxy.UsersProxy;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.Window;
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

/**
 * 15.11.2013
 * Выбор станции ИДС УЖДТ
 * @author budukh-rn
 *
 */
public class WindowSelectRailwayGroup extends Window {

	private final RailwayGroupProperties2 props = GWT.create(RailwayGroupProperties2.class);
	private final MyMessages messages = GWT.create(MyMessages.class);

	private List<RailwayGroup> items;
	private RailwayGroup selectedItem;
	private VerticalLayoutContainer container;
	private UsersProxy usersProxy;
	private ManageServiceAsync manageService;
	private String serverName;
	
	
	public WindowSelectRailwayGroup(ManageServiceAsync manageService,  UsersProxy usersProxy, List<RailwayGroup> list, String serverName) {
		this.items = list;
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
		List<ColumnConfig<RailwayGroup, ?>> configList = getConfigColumnList(props);
		 
		ColumnModel<RailwayGroup> columnModel = new ColumnModel<RailwayGroup>(configList);
		
		ListStore<RailwayGroup> listStore = new ListStore<RailwayGroup>(props.key());
		
		listStore.addAll(items);
	
	
		Grid<RailwayGroup> grid = new Grid<RailwayGroup>(listStore, columnModel);

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
		layoutData.setHeight(350);
		layoutData.setWidth(1);
	    
			    
	    TextButton buttonAdd = new TextButton(messages.save());
	    buttonAdd.setIcon(Images.INSTANCE.save());
	    buttonAdd.setTitle(messages.addRecord());
	    buttonAdd.addSelectHandler(handlerAddStation());
	    buttonAdd.setEnabled(!items.isEmpty());
	   	   
	    TextButton buttonClose = new TextButton(messages.close());
		buttonClose.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				hide();
			}
		});
				
	    container.add(grid, layoutData);
	    addButton(buttonAdd);
	    addButton(buttonClose);
	   
		
	    new QuickTip(grid);		
					
	}
	
	/**
	 * Выбор станции
	 */
	private SelectionHandler<RailwayGroup> selection = new SelectionHandler<RailwayGroup>() {
		@Override
		public void onSelection(SelectionEvent<RailwayGroup> event) {
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
				manageService.addStationUserIds(usersProxy.getId(), selectedItem, serverName, callbackAddStationUserIds());
			}
		};
		return handler;
	}
	
	
	protected AsyncCallback<Boolean> callbackAddStationUserIds() {
		return new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				if(result){
					Mainwidget2 mainwidget2 = Mainwidget2.getInstance();
					mainwidget2.updateIdsStationByUser(usersProxy.getName());
					Info.display(messages.idsInfo(), messages.infoAddStation());
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


	private<T> List<ColumnConfig<RailwayGroup, ?>> getConfigColumnList(
			RailwayGroupProperties2 props2) {

		ColumnConfig<RailwayGroup, String> col1 = new ColumnConfig<RailwayGroup, String>(props2.fullName(), 180, messages.station());

		List<ColumnConfig<RailwayGroup, ?>> configList =  new ArrayList<ColumnConfig<RailwayGroup,?>>();
		configList.add(col1);
		
		return configList;
	}
	
	public int getListSize(){
		if(items != null)
			return items.size();
		
		return 0;
	}


	public RailwayGroup getSelectedItem() {
		return selectedItem;
	}

}
