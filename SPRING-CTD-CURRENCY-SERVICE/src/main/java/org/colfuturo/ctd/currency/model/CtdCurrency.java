package org.colfuturo.ctd.currency.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the ctd_currency database table.
 * 
 */
@Entity
@Table(name="ctd_currency")
@NamedQuery(name="CtdCurrency.findAll", query="SELECT c FROM CtdCurrency c")
public class CtdCurrency implements Serializable {
	private static final long serialVersionUID = 1L;
	private int ccyId;
	private double ccyAgreedRate;
	private Timestamp ccyBookingDatetime;
	private String ccyConversionId;
	private int ccyStatusId;
	private Timestamp ccyValueDate;

	public CtdCurrency() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CCY_ID", unique=true, nullable=false)
	public int getCcyId() {
		return this.ccyId;
	}

	public void setCcyId(int ccyId) {
		this.ccyId = ccyId;
	}


	@Column(name="CCY_AGREED_RATE", nullable=false)
	public double getCcyAgreedRate() {
		return this.ccyAgreedRate;
	}

	public void setCcyAgreedRate(double ccyAgreedRate) {
		this.ccyAgreedRate = ccyAgreedRate;
	}


	@Column(name="CCY_BOOKING_DATETIME", nullable=false)
	public Timestamp getCcyBookingDatetime() {
		return this.ccyBookingDatetime;
	}

	public void setCcyBookingDatetime(Timestamp ccyBookingDatetime) {
		this.ccyBookingDatetime = ccyBookingDatetime;
	}


	@Column(name="CCY_CONVERSION_ID", nullable=false, length=128)
	public String getCcyConversionId() {
		return this.ccyConversionId;
	}

	public void setCcyConversionId(String ccyConversionId) {
		this.ccyConversionId = ccyConversionId;
	}


	@Column(name="CCY_STATUS_ID", nullable=false)
	public int getCcyStatusId() {
		return this.ccyStatusId;
	}

	public void setCcyStatusId(int ccyStatusId) {
		this.ccyStatusId = ccyStatusId;
	}


	@Column(name="CCY_VALUE_DATE", nullable=false)
	public Timestamp getCcyValueDate() {
		return this.ccyValueDate;
	}

	public void setCcyValueDate(Timestamp ccyValueDate) {
		this.ccyValueDate = ccyValueDate;
	}

}