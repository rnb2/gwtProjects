/**
 * 
 */
package com.rnb.todoList.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.polymer.paper.widget.PaperDialog;
import com.vaadin.polymer.paper.widget.PaperDrawerPanel;
import com.vaadin.polymer.paper.widget.PaperInput;
import com.vaadin.polymer.paper.widget.PaperTextarea;

/**
 * @author budukh
 *
 */
public class Main extends Composite {

	interface MainUiBinder extends UiBinder<Widget, Main> {
	}

	private static MainUiBinder uiBinder = GWT.create(MainUiBinder.class);

	@UiField
	PaperDrawerPanel drawerPanel;

	@UiField
	PaperDialog addItemDialog;

	@UiField
	HTMLPanel content;

	// @UiField
	// PaperIconButton menu;

	@UiField
	PaperInput titleInput;

	@UiField
	PaperTextarea descriptionInput;

	// @UiField
	// PaperButton confirmAddButton;

	// @UiField
	// protected PaperFab addButton;

	/**
	 * Because this class has a default constructor, it can be used as a binder
	 * template. In other words, it can be used in other *.ui.xml files as follows:
	 * <ui:UiBinder xmlns:ui="urn:ui:com.google.gwt.uibinder" xmlns:g=
	 * "urn:import:**user's package**">
	 * <g:**UserClassName**>Hello!</g:**UserClassName> </ui:UiBinder> Note that
	 * depending on the widget that is used, it may be necessary to implement
	 * HasHTML instead of HasText.
	 */
	public Main() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("addButton")
	protected void onAddButtonClick(ClickEvent e) {
		addItemDialog.open();
	}

	@UiHandler("confirmAddButton")
	protected void onConfirmAddButtonClick(ClickEvent e) {
		if (!titleInput.getValue().isEmpty()) {
			addItem(titleInput.getValue(), descriptionInput.getValue());
			// clear text fields
			titleInput.setValue("");
			descriptionInput.setValue("");
		}
	}

	private void addItem(String title, String description) {
		Item item = new Item();
		item.setTitle(title);
		item.setDescription(description);
		content.add(item);
	}
}
