package org.colfuturo.ctd.payment.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the ctd_payment database table.
 * 
 */
@Entity
@Table(name="ctd_payment")
@NamedQuery(name="CtdPayment.findAll", query="SELECT c FROM CtdPayment c")
public class CtdPayment implements Serializable {
	private static final long serialVersionUID = 1L;
	private int pymtId;
	private Timestamp pymtCreatedDatetime;
	private Timestamp pymtPaymentDate;
	private String pymtPaymentId;
	private int pymtStatusId;

	public CtdPayment() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="PYMT_ID", unique=true, nullable=false)
	public int getPymtId() {
		return this.pymtId;
	}

	public void setPymtId(int pymtId) {
		this.pymtId = pymtId;
	}


	@Column(name="PYMT_CREATED_DATETIME", nullable=false)
	public Timestamp getPymtCreatedDatetime() {
		return this.pymtCreatedDatetime;
	}

	public void setPymtCreatedDatetime(Timestamp pymtCreatedDatetime) {
		this.pymtCreatedDatetime = pymtCreatedDatetime;
	}


	@Column(name="PYMT_PAYMENT_DATE", nullable=false)
	public Timestamp getPymtPaymentDate() {
		return this.pymtPaymentDate;
	}

	public void setPymtPaymentDate(Timestamp pymtPaymentDate) {
		this.pymtPaymentDate = pymtPaymentDate;
	}


	@Column(name="PYMT_PAYMENT_ID", nullable=false, length=128)
	public String getPymtPaymentId() {
		return this.pymtPaymentId;
	}

	public void setPymtPaymentId(String pymtPaymentId) {
		this.pymtPaymentId = pymtPaymentId;
	}


	@Column(name="PYMT_STATUS_ID", nullable=false)
	public int getPymtStatusId() {
		return this.pymtStatusId;
	}

	public void setPymtStatusId(int pymtStatusId) {
		this.pymtStatusId = pymtStatusId;
	}

}