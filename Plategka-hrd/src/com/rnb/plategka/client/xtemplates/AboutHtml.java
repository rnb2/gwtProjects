/**
 * 
 */
package com.rnb.plategka.client.xtemplates;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.sencha.gxt.core.client.XTemplates;

/**
 * @author budukh-rn
 * 10 ���. 2015 �.	
 *
 */
public interface AboutHtml extends XTemplates {
	
	@XTemplate(source = "about.html")
	SafeHtml getTemplate();
}
