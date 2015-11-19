package com.rnb2.gwt1.client.widgets.window;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rnb2.gwt1.client.Mainwidget2;
import com.rnb2.gwt1.client.ManageService;
import com.rnb2.gwt1.client.ManageServiceAsync;
import com.rnb2.gwt1.client.messages.MyMessages;
import com.rnb2.gwt1.client.utils.CustomWidgets;
import com.rnb2.gwt1.data.pm.proxy.UserProxy;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.event.SubmitCompleteEvent;
import com.sencha.gxt.widget.core.client.event.SubmitCompleteEvent.SubmitCompleteHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FileUploadField;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.FormPanel.Encoding;
import com.sencha.gxt.widget.core.client.form.FormPanel.Method;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;


/**
 * Настройки импрота пользователей из Excel 
 * @author budukh-rn
 * 22 сент. 2015 г.	
 *
 */
public class WindowUserPmXlsImport extends Window {

	private final ManageServiceAsync manageService = GWT
			.create(ManageService.class);
	private MyMessages messages;
	private boolean isOkclicked = false;
	
	public WindowUserPmXlsImport() {
	}

	/**
	 * Настройки импрота пользователей из Excel 
	 * @author budukh-rn
	 * 22 сент. 2015 г.	
	 *
	 */
	public WindowUserPmXlsImport(String title, final MyMessages messages) {
		this.messages = messages;
			
		setModal(true);
		setBlinkModal(true);
		setHeadingText(title);
		setBodyStyle("padding: 5px");
		setWidth(350);
		
	
		VerticalLayoutContainer p = new VerticalLayoutContainer();
		 
	    final TextField column1 = new TextField();
	    column1.setAllowBlank(false);
	    column1.setEmptyText(messages.inputValue());
	    column1.setValue("0");

	    final TextField column2 = new TextField();
	    column2.setAllowBlank(false);
	    column2.setEmptyText(messages.inputValue());
	    column2.setValue("1");
	    
	    final TextField rowBegin = new TextField();
	    rowBegin.setEmptyText(messages.inputValue());
	    rowBegin.setAllowBlank(false);
	    rowBegin.setValue("2");
	    
	    
	    final TextField rowEnd = new TextField();
	    rowEnd.setAllowBlank(false);
	    rowEnd.setValue("100");
	    
	    final FileUploadField file = new FileUploadField();
	    file.setName("uploadedfile");
	    file.setAllowBlank(false);
	 
	       	  
	    p.add(new FieldLabel(file, messages.file()), new VerticalLayoutData(1, -1));
	    p.add(new FieldLabel(column1, messages.loginNameOld()), new VerticalLayoutData(1, -1));
	    p.add(new FieldLabel(column2, messages.loginNameNew()), new VerticalLayoutData(1, -1));
     	p.add(new FieldLabel(rowBegin, messages.rowBegin()), new VerticalLayoutData(1, -1));
	    p.add(new FieldLabel(rowEnd, messages.rowEnd()), new VerticalLayoutData(1, -1));
		
	    final FormPanel form = new FormPanel();
	    //form.setAction("/project_gwt_1/fileUpload");
	    form.setAction(GWT.getModuleBaseURL() + "fileUpload");
	    form.setEncoding(Encoding.MULTIPART);
	    form.setMethod(Method.POST);
	    form.add(p, new MarginData(5));
	    
	       
		
		TextButton bClose = new TextButton(messages.close());
		bClose.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				isOkclicked = false;
				hide();
			}
		});
		final TextButton bSave = new TextButton(messages.save());
		bSave.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				form.submit();
				isOkclicked = true;
				bSave.setEnabled(false);
				CustomWidgets.showWaitCursor();
			}
		});
		
		form.addSubmitCompleteHandler(new SubmitCompleteHandler() {
		      public void onSubmitComplete(SubmitCompleteEvent event) {
		    	  Integer columnIndexLoginNameOld = Integer.valueOf(column1.getCurrentValue()) - 1;
					if(columnIndexLoginNameOld < 0)
						columnIndexLoginNameOld = 0;

					String resultHtml = event.getResults();
					Info.display("Upload Response", resultHtml);
				
					manageService.readFileXls("", Integer.valueOf(rowBegin.getCurrentValue()), Integer.valueOf(rowEnd.getCurrentValue()), columnIndexLoginNameOld, Integer.valueOf(column2.getCurrentValue()), callbackRead());
				
		        }
		});
		
		
		add(form);
		addButton(bSave);
		addButton(bClose);
		
	}

	protected AsyncCallback<List<UserProxy>> callbackRead() {
		return new AsyncCallback<List<UserProxy>>() {
			
			@Override
			public void onSuccess(List<UserProxy> result) {
				
				CustomWidgets.showDefaultCursor();
				hide();	
				//Info.display(messages.titleUsersXlsImport(), messages.foundUsers() +": " +result.size());
				Mainwidget2 mainwidget2 = Mainwidget2.getInstance();
				mainwidget2.addUsersXlsDetail(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				CustomWidgets.showDefaultCursor();
				CustomWidgets.createAlert(messages.titleUsersXlsImport(), caught.getLocalizedMessage());
				
			}
		};
	}

	
	public boolean isOkclicked() {
		return isOkclicked;
	}


}
