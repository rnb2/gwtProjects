/**
 * 
 */
package com.rnb2.gwt1.client.widgets.window;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rnb2.gwt1.client.ManageService;
import com.rnb2.gwt1.client.ManageServiceAsync;
import com.rnb2.gwt1.client.images.Images;
import com.rnb2.gwt1.client.messages.MyMessages;
import com.rnb2.gwt1.data.pm.Application;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;

/**
 *  Добавление нового приложения
 * @author budukh-rn
 *
 */
public class WindowUserPmAddAppNew extends Window {

	private final ManageServiceAsync manageService = GWT
			.create(ManageService.class);
	private MyMessages messages;

	
	/**
	 *  Добавление нового приложения
	 */
	public WindowUserPmAddAppNew() {
		
	}

	/**
	 *  Добавление нового приложения
	 */
	public WindowUserPmAddAppNew(MyMessages messages, final String serverName) {
		setModal(true);
		setBlinkModal(true);
		setHeadingText(messages.ApplicationAdd());
		setBodyStyle("padding: 5px");
		setWidth(350);
		
		VerticalLayoutContainer p = new VerticalLayoutContainer();
		add(p);
		
	    final TextField shortName = new TextField();
	    shortName.setAllowBlank(false);
	    shortName.setEmptyText(messages.inputValue());
	    p.add(new FieldLabel(shortName, messages.nameTask()), new VerticalLayoutData(1, -1));
	    
	    final TextField fullName = new TextField();
	    fullName.setAllowBlank(true);
	    p.add(new FieldLabel(fullName, messages.nameApp()), new VerticalLayoutData(1, -1));
	    
	    
	    final TextField description = new TextField();
	    description.setAllowBlank(true);
	    p.add(new FieldLabel(description, messages.description()), new VerticalLayoutData(1, -1));
	    
	    final TextField programmer = new TextField();
	    programmer.setAllowBlank(true);
	    p.add(new FieldLabel(programmer, messages.programmer()), new VerticalLayoutData(1, -1));
	    
	    final TextField architect = new TextField();
	    architect.setAllowBlank(true);
	    p.add(new FieldLabel(architect, messages.architect()), new VerticalLayoutData(1, -1));

		
		
		TextButton bClose = new TextButton(messages.close());
		bClose.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				hide();
			}
		});
		TextButton bSave = new TextButton(messages.save());
		bSave.setIcon(Images.INSTANCE.save());
		bSave.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
					Application app = new Application();
					app.setShortName(shortName.getCurrentValue());
					app.setFullName(fullName.getCurrentValue());
					app.setDescription(description.getCurrentValue());
					app.setProgrammer(programmer.getCurrentValue());
					app.setArchitect(architect.getCurrentValue());

					manageService.addApplicaion(app, serverName, callback());
			
				
				
			}
		});
		addButton(bSave);
		addButton(bClose);
	}

	protected AsyncCallback<Void> callback() {
		return new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				Info.display("", messages.ApplicationAdded());
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
		};
	}
}
