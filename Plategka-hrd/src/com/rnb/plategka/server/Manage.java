package com.rnb.plategka.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * 
 * @author budukh-rn
 *
 */
public final class Manage {
	private static PersistenceManager pm = PMF.getInstance().getPersistenceManager();
	
	public Manage() {}

	public static PersistenceManager getPm(){
		return pm;
	}
	
	public static <T> List<T> executeNamedQuery(String queryString, String orderBy, String groupBy){
		if (pm==null || pm.isClosed()){
			pm = PMF.getInstance().getPersistenceManager();
		}
		List<T> resultList = new ArrayList<T>();
		Query query = pm.newQuery(queryString);
		 if(orderBy!=null && orderBy!=""){
			 query.setOrdering(orderBy);
		 }
		 if(groupBy!=null && groupBy!=""){
			 query.setGrouping(groupBy);
		 }

        resultList = (List<T>) query.execute();
        return resultList;
	}

	public static <T> List<T> executeNamedQuery(String queryString, String orderBy){
		if (pm==null || pm.isClosed()){
			pm = PMF.getInstance().getPersistenceManager();
		}
		List<T> resultList = new ArrayList<T>();
		Query query = pm.newQuery(queryString);
		 if(orderBy!=null && orderBy!=""){
			 query.setOrdering(orderBy);
		 }
        resultList = (List<T>) query.execute();
        return resultList;
	}
	
	public static <T> List<T> executeNamedQuery(String query) {
		if (pm==null || pm.isClosed()){
			pm = PMF.getInstance().getPersistenceManager();
		}
		List<T> resultList = new ArrayList<T>();
        resultList = (List<T>) pm.newQuery(query).execute();
        return resultList;
	}

	public static List<Object[]> executeNamedQueryProxy(String query) {
		if (pm==null || pm.isClosed()){
			pm = PMF.getInstance().getPersistenceManager();
		}
		List<Object[]> resultList = new ArrayList<Object[]>();
		resultList = (List<Object[]>) pm.newQuery(query).execute();
		return resultList;
	}
	
	public static <T> T findEntity(Class clazz, Object id){
		if (pm==null || pm.isClosed()){
			pm = PMF.getInstance().getPersistenceManager();
		}
		return (T) pm.getObjectById(clazz, id);
	}
	
	public static <T> T addEntity(T t){
		if (pm==null || pm.isClosed()){
			pm = PMF.getInstance().getPersistenceManager();
		}
		try {
			return pm.makePersistent(t);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}
	
	public static <T> void deleteEntity(T t){
		if (pm==null || pm.isClosed()){
			pm = PMF.getInstance().getPersistenceManager();
		}
		try {
			pm.deletePersistent(t);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
