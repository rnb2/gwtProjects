package com.rnb.plategka.client;


import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.rnb.plategka.client.images.Images;
import com.rnb.plategka.client.messages.MyMessages;
import com.rnb.plategka.client.widgets.TableConstants;
import com.rnb.plategka.client.widgets.TablePayments;
import com.rnb.plategka.client.windows.About;
import com.rnb.plategka.data.ConstantsProxy;
import com.sencha.gxt.core.client.resources.ThemeStyles;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuBar;
import com.sencha.gxt.widget.core.client.menu.MenuBarItem;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

/**
 * 
 * @author budukh-rn
 * 02 апр. 2015 г.	
 *
 */
public class Plategka_hrd implements EntryPoint, IsWidget {

	private static final Images Image = Images.INSTANCE;
	private static final String ID_MENU_PAYMENTS = "id_payments";
	private static final String ID_MENU_CONSTANTS = "id_constants";
	private static final String ID_MENU_ABOUT = "id_about";
	private static final String ID_MENU_ABOUT2 = "id_about2";
	private final ConstantServiceAsync serviceConstant = GWT.create(ConstantService.class);
	private final UsersServiceAsync usersServiceAsync = GWT.create(UsersService.class);


	private SimpleContainer widget;
	private BorderLayoutContainer borderLayoutContainer;
	private ContentPanel center;
	private final MyMessages messages = GWT.create(MyMessages.class);
	protected List<Integer> yearList;
	private Menu menuData;
	private Menu menuSettings;
	private MenuItem menuItemLogOut;
	  

	public void onModuleLoad() {
	    Viewport viewport = new Viewport();
	    viewport.add(asWidget());
	 
	    RootPanel.get().add(viewport);
	}
	
	@Override
	public Widget asWidget() {

	if(widget == null){
		
		ContentPanel north = new ContentPanel();
		center = new ContentPanel();
		center.setHeaderVisible(false);
		
		
		
		
		north.setHeadingText(messages.application());
		/*ToolBar toolbar = new ToolBar();
	      final ToggleButton button = new ToggleButton("Toggle Size");
	      button.addSelectHandler(new SelectHandler() {
	        @Override
	        public void onSelect(SelectEvent event) {
	          if (button.getValue()) {
	        	  System.out.println("1");
	          } else {
	        	  System.out.println("2");
	          }
	        }
	      });
	    //toolbar.add(button);
		toolbar.add(WidgetUtils.createButton(messages.settings(), handlerSettings()));
		toolbar.add(WidgetUtils.createButton(messages.payments(), handlerPayments()));
	    north.add(toolbar);*/

		BorderLayoutData northData = new BorderLayoutData(50);
		northData.setMargins(new Margins(1, 1, 1, 1));
	    northData.setCollapsible(false);
	    northData.setSplit(true);
	   
				
		MarginData centerData = new MarginData();
		centerData.setMargins(new Margins(10));
				
				
		//menuBar
		MenuItem menuItemConstants = new MenuItem(messages.constants());
		MenuItem menuItemPayments = new MenuItem(messages.payments());
		MenuItem menuItemAbout = new MenuItem("...");
		
		
		menuItemLogOut = new MenuItem("out");
		
		usersServiceAsync.getUserName(callbackUser());

		menuItemConstants.setId(ID_MENU_CONSTANTS);
		menuItemPayments.setId(ID_MENU_PAYMENTS);
		menuItemAbout.setId(ID_MENU_ABOUT);
		menuItemLogOut.setId(ID_MENU_ABOUT2);

		menuItemConstants.setIcon(Image.script());
		menuItemPayments.setIcon(Image.table());
		menuItemAbout.setIcon(Image.about());
		
		menuSettings = new Menu();
	      menuSettings.addSelectionHandler(handlerMenu());
	      menuSettings.add(menuItemConstants);
	    
	    menuData = new Menu();
	      menuData.addSelectionHandler(handlerMenu());
	      menuData.add(menuItemPayments);

	    Menu menuAbout = new Menu();
	    menuAbout.addSelectionHandler(handlerMenu());
	    menuAbout.add(menuItemAbout);
	    menuAbout.add(menuItemLogOut);
	    
	      
	    MenuBarItem itemSettings = new MenuBarItem(messages.settings(), menuSettings);
	    MenuBarItem itemData = new MenuBarItem(messages.datas(), menuData);
		MenuBarItem itemAbout = new MenuBarItem(messages.about(), menuAbout);
		
		
		
		MenuBar menuBar = new MenuBar();
	     	menuBar.addStyleName(ThemeStyles.get().style().borderBottom());
			menuBar.add(itemSettings);
	    	menuBar.add(itemData);
	    	menuBar.add(itemAbout);
		//--
				
		borderLayoutContainer = new BorderLayoutContainer();
	    borderLayoutContainer.setBorders(true);
	    //borderLayoutContainer.setNorthWidget(north, northData);
	    borderLayoutContainer.setNorthWidget(menuBar, northData);
	    borderLayoutContainer.setCenterWidget(center, centerData);
	    
	    widget = new SimpleContainer();
	    widget.add(borderLayoutContainer);
	}   
		return widget;
	}


