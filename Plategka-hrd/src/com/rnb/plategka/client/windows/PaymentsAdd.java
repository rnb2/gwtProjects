/**
 * 
 */
package com.rnb.plategka.client.windows;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rnb.plategka.client.PaymentsService;
import com.rnb.plategka.client.PaymentsServiceAsync;
import com.rnb.plategka.client.images.Images;
import com.rnb.plategka.client.messages.MyMessages;
import com.rnb.plategka.client.utils.DateUtil;
import com.rnb.plategka.data.Payments;
import com.rnb.plategka.shared.AppUtils;
import com.sencha.gxt.core.client.util.DateWrapper;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.ParseErrorEvent;
import com.sencha.gxt.widget.core.client.event.ParseErrorEvent.ParseErrorHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.DateTimePropertyEditor;
import com.sencha.gxt.widget.core.client.form.DoubleField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FieldSet;
import com.sencha.gxt.widget.core.client.form.IntegerField;
import com.sencha.gxt.widget.core.client.form.PropertyEditor;
import com.sencha.gxt.widget.core.client.form.validator.MinDateValidator;
import com.sencha.gxt.widget.core.client.info.Info;

/**
 * @author budukh-rn
 * 07 апр. 2015 г.	
 *
 */
public class PaymentsAdd extends Window {

	private final PaymentsServiceAsync service = GWT.create(PaymentsService.class);
	private Payments selected;
	private MyMessages messages;
	private DateField date;
	private Integer yearOfPay;
	
	private DoubleField pay1;
	private DoubleField pay2;
	private DoubleField pay5;
	private DoubleField pay6;
	private DoubleField pay7;
	
	//Water
	private IntegerField pay3_1;
	private IntegerField pay3_2;
	
	private DoubleField pay3Odd;
	private DoubleField pay3Summa;
	
	//Light
	private IntegerField pay4_1;
	private IntegerField pay4_2;
	
	private IntegerField pay4Odd;
	private DoubleField pay4Summa;

	private DoubleField summaItog;
	
	
	/**
	 * 
	 */
	public PaymentsAdd() {
	}


	public PaymentsAdd(MyMessages messages, Integer yearOfPay,  Payments selected) {
		this.messages = messages;
		this.selected = selected;
		this.yearOfPay = yearOfPay;
		init();
	}

