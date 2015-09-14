
package com.rnb2.gwt1.client.fileload;

/**
 * Interface to represent a request to load an SVG resource
 * into the application
 * @author laaglu
 */
public interface ILoadRequest {
	/**
	 * Load the request
	 */
	public void load();
	/**
	 * Returns the request title
	 * (to display in the open recent document menu).
	 * @return the request title
	 */
	public String getTitle();
}
