/**
 * 
 */
package com.rnb2.gwt1.client.widgets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.rnb2.gwt1.client.images.Images;
import com.rnb2.gwt1.client.messages.MyMessages;
import com.rnb2.gwt1.client.model.UserPmProperties;
import com.rnb2.gwt1.client.utils.Constants;
import com.rnb2.gwt1.client.widgets.window.WindowUserPmAdd;
import com.rnb2.gwt1.client.widgets.window.WindowUserPmAddCopy;
import com.rnb2.gwt1.data.pm.proxy.ApplicationProxy;
import com.rnb2.gwt1.data.pm.proxy.UserProxy;
import com.rnb2.gwt1.shared.ServerProxy;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.resources.ThemeStyles;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Status;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.tips.QuickTip;
import com.sencha.gxt.widget.core.client.toolbar.FillToolItem;
import com.sencha.gxt.widget.core.client.toolbar.SeparatorToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

/**
 * Отображение /Добавление/редактирование/удаление пользователей JBOSS
 * @author budukh-rn
 *
 */
public class TableUserPm implements IsWidget {

	private ContentPanel root;
	private final UserPmProperties props = GWT.create(UserPmProperties.class);
	private Collection<? extends UserProxy> items;
	private Status status;
	private UserProxy selectedElement;
	private MyMessages messages;
	private VerticalLayoutContainer container;
	private Grid<UserProxy> grid;
	private HorizontalLayoutContainer containerH;
	private List<ApplicationProxy> applications;
	private ComboBox<ApplicationProxy> combo;
	private TextButton buttonDetail, buttonAcl; 
	private TextButton buttonAdd, buttonEdit, buttonDelete, buttonCopy;
	private String headingText, serverName;
	private boolean isFromAD = false;
	
	
	interface ApplicationsProperties extends PropertyAccess<ApplicationProxy>{
		@Path("id")
		ModelKeyProvider<ApplicationProxy> key();

		@Path("fullName")
		LabelProvider<? super ApplicationProxy> fullName();
	}

	
	/**
	 * 
	 * @param result
	 * @param applications
	 * @param messages2
	 */
	public TableUserPm(List<UserProxy> result, List<ApplicationProxy> applications, MyMessages messages2, boolean isFromAD, String serverName) {
		this.items = result;
		this.messages = messages2;
		this.applications = applications;
		this.isFromAD = isFromAD;
		this.serverName = serverName;
		
		if(isFromAD){
			this.headingText = messages.titleUserAD();
		}else{
			this.headingText =  messages.titleUsers() + " " + this.serverName;
		}

		/**Если компонент использ. вне этого класса, 
		 * например handler к кнопке присваивается в MainWidget2.java через addHandlerButtonDetail
		 * то его(компонент необходимо инициализировать в констр.) */
		buttonDetail = new TextButton(this.messages.detail());
		buttonAcl = new TextButton(this.messages.detailAcl());
		buttonAcl.setIcon(Images.INSTANCE.key());
		containerH = new HorizontalLayoutContainer();
		containerH.setId("ContainerIds");
		
		buttonAdd = new TextButton(messages.add());
	    buttonEdit = new TextButton(messages.edit());
	    buttonDelete = new TextButton(messages.delete());
	    buttonCopy = new TextButton(messages.copy());
	    buttonCopy.setToolTip(messages.copyUser());
	}