	private void init() {
		setModal(true);
		setBlinkModal(true);
		setHeadingText(messages.paymentsAdd());
		setBodyStyle("padding: 5px");
		setWidth(350);
			
		summaItog = new DoubleField();
		String format = "#.##";
		NumberFormat fmt = NumberFormat.getFormat(format);
		summaItog.setFormat(fmt);
		summaItog.setEditable(false);

		 
	    pay1 = new DoubleField();
	    pay1.setAllowBlank(false);
	    pay1.setSelectOnFocus(true);
	    pay1.setEmptyText(messages.inputValue());
	    pay1.setValue(selected != null ? selected.getPay1() : 0d);

	    pay2 = new DoubleField();
	    pay2.setAllowBlank(false);
	    pay2.setSelectOnFocus(true);
	    pay2.setEmptyText(messages.inputValue());
	    pay2.setValue(selected != null ? selected.getPay2() : 0d);

	    pay3_1  = new IntegerField();
	    pay3_1.setAllowBlank(false);
	    pay3_1.setSelectOnFocus(true);
	    pay3_1.setEmptyText(messages.inputValue());
	    pay3_1.setValue(selected != null ? selected.getPay3_2() : 0);
	    pay3_1.addValueChangeHandler(handlerPay3());
	    
	    pay3Odd  = new DoubleField();
	    pay3Odd.setEnabled(false);
	    pay3Summa = new DoubleField();
	    pay3Summa.setEnabled(false);
	    
	    pay3_2  = new IntegerField();
	    pay3_2.setAllowBlank(false);
	    pay3_2.setSelectOnFocus(true);
	    pay3_2.setEmptyText(messages.inputValue());
	    pay3_2.addValueChangeHandler(handlerPay3());
	    pay3_2.setValue(0);
	    	    
	    
	    date = new DateField();
	    Date valueDate = new Date();
		date.setValue(selected != null ? new Date(selected.getDateOfPay()) : valueDate);
	    date.addValidator(new MinDateValidator(new DateWrapper(2009, 1, 1).asDate()));
	    date.addParseErrorHandler(new ParseErrorHandler() {
	      @Override
	      public void onParseError(ParseErrorEvent event) {
	        Info.display(messages.parseError(), event.getErrorValue() + messages.dateParseError());
	      }
	    });
	    date.setAllowBlank(true);
	    PropertyEditor<Date> propertyEditorDate = new DateTimePropertyEditor(DateUtil.DEFAULT_DATE_FORMAT);
		date.setPropertyEditor(propertyEditorDate);
	
	    
		pay4_1  = new IntegerField();
	    pay4_1.setAllowBlank(false);
	    pay4_1.setSelectOnFocus(true);
	    pay4_1.setEmptyText(messages.inputValue());
	    pay4_1.setValue(selected != null ? selected.getPay4_2() : 0);
	    pay4_1.addValueChangeHandler(handlerPay4());

	    pay4_2  = new IntegerField();
	    pay4_2.setAllowBlank(false);
	    pay4_2.setSelectOnFocus(true);
	    pay4_2.setEmptyText(messages.inputValue());
	    pay4_2.addValueChangeHandler(handlerPay4());
	    pay4_2.setValue(0);
	    
		pay4Odd  = new IntegerField();
		pay4Odd.setEnabled(false);
		pay4Summa = new DoubleField();
		pay4Summa.setEnabled(false);
	    
		pay5 = new DoubleField();
		pay5.setAllowBlank(false);
		pay5.setSelectOnFocus(true);
		pay5.setEmptyText(messages.inputValue());
		pay5.setValue(selected != null ? selected.getPay5() : 0d);

		pay6 = new DoubleField();
		pay6.setAllowBlank(true);
		pay6.setSelectOnFocus(true);
		pay6.setEmptyText(messages.inputValue());
		pay6.setValue(selected != null ? selected.getPay6() : 0d);

		pay7 = new DoubleField();
		pay7.setAllowBlank(true);
		pay7.setSelectOnFocus(true);
		pay7.setEmptyText(messages.inputValue());
		pay7.setValue(selected != null ? selected.getPay7() : 0d);
	   
	    VerticalLayoutContainer container = new VerticalLayoutContainer();
	   	    
	    container.add(new FieldLabel(date, messages.data()), new VerticalLayoutData(1, -1));
	    container.add(new FieldLabel(pay1, "#1 " + messages.pay1()), new VerticalLayoutData(1, -1));
	    container.add(new FieldLabel(pay2, "#2 " + messages.pay2()), new VerticalLayoutData(1, -1));

	    
	    VerticalLayoutContainer vlc = new VerticalLayoutContainer();
	    vlc.add(new FieldLabel(pay3_1, messages.pay4_1()), new VerticalLayoutData(1, -1));
	    vlc.add(new FieldLabel(pay3_2, messages.pay4_2()), new VerticalLayoutData(1, -1));
	    vlc.add(new FieldLabel(pay3Odd, messages.odds()), new VerticalLayoutData(1, -1));
	    vlc.add(new FieldLabel(pay3Summa, messages.summa()), new VerticalLayoutData(1, -1));
	    
	    FieldSet fieldSet = new FieldSet();
	    fieldSet.setHeadingText("#3 " + messages.waterShort());
	    fieldSet.setCollapsible(true);
	    fieldSet.add(vlc);
  
	    VerticalLayoutContainer vlc2 = new VerticalLayoutContainer();
	    vlc2.add(new FieldLabel(pay4_1, messages.pay4_1()), new VerticalLayoutData(1, -1));
	    vlc2.add(new FieldLabel(pay4_2, messages.pay4_2()), new VerticalLayoutData(1, -1));
	    vlc2.add(new FieldLabel(pay4Odd, messages.odds()), new VerticalLayoutData(1, -1));
	    vlc2.add(new FieldLabel(pay4Summa, messages.summa()), new VerticalLayoutData(1, -1));

	    FieldSet fieldSet2 = new FieldSet();
	    fieldSet2.setHeadingText("#4 " + messages.lightShort());
	    fieldSet2.setCollapsible(true);
	    fieldSet2.add(vlc2);
	    
	   
	    VerticalLayoutContainer vlc3 = new VerticalLayoutContainer();
	    vlc3.add(summaItog, new VerticalLayoutData(1, -1));
	    
	    FieldSet fieldSet3 = new FieldSet();
	    fieldSet3.setHeadingText(messages.itog());
	    fieldSet3.setCollapsible(false);
	    fieldSet3.add(vlc3);
	    
	    container.add(fieldSet, new VerticalLayoutData(1, -1));    
	    container.add(fieldSet2, new VerticalLayoutData(1, -1));    
	    container.add(new FieldLabel(pay5, "#5 " + messages.pay5()), new VerticalLayoutData(1, -1));
	    container.add(new FieldLabel(pay6, "#6 " + messages.pay6()), new VerticalLayoutData(1, -1));
	    container.add(new FieldLabel(pay7, "#7 " + messages.pay7()), new VerticalLayoutData(1, -1));
	    container.add(fieldSet3, new VerticalLayoutData(1, -1));   
	    
		
		
		TextButton bClose = new TextButton(messages.close());
		bClose.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				hide();
			}
		});
		TextButton bSave = new TextButton(messages.save(), Images.INSTANCE.save());
		bSave.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
					Payments p = buildPayments(true);
					service.addPayment(p, callbackPaymentsAdd());
			}
		});
			    
	    add(container);
		addButton(bSave);
		addButton(bClose);
	    
		calculateAllSumma();
		
	}
	
	
	
	private ValueChangeHandler<Integer> handlerPay3() {
		return new ValueChangeHandler<Integer>() {
			@Override
			public void onValueChange(ValueChangeEvent<Integer> event) {
				if(event.getValue() != null 
						&& pay3_1.getValue() != null
						&& pay3_2.getValue() != null){
					Integer i = pay3_2.getValue() - pay3_1.getValue();
					pay3Odd.setText(String.valueOf(i));
					calculateAllSumma();
				}
			}
		};
	}

	private ValueChangeHandler<Integer> handlerPay4() {
		return new ValueChangeHandler<Integer>() {
			@Override
			public void onValueChange(ValueChangeEvent<Integer> event) {
				if(event.getValue() != null 
						&& pay4_1.getValue() != null
						&& pay4_2.getValue() != null){
					Integer oddValue = pay4_2.getValue() - pay4_1.getValue();
					pay4Odd.setValue(oddValue);
					
					double resultLight = AppUtils.getRaschetLight(100, 2d, 3d, oddValue);
					pay4Summa.setValue(resultLight);

					calculateAllSumma();
				}
			}
		};
	}


	private Payments buildPayments(boolean isNewRecord){
		Payments payments = isNewRecord ? new Payments() : selected;
			
		//String monthOfPay = DateTimeFormat.getFormat("MMM").format(date.getValue());

		payments.setPay1(pay1.getValue());
		payments.setPay2(pay2.getValue());
		payments.setPay3_1(pay3_1.getValue());
		payments.setPay3_2(pay3_2.getValue());
		payments.setPay4_1(pay4_1.getValue());
		payments.setPay4_2(pay4_2.getValue());
		payments.setPay5(pay5.getValue());
		payments.setPay6(pay6.getValue());
		payments.setPay7(pay7.getValue());
		payments.setDateOfPay(DateTimeFormat.getFormat(DateUtil.DEFAULT_DATE_TIME_FORMAT).format(date.getValue()));
		payments.setYearOfPay(yearOfPay);
		payments.setUser("testUser");
		return payments;
	}

	protected AsyncCallback<Payments> callbackPaymentsAdd() {
		return new AsyncCallback<Payments>() {			
			@Override
			public void onSuccess(Payments result) {
				Info.display(messages.paymentsAdd(), messages.dataAdded());
				okResult = true;
				hide();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				okResult = false;
			}
		};
	}

	public boolean okResult = false;


	public boolean isOkResult() {
		return okResult;
	}	

	private void calculateAllSumma(){
		Double p1 = pay1.getValue() == null ? 0 : pay1.getValue();
		Double p2 = pay2.getValue() == null ? 0 : pay2.getValue();
		Double p3 = pay3Summa.getValue() == null ? 0 : pay3Summa.getValue();
		Double p4 = pay4Summa.getValue() == null ? 0 : pay4Summa.getValue();
		Double p5 = pay5.getValue() == null ? 0 : pay5.getValue();
		Double p6 = pay6.getValue() == null ? 0 : pay6.getValue();
		Double p7 = pay7.getValue() == null ? 0 : pay7.getValue();

		Double d = p1 + p2 + p3 + p4 + p5 + p6+p7;
		summaItog.setValue(d);
	}
}
