/**
 * 
 */
package com.rnb2.gwt1.data.pm.proxy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author budukh-rn
 *
 */
public class ApplicationProxyFull implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	List<ApplicationProxy> list1 = new ArrayList<ApplicationProxy>();
	List<ApplicationProxy> list2 = new ArrayList<ApplicationProxy>();
	
	public ApplicationProxyFull() {
	}

	public ApplicationProxyFull(List<ApplicationProxy> list1,
			List<ApplicationProxy> list2) {
		super();
		this.list1 = list1;
		this.list2 = list2;
	}

	public List<ApplicationProxy> getList1() {
		return list1;
	}

	public void setList1(List<ApplicationProxy> list1) {
		this.list1 = list1;
	}

	public List<ApplicationProxy> getList2() {
		return list2;
	}

	public void setList2(List<ApplicationProxy> list2) {
		this.list2 = list2;
	}
	
	
}
