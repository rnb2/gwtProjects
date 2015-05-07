/**
 * 
 */
package com.rnb.plategka.client.widgets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.rnb.plategka.client.ConstantService;
import com.rnb.plategka.client.ConstantServiceAsync;
import com.rnb.plategka.client.images.Images;
import com.rnb.plategka.client.messages.MyMessages;
import com.rnb.plategka.client.model.ConstantsProperties;
import com.rnb.plategka.client.windows.ConstantsAdd;
import com.rnb.plategka.data.ConstantsProxy;
import com.sencha.gxt.core.client.resources.ThemeStyles;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.Resizable;
import com.sencha.gxt.widget.core.client.Resizable.Dir;
import com.sencha.gxt.widget.core.client.Status;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.tips.QuickTip;
import com.sencha.gxt.widget.core.client.toolbar.FillToolItem;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;
import com.sencha.gxt.widget.core.client.toolbar.SeparatorToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

/**
 * 
 * @author budukh-rn
 * 02 апр. 2015 г.	
 *
 */
public class TableConstants implements IsWidget {

	private ContentPanel root;
	private String headingText = "";
	private final ConstantsProperties props = GWT.create(ConstantsProperties.class);
	private Collection<? extends ConstantsProxy> items;
	private Status status, statusBusy;
	private MyMessages messages;
	private final ConstantServiceAsync serviceConstant = GWT.create(ConstantService.class);
	private static TableConstants instance;
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public TableConstants() {
		instance = this;
	}
	
