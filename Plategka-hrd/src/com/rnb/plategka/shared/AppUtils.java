/**
 * 
 */
package com.rnb.plategka.shared;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.logging.Logger;

/**
 * @author budukh-rn
 * 08 апр. 2015 г.	
 *
 */
public class AppUtils {
	
	private static final Logger log = Logger.getLogger(AppUtils.class.getName());

	/**
	 * 
	 */
	public AppUtils() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param lightPredel 
	 * @param light
	 * @param lightMore
	 * @param oddsLight
	 * @return
	 */
	public static Double getRaschetLight(final Integer lightPredel, final Double light, final Double lightMore, final Integer oddsLight){
		double resultLight = 0d;
		double resultLightMore = 0d;
	/*log.info("getRaschetLight -------------");
	log.info("getRaschetLight: lightPredel=" + lightPredel);	
	log.info("getRaschetLight: light=" + light);	
	log.info("getRaschetLight: lightMore=" + lightMore);	
	log.info("getRaschetLight: oddsLight=" + oddsLight);*/	
		if(lightPredel > 0 && oddsLight > lightPredel){
			resultLight = getRoundingValue(2, RoundingMode.UP, lightPredel * light);
			resultLightMore = getRoundingValue(2, RoundingMode.UP,(oddsLight - lightPredel) * lightMore);
		}else{
			resultLight = getRoundingValue(2, RoundingMode.UP, oddsLight * light);
		}
		return getRoundingValue(2, RoundingMode.UP, resultLight + resultLightMore);
	}

	/**
	 * 
	 * @param lightPredel 
	 * @param lightMore
	 * @param oddsLight
	 * @return
	 */
	public static Double getRaschetLightMore(final Integer lightPredel, final Double lightMore, final Integer oddsLight){
		/*log.info("getRaschetLightMore +++++++++++++ ");	
		log.info("getRaschetLightMore: lightPredel=" + lightPredel);	
		log.info("getRaschetLightMore: lightMore=" + lightMore);	
		log.info("getRaschetLightMore: oddsLight=" + oddsLight);*/
		
		double resultLightMore = 0d;
		if(lightPredel > 0 && oddsLight > lightPredel){
			resultLightMore = getRoundingValue(2, RoundingMode.UP,(oddsLight - lightPredel) * lightMore);
		}
		return resultLightMore;
	}
	
	public static Double getRoundingValue(int scale, RoundingMode mode, double value){
		return new BigDecimal(value).setScale(scale, mode).doubleValue();
	}

}
