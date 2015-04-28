package com.rnb.plategka.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.rnb.plategka.client.ConstantService;
import com.rnb.plategka.data.Constants;
import com.rnb.plategka.data.ConstantsProxy;

/**
 * 
 * @author budukh-rn
 *
 *
 */
public class ConstantServiceBean extends RemoteServiceServlet implements ConstantService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String QUERY_ALL_CONSTANTS = "select from " + Constants.class.getName() + " order by yearOfPay";
	private final String QUERY_ALL_CONSTANTS_PROXY = "select a.id, a.lightPredel, a.water, a.light, a.lightMore, a.yearOfPay, a.date from " + Constants.class.getName() + " a order by a.yearOfPay, a.date";
	private final String QUERY_YEAR_CONSTANTS = "select distinct yearOfPay from " + Constants.class.getName();
	//private static final Logger log = Logger.getLogger(ConstantServiceBean.class.getName());

	
	public int getYear(Date date){
		return DateUtil.getYear(date);
	}
	
	@Override
	public Constants addConstant(Constants c) {
		//Integer year = DateUtil.getYear(c.getDate());
		//c.setYearOfPay(year);
		return Manage.addEntity(c);
	}

	@Override
	public void delete(Constants c) {
		Constants constants  = Manage.findEntity(Constants.class, c.getId());
		Manage.deleteEntity(constants);
	}

	@Override
	public void delete(Long c) {
		Constants constants  = Manage.findEntity(Constants.class, c);
		Manage.deleteEntity(constants);
	}

	@Override
	public List<Integer> getYears() {
		List<Integer> list = Manage.executeNamedQuery(QUERY_YEAR_CONSTANTS, "yearOfPay ascending");
		Map<Integer, Integer> tmpMap = new HashMap<Integer, Integer>();
		
		for (Integer integer : list) {
			if(!tmpMap.containsKey(integer)){
				tmpMap.put(integer, integer);
			}
		}
		List<Integer> newList = new ArrayList<Integer>();
		for(Integer i: tmpMap.keySet()){
			newList.add(i);
		}
		Collections.sort(newList);
	return new ArrayList<Integer>(newList);
	}	
	@Override
	public List<Constants> getConstants() {
		List<Constants> list = Manage.executeNamedQuery(QUERY_ALL_CONSTANTS);
	return new ArrayList<Constants>(list);
	}
	
	@Override
	public List<ConstantsProxy> getConstantsProxy() {
		List<Object[]> list = Manage.executeNamedQueryProxy(QUERY_ALL_CONSTANTS_PROXY);
		
		ArrayList<ConstantsProxy> list2 = new ArrayList<ConstantsProxy>();
		if(!list.isEmpty()){
			for(Object[] o : list){
				Long id = (Long)o[0];
				Integer lightPredel = (Integer) o[1];
				Double water = (Double) o[2];
				Double light = (Double) o[3];
				Double lightMore = (Double) o[4];
				Integer yearOfPay = (Integer) o[5];
				String date = (String) o[6];
				list2.add(new ConstantsProxy(id, lightPredel, water, light, lightMore, yearOfPay, date));
			}
		}
		return list2;
	}

	@Override
	public Constants saveConstant(Constants c) {
		//Integer year = DateUtil.getYear(c.getDate());
		//c.setYearOfPay(year);
		return Manage.addEntity(c);
	}

}
