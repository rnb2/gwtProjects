/**
 * 
 */
package com.rnb2.gwt1.client.widgets;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.rnb2.gwt1.client.images.Images;
import com.rnb2.gwt1.client.messages.MyMessages;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.TabItemConfig;
import com.sencha.gxt.widget.core.client.TabPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;

/**
 * @author budukh-rn
 * 
 */
public class TestTabPanel implements IsWidget {

	private ContentPanel root;
	private TabPanel panel;
	private MyMessages messages;
	private VerticalLayoutContainer containerStation;
	private VerticalLayoutContainer containerSpr;
	private VerticalLayoutContainer containerForm;
	private VerticalLayoutContainer containerDepartments;

	public TestTabPanel() {
	}

	public TestTabPanel(MyMessages messages) {
		this.messages = messages;

		containerStation = new VerticalLayoutContainer();
		containerStation.setId("containerStation");
		containerSpr = new VerticalLayoutContainer();
		containerSpr.setId("containerSpr");
		containerForm = new VerticalLayoutContainer();
		containerForm.setId("containerForm");
		containerDepartments = new VerticalLayoutContainer();
		containerDepartments.setId("containerDepartments");
	}

	@Override
	public Widget asWidget() {

		if(root == null){
			root = new ContentPanel();
			root.setId("TestTabPanel");
		    root.setBorders(true);
		    root.setHeaderVisible(true);
		   			
			panel = new TabPanel();
			panel.setAnimScroll(true);
			panel.setTabScroll(true);
			
	
			panel.add(containerStation, messages.station());
	
			panel.add(containerSpr, messages.accessSpr());
	
			TabItemConfig config = new TabItemConfig(messages.accessForm());
			config.setIcon(Images.INSTANCE.table());
			panel.add(containerForm, config);
			
			panel.add(containerDepartments, messages.departments() + " " + messages.egd());
			
			
			panel.setActiveWidget(panel.getWidget(0));
			
			panel.addSelectionHandler(new SelectionHandler<Widget>() {
				
				@Override
				public void onSelection(SelectionEvent<Widget> event) {
					
					
					
					Widget selectedItem = event.getSelectedItem();
					selectedItem.getElement().getId();
					
					//VerticalLayoutContainer c =  (VerticalLayoutContainer) selectedItem;
					//c.getItemByItemId(Constants.GRID_RAILWAY_GROUP_PROXY);
					//Window.alert("selected: " + selectedItem.getElement().getId() + " panel.getTabIndex(): " + panel.getTabIndex());
					
				}
			});
			
			root.setWidget(panel);
		}

		return panel;
	}

	public void clearContainerStation() {
		if (containerStation != null)
			containerStation.clear();
	}

	public VerticalLayoutContainer getContainerStation() {
		return containerStation;
	}

	public VerticalLayoutContainer getContainerSpr() {
		return containerSpr;
	}

	public VerticalLayoutContainer getContainerForm() {
		return containerForm;
	}
	
	public VerticalLayoutContainer getContainerDepartments() {
		return containerDepartments;
	}

}
