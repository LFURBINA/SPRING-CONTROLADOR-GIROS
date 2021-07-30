package org.colfuturo.ctd.quote.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the ctd_quote database table.
 * 
 */
@Entity
@Table(name="ctd_quote")
@NamedQuery(name="CtdQuote.findAll", query="SELECT c FROM CtdQuote c")
public class CtdQuote implements Serializable {
	private static final long serialVersionUID = 1L;
	private int qteId;
	private double qteAmount;
	private String qteBuyCurrency;
	private String qteCustomerId;
	private Timestamp qteExpiry;
	private String qteFixedSide;
	private String qteQuoteId;
	private double qteQuotePrice;
	private double qteQuoteRate;
	private String qteSellCurrency;

	public CtdQuote() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="QTE_ID", unique=true, nullable=false)
	public int getQteId() {
		return this.qteId;
	}

	public void setQteId(int qteId) {
		this.qteId = qteId;
	}


	@Column(name="QTE_AMOUNT", nullable=false)
	public double getQteAmount() {
		return this.qteAmount;
	}

	public void setQteAmount(double qteAmount) {
		this.qteAmount = qteAmount;
	}


	@Column(name="QTE_BUY_CURRENCY", nullable=false, length=128)
	public String getQteBuyCurrency() {
		return this.qteBuyCurrency;
	}

	public void setQteBuyCurrency(String qteBuyCurrency) {
		this.qteBuyCurrency = qteBuyCurrency;
	}


	@Column(name="QTE_CUSTOMER_ID", nullable=false, length=128)
	public String getQteCustomerId() {
		return this.qteCustomerId;
	}

	public void setQteCustomerId(String qteCustomerId) {
		this.qteCustomerId = qteCustomerId;
	}


	@Column(name="QTE_EXPIRY", nullable=false)
	public Timestamp getQteExpiry() {
		return this.qteExpiry;
	}

	public void setQteExpiry(Timestamp qteExpiry) {
		this.qteExpiry = qteExpiry;
	}


	@Column(name="QTE_FIXED_SIDE", nullable=false, length=128)
	public String getQteFixedSide() {
		return this.qteFixedSide;
	}

	public void setQteFixedSide(String qteFixedSide) {
		this.qteFixedSide = qteFixedSide;
	}


	@Column(name="QTE_QUOTE_ID", nullable=false, length=128)
	public String getQteQuoteId() {
		return this.qteQuoteId;
	}

	public void setQteQuoteId(String qteQuoteId) {
		this.qteQuoteId = qteQuoteId;
	}


	@Column(name="QTE_QUOTE_PRICE", nullable=false)
	public double getQteQuotePrice() {
		return this.qteQuotePrice;
	}

	public void setQteQuotePrice(double qteQuotePrice) {
		this.qteQuotePrice = qteQuotePrice;
	}


	@Column(name="QTE_QUOTE_RATE", nullable=false)
	public double getQteQuoteRate() {
		return this.qteQuoteRate;
	}

	public void setQteQuoteRate(double qteQuoteRate) {
		this.qteQuoteRate = qteQuoteRate;
	}


	@Column(name="QTE_SELL_CURRENCY", nullable=false, length=128)
	public String getQteSellCurrency() {
		return this.qteSellCurrency;
	}

	public void setQteSellCurrency(String qteSellCurrency) {
		this.qteSellCurrency = qteSellCurrency;
	}

}