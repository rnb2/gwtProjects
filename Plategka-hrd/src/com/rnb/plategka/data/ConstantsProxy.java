package com.rnb.plategka.data;

import java.io.Serializable;

/**
 * 
 * @author budukh-rn 03 апр. 2015 г.
 *
 */
public class ConstantsProxy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Integer lightPredel = 100;
	private double water = 0.0;
	private double light = 0.0;
	private double lightMore = 0.0;
	private Integer yearOfPay = 0;
	private String date;

	public ConstantsProxy() {
	}

	public ConstantsProxy(Long id, Integer lightPredel, double water,
			double light, double lightMore, Integer yearOfPay, String date) {
		super();
		this.id = id;
		this.lightPredel = lightPredel;
		this.water = water;
		this.light = light;
		this.lightMore = lightMore;
		this.yearOfPay = yearOfPay;
		this.date = date;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getWater() {
		return water;
	}

	public void setWater(double water) {
		this.water = water;
	}

	public double getLight() {
		return light;
	}

	public void setLight(double light) {
		this.light = light;
	}

	public Long getId() {
		return id;
	}

	public Integer getYearOfPay() {
		return yearOfPay;
	}

	public void setYearOfPay(Integer yearOfPay) {
		this.yearOfPay = yearOfPay;
	}

	public double getLightMore() {
		return lightMore;
	}

	public void setLightMore(double lightMore) {
		this.lightMore = lightMore;
	}

	public Integer getLightPredel() {
		return lightPredel;
	}

	public void setLightPredel(Integer lightPredel) {
		this.lightPredel = lightPredel;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
