package com.rnb.plategka.server;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;







import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.rnb.plategka.client.PaymentsService;
import com.rnb.plategka.data.Constants;
import com.rnb.plategka.data.Payments;
import com.rnb.plategka.shared.AppUtils;

/**
 * 
 * @author budukh-rn
 * 2010 -
 * апр.2015 г.	
 *
 */
public class PaymentsServiceBean extends RemoteServiceServlet implements
		PaymentsService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String QUERY_ALL_PAYMENTS = "select from " + Payments.class.getName();
	private final String QUERY_ALL_CONSTANTS = "select from " + Constants.class.getName();
	//private static final Logger log = Logger.getLogger(PaymentsServiceBean.class.getName());	
	
	private Payments calcPayment(Payments p){
		
		List<Constants> resultList = new ArrayList<Constants>();
		resultList = Manage.executeNamedQuery(QUERY_ALL_CONSTANTS + " WHERE yearOfPay == " + p.getYearOfPay(), "date descending");
		if(resultList.isEmpty()){
			return null;
		}
		Constants constants = resultList.get(0);
		double light = constants.getLight();
		double lightMore = constants.getLightMore();
		Integer lightPredel = constants.getLightPredel();
		double water = constants.getWater();
		
		Integer oddsLight = p.getPay4_2() - p.getPay4_1();
		Integer oddsWater = p.getPay3_2() - p.getPay3_1();
		
		if(oddsWater != null && oddsWater > 0 ){
			double resultWater = new BigDecimal(oddsWater * water).setScale(2, RoundingMode.UP).doubleValue();
			p.setPay3(resultWater);
		
		}
	
		if(oddsLight != null && oddsLight > 0 ){
			double resultLight = AppUtils.getRaschetLight(lightPredel, light, lightMore, oddsLight);
			double resultLightMore =  AppUtils.getRaschetLightMore(lightPredel, lightMore, oddsLight);
			
			p.setPay4Less(resultLight);
			p.setPay4More(resultLightMore);
			p.setPay4(getRoundingValue(2, RoundingMode.UP, resultLight + resultLightMore));
		}
	 return p;	
	}
	
	private Double getRoundingValue(int scale, RoundingMode mode, double value){
		return new BigDecimal(value).setScale(scale, mode).doubleValue();
	}
	
	@Override
	public Payments addPayment(Payments p) {
		if(p==null){
			return Manage.addEntity(new Payments());
		}
		return Manage.addEntity(calcPayment(p));
	}

	@Override
	public void deletePayment(Payments p) {
		Payments pp = Manage.findEntity(Payments.class, p.getId());
		Manage.deleteEntity(pp);
	}

	@Override
	public List<Payments> getPayments(int year) {
		List<Payments> resultList = new ArrayList<Payments>();
		resultList = Manage.executeNamedQuery(QUERY_ALL_PAYMENTS
				+ " WHERE yearOfPay == " + year, "dateOfPay ascending");
		return new ArrayList<Payments>(resultList);
	}

	@SuppressWarnings("unused")
	private String getCurrentUser() {
		UserService userService = UserServiceFactory.getUserService();
		System.out.println("userService=" + userService);
		if (userService != null) {
			User us = userService.getCurrentUser();
			System.out.println("us=" + us);
			return us.getNickname();
		}
		return "";
	}

}