	public TableConstants(List<ConstantsProxy> result, MyMessages messages) {
		this.items = result;
		this.messages = messages;
		instance = this;
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public Widget asWidget() {
		
			
		if(root == null){
			
			root = new ContentPanel();
		    root.setHeadingText(headingText);
		    root.setHeaderVisible(false);
		    root.setBorders(true);
		    //root.getHeader().setIcon();
		   // root.setPixelSize(600, 450);
		    root.setHeight(450);
		    root.addStyleName("margin-10");
			
		 
			List<ColumnConfig<ConstantsProxy, ?>> configList = getConfigColumnList(props);
			 
			ColumnModel<ConstantsProxy> columnModel = new ColumnModel<ConstantsProxy>(configList);
			
			listStore = new ListStore<ConstantsProxy>(props.id());
			
			listStore.addAll(items);
			
			new Resizable(root, Dir.E, Dir.SE, Dir.S);
			  
			grid = new Grid<ConstantsProxy>(listStore, columnModel);

			//grid.getView().setAutoExpandColumn(configList.get(0));
			grid.getView().setStripeRows(true);
			grid.getView().setColumnLines(true);
			grid.setBorders(false);
			grid.setColumnReordering(true);
			grid.setStateful(true);
			grid.setStateId("gridExample");
			grid.getSelectionModel().addSelectionHandler(selection);
	  
			ToolBar toolBarStatus = new ToolBar();
			toolBarStatus.addStyleName(ThemeStyles.get().style().borderTop());
		    
			statusBusy = new Status();
		    statusBusy.setText("");

		    status = new Status();
			status.setText(feelStatus(items));

		    
		    toolBarStatus.add(status);
		    toolBarStatus.add(new LabelToolItem(" "));		    
		    toolBarStatus.add(statusBusy);
		    toolBarStatus.add(new FillToolItem());

		    
		    //
		    TextButton buttonAdd = new TextButton(messages.add(), Images.INSTANCE.add());
		    buttonAdd.addSelectHandler(handlerAdd());
		    TextButton buttonDelete = new TextButton(messages.delete(), Images.INSTANCE.remove());
		    buttonDelete.addSelectHandler(handlerDelete());

		    ToolBar toolBarAction = new ToolBar();
		    toolBarAction.add(buttonAdd);
		    toolBarAction.add(new SeparatorToolItem());
		    toolBarAction.add(buttonDelete);

		    HorizontalLayoutContainer containerButton = new HorizontalLayoutContainer();
		    containerButton.add(toolBarAction, new HorizontalLayoutData(1, 1));
		    //--
			
		    VerticalLayoutContainer verticalContainer = new VerticalLayoutContainer();
		    verticalContainer.add(grid, new VerticalLayoutData(1, .7d, new Margins(4)));
		    verticalContainer.add(toolBarStatus, new VerticalLayoutData(1, -1, new Margins(0, 4, 0, 4)));
		    verticalContainer.add(containerButton, new VerticalLayoutData(1, -1, new Margins(4)));

		    
		    root.setWidget(verticalContainer);		    		   
		    
		    new QuickTip(grid);		    
		}		
		return root;
	}

	private SelectHandler handlerDelete() {
		return new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				if (selected == null) {
					return;
				}
				ConfirmMessageBox box = new ConfirmMessageBox(messages.application(), messages.messageDeleteQ());
				box.addDialogHideHandler(handlerHide());
				box.show();
			}
		};
	}

	protected DialogHideHandler handlerHide() {

		return new DialogHideHandler() {
			
			@Override
			public void onDialogHide(DialogHideEvent event) {
				if(PredefinedButton.YES == event.getHideButton()){
					serviceConstant.delete(selected.getId(), callbackDelete());
				}
			}
		};
	}

	protected AsyncCallback<Void> callbackDelete() {
		return new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				listStore.remove(selected);
				grid.getStore().remove(selected);
				listStore.commitChanges();
				status.setText(feelStatus(listStore.getAll()));
			}
			
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}
		};
	}

	private SelectHandler handlerAdd() {
		return new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				//другой вид формы
				//Config config = new Config(messages);
				//root.add(config);
				
				ConstantsAdd add = new ConstantsAdd(messages, selected);
				add.show();
			}
		};
	}

	private ConstantsProxy selected;
	private SelectionHandler<ConstantsProxy> selection = new SelectionHandler<ConstantsProxy>() {
		@Override
		public void onSelection(SelectionEvent<ConstantsProxy> event) {
			selected = event.getSelectedItem();
		}
	};
	private Grid<ConstantsProxy> grid;
	private ListStore<ConstantsProxy> listStore;
	
	private List<ColumnConfig<ConstantsProxy, ?>> getConfigColumnList(
			ConstantsProperties props2) {
		
		ColumnConfig<ConstantsProxy, String> col1 = new ColumnConfig<ConstantsProxy, String>(props2.date(), 100, messages.data());
		ColumnConfig<ConstantsProxy, Integer> col2 = new ColumnConfig<ConstantsProxy, Integer>(props2.yearOfPay(), 80, messages.yearOfPay());
		ColumnConfig<ConstantsProxy, Integer> col3 = new ColumnConfig<ConstantsProxy, Integer>(props2.lightPredel(), 80, messages.lightPredel());
		ColumnConfig<ConstantsProxy, Double> col4 = new ColumnConfig<ConstantsProxy, Double>(props2.light(), 80, messages.light());
		ColumnConfig<ConstantsProxy, Double> col5 = new ColumnConfig<ConstantsProxy, Double>(props2.lightMore(), 80, messages.lightMore());
		ColumnConfig<ConstantsProxy, Double> col6 = new ColumnConfig<ConstantsProxy, Double>(props2.water(), 80, messages.water());
		 
		List<ColumnConfig<ConstantsProxy, ?>> configList =  new ArrayList<ColumnConfig<ConstantsProxy,?>>();
		configList.add(col1);
		configList.add(col2);
		configList.add(col3);
		configList.add(col4);
		configList.add(col5);
		configList.add(col6);
		return configList;
	}

	public static TableConstants getInstance() {
		return instance;
	}
	
	private String feelStatus(Collection<? extends ConstantsProxy> items2) {
		StringBuilder sb = new StringBuilder();
		sb.append(messages.count());
		sb.append(": ");
		sb.append(items2.size());
		return sb.toString();
	}
	
	public void refreshView(){
		statusBusy.setBusy(messages.loadData());
		serviceConstant.getConstantsProxy(callbackGetConstants());
	}

	private AsyncCallback<List<ConstantsProxy>> callbackGetConstants() {
		return new AsyncCallback<List<ConstantsProxy>>() {
			
			@Override
			public void onSuccess(List<ConstantsProxy> result) {
				grid.getSelectionModel().deselectAll();
				listStore.clear();
				listStore.addAll(result);
				
				grid.getView().refresh(true);
				status.setText(feelStatus(result));
				statusBusy.clearStatus("");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				statusBusy.clearStatus("");
				status.setText(caught.getLocalizedMessage());
			}
		};
	}

}
