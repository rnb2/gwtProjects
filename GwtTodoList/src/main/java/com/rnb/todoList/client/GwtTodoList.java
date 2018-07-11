package com.rnb.todoList.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.vaadin.polymer.Polymer;
import com.vaadin.polymer.elemental.Function;
import com.vaadin.polymer.paper.widget.PaperButton;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GwtTodoList implements EntryPoint {
	public void onModuleLoad() {
	    // Use Widget API to Create a <paper-button>
	   /* PaperButton button = new PaperButton("Press me!");
	    button.setRaised(true);
	    RootPanel.get().add(button);*/

	 // We have to load icon sets before run application
	    Polymer.importHref("iron-icons/iron-icons.html", new Function() {
	        public Object call(Object arg) {
	            // The app is executed when all imports succeed.
	            startApplication();
	            return null;
	        }
	    });
	    
	  }
	
	private void startApplication() {
		RootPanel.get().add(new Main());
	}
}