	private AsyncCallback<String> callbackUser() {
	
		return new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				 if(result == null){
					menuSettings.setEnabled(false);
					menuData.setEnabled(false);
					
					return;
				 }
			 
				/*TextBox child = new TextBox();
				child.setText(result);
				child.setWidth("350");
				center.add(child);*/
			
				menuItemLogOut.setText("LogOut-" + result);
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Info.display("Error callbackUser:", caught.getLocalizedMessage());
				menuSettings.setEnabled(false);
				menuData.setEnabled(false);
			}	
		};
	}

	/**
	 * menu handler
	 * @return
	 */
	private SelectionHandler<Item> handlerMenu() {
		return new SelectionHandler<Item>() {
			@Override
			public void onSelection(SelectionEvent<Item> event) {
				if (event.getSelectedItem() instanceof MenuItem) {
					switch (event.getSelectedItem().getId()) {
					case ID_MENU_CONSTANTS:
						serviceConstant.getConstantsProxy(callbackGetConstants());
						break;

					case ID_MENU_PAYMENTS:
						
						initYearlist();
												
						break;

					case ID_MENU_ABOUT:
							
							About about = new About(messages);
							about.show();
							
						break;

					case ID_MENU_ABOUT2:
						
						usersServiceAsync.logout(callbackLogOut());
						
						break;

					default:
						break;
					}
				}
				
			}
		};
	}

	protected AsyncCallback<String> callbackLogOut() {
		
		return new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				Info.display("callbackLogOut result=", result);
				
				if(!result.isEmpty())
					redirect(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Info.display("Error callbackLogOut", caught.getLocalizedMessage());
			}
		};
	}

	private native void redirect(String url)/*-{
	  var win = $wnd;
	  while (win.parent != null && win.parent != win)
	    win = win.parent;
	  win.location = url;
	}-*/;
	
	protected void initYearlist() {
		yearList = new ArrayList<Integer>();
		serviceConstant.getYears(callbackYearList());
	}

	private AsyncCallback<List<Integer>> callbackYearList() {
		return new AsyncCallback<List<Integer>>() {
			
			@Override
			public void onSuccess(List<Integer> result) {
				yearList = result;	
				
				if(yearList == null || yearList.isEmpty()){
					Info.display("Error callbackYearList", "yearList=" + yearList);
					return;
				}	
				showPaymentsForm(yearList);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}
		};
	}


	protected void showPaymentsForm(List<Integer> yearList) {
		ContentPanel container = new ContentPanel();	
				
		TablePayments tablePayments = new TablePayments(yearList, messages);
		
		container.add(tablePayments);
		container.forceLayout();
		
		addToCenter(container);
	}


	protected AsyncCallback<List<ConstantsProxy>> callbackGetConstants() {
		return new AsyncCallback<List<ConstantsProxy>>() {
			
			@Override
			public void onSuccess(List<ConstantsProxy> result) {
				showConstantsForm(result);		
			}
			
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				new MessageBox("callbackGetConstants:", " ERROR !!!! cause=" + caught.getLocalizedMessage()).show();				
			}
		};
	}

	private void showConstantsForm(List<ConstantsProxy> result) {
		ContentPanel container = new ContentPanel();
		BorderLayoutData inWest = new BorderLayoutData();
		inWest.setSplit(true);
		inWest.setMargins(new Margins(0, 5, 0, 5));
		
		BorderLayoutData inEast = new BorderLayoutData();
		inEast.setMargins(new Margins(0, 5, 0, 0));
		inEast.setSplit(true);

		//FormPanel constantForm = new Config(messages);
		TableConstants tableConstants = new TableConstants(result, messages);
		
		//container.add(constantForm, inEast);
		container.add(tableConstants);
		container.forceLayout();
		
		addToCenter(container);
	}
	
	 private void addToCenter(ContentPanel p) {  
		center.clear();
	     if(p != null){
	    	 center.add(p);
	     }
		
	 }
	 
	 

	
}
