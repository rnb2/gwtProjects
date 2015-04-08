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
import com.rnb.plategka.client.PaymentsService;
import com.rnb.plategka.client.PaymentsServiceAsync;
import com.rnb.plategka.client.images.Images;
import com.rnb.plategka.client.messages.MyMessages;
import com.rnb.plategka.client.model.PaymentsProperties;
import com.rnb.plategka.client.model.combo.YearProperties;
import com.rnb.plategka.client.windows.PaymentsAdd;
import com.rnb.plategka.data.Payments;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.LabelProvider;
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
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.HeaderGroupConfig;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.tips.QuickTip;
import com.sencha.gxt.widget.core.client.toolbar.FillToolItem;
import com.sencha.gxt.widget.core.client.toolbar.SeparatorToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

/**
 * 
 * @author budukh-rn
 * 07 апр. 2015 г.	
 *
 */
public class TablePayments implements IsWidget {

	private ContentPanel root;
	private final PaymentsProperties props = GWT.create(PaymentsProperties.class);
	private Collection<? extends Payments> items;
	private List<Integer> yearList;
	private Status status;
	private MyMessages messages;
	private final PaymentsServiceAsync service = GWT.create(PaymentsService.class);
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public TablePayments() {
	}
	

	public TablePayments(List<Integer> yearList, MyMessages messages) {
		this.items = new ArrayList<Payments>();
		this.messages = messages;
		this.yearList = yearList;
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public Widget asWidget() {
		
			
		if(root == null){
			
			root = new ContentPanel();
		    root.setHeadingText(messages.payments());
		    root.setHeaderVisible(false);
		    root.setBorders(true);
		    //root.getHeader().setIcon();
		   // root.setPixelSize(600, 450);
		    root.setHeight(450);
		    root.addStyleName("margin-10");
		    
		   //
		    YearProperties keyProvider = GWT.create(YearProperties.class);
			ListStore<Integer> store = new ListStore<Integer>(keyProvider.key());
			store.addAll(yearList);
			comboBox = new ComboBox<Integer>(store, new LabelProvider<Integer>() {
				@Override
				public String getLabel(Integer item) {
					return item.toString();
				}
			});
			comboBox.addSelectionHandler(handlerCombo());
			comboBox.setWidth(100);
			comboBox.setTriggerAction(TriggerAction.ALL);
			comboBox.setEditable(false);
			//---
		 
			List<ColumnConfig<Payments, ?>> configList = getConfigColumnList(props);
			 
			ColumnModel<Payments> columnModel = new ColumnModel<Payments>(configList);
			
			columnModel.addHeaderGroup(0, 4, new HeaderGroupConfig(messages.waterShort(), 1, 3));
			columnModel.addHeaderGroup(0, 7, new HeaderGroupConfig(messages.lightShort(), 1, 5));
			
			listStore = new ListStore<Payments>(props.id());
			listStore.addAll(items);
			
			new Resizable(root, Dir.E, Dir.SE, Dir.S);
			  
			grid = new Grid<Payments>(listStore, columnModel);

			//grid.getView().setAutoExpandColumn(configList.get(0));
			grid.getView().setStripeRows(true);
			grid.getView().setColumnLines(true);
			grid.setBorders(false);
			grid.setColumnReordering(true);
			grid.setStateful(true);
			grid.setStateId("gridExample");
			grid.getSelectionModel().addSelectionHandler(selection);
	  
		    
		    ToolBar toolBarStatus = new ToolBar();
		    status = new Status();
		    StringBuilder sb = feelStatus(items);
			status.setText(sb.toString());
		    status.setWidth(150);
		    toolBarStatus.add(status);
		    toolBarStatus.add(new FillToolItem());
		    
		    //
		    TextButton buttonAdd = new TextButton("Add", Images.INSTANCE.add());
		    buttonAdd.addSelectHandler(handlerAdd());
		    TextButton buttonDelete = new TextButton("Delete", Images.INSTANCE.remove());
		    buttonDelete.addSelectHandler(handlerDelete());

		    ToolBar toolBarAction = new ToolBar();
		    toolBarAction.add(buttonAdd);
		    toolBarAction.add(new SeparatorToolItem());
		    toolBarAction.add(buttonDelete);

		    HorizontalLayoutContainer containerButton = new HorizontalLayoutContainer();
		    containerButton.add(toolBarAction, new HorizontalLayoutData(1, 1));
		    //--
			
		    VerticalLayoutContainer verticalContainer = new VerticalLayoutContainer();
		    verticalContainer.add(comboBox, new VerticalLayoutData(-1, -1, new Margins(2)));
		    verticalContainer.add(grid, new VerticalLayoutData(1, .7d, new Margins(4)));
		    verticalContainer.add(toolBarStatus, new VerticalLayoutData(1, -1, new Margins(0, 4, 0, 4)));
		    verticalContainer.add(containerButton, new VerticalLayoutData(1, -1, new Margins(4)));

		    
		    root.setWidget(verticalContainer);		    		   
		    
		    new QuickTip(grid);		    
		}		
		return root;
	}


	private StringBuilder feelStatus(Collection<? extends Payments> items2) {
		StringBuilder sb = new StringBuilder();
		sb.append(messages.count());
		sb.append(": ");
		sb.append(items2.size());
		return sb;
	}
	
	
	private SelectionHandler<Integer> handlerCombo() {
		return new SelectionHandler<Integer>() {
			
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				service.getPayments(event.getSelectedItem(), callbackGetPayments());			
			}
		};
	}

