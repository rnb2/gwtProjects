package com.rnb2.gwt1.client.widgets.window;


import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.rnb2.gwt1.client.Mainwidget2;
import com.rnb2.gwt1.client.ManageService;
import com.rnb2.gwt1.client.ManageServiceAsync;
import com.rnb2.gwt1.client.images.Images;
import com.rnb2.gwt1.client.messages.MyMessages;
import com.rnb2.gwt1.client.utils.Constants;
import com.rnb2.gwt1.client.utils.CustomWidgets;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;

/**
 * Авторизация пользователей в AD
 * @author budukh-rn
 * 09.01.2014
 *
 */
public class WindowLogin extends Window {

	private static final String COM_RNB2_FILTER_BASE_KEY = "com.rnb2.filterBase.key";
	private static final String PREFIX_METINVESTHOLDING_COM = "@metinvestholding.com";
	private final String ad_Azovstal = "AZ-DC1.corp.azovstal.ua";
	private final String ad_MIH = "DC03-ADDS-02.metinvest.ua";//"Dc01-adds-01.metinvest.ua";

	private final String filterBaseAzovstal = "DC=corp,DC=azovstal,DC=ua";
	private final String filterBaseMIH = "DC=Metinvest,DC=UA"; //"OU=AZS,OU=AZS,DC=Metinvest,DC=UA";

	
	private final ManageServiceAsync manageService = GWT
			.create(ManageService.class);
	private final MyMessages messages = GWT.create(MyMessages.class);
	private boolean isOkclicked = false;
	
	private String userName = null;
	private String password = null;
 
	private Map<String,String> enviromentTable = null;
		
	/**
	 * Авторизация пользователей в AD
	 * @author budukh-rn
	 * 09.01.2014
	 * @param entryPoint 
	 *
	 */
	public WindowLogin() {
 
		setModal(true);
		setBlinkModal(true);
		setHeadingText(messages.authorization());
		setBodyStyle("padding: 5px");
		setWidth(350);
		
		
		String loginNameS = "";

		
		VerticalLayoutContainer p = new VerticalLayoutContainer();
		add(p);
				 
	    final TextField loginName = new TextField();
	    loginName.setAllowBlank(false);
	    loginName.setEmptyText(messages.inputValue());
	    loginName.setValue(loginNameS);
	    p.add(new FieldLabel(loginName, messages.loginName()), new VerticalLayoutData(1, -1));
	    
	    final PasswordField textFieldPass = new PasswordField();
	    textFieldPass.setAllowBlank(false);
	    p.add(new FieldLabel(textFieldPass, messages.passwordLabel()), new VerticalLayoutData(1, -1));
	      
	   
		TextButton bClose = new TextButton(messages.cancel());
		bClose.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				isOkclicked = false;
				hide();
			}
		});
		

		
		TextButton bFind = new TextButton("Ok");
		bFind.addSelectHandler(new SelectHandler() {


			@Override
			public void onSelect(SelectEvent event) {
				
				String login = null;
				if(loginName.getCurrentValue() != null && !loginName.getCurrentValue().isEmpty() && loginName.getCurrentValue().trim().length() > 3){
					login = loginName.getCurrentValue();
				}
				
				if(login == null){
					Info.display(messages.authorization(), messages.inputValue());
					return;
				}
				
				if(textFieldPass.getCurrentValue() == null || textFieldPass.getCurrentValue().isEmpty()){
					Info.display(messages.authorization(), messages.inputPassword());
					return;
				}
				
				CustomWidgets.showWaitCursor(); 
				
				enviromentTable = getEnviromentTable(login, textFieldPass.getCurrentValue());
				
				manageService.autorizationByLoginName(enviromentTable, login, Constants.server_name_jboss, callbackAutorizationByLoginName());
			}
		});
		bFind.setIcon(Images.INSTANCE.key());
		
		addButton(bFind);
		addButton(bClose);
	}

	private final Map<String, String> getEnviromentTable(String login, String password) {
			Map<String, String> environment = new HashMap<String, String>();
	        environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			environment.put(Context.PROVIDER_URL, "ldap://" + ad_MIH + ":389");//10.11.11.3
	        environment.put(Context.SECURITY_AUTHENTICATION, "simple");
	        environment.put(Context.SECURITY_PRINCIPAL, login + PREFIX_METINVESTHOLDING_COM);
	        environment.put(Context.SECURITY_CREDENTIALS, password);
	        environment.put(COM_RNB2_FILTER_BASE_KEY, filterBaseMIH);
	     return environment;
	}
		


	protected AsyncCallback<String> callbackAutorizationByLoginName() {
		return new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				isOkclicked = true;
				
				if(result == null){
					CustomWidgets.showDefaultCursor();
					userName = null;
					Info.display(messages.authorization(), messages.userNotFound());					
					return;
				}
				
				userName = result;
				
				if(userName != null){					
					CustomWidgets.showDefaultCursor();
					Info.display(messages.authorization(), messages.authorizationOk());
					hide();
					
					String loginName = userName.substring(userName.indexOf("(")+1, userName.indexOf(")"));
					Mainwidget2 con = new Mainwidget2(enviromentTable, userName, loginName);					    	    
					Viewport viewport = new Viewport();
					viewport.add(con);							
					RootPanel.get().add(viewport);
				}					
			}
			
			@Override
			public void onFailure(Throwable caught) {
				CustomWidgets.showDefaultCursor();
				Info.display(messages.authorization(), messages.error() + caught.getLocalizedMessage());
			}
		};
	}

	
	public boolean isOkclicked() {
		return isOkclicked;
	}
	
}
