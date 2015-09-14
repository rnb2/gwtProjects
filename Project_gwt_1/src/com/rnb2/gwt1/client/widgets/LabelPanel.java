/**
 * 
 */
package com.rnb2.gwt1.client.widgets;

import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;

/**
 * @author budukh-rn
 *
 */
public class LabelPanel extends ContentPanel {

	public LabelPanel(String title1, String title2, int padding) {
		
        setHeaderVisible(false);
        setBodyBorder(false);
        setBorders(false);
        setBodyStyle("backgroundColor: transparent;");
        
        HorizontalLayoutData layoutData0 = new HorizontalLayoutData();
        layoutData0.setWidth(1);
        layoutData0.setHeight(30);
		
        setLayoutData(layoutData0);
        
        Label label1 = new Label(title1);
        label1.setStylePrimaryName("textBold");
        
        
        HBoxLayoutContainer container = new HBoxLayoutContainer();
        HorizontalLayoutData layoutData = new HorizontalLayoutData(1, 1, new Margins(5));
		container.setLayoutData(layoutData);
		container.add(label1);
		
        
        Label label2 = new Label(title2);
        label2.setStylePrimaryName("textBold");
		
        container.add(label2, new BoxLayoutData(new Margins(0, 0, 0, padding)));
    
        add(container);
        forceLayout();
	}
}
