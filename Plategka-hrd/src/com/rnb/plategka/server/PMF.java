package com.rnb.plategka.server;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

/**
 * 
 * @author budukh-rn
 *
 */
public final class PMF {
	
	private static final PersistenceManagerFactory pmfInstance 
			= JDOHelper.getPersistenceManagerFactory("transactions-optional"); 

	private PMF() {
		super();
	}

	public static PersistenceManagerFactory getInstance(){
		return pmfInstance;
	}
	
}