	protected AsyncCallback<List<Payments>> callbackGetPayments() {
		return new AsyncCallback<List<Payments>>() {
			
			@Override
			public void onSuccess(List<Payments> result) {
				grid.getSelectionModel().deselectAll();
				listStore.clear();
				listStore.addAll(result);
				grid.getView().refresh(true);
				feelStatus(result);								
			}
			
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}
		};
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
					service.deletePayment(selected, callbackDelete());
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
				grid.getView().refresh(true);
				
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
				if(comboBox.getValue() == null){
					Info.display(messages.paymentsAdd(), messages.selectYear());
					return;
				}	
				
				PaymentsAdd add = new PaymentsAdd(messages, comboBox.getValue(), selected);
				add.show();
			}
		};
	}

	private Payments selected;
	
	private SelectionHandler<Payments> selection = new SelectionHandler<Payments>() {
		@Override
		public void onSelection(SelectionEvent<Payments> event) {
			selected = event.getSelectedItem();
		}
	};
	private Grid<Payments> grid;
	private ListStore<Payments> listStore;
	private ComboBox<Integer> comboBox;
	
	private List<ColumnConfig<Payments, ?>> getConfigColumnList(
			PaymentsProperties props2) {
		
		ColumnConfig<Payments, Integer> col2 = new ColumnConfig<Payments, Integer>(props2.yearOfPay(), 80, messages.yearOfPay());
		ColumnConfig<Payments, String> col3 = new ColumnConfig<Payments, String>(props2.dateOfPay(), 80, messages.data());
		ColumnConfig<Payments, Double> col4 = new ColumnConfig<Payments, Double>(props2.pay1(), 80, messages.pay1());
		ColumnConfig<Payments, Double> col5 = new ColumnConfig<Payments, Double>(props2.pay2(), 80, messages.pay2());
		
		ColumnConfig<Payments, Double> col6 = new ColumnConfig<Payments, Double>(props2.pay3(), 80, messages.summa());
		ColumnConfig<Payments, Integer> col7 = new ColumnConfig<Payments, Integer>(props2.pay3_1(), 80, messages.pay4_1());
		ColumnConfig<Payments, Integer> col8 = new ColumnConfig<Payments, Integer>(props2.pay3_2(), 80, messages.pay4_2());

		ColumnConfig<Payments, Double> col9 = new ColumnConfig<Payments, Double>(props2.pay4(), 80, messages.summa());
		ColumnConfig<Payments, Integer> col10 = new ColumnConfig<Payments, Integer>(props2.pay4_1(), 80, messages.pay4_1());
		ColumnConfig<Payments, Integer> col11 = new ColumnConfig<Payments, Integer>(props2.pay4_2(), 80, messages.pay4_2());
		ColumnConfig<Payments, Double> col12 = new ColumnConfig<Payments, Double>(props2.pay4Less(), 80, messages.pay4Less());
		ColumnConfig<Payments, Double> col13 = new ColumnConfig<Payments, Double>(props2.pay4More(), 80, messages.pay4More());

		ColumnConfig<Payments, Double> col14 = new ColumnConfig<Payments, Double>(props2.pay5(), 80, messages.pay5());
		ColumnConfig<Payments, Double> col15 = new ColumnConfig<Payments, Double>(props2.pay6(), 80, messages.pay6());
		ColumnConfig<Payments, Double> col16 = new ColumnConfig<Payments, Double>(props2.pay7(), 80, messages.pay7());
		
		 
		List<ColumnConfig<Payments, ?>> configList =  new ArrayList<ColumnConfig<Payments,?>>();
		configList.add(col2);
		configList.add(col3);
		configList.add(col4);
		configList.add(col5);
		configList.add(col6);
		configList.add(col7);
		configList.add(col8);
		configList.add(col9);
		configList.add(col10);
		configList.add(col11);
		configList.add(col12);
		configList.add(col13);
		configList.add(col14);
		configList.add(col15);
		configList.add(col16);
		return configList;
	}
	
	

}
