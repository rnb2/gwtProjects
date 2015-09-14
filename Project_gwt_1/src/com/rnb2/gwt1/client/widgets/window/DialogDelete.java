package com.rnb2.gwt1.client.widgets.window;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;
import com.rnb2.gwt1.client.messages.MyMessages;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

public class DialogDelete extends Dialog {

	private final MyMessages messages = GWT.create(MyMessages.class);
	
	private int returnedValue = -1;
	
	public DialogDelete(String textToDelete) {
		 setHeadingText(messages.deleteRecord());
		 setPredefinedButtons(PredefinedButton.YES, PredefinedButton.NO);
		 setBodyStyleName("pad-text");
		 add(new Label(textToDelete));
		 getBody().addClassName("pad-text");
		 setHideOnButtonClick(true);
		 setWidth(300);
		 setModal(true);
		 
		 
		 
		/*TextButton bDelete = new TextButton(messages.delete());
		bDelete.setId("YES");
		bDelete.addSelectHandler(new SelectHandler() {
	
			@Override
			public void onSelect(SelectEvent event) {
				returnedValue = 1;
				hide();
			}
		});
		
		TextButton bCancel = new TextButton(messages.cancel());
		bCancel.setId("NO");
		bCancel.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				returnedValue = 0;
				hide();
			}
		});
		
		
		addButton(bDelete);
		addButton(bCancel);*/
				
		show();
	}
	
	
	
	public int getReturnedValue() {
		return returnedValue;
	}
	
	public boolean isOkPressed() {
		//TextButton textButton = getButtonBar().getItemByItemId(PredefinedButton.YES.name());
		//return textButton.getText().equals(PredefinedButton.YES.name());
		return returnedValue == 1;
	}
}
