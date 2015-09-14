/**
 * 
 */
package com.rnb2.gwt1.client.widgets;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rnb2.gwt1.client.ManageService;
import com.rnb2.gwt1.client.ManageServiceAsync;
import com.rnb2.gwt1.client.messages.MyMessages;
import com.rnb2.gwt1.data.pm.proxy.PermissionProxy;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.TabItemConfig;
import com.sencha.gxt.widget.core.client.TabPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

/**
 * Отображение Ролей для приложения
 * 
 * @author budukh-rn
 * 
 */
public class WindowPermission extends Window {

	private final ManageServiceAsync manageService = GWT
			.create(ManageService.class);
	private List<PermissionProxy> result2 = new ArrayList<PermissionProxy>();
	private TabPanel panel;
	private ListView<PermissionProxy, String> list1;

	public WindowPermission() {
	}

	interface PermisionProperties extends PropertyAccess<PermissionProxy> {
		@Path("id")
		ModelKeyProvider<PermissionProxy> key();

		@Path("fullName")
		ValueProvider<PermissionProxy, String> fullName();
	}

	/**
	 * Отображение Ролей для приложения
	 * 
	 * @param login
	 * @param shortname
	 * @param messages
	 */
	public WindowPermission(String login, String shortname, MyMessages messages, String serverName) {

		setPixelSize(500, 300);
		setModal(true);
		setBlinkModal(true);
		setHeadingText(messages.permisions() + " " + login);

		panel = new TabPanel();
		panel.setBorders(false);

		manageService.getApplicationPmPermission(login, shortname, serverName,
				callbackGetPermisions());

		PermisionProperties props = GWT.create(PermisionProperties.class);
		ListStore<PermissionProxy> store = new ListStore<PermissionProxy>(
				props.key());
		store.addAll(result2);
		list1 = new ListView<PermissionProxy, String>(store, props.fullName());
		panel.add(list1, new TabItemConfig(messages.permisionsFor() + " "
				+ shortname));

		add(panel);
		TextButton b = new TextButton(messages.close());
		b.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				hide();
			}
		});
		addButton(b);
	}

	private AsyncCallback<List<PermissionProxy>> callbackGetPermisions() {
		return new AsyncCallback<List<PermissionProxy>>() {

			@Override
			public void onSuccess(List<PermissionProxy> result) {
				list1.getStore().clear();
				list1.getStore().addAll(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				new MessageBox("show permision", caught.getLocalizedMessage())
						.show();
			}
		};
	}

}
