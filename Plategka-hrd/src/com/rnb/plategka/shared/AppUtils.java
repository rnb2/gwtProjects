/**
 * 
 */
package com.rnb.plategka.shared;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author budukh-rn
 * 08 апр. 2015 г.	
 *
 */
public class AppUtils {

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
		double resultLightMore = 0d;
		if(oddsLight > lightPredel){
			resultLightMore = getRoundingValue(2, RoundingMode.UP,(oddsLight - lightPredel) * lightMore);
		}
		return getRoundingValue(2, RoundingMode.UP, resultLightMore);
	}
	
	public static Double getRoundingValue(int scale, RoundingMode mode, double value){
		return new BigDecimal(value).setScale(scale, mode).doubleValue();
	}

}