	@Override
	public Widget asWidget() {
		
		if(root == null){
			buttonDetail.setIcon(Images.INSTANCE.vcard());
			buttonDetail.setTitle(messages.appDetail());
			root = new ContentPanel();
		    root.setHeadingText(this.headingText);
		    root.setBorders(true);
		    root.addStyleName("margin-10");
		    root.getHeader().setIcon(Images.INSTANCE.table());
		    root.setCollapsible(true);
		 
			List<ColumnConfig<UserProxy, ?>> configList = getConfigColumnList(props, isFromAD);
			 
			ColumnModel<UserProxy> columnModel = new ColumnModel<UserProxy>(configList);
			
			ListStore<UserProxy> listStore = new ListStore<UserProxy>(props.key());
			
			listStore.addAll(items);
			  
			grid = new Grid<UserProxy>(listStore, columnModel);

			grid.getView().setStripeRows(true);
			grid.getView().setAutoFill(true);
			grid.getView().setColumnLines(true);
			grid.setBorders(false);
			grid.setColumnReordering(true);
			grid.setStateful(true);
			grid.setStateId("gridExample");
			
			grid.getSelectionModel().addSelectionHandler(selection);
			grid.setLoadMask(true);
		    
			container = new VerticalLayoutContainer();
			
		    ToolBar toolBar = new ToolBar();
		    toolBar.addStyleName(ThemeStyles.getStyle().borderTop());
		 
		    status = new Status();
		    status.setText(messages.records() + ": " + items.size());
		    status.setWidth(250);
		    toolBar.add(status);
		    toolBar.add(new SeparatorToolItem());
			toolBar.add(buttonDetail);
			
			//combo applications
			 ApplicationsProperties props = GWT.create(ApplicationsProperties.class);
			 ListStore<ApplicationProxy> store = new ListStore<ApplicationProxy>(props.key());
			 store.addAll(applications);
			 
			 combo = new ComboBox<ApplicationProxy>(store, props.fullName());
			 combo.setTriggerAction(TriggerAction.ALL);
			 ApplicationProxy findModelWithKey = combo.getStore().findModelWithKey(Constants.ID_APPLICATION_IDS.toString());
			 combo.setValue(findModelWithKey);
			

			 List<ServerProxy> serversList = new ArrayList<ServerProxy>();
				int i=1;
				for(String server : Constants.serverList){
					serversList.add(new ServerProxy(i, server));
					i++;
				}
					 
							
			 VerticalLayoutData layoutData = new VerticalLayoutData();
			 layoutData.setMargins(new Margins(0, 0, 5, 0));
			 layoutData.setHeight(1);
			 layoutData.setWidth(1);
			 
			
			 toolBar.add(combo);
					 
			 toolBar.add(new FillToolItem());
			 toolBar.add(new SeparatorToolItem());
			 toolBar.add(buttonAcl);
			 toolBar.forceLayout();
		 
		    container.add(toolBar, new VerticalLayoutData(1,-1));
		    container.add(grid, layoutData);
		    
		    
		    ToolBar toolBar2 = new ToolBar();
		    toolBar2.setSpacing(3);
		    
		    buttonAdd.setIcon(Images.INSTANCE.table_add());
		    buttonAdd.addSelectHandler(handlerAdd());
		    
		    buttonEdit.setIcon(Images.INSTANCE.table_edit());
		    buttonEdit.addSelectHandler(handlerEdit());
		    		    
		    buttonDelete.setIcon(Images.INSTANCE.table_row_delete());
		    
		    buttonCopy.setIcon(Images.INSTANCE.user());
		    buttonCopy.addSelectHandler(handlerCopy());
		    
		    toolBar2.add(buttonAdd);
		    toolBar2.add(buttonEdit);
		    toolBar2.add(buttonDelete);
		    toolBar2.add(buttonCopy);

		    buttonEdit.setEnabled(false);
		    buttonDelete.setEnabled(false);
		    buttonCopy.setEnabled(false);
		    
		    container.add(toolBar2, new VerticalLayoutData(1,-1));
		    
		    container.add(containerH);
		    
		    root.setWidget(container);
		  		    
		    new QuickTip(grid);		    
		}		
		return root;
	}
	
	/**
	 * Уд пользователя
	 * @return
	 */
	public void handlerDelete(SelectHandler handler) {
		if(buttonDelete != null)
			buttonDelete.addSelectHandler(handler);
	}
	
