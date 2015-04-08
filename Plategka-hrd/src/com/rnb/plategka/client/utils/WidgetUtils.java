package com.rnb.plategka.client.utils;


import com.sencha.gxt.core.client.util.Padding;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer.VBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

/**
 * 
 * @author budukh-rn
 * 08 апр. 2015 г.	
 *
 */
public final class WidgetUtils {
	
	public static TextButton createButton(String name, SelectHandler handler ) {
		TextButton button = new TextButton(name);
		button.addSelectHandler(handler);		
		return button;
	}
	
	public static VBoxLayoutContainer stretchVBoxLayout(int padding){
		VBoxLayoutContainer layout = new VBoxLayoutContainer(VBoxLayoutAlign.STRETCH);
	    layout.setPadding(new Padding(padding));  
	    return layout;
	}

}
