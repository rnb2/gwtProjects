package com.rnb2.gwt1.data.pm.proxy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PermissionProxyFull implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<PermissionProxy> list1 = new ArrayList<PermissionProxy>();
	List<PermissionProxy> list2 = new ArrayList<PermissionProxy>();

	public PermissionProxyFull() {
	}
	
	
	public PermissionProxyFull(List<PermissionProxy> list1,
			List<PermissionProxy> list2) {
		super();
		this.list1 = list1;
		this.list2 = list2;
	}



	public List<PermissionProxy> getList1() {
		return list1;
	}

	public void setList1(List<PermissionProxy> list1) {
		this.list1 = list1;
	}

	public List<PermissionProxy> getList2() {
		return list2;
	}

	public void setList2(List<PermissionProxy> list2) {
		this.list2 = list2;
	}
	
	
}
