package com.rnb.plategka.client.forms;

import com.rnb.plategka.client.messages.MyMessages;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.TextField;

@Deprecated
public class Config extends FormPanel {

	private TextField lightField;
	private TextField lightFieldMore;
	private TextField waterTextField;
	private DateField dateField;
	//private final String errMess = "���� �� ������ ���� ������!";
	private final String emptyMess = "������� ��������";
	private MyMessages messages;

	public Config(MyMessages messages) {
		this.messages = messages;
		setTitle(this.messages.settings());
		
		lightField = new TextField();
		lightField.setId("light");
		//lightField.setEmptyText("����");
		lightField.setEmptyText(emptyMess);
		lightField.setAllowBlank(false);
		lightField.setSelectOnFocus(true);
		//lightField.setValidator(new EmptyValidator(errMess));

		lightFieldMore = new TextField();
		lightFieldMore.setId("lightMore");
		//lightFieldMore.setFieldLabel("1 кВт, >150");
		lightFieldMore.setEmptyText(emptyMess);
		lightFieldMore.setAllowBlank(false);
		lightFieldMore.setSelectOnFocus(true);
		//lightFieldMore.addValidator(new EmptyValidator(errMess));


		waterTextField = new TextField();
		waterTextField.setId("water");
		//waterTextField.setFieldLabel("1 м3");
		waterTextField.setEmptyText(emptyMess);
		waterTextField.setAllowBlank(false);
		waterTextField.setSelectOnFocus(true);
		//waterTextField.setValidator(new EmptyValidator(errMess));

		dateField = new DateField();
		dateField.setId("date");
		dateField.setAllowBlank(false);
		//dateField.setFieldLabel("Дата");
		dateField.setSelectOnFocus(true);
		//dateField.setfgetPropertyEditor().setFormat(DateTimeFormat.getFormat(DateUtil.DEFAULT_DATE_FORMAT));

				
		 VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		 
		 vlc.add(new FieldLabel(lightField, this.messages.light()), new VerticalLayoutData(1, -1));
		 vlc.add(new FieldLabel(lightFieldMore, this.messages.lightMore()), new VerticalLayoutData(1, -1));
		 vlc.add(new FieldLabel(waterTextField, this.messages.water()), new VerticalLayoutData(1, -1));
		 vlc.add(new FieldLabel(dateField, this.messages.data()), new VerticalLayoutData(1, -1));
		 
		 add(vlc);

		// Button save = new Button("Сохранить");
		//
		// save.addSelectionListener(new SelectionListener<ButtonEvent>() {
		//
		// @Override
		// public void componentSelected(ButtonEvent ce) {
		// System.out.println("save config...");
		// System.out.println("light=" + lightField.getValue());
		// System.out.println("water=" + waterTextField.getValue());
		// StateManager.get().set("light", lightField.getValue());
		// StateManager.get().set("water", waterTextField.getValue());
		// }
		// });
		//
		// this.getButtonBar().add(save);
		setWidth(250);
		//this.setFieldWidth(145);
		//this.setPadding(5);
		//this.setLabelWidth(70);
		//this.setFrame(true);
		//this.layout();
	}

	public TextField getLightField() {
		return lightField;
	}

	public TextField getWaterTextField() {
		return waterTextField;
	}

	public DateField getDateField() {
		return dateField;
	}

	public TextField getLightFieldMore() {
		return lightFieldMore;
	}
	

}

