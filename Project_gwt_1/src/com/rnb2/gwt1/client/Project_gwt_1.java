package com.rnb2.gwt1.client;

import org.vectomatic.dom.svg.OMAttr;
import org.vectomatic.dom.svg.OMElement;
import org.vectomatic.dom.svg.OMNamedNodeMap;
import org.vectomatic.dom.svg.OMSVGDocument;
import org.vectomatic.dom.svg.OMSVGLength;
import org.vectomatic.dom.svg.OMSVGSVGElement;
import org.vectomatic.file.File;
import org.vectomatic.file.FileList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.rnb2.gwt1.client.fileload.FileLoadRequest;
import com.rnb2.gwt1.client.fileload.ILoadRequest;
import com.rnb2.gwt1.client.widgets.window.WindowLogin;
import com.sencha.gxt.widget.core.client.box.MessageBox;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Project_gwt_1 implements EntryPoint {
	
	private final ManageServiceAsync manageService = GWT.create(ManageService.class);
	
	private static final String LOAD_SVG_IMAGE = "Load SVG image: ";

	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	private static Project_gwt_1 instance;


	//private final ManageServiceAsync manageService2 = GWT.create(ManageService.class);
	
	private OMSVGSVGElement svgElement;

	private OMSVGDocument svgDoc;

	private Label labelFile;

	
	public void onModuleLoad() {
		
		try {
			WindowLogin login = new WindowLogin();
			login.show();
	
		} catch (Exception e) {
			new MessageBox("getUserName", "error !!!! " + e.getMessage()).show();
		}
//		if(userName != null){
//	new MessageBox("getUserName", "!!!! " + userName).show();
//			Mainwidget2 con = new Mainwidget2(userName);
//	    	    
//			Viewport viewport = new Viewport();
//			viewport.add(con);
//			
//			RootPanel.get().add(viewport);
//		}
	  };
	  
	
		  
	  //public void init(MainWidget con){
		 /* final  HorizontalPanel vPanel = new HorizontalPanel();
			final TextButton button1 = new TextButton("Open file");	
			final Button button2 = new Button("load");
		
			vPanel.setSpacing(5);
		    vPanel.setTitle(LOAD_SVG_IMAGE);
		    vPanel.add(button1);
		    vPanel.add(button2);*/	

		/* AsyncCallback<String> callback = new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				new MessageBox("MainWindow","result: " + result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				new MessageBox("MainWindow","error!!!" + caught.getLocalizedMessage());
			}
		};
		manageService2.getLocalityName("test", callback );
		  */
	 // }
	
	/**
	 * This is the entry point method.
	 */
	/*public void onModuleLoad() {
	instance = this;
	final  VerticalPanel vPanel = new VerticalPanel();
	final Button button1 = new Button("Open file");	
	final Button button2 = new Button("load");
    final Button sendButton = new Button("Send");
    final TextBox nameField = new TextBox();
    nameField.setText("GWT User");
    labelFile = new Label(LOAD_SVG_IMAGE);
	final Label errorLabel = labelFile;

    // We can add style names to widgets
    sendButton.addStyleName("sendButton");

    // Add the nameField and sendButton to the RootPanel
    // Use RootPanel.get() to get the entire body element
    RootPanel.get("nameFieldContainer").add(nameField);
    RootPanel.get("sendButtonContainer").add(sendButton);
    RootPanel.get("errorLabelContainer").add(errorLabel);
    

    
    final FileUploadExt fileUpload = new FileUploadExt();
	Style style = fileUpload.getElement().getStyle();
	style.setVisibility(Visibility.HIDDEN);
	style.setWidth(0, Unit.PX);
	style.setHeight(0, Unit.PX);
	fileUpload.addChangeHandler(new ChangeHandler() {
		
		
		public void onChange(ChangeEvent event) {
			CustomWidgets.alertWidget("before processFiles:", "file:" + fileUpload.getFilename()).show();
			processFiles(fileUpload.getFiles());				
		}

		
	});

			
  
    button1.addClickHandler(new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			
			MessageBox box = new MessageBox("message", "isAttached:" + fileUpload.isAttached());
			box.show();
			
			fileUpload.click();
			
			//Timer timer = new Timer() {
				
			//	@Override
			//	public void run() {
					// TODO Auto-generated method stub
			//		button2.click();
			//		Window.alert("click");		
			//	}
			//};
			//timer.schedule(30000);
				
		}
	});
    
    
    button2.addClickHandler(new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			
			//CustomWidgets.alertWidget("openLocal(): fileList", "s=" + fileUpload.getFilename()).show();
			
			Window.alert("isAttached:" + fileUpload.isAttached());
			
			if(fileUpload.getFiles().getLength() >0){
			
				removeSvgElement("circleReds");
				removeSvgElement("circleBlue");
				removeSvgElement("svgImage");
				
				vPanel.setTitle(LOAD_SVG_IMAGE + fileUpload.getFilename());
				labelFile.setText(LOAD_SVG_IMAGE  + fileUpload.getFilename());
				processFiles(fileUpload.getFiles());
				
			}	

		}
	});
    
   
    vPanel.setSpacing(5);
    vPanel.setTitle(LOAD_SVG_IMAGE);
    labelFile.setText(LOAD_SVG_IMAGE);
    vPanel.add(labelFile);
    vPanel.add(button1);
    vPanel.add(button2);
    RootPanel.get().add(vPanel);
    
    svgDoc = OMSVGParser.currentDocument();
    createSvgElement();
   
  
  
    
    OMSVGCircleElement circle = svgDoc.createSVGCircleElement(150, 150, 100);
    circle.getStyle().setSVGProperty(SVGConstants.CSS_FILL_PROPERTY, "blue");
    circle.setId("circleBlue");
    svgElement.appendChild(circle);
    
    OMSVGCircleElement circle2 = svgDoc.createSVGCircleElement(50, 50, 30);
    circle2.getStyle().setSVGProperty(SVGConstants.CSS_FILL_PROPERTY, "red");
    circle2.setId("circleRed");
    svgElement.appendChild(circle2);
    
    
      
    RootPanel.get().getElement().appendChild(svgElement.getElement());
    ///

    // Focus the cursor on the name field when the app loads
    nameField.setFocus(true);
    nameField.selectAll();

    // Create the popup dialog box
    final DialogBox dialogBox = new DialogBox();
    dialogBox.setText("Remote Procedure Call");
    dialogBox.setAnimationEnabled(true);
    final Button closeButton = new Button("Close");
    // We can set the id of a widget by accessing its Element
    closeButton.getElement().setId("closeButton");
    final Label textToServerLabel = labelFile;
    final HTML serverResponseLabel = new HTML();
    VerticalPanel dialogVPanel = new VerticalPanel();
    dialogVPanel.addStyleName("dialogVPanel");
    dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
    dialogVPanel.add(textToServerLabel);
    dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
    dialogVPanel.add(serverResponseLabel);
    dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
    dialogVPanel.add(closeButton);
    dialogBox.setWidget(dialogVPanel);

    // Add a handler to close the DialogBox
    closeButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        dialogBox.hide();
        sendButton.setEnabled(true);
        sendButton.setFocus(true);
      }
    });

    // Create a handler for the sendButton and nameField
    class MyHandler implements ClickHandler, KeyUpHandler {

      public void onClick(ClickEvent event) {
        sendNameToServer();
      }

     
      public void onKeyUp(KeyUpEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
          sendNameToServer();
        }
      }

      
      private void sendNameToServer() {
        // First, we validate the input.
        errorLabel.setText("");
        String textToServer = nameField.getText();
        if (!FieldVerifier.isValidName(textToServer)) {
          errorLabel.setText("Please enter at least four characters");
          return;
        }
        
        // Then, we send the input to the server.
        sendButton.setEnabled(false);
        textToServerLabel.setText(textToServer);
        serverResponseLabel.setText("");
        greetingService.greetServer(textToServer, new AsyncCallback<String>() {
          public void onFailure(Throwable caught) {
            // Show the RPC error message to the user
            dialogBox.setText("Remote Procedure Call - Failure");
            serverResponseLabel.addStyleName("serverResponseLabelError");
            serverResponseLabel.setHTML(SERVER_ERROR);
            dialogBox.center();
            closeButton.setFocus(true);
          }

          public void onSuccess(String result) {
            dialogBox.setText("Remote Procedure Call");
            serverResponseLabel.removeStyleName("serverResponseLabelError");
            serverResponseLabel.setHTML(result);
            dialogBox.center();
            closeButton.setFocus(true);
          }
        });
      }
    }

    // Add a handler to send the name to the server
    MyHandler handler = new MyHandler();
    sendButton.addClickHandler(handler);
    nameField.addKeyUpHandler(handler);

  }*/

	private void removeSvgElement(String id){
		if(svgElement == null){
			Window.alert("svgElement is null");
			createSvgElement();
		}
			
		OMElement omElement = null;
		try {
			omElement = svgElement.getElementById(id);
		} catch (Exception e) {
		}
		
		if(omElement != null)
			svgElement.removeChild(omElement);
	}
	
	private void createSvgElement() {
		svgElement = svgDoc.createSVGSVGElement();
		svgElement.setWidth(OMSVGLength.SVG_LENGTHTYPE_PX, 500);
		svgElement.setHeight(OMSVGLength.SVG_LENGTHTYPE_PX, 500);
	}
	
	private void processFiles(FileList files) {
		for (File file : files) {
			String type = file.getType();
			if ("image/svg+xml".equals(type)) {
				new FileLoadRequest(file).load();
			}
		}		
	}

	public static Project_gwt_1 getApp() {
		if (instance == null) {
			instance = new Project_gwt_1();
		}
		return instance;
	}
	
	public void addWindow(OMSVGSVGElement svg, ILoadRequest request) {
		
		svg.setWidth(Unit.PX, 250);
		svg.setHeight(Unit.PX, 250);
		svg.setId("svgImage");
		OMNamedNodeMap<OMAttr> attributes = svg.getAttributes();
		StringBuffer sb = new StringBuffer();
		for (OMAttr omAttr : attributes) {
			sb.append(omAttr.getName());
			sb.append("\n\r");
		}
		svgElement.appendChild(svg);
		RootPanel.get().getElement().appendChild(svgElement.getElement());

		//CustomWidgets.alertWidget("svg attr", sb.toString());
		

		
	}

}
