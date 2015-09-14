/**
 * 
 */
package com.rnb2.gwt1.client.widgets.window;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rnb2.gwt1.client.Mainwidget2;
import com.rnb2.gwt1.client.ManageService;
import com.rnb2.gwt1.client.ManageServiceAsync;
import com.rnb2.gwt1.client.messages.MyMessages;
import com.rnb2.gwt1.client.widgets.LabelPanel;
import com.rnb2.gwt1.data.pm.proxy.ApplicationProxy;
import com.rnb2.gwt1.data.pm.proxy.ApplicationProxyFull;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.DualListField;
import com.sencha.gxt.widget.core.client.form.DualListField.Mode;
import com.sencha.gxt.widget.core.client.form.validator.EmptyValidator;
import com.sencha.gxt.widget.core.client.info.Info;

/**
 *  ƒобавление приложений пользователю
 * @author budukh-rn
 *
 */
public class WindowUserPmAddApp extends Window {
	
	private final ManageServiceAsync manageService = GWT
			.create(ManageService.class);
	protected ListStore<ApplicationProxy> store2;
	private ListStore<ApplicationProxy> store1;
	private MyMessages messages;

	interface ApplicationProperties extends PropertyAccess<ApplicationProxy> {
		@Path("id")
		ModelKeyProvider<ApplicationProxy> key();

		@Path("fullName")
		ValueProvider<ApplicationProxy, String> fullName();
	}

	/**
	 *  ƒобавление приложений пользователю
	 * @author budukh-rn
	 *
	 */	
	public WindowUserPmAddApp(final String login, final String shortname, MyMessages messages, final String serverName) {
		this.messages = messages;
		
		setModal(true);
		setBlinkModal(true);
		setHeadingText(messages.application());
		setBodyStyle("padding: 5px");
		setWidth(500);
		setHeight(400);
		
		VerticalLayoutContainer p = new VerticalLayoutContainer();
		add(p);
		
		ApplicationProperties props = GWT.create(ApplicationProperties.class);
		store1 = new ListStore<ApplicationProxy>(props.key());
		store2 = new ListStore<ApplicationProxy>(props.key());
		
		manageService.getApplicationPmFull(login, serverName, callbackGetApplication());
		
		 
		final DualListField<ApplicationProxy, String> dualList = new DualListField<ApplicationProxy, String>(store1, store2, props.fullName(), new TextCell());
	    dualList.addValidator(new EmptyValidator<List<ApplicationProxy>>());
	    dualList.setEnableDnd(true);
	    dualList.setMode(Mode.INSERT);
	  
	      
	    p.add(new LabelPanel(messages.allApps()+":", messages.useraApps()+":", 170));
	    
	    p.add(dualList, new VerticalLayoutData(1, 1));

		
		
		TextButton bClose = new TextButton(messages.close());
		bClose.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				hide();
			}
		});
		TextButton bSave = new TextButton(messages.save());
		bSave.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				
			try{
				
				List<ApplicationProxy> list = new ArrayList<ApplicationProxy>(store2.getAll());
				manageService.addUserApplication(login, shortname, list, serverName, callbackSave());
				
				
			}catch(Exception e){
				new MessageBox("getModifiedRecords Exception", e.fillInStackTrace().toString())
				.show();
			}
				
			}
		});
		bSave.setEnabled(false);
		
		addButton(bSave);
		addButton(bClose);
	}


	private AsyncCallback<ApplicationProxyFull> callbackGetApplication() {
		
		return new AsyncCallback<ApplicationProxyFull>() {
			
			@Override
			public void onSuccess(ApplicationProxyFull result) {
				store1.clear();
				store1.addAll(result.getList1());
				
				store2.clear();
				store2.addAll(result.getList2());
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				new MessageBox("Can't getList", caught.getMessage())
				.show();
				
			}
		};
	}


	protected AsyncCallback<String> callbackSave() {
		return new AsyncCallback<String>() {
			final Mainwidget2 mainwidget2 = Mainwidget2.getInstance();
			
			@Override
			public void onSuccess(String result) {
				mainwidget2.handlerFindUsersPm();
				Info.display("", messages.recordAdded());
				hide();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				
			}
		};
	}
}
