/**
 * 
 */
package com.rnb.plategka.client.windows;

import java.util.Date;

import com.google.gwt.i18n.client.NumberFormat;
import com.rnb.plategka.client.messages.MyMessages;
import com.rnb.plategka.client.utils.DateUtil;
import com.rnb.plategka.data.Payments;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.DoubleField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FieldSet;
import com.sencha.gxt.widget.core.client.form.IntegerField;

/**
 * Информация о текущем платеже
 * @author budukh-rn
 * 07 мая 2015 г.	
 *
 */
public class PaymentsInfo extends Window{

		private MyMessages messages;
		private DateField date;
		//private Integer yearOfPay;
		
		private DoubleField pay1;
		private DoubleField pay2;
		private DoubleField pay5;
		private DoubleField pay6;
		private DoubleField pay7;
		
		//Water
		private IntegerField pay3_1;
		private IntegerField pay3_2;
		
		private IntegerField pay3Odd;
		private DoubleField pay3Summa;
		
		//Light
		private IntegerField pay4_1;
		private IntegerField pay4_2;
		
		private IntegerField pay4Odd;
		private DoubleField pay4Summa;

		private DoubleField summaItog;
		
		private Payments selected;
		
	/**
	 * 
	 */
	public PaymentsInfo() {
	}

	/**
	 * 
	 */
	public PaymentsInfo(MyMessages messages, Payments payments) {
		this.selected = payments;
		this.messages = messages;
		init();
	}
	
	private void init() {
		setModal(true);
		setBlinkModal(true);
		setHeadingText(messages.paymentsInfo());
		setBodyStyle("padding: 5px");
		setWidth(350);
		
		 
	    pay1 = new DoubleField();
	    pay1.setValue(selected != null ? selected.getPay1() : 0d);
	    pay1.setEditable(false);

	    pay2 = new DoubleField();
	    pay2.setValue(selected != null ? selected.getPay2() : 0d);
	    pay2.setEditable(false);

	    pay3_1  = new IntegerField();
	    pay3_1.setValue(selected != null ? selected.getPay3_1() : 0);
	    pay3_1.setEditable(false);

	    
	    pay3Odd  = new IntegerField();
	    pay3Odd.setEnabled(false);
	    pay3Odd.setValue(selected.getPay3_2() - selected.getPay3_1());
	   
	    pay3Summa = new DoubleField();
	    pay3Summa.setEnabled(false);
	    pay3Summa.setValue(selected.getPay3());
	    
	    pay3_2  = new IntegerField();
	    pay3_2.setValue(selected != null ? selected.getPay3_2() : 0);
	    pay3_2.setEditable(false);
	    	    
	    
	    date = new DateField();
	    Date valueDate = new Date();
		date.setValue(selected != null ? DateUtil.DATE_TIME_FORMAT.parse(selected.getDateOfPay()) : valueDate);
		date.setEditable(false);
			
	    
		pay4_1  = new IntegerField();
	    pay4_1.setValue(selected != null ? selected.getPay4_1() : 0);
	    pay4_1.setEditable(false);

	    pay4_2  = new IntegerField();
	    pay4_2.setEditable(false);
	    pay4_2.setValue(selected != null ? selected.getPay4_2() : 0);
	    
		pay4Odd  = new IntegerField();
		pay4Odd.setEnabled(false);
		pay4Odd.setValue(selected.getPay4_2() - selected.getPay4_1());
		
		pay4Summa = new DoubleField();
		pay4Summa.setEnabled(false);
		pay4Summa.setValue(selected.getPay4());
	    
		pay5 = new DoubleField();
		pay5.setValue(selected != null ? selected.getPay5() : 0d);
		pay5.setEditable(false);

		pay6 = new DoubleField();
		pay6.setValue(selected != null ? selected.getPay6() : 0d);
		pay6.setEditable(false);

		pay7 = new DoubleField();
		pay7.setValue(selected != null ? selected.getPay7() : 0d);
		pay7.setEditable(false);
				
		summaItog = new DoubleField();
		String format = "#.##";
		NumberFormat fmt = NumberFormat.getFormat(format);
		summaItog.setFormat(fmt);
		summaItog.setEditable(false);
		summaItog.setValue(selected.getPay1() + selected.getPay2() + selected.getPay3() + selected.getPay4() + selected.getPay5() + selected.getPay6() + selected.getPay7());
	   
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
	    fieldSet.setCollapsible(false);
	    fieldSet.add(vlc);
  
	    VerticalLayoutContainer vlc2 = new VerticalLayoutContainer();
	    vlc2.add(new FieldLabel(pay4_1, messages.pay4_1()), new VerticalLayoutData(1, -1));
	    vlc2.add(new FieldLabel(pay4_2, messages.pay4_2()), new VerticalLayoutData(1, -1));
	    vlc2.add(new FieldLabel(pay4Odd, messages.odds()), new VerticalLayoutData(1, -1));
	    vlc2.add(new FieldLabel(pay4Summa, messages.summa()), new VerticalLayoutData(1, -1));

	    FieldSet fieldSet2 = new FieldSet();
	    fieldSet2.setHeadingText("#4 " + messages.lightShort());
	    fieldSet2.setCollapsible(false);
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
		
		add(container);
		addButton(bClose);
	}	
}
