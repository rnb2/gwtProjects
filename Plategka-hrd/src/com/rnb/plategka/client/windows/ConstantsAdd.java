/**
 * 
 */
package com.rnb.plategka.client.windows;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rnb.plategka.client.ConstantService;
import com.rnb.plategka.client.ConstantServiceAsync;
import com.rnb.plategka.client.images.Images;
import com.rnb.plategka.client.messages.MyMessages;
import com.rnb.plategka.client.utils.DateUtil;
import com.rnb.plategka.client.widgets.TableConstants;
import com.rnb.plategka.data.Constants;
import com.rnb.plategka.data.ConstantsProxy;
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
import com.sencha.gxt.widget.core.client.form.IntegerField;
import com.sencha.gxt.widget.core.client.form.PropertyEditor;
import com.sencha.gxt.widget.core.client.form.validator.MaxNumberValidator;
import com.sencha.gxt.widget.core.client.form.validator.MinDateValidator;
import com.sencha.gxt.widget.core.client.form.validator.MinNumberValidator;
import com.sencha.gxt.widget.core.client.info.Info;

/**
 * @author budukh-rn
 * 03 апр. 2015 г.	
 *
 */
public class ConstantsAdd extends Window {

	private final ConstantServiceAsync serviceConstant = GWT.create(ConstantService.class);
	private MyMessages messages;
	private DoubleField water;
	private DateField date;
	private IntegerField yearOfPay;
	private DoubleField lightMore;
	private DoubleField light;
	private IntegerField lightPredel;
	private ConstantsProxy selected;
	/**
	 * 
	 */
	public ConstantsAdd() {
	}

	/**
	 * @param appearance
	 */
	public ConstantsAdd(WindowAppearance appearance) {
		super(appearance);
	}

	public ConstantsAdd(MyMessages messages, ConstantsProxy selected) {
		this.messages = messages;
		this.selected = selected;
		init();
	}

	private void init() {
		setModal(true);
		setBlinkModal(true);
		setHeadingText(messages.constantAdd());
		setBodyStyle("padding: 5px");
		setWidth(350);
		
		
		
	    lightPredel = new IntegerField();
	    lightPredel.setAllowBlank(false);
	    lightPredel.setSelectOnFocus(true);
	    lightPredel.setEmptyText(messages.inputValue());
	    lightPredel.setValue(selected != null ? selected.getLightPredel() : null);

	    light = new DoubleField();
	    light.setAllowBlank(false);
	    light.setSelectOnFocus(true);
	    light.setEmptyText(messages.inputValue());
	    light.setValue(selected != null ? selected.getLight() : null);

	    lightMore = new DoubleField();
	    lightMore.setAllowBlank(false);
	    lightMore.setSelectOnFocus(true);
	    lightMore.setEmptyText(messages.inputValue());
	    lightMore.setValue(selected != null ? selected.getLightMore() : null);
	    
	    
	    date = new DateField();
	    Date valueDate = null;
		
		valueDate = selected == null ? new DateWrapper(2009, 1, 1).asDate() 
						: DateUtil.DATE_FORMAT.parse(selected.getDate());
		
		date.setValue(valueDate);
	    date.addValidator(new MinDateValidator(valueDate));
	    date.addParseErrorHandler(new ParseErrorHandler() {
	      @Override
	      public void onParseError(ParseErrorEvent event) {
	        Info.display(messages.parseError(), event.getErrorValue() + messages.dateParseError());
	      }
	    });
	    date.setAllowBlank(true);
	    PropertyEditor<Date> propertyEditorDate = new DateTimePropertyEditor(DateUtil.DEFAULT_DATE_FORMAT);
		date.setPropertyEditor(propertyEditorDate);
	    
	    yearOfPay = new IntegerField();
	    yearOfPay.setAllowBlank(true);
	    yearOfPay.setSelectOnFocus(true);
	    yearOfPay.addValidator(new MinNumberValidator<Integer>(2008));
	    yearOfPay.addValidator(new MaxNumberValidator<Integer>(2222));
	    yearOfPay.setEmptyText(messages.inputValue());
	    yearOfPay.setValue(selected == null ? null : selected.getYearOfPay());

	  
	    water = new DoubleField();
	    water.setAllowBlank(false);
	    water.setSelectOnFocus(true);
	    water.setEmptyText(messages.inputValue());
	    water.setValue(selected == null ? null : selected.getWater());
	    
	   
	    VerticalLayoutContainer container = new VerticalLayoutContainer();
	   	    
	    container.add(new FieldLabel(date, messages.data()), new VerticalLayoutData(1, -1));
	    container.add(new FieldLabel(yearOfPay, messages.yearOfPay()), new VerticalLayoutData(1, -1));
	    container.add(new FieldLabel(lightPredel, messages.lightPredel()), new VerticalLayoutData(1, -1));
	    container.add(new FieldLabel(light, messages.light()), new VerticalLayoutData(1, -1));
	    container.add(new FieldLabel(lightMore, messages.lightMore()), new VerticalLayoutData(1, -1));
	    container.add(new FieldLabel(water, messages.water()), new VerticalLayoutData(1, -1));
		
		
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
					Constants app = new Constants();
					String date2 = DateTimeFormat.getFormat(DateUtil.DEFAULT_DATE_FORMAT).format(date.getValue());
					app.setDate(date2);
					app.setLight(light.getValue());
					app.setLightMore(lightMore.getValue());
					app.setLightPredel(lightPredel.getValue());
					app.setWater(water.getValue());
					app.setYearOfPay(yearOfPay.getValue());
					app.setMothOfPay(date2.substring(3, 5));
					serviceConstant.addConstant(app, callback());
			}
		});
		
		/*FramedPanel panel = new FramedPanel();
	    panel.setHeadingText(messages.constantAdd());
	    panel.setWidth(350);
	    panel.setBodyStyle("background: none; padding: 15px");
	    panel.add(container);
	    panel.addButton(bSave);
	    panel.addButton(bClose);*/
	    
	    add(container);
		addButton(bSave);
		addButton(bClose);
	    
	    //add(panel);
	}

	protected AsyncCallback<Constants> callback() {
		return new AsyncCallback<Constants>() {
			
			@Override
			public void onSuccess(Constants result) {
				TableConstants tableConstants = TableConstants.getInstance();
				tableConstants.refreshView();
				Info.display(messages.constantAdd(), messages.dataAdded());
				hide();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();				
			}
		};
	}

}
