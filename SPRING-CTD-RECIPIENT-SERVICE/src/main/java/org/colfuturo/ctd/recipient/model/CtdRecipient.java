package org.colfuturo.ctd.recipient.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ctd_recipient database table.
 * 
 */
@Entity
@Table(name="ctd_recipient")
@NamedQuery(name="CtdRecipient.findAll", query="SELECT c FROM CtdRecipient c")
public class CtdRecipient implements Serializable {
	private static final long serialVersionUID = 1L;
	private int rcptId;
	private String rcptClienteReference;
	private String rcptRecipientId;

	public CtdRecipient() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="RCPT_ID", unique=true, nullable=false)
	public int getRcptId() {
		return this.rcptId;
	}

	public void setRcptId(int rcptId) {
		this.rcptId = rcptId;
	}


	@Column(name="RCPT_CLIENTE_REFERENCE", nullable=false, length=128)
	public String getRcptClienteReference() {
		return this.rcptClienteReference;
	}

	public void setRcptClienteReference(String rcptClienteReference) {
		this.rcptClienteReference = rcptClienteReference;
	}


	@Column(name="RCPT_RECIPIENT_ID", nullable=false, length=128)
	public String getRcptRecipientId() {
		return this.rcptRecipientId;
	}

	public void setRcptRecipientId(String rcptRecipientId) {
		this.rcptRecipientId = rcptRecipientId;
	}

}