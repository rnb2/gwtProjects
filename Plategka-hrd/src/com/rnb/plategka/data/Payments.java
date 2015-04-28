package com.rnb.plategka.data;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.gwt.i18n.shared.DateTimeFormat;
import com.rnb.plategka.server.DateUtil;

/**
 * 
 * @author budukh-rn
 * 2010 -
 * апр.2015 г.	
 *
 */
@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Payments implements Serializable, Comparable<Payments> {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String user;

	/** #1: Теплосеть: */
	@Persistent
	private double pay1 = 0.0;

	/** #2 Газ */
	@Persistent
	private double pay2 = 0.0;

	/** ВКХ #3 сумма */
	@Persistent
	private double pay3 = 0.0;

	/** ВКХ #3 начальные */
	@Persistent
	private Integer pay3_1 = 0;

	/** ВКХ #3 конечные */
	@Persistent
	private Integer pay3_2 = 0;

	/** Свет #4 сумма */
	@Persistent
	private double pay4 = 0.0;

	/** Свет #4 начальные */
	@Persistent
	private Integer pay4_1 = 0;

	/** Свет #4 конечные */
	@Persistent
	private Integer pay4_2 = 0;

	/** Свет #4 сумма <150 kBt */
	@Persistent
	private double pay4Less = 0.0;

	/** Свет #4 сумма >150 kBt */
	@Persistent
	private double pay4More = 0.0;

	/** КвартПл #5 */
	@Persistent
	private double pay5 = 0.0;

	/** Мусор #6 */
	@Persistent
	private double pay6 = 0.0;

	/** Домофон */
	@Persistent
	private double pay7 = 0.0;

	/** Год оплаты */
	@Persistent
	private Integer yearOfPay = 2010;

	/** Дата оплаты */
	@Persistent
	private String dateOfPay = "";

	public Payments() {
		super();
	}

	public String getDateOfPay() {
		return dateOfPay;
	}

	public void setDateOfPay(String dateOfPay) {
		this.dateOfPay = dateOfPay;
	}

	public Integer getYearOfPay() {
		return yearOfPay;
	}

	public void setYearOfPay(Integer yearOfPay) {
		this.yearOfPay = yearOfPay;
	}

	public double getPay1() {
		return pay1;
	}

	public void setPay1(double pay1) {
		this.pay1 = pay1;
	}

	public double getPay2() {
		return pay2;
	}

	public void setPay2(double pay2) {
		this.pay2 = pay2;
	}

	public double getPay3() {
		return pay3;
	}

	public void setPay3(double pay3) {
		this.pay3 = pay3;
	}

	public double getPay4() {
		return pay4;
	}

	public void setPay4(double pay4) {
		this.pay4 = pay4;
	}

	public Integer getPay3_1() {
		return pay3_1;
	}

	public void setPay3_1(Integer pay3_1) {
		this.pay3_1 = pay3_1;
	}

	public Integer getPay3_2() {
		return pay3_2;
	}

	public void setPay3_2(Integer pay3_2) {
		this.pay3_2 = pay3_2;
	}

	public Integer getPay4_1() {
		return pay4_1;
	}

	public void setPay4_1(Integer pay4_1) {
		this.pay4_1 = pay4_1;
	}

	public Integer getPay4_2() {
		return pay4_2;
	}

	public void setPay4_2(Integer pay4_2) {
		this.pay4_2 = pay4_2;
	}

	public double getPay5() {
		return pay5;
	}

	public void setPay5(double pay5) {
		this.pay5 = pay5;
	}

	public double getPay6() {
		return pay6;
	}

	public void setPay6(double pay6) {
		this.pay6 = pay6;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public double getPay4Less() {
		return pay4Less;
	}

	public void setPay4Less(double pay4Less) {
		this.pay4Less = pay4Less;
	}

	public double getPay4More() {
		return pay4More;
	}

	public void setPay4More(double pay4More) {
		this.pay4More = pay4More;
	}

	public double getPay7() {
		return pay7;
	}

	public void setPay7(double pay7) {
		this.pay7 = pay7;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dateOfPay == null) ? 0 : dateOfPay.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payments other = (Payments) obj;
		if (dateOfPay == null) {
			if (other.dateOfPay != null)
				return false;
		} else if (!dateOfPay.equals(other.dateOfPay))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int compareTo(Payments o) {
		DateTimeFormat dateFormat = DateTimeFormat
				.getFormat(DateUtil.DEFAULT_DATE_TIME_FORMAT);

		Date date1 = dateFormat.parse(dateOfPay);
		Date date2 = dateFormat.parse(o.getDateOfPay());
		return date1.compareTo(date2);
	}

}
