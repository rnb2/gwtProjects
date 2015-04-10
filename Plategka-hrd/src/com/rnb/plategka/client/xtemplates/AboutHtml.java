/**
 * 
 */
package com.rnb.plategka.client.xtemplates;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.sencha.gxt.core.client.XTemplates;

/**
 * @author budukh-rn
 * 10 апр. 2015 г.	
 *
 */
public interface AboutHtml extends XTemplates {
	
	@XTemplate(source = "about.html")
	SafeHtml getTemplate();
}
