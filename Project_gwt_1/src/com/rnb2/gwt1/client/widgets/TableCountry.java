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
import com.rnb2.gwt1.client.model.CountryProperties;
import com.rnb2.gwt1.data.idsugdt.Country;
import com.sencha.gxt.core.client.resources.ThemeStyles;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Resizable;
import com.sencha.gxt.widget.core.client.Resizable.Dir;
import com.sencha.gxt.widget.core.client.Status;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.tips.QuickTip;
import com.sencha.gxt.widget.core.client.toolbar.FillToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

/**
 * @author budukh-rn
 *
 */
public class TableCountry implements IsWidget {

	private ContentPanel root;
	private String headingText = "";
	private final CountryProperties props = GWT.create(CountryProperties.class);
	private Collection<? extends Country> items;
	//private ManageServiceAsync manageService;
	//private String codeEnum;
	private Status status;

	
	/**
	 * @wbp.parser.entryPoint
	 */
	public TableCountry() {
	}
	
	/*public TableCountry(String headingText) {
		this.headingText = headingText;
	}
		
	public TableCountry(ManageServiceAsync manageService, String codeEnum) {
		this.manageService = manageService;
		this.codeEnum = codeEnum;
	}
	
	public TableCountry(List<Country> result) {
		items = result;
	}*/

	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public Widget asWidget() {
		
		//initItemList(manageService, codeEnum);
		
		
		if(root == null){
			
			root = new ContentPanel();
		    root.setHeadingText(headingText);
		    root.setHeaderVisible(false);
		    root.setBorders(true);
		    //root.getHeader().setIcon();
		   // root.setPixelSize(600, 450);
		    root.setHeight(450);
		    root.addStyleName("margin-10");
			
		 
			List<ColumnConfig<Country, ?>> configList = getConfigColumnList(props);
			 
			ColumnModel<Country> columnModel = new ColumnModel<Country>(configList);
			
			ListStore<Country> listStore = new ListStore<Country>(props.key());
			
			listStore.addAll(items);
			
			new Resizable(root, Dir.E, Dir.SE, Dir.S);
			  
			final Grid<Country> grid = new Grid<Country>(listStore, columnModel);

			//grid.getView().setAutoExpandColumn(configList.get(0));
			grid.getView().setStripeRows(true);
			grid.getView().setColumnLines(true);
			grid.setBorders(false);
			grid.setColumnReordering(true);
			grid.setStateful(true);
			grid.setStateId("gridExample");
			
			
			grid.getSelectionModel().addSelectionHandler(selection);

			//GridStateHandler<Country> state = new GridStateHandler<Country>(grid);
			//state.loadState();
		    
			VerticalLayoutContainer con = new VerticalLayoutContainer();
		    con.add(grid, new VerticalLayoutData(1, 1));
		    
		    
		    ToolBar toolBar = new ToolBar();
		    toolBar.addStyleName(ThemeStyles.getStyle().borderTop());
		 
		    status = new Status();
		    status.setText("Count: " + items.size());
		    status.setWidth(150);
		    toolBar.add(status);
		    toolBar.add(new FillToolItem());
		    
		    con.add(toolBar, new VerticalLayoutData(1, -1));
		    
		   //root.add(grid);
		    root.setWidget(con);
		    
		   
		    
		    new QuickTip(grid);		    
		}		
		return root;
	}

	private SelectionHandler<Country> selection = new SelectionHandler<Country>() {
		
		@Override
		public void onSelection(SelectionEvent<Country> event) {
				Country country = event.getSelectedItem();
				new MessageBox("selected country:", "selected country: "
						+ country.getFullName() + " ("+ country.getId()+")").show();		
		}
	};
	
	private List<ColumnConfig<Country, ?>> getConfigColumnList(
			CountryProperties props2) {
		
		ColumnConfig<Country, String> col1 = new ColumnConfig<Country, String>(props2.fullName(), 100, "FullName");
		ColumnConfig<Country, String> col2 = new ColumnConfig<Country, String>(props2.shortName(), 80, "ShortName");
		ColumnConfig<Country, String> col3 = new ColumnConfig<Country, String>(props2.codeNUM(), 80, "CodeNum");
		ColumnConfig<Country, String> col4 = new ColumnConfig<Country, String>(props2.codeISOA2(), 80, "ISOA2");
		ColumnConfig<Country, String> col5 = new ColumnConfig<Country, String>(props2.codeISOA3(), 80, "ISOA3");
		 
		List<ColumnConfig<Country, ?>> configList =  new ArrayList<ColumnConfig<Country,?>>();
		configList.add(col1);
		configList.add(col2);
		configList.add(col3);
		configList.add(col4);
		configList.add(col5);
		return configList;
	}
	
	/*private void initItemList(ManageServiceAsync manageService, String codeEnum){
		
		//Init list
		manageService.getCountryList(codeEnum,
				new AsyncCallback<List<Country>>() {
					@Override
					public void onFailure(Throwable caught) {
						new MessageBox("getCountryList Err", "Error !!!! "
								+ caught.getLocalizedMessage()).show();
					}

					@Override
					public void onSuccess(List<Country> result) {
						items = result;
						new MessageBox("onSuccess", "Success !!!! "
								).show();
					}
				});
	}*/

}
