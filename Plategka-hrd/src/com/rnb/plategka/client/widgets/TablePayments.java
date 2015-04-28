/**
 * 
 */
package com.rnb.plategka.client.widgets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.rnb.plategka.client.PaymentsService;
import com.rnb.plategka.client.PaymentsServiceAsync;
import com.rnb.plategka.client.images.Images;
import com.rnb.plategka.client.messages.MyMessages;
import com.rnb.plategka.client.model.PaymentsProperties;
import com.rnb.plategka.client.windows.PaymentsAdd;
import com.rnb.plategka.data.Payments;
import com.sencha.gxt.cell.core.client.PropertyDisplayCell;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.resources.ThemeStyles;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
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
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor.DoublePropertyEditor;
import com.sencha.gxt.widget.core.client.grid.AggregationNumberSummaryRenderer;
import com.sencha.gxt.widget.core.client.grid.AggregationRowConfig;
import com.sencha.gxt.widget.core.client.grid.AggregationSafeHtmlRenderer;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.HeaderGroupConfig;
import com.sencha.gxt.widget.core.client.grid.SummaryType.SumSummaryType;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.tips.QuickTip;
import com.sencha.gxt.widget.core.client.toolbar.FillToolItem;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;
import com.sencha.gxt.widget.core.client.toolbar.SeparatorToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

/**
 * 
 * @author budukh-rn
 * 07 апр. 2015 г.	
 *
 */
public class TablePayments implements IsWidget {

	private static final String STYLE_HEADER_BOLD = "headerBold";
	private ContentPanel root;
	private final PaymentsProperties props = GWT.create(PaymentsProperties.class);
	private Collection<? extends Payments> items;
	private List<Integer> yearList;
	private Status status, statusBusy;
	private MyMessages messages;
	private final PaymentsServiceAsync service = GWT.create(PaymentsService.class);
	private PaymentsAdd paymentsAdd; 
	private static TablePayments instance;
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public TablePayments() {
	}
	

