package com.rnb2.gwt1.client.widgets.window;

import com.google.gwt.user.client.ui.Label;
import com.rnb2.gwt1.client.messages.MyMessages;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

/**
 * Стандартное диалоговое окно 
 * @author budukh-rn
 *
 */
public class DialogDelete2 extends Window {


	public DialogDelete2(String title, String body, MyMessages messages, SelectHandler handler){
		
		setModal(true);
		setBlinkModal(true);
		setHeadingText(title);
		setBodyStyle("padding: 5px");
		setWidth(300);
		
		VerticalLayoutContainer p = new VerticalLayoutContainer();
		add(p);
		
		Label child = new Label(body);
		child.setStyleName("textBold");
		p.add(child);
		
		TextButton bCancel = new TextButton(messages.cancel());
		bCancel.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				hide();
			}
		});
		TextButton bAction = new TextButton(messages.delete());
		bAction.addSelectHandler(handler);
				
		addButton(bAction);
		addButton(bCancel);
	}

}
