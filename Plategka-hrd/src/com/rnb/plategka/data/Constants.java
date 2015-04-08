package com.rnb.plategka.data;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NullValue;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * 
 * @author budukh-rn 01 апр. 2015 г.
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class Constants implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8273958642004245909L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent(nullValue = NullValue.NONE)
	private Integer lightPredel = 100;

	@Persistent
	private double water = 0.0;

	@Persistent
	private double light = 0.0;

	@Persistent(nullValue = NullValue.NONE, nullIndicatorColumn = "-")
	private double lightMore = 0.0;

	@Persistent
	private String date;

	@Persistent
	private Integer yearOfPay = 0;

	public Constants() {
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

	public void setDate(String date) {
		this.date = date;
	}

	public String getDate() {
		return date;
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

}