	public TablePayments(List<Integer> yearList, MyMessages messages) {
		this.items = new ArrayList<Payments>();
		this.messages = messages;
		this.yearList = yearList;
		
		instance = this;
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public Widget asWidget() {
		
			
		if(root == null){
			
			StyleInjector.inject(".headerBold{ font-weight: 600; color: maroon;} ");
			
			root = new ContentPanel();
		    root.setHeadingText(messages.payments());
		    root.setHeaderVisible(false);
		    root.setBorders(true);
		    //root.getHeader().setIcon();
		   // root.setPixelSize(600, 450);
		    root.setHeight(450);
		    root.addStyleName("margin-10");
		    
		   //
			ListStore<Integer> store = new ListStore<Integer>(new ModelKeyProvider<Integer>() {
				@Override
				public String getKey(Integer item) {
					return item.toString();
				}
			});
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
			
			ColumnConfig<Payments, ?> column0 = configList.get(0);
			ColumnConfig<Payments, ?> columnAll_2 = configList.get(2);
			ColumnConfig<Payments, ?> columnAll_3 = configList.get(3);
			ColumnConfig<Payments, ?> columnAllWater = configList.get(4);
			ColumnConfig<Payments, ?> columnAll_7 = configList.get(7);
			ColumnConfig<Payments, ?> columnAll_10 = configList.get(10);
			ColumnConfig<Payments, ?> columnAll_11 = configList.get(11);
			ColumnConfig<Payments, ?> columnAll_12 = configList.get(12);
			ColumnConfig<Payments, ?> columnAll_13 = configList.get(13);
			ColumnConfig<Payments, ?> columnAll_14 = configList.get(14);
			 
			ColumnModel<Payments> columnModel = new ColumnModel<Payments>(configList);
			
			HeaderGroupConfig headerGroupConfig = new HeaderGroupConfig(messages.waterShort(), 1, 3);
			//TODO ошибка
			//headerGroupConfig.getWidget().setStyleName(STYLE_HEADER_BOLD);
			
			columnModel.addHeaderGroup(0, 4, headerGroupConfig);
			columnModel.addHeaderGroup(0, 7, new HeaderGroupConfig(messages.lightShort(), 1, 5));
			
			NumberFormat numberFormat = NumberFormat.getCurrencyFormat();
			
			AggregationRowConfig<Payments> aggregation1 = new AggregationRowConfig<Payments>();
			
			aggregation1.setRenderer(column0, new AggregationSafeHtmlRenderer<Payments>(messages.itog()));
			aggregation1.setRenderer(columnAll_2, new AggregationNumberSummaryRenderer<Payments, Double>(numberFormat, new SumSummaryType<Double>()));
			aggregation1.setRenderer(columnAll_3, new AggregationNumberSummaryRenderer<Payments, Double>(numberFormat, new SumSummaryType<Double>()));
			aggregation1.setRenderer(columnAllWater, new AggregationNumberSummaryRenderer<Payments, Double>(numberFormat, new SumSummaryType<Double>()));
			aggregation1.setRenderer(columnAll_7, new AggregationNumberSummaryRenderer<Payments, Double>(numberFormat, new SumSummaryType<Double>()));
			aggregation1.setRenderer(columnAll_10, new AggregationNumberSummaryRenderer<Payments, Double>(numberFormat, new SumSummaryType<Double>()));
			aggregation1.setRenderer(columnAll_11, new AggregationNumberSummaryRenderer<Payments, Double>(numberFormat, new SumSummaryType<Double>()));
			aggregation1.setRenderer(columnAll_12, new AggregationNumberSummaryRenderer<Payments, Double>(numberFormat, new SumSummaryType<Double>()));
			aggregation1.setRenderer(columnAll_13, new AggregationNumberSummaryRenderer<Payments, Double>(numberFormat, new SumSummaryType<Double>()));
			aggregation1.setRenderer(columnAll_14, new AggregationNumberSummaryRenderer<Payments, Double>(numberFormat, new SumSummaryType<Double>()));
			
			columnModel.addAggregationRow(aggregation1);
			
						
			listStore = new ListStore<Payments>(props.id());
			listStore.addAll(items);
			
			new Resizable(root, Dir.E, Dir.SE, Dir.S);
			  
			grid = new Grid<Payments>(listStore, columnModel);/*{
				protected void onAfterFirstAttach() {
					statusBusy.setBusy(messages.loadData());
				};
			};*/


			grid.getView().setStripeRows(true);
			grid.getView().setColumnLines(true);
			grid.setBorders(false);
			grid.setColumnReordering(true);
			grid.setStateful(true);
			grid.setStateId("gridExample");
			grid.getSelectionModel().addSelectionHandler(selection);
			grid.getView().setEmptyText(messages.noDataFound());
			grid.setLoadMask(true);
			
			//TODO ошибка
			//grid.getView().getHeader().getElement().select("td[colspan]").getItem(0).addClassName("headerBold");
			
		  
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
		    TextButton buttonAdd = new TextButton("Add", Images.INSTANCE.add());
		    buttonAdd.addSelectHandler(handlerAdd());
		    TextButton buttonDelete = new TextButton("Delete", Images.INSTANCE.remove());
		    buttonDelete.addSelectHandler(handlerDelete());

		    ToolBar toolBarAction = new ToolBar();
		    toolBarAction.add(buttonAdd);
		    toolBarAction.add(new SeparatorToolItem());
		    toolBarAction.add(buttonDelete);
		    toolBarAction.setWidth(100);

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


	private String feelStatus(Collection<? extends Payments> items2) {
		StringBuilder sb = new StringBuilder();
		sb.append(messages.count());
		sb.append(": ");
		sb.append(items2.size());
		return sb.toString();
	}
	
	
	private SelectionHandler<Integer> handlerCombo() {
		return new SelectionHandler<Integer>() {
			
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				statusBusy.setBusy(messages.loadData());
				service.getPayments(event.getSelectedItem(), callbackGetPayments());			
			}
		};
	}

	protected AsyncCallback<List<Payments>> callbackGetPayments() {
		return new AsyncCallback<List<Payments>>() {
			
			@Override
			public void onSuccess(List<Payments> result) {
				selected = null;
				grid.getSelectionModel().deselectAll();
				listStore.clear();
				listStore.addAll(result);
				grid.getView().refresh(true);
				status.setText(feelStatus(result));
				statusBusy.clearStatus("");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				statusBusy.clearStatus("");
				status.setText(caught.getLocalizedMessage());
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
				if(comboBox.getValue() == null){
					Info.display(messages.paymentsAdd(), messages.selectYear());
					return;
				}	
				
				try {
					paymentsAdd = new PaymentsAdd(messages, comboBox.getValue(), selected);
					paymentsAdd.show();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
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
	private NumberFormat numberFormatDouble = NumberFormat.getFormat("0.00");
	
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

		ValueProvider<Payments, Double> valueProvider = new ValueProvider<Payments, Double>() {
			public Double getValue(Payments object) {
				return object.getPay1() + object.getPay2() + object.getPay3() + object.getPay4() + object.getPay5() + object.getPay6() + object.getPay7();
			}

			@Override
			public void setValue(Payments object, Double value) {}

			@Override
			public String getPath() {
				return "1";
			};
		};
		ColumnConfig<Payments, Double> col17 = new ColumnConfig<Payments, Double>(valueProvider
		, 80, messages.itog());
		
		 
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
		configList.add(col17);
		
		col2.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		col3.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		col4.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		col5.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		col14.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		col15.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		col16.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		col17.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		col2.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		col3.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		col4.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		col5.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		col14.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		col15.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		col16.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		col17.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		col2.setWidth(60);
		col3.setWidth(100);
		
		DoublePropertyEditor doublePropertyEditor = new DoublePropertyEditor(numberFormatDouble);
		PropertyDisplayCell<Double> propertyDisplayCell = new PropertyDisplayCell<Double>(doublePropertyEditor);
		col4.setCell(propertyDisplayCell);
		col5.setCell(propertyDisplayCell);
		col6.setCell(propertyDisplayCell);
		col9.setCell(propertyDisplayCell);
		col12.setCell(propertyDisplayCell);
		col13.setCell(propertyDisplayCell);
		col14.setCell(propertyDisplayCell);
		col15.setCell(propertyDisplayCell);
		col16.setCell(propertyDisplayCell);
		
		col17.setCell(propertyDisplayCell);
		
		col2.setColumnHeaderClassName(STYLE_HEADER_BOLD);
		col3.setColumnHeaderClassName(STYLE_HEADER_BOLD);
		col4.setColumnHeaderClassName(STYLE_HEADER_BOLD);
		col5.setColumnHeaderClassName(STYLE_HEADER_BOLD);
		col14.setColumnHeaderClassName(STYLE_HEADER_BOLD);
		col15.setColumnHeaderClassName(STYLE_HEADER_BOLD);
		col16.setColumnHeaderClassName(STYLE_HEADER_BOLD);
		col17.setColumnHeaderClassName(STYLE_HEADER_BOLD);
		
		return configList;
	}


	public static TablePayments getInstance() {
		return instance;
	}
	
	public void refreshView(int year){
		statusBusy.setBusy(messages.loadData());
		service.getPayments(year, callbackGetPayments());		
	}
	

}
