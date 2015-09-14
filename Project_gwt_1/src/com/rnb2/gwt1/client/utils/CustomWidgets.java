/**
 * 
 */
package com.rnb2.gwt1.client.utils;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;

/**
 * @author budukh-rn
 *
 */
public final class CustomWidgets {

	/**
	 * 
	 */
	public CustomWidgets() {
	}
	
	public static void createAlert(String title, String message) {
	    AlertMessageBox messageBox = new AlertMessageBox(title, message);
	    messageBox.show();
	  }

	
	public static void showWaitCursor() {
	    DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor", "wait");
	}
	 
	public static void showDefaultCursor() {
	    DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor", "default");
	}
	
	
	public static DialogBox alertWidget(final String header, final String content) {
        final DialogBox box = new DialogBox();
        final VerticalPanel panel = new VerticalPanel();
        box.setText(header);
        panel.add(new Label(content));
        panel.setSpacing(5);
        final Button buttonClose = new Button("Close",new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                box.hide();
            }
        });

        buttonClose.setWidth("90px");
        panel.add(buttonClose);
        panel.setCellHorizontalAlignment(buttonClose, HasAlignment.ALIGN_RIGHT);
        box.add(panel);
        box.center();
        return box;
    }

}