	/**
	 * Добавление пользователя
	 * @return
	 */
	private SelectHandler handlerAdd() {
		SelectHandler handler = new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				WindowUserPmAdd user = null;
				if(selectedElement !=null && selectedElement.isFromAD()){
					user = new WindowUserPmAdd(messages.userEdit(), selectedElement, messages, serverName);
				}else{
					user = new WindowUserPmAdd(messages.userAdd(), messages, serverName);
				}
				user.show();
			}
		};
		return handler;
	}
	
	/**
	 * Редактирование пользователя
	 * @return
	 */
	private SelectHandler handlerEdit() {
		SelectHandler handler = new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				WindowUserPmAdd user = new WindowUserPmAdd(messages.userEdit(), selectedElement, messages, serverName);
				user.show();
			}
		};
		return handler;
	}
	
	
	/**
	 * 08.09.2015
	 * Копирование пользователя, новая учетка, приложения старые
	 * @return
	 */
	private SelectHandler handlerCopy() {
		SelectHandler handler = new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				
				if(selectedElement == null){
					Info.display(messages.error(), messages.selectRecord());
					return;
				}
				
				WindowUserPmAddCopy user = new WindowUserPmAddCopy(messages.copyUser(), selectedElement, messages, serverName);
				user.show();
			}
		};
		return handler;
	}
	
	
	private SelectionHandler<UserProxy> selection = new SelectionHandler<UserProxy>() {
		@Override
		public void onSelection(SelectionEvent<UserProxy> event) {
			selectedElement = event.getSelectedItem();
			//UserProxy obj = event.getSelectedItem();
			//status.setText(obj.getFullName() + " (" + obj.getLoginName()+")");
		}
	};
	

	
	private<T> List<ColumnConfig<UserProxy, ?>> getConfigColumnList(
			UserPmProperties props2, final boolean isFromAD) {
		
		/*List<ColumnConfig<T, ?>> configList =  new ArrayList<ColumnConfig<T,?>>();
		Field[] fields = props2.getClass().getDeclaredFields();
		for (Field field : fields) {

			Field field2;
			try {
				field2 = props2.getClass().getField(field.getName());
				Object object = field2.get(props2);
				ColumnConfig<T, String> col1 = new ColumnConfig<T, String>((ValueProvider<? super T, String>)object, 80, field.getName());
				configList.add(col1);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}*/
		
	 
		ColumnConfig<UserProxy, String> col1 = new ColumnConfig<UserProxy, String>(props2.loginName(), 80, messages.loginName());
		ColumnConfig<UserProxy, String> col2 = new ColumnConfig<UserProxy, String>(props2.fullName(), 150, messages.fullName());
		ColumnConfig<UserProxy, String> col3 = new ColumnConfig<UserProxy, String>(props2.workPhone(), 80, messages.workPhone());
		ColumnConfig<UserProxy, String> col4 = new ColumnConfig<UserProxy, String>(props2.employeeID(), 80, messages.employeIDshort());
		
		col1.setCell(new AbstractCell<String>(){
			@Override
			public void render(com.google.gwt.cell.client.Cell.Context context,
					String value, SafeHtmlBuilder sb) {
				  final String style = "style='color: " + (isFromAD ? "red" : "green") + "'";
			  sb.appendHtmlConstant("<span " + style + " qtitle='Change' qtip='" + value + "'>" + value + "</span>");
	        
			}
		});
		 
		List<ColumnConfig<UserProxy, ?>> configList =  new ArrayList<ColumnConfig<UserProxy,?>>();
		configList.add(col1);
		configList.add(col2);
		configList.add(col3);
		configList.add(col4);
		return configList;
	}

	public void selectDefaultComboValue(){
		 ApplicationProxy findModelWithKey = combo.getStore().findModelWithKey(Constants.ID_APPLICATION_IDS.toString());
		 combo.select(findModelWithKey);
	}
	
	public ApplicationProxy getSelectedApplication(){
		if(combo == null || combo.getCurrentValue() == null){
			return null;
		}
		return combo.getCurrentValue();
	}
	
	public UserProxy getReturnedvalue(){
		return selectedElement;
	}
	
	
	public void  addSelectionHandler(SelectionHandler<UserProxy> handler){
		if(grid !=null)
			grid.getSelectionModel().addSelectionHandler(handler);
	}
	
	public void addHandlerButtonDetail(SelectHandler handler){
		if(buttonDetail != null)
			this.buttonDetail.addSelectHandler(handler);
	}

	public void addHandlerButtonAcl(SelectHandler handler){
		if(buttonAcl != null)
			this.buttonAcl.addSelectHandler(handler);
	}

	public VerticalLayoutContainer getContainer() {
		return container;
	}

	public ContentPanel getRoot() {
		return root;
	}

	public HorizontalLayoutContainer getContainerH() {
		return containerH;
	}

	public TextButton getButtonAdd() {
		return buttonAdd;
	}

	public TextButton getButtonCopy() {
		return buttonCopy;
	}

	public TextButton getButtonDetail() {
		return buttonDetail;
	}

	public TextButton getButtonDelete() {
		return buttonDelete;
	}

	public TextButton getButtonEdit() {
		return buttonEdit;
	}

	public Collection<? extends UserProxy> getItems() {
		return items;
	}

}
