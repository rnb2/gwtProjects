/**
 * 
 */
package com.rnb.plategka.client.windows;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HTML;
import com.rnb.plategka.client.messages.MyMessages;
import com.rnb.plategka.client.xtemplates.AboutHtml;
import com.sencha.gxt.fx.client.FxElement;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.BeforeHideEvent.BeforeHideHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

/**
 * @author budukh-rn
 * 09 апр. 2015 г.	
 *
 */
public class About extends Window{

	
	private MyMessages messages;
	private FxElement fxElement;
	/**
	 * 
	 */
	public About() {
	}

	@Override
	public HandlerRegistration addBeforeHideHandler(BeforeHideHandler handler) {
		fxElement.fadeToggle();
		return super.addBeforeHideHandler(handler);
	}
	/**
	 * 
	 */
	public About(MyMessages messages) {
		this.messages = messages;
		init();
	}

	private void init() {
		setModal(true);
		setBlinkModal(true);
		setHeadingText(messages.about());
		
		TextButton bClose = new TextButton(messages.close());
		bClose.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				hide();
			}
		});
		
		
		
		/*Text box = new TextBox();
		box.setText("R.N.B.");
		box.setEnabled(false);
		box.setAlignment(TextAlignment.CENTER);*/
		
		AboutHtml aboutHtml = GWT.create(AboutHtml.class);
		HtmlLayoutContainer htmlLayoutContainer = new HtmlLayoutContainer(aboutHtml.getTemplate());
		
		//HTML html = new HTML("<div align=\"center\"></br>&copy;&nbsp;R.N.B., 2015</br><span><b>budukh.rn@gmail.com</b></span></div>");
		//VerticalLayoutContainer container = new VerticalLayoutContainer();
		//container.add(html, new VerticalLayoutData(1, 1));
		

		FramedPanel panel = new FramedPanel();
	    panel.setHeaderVisible(false);
	    panel.setWidth(350);
	   // panel.setBodyStyle("background: none; padding: 15px");
	  
	    //panel.setWidget(container);
	    panel.setWidget(htmlLayoutContainer);
	    panel.addButton(bClose);
	    panel.setButtonAlign(BoxLayoutPack.CENTER);
		
		add(panel);
		
		fxElement = this.getElement().<FxElement> cast();
		
		fxElement.fadeToggle();
	}

}
