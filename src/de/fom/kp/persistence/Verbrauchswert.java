package de.fom.kp.persistence;

import java.sql.Timestamp;
import java.util.Date;

public class Verbrauchswert {
	
	private Integer zId;
	private Date from;
	private Date to;
	private Double verbrauch;
	private String Unit;

	public Verbrauchswert() {
		// TODO Auto-generated constructor stub
	}

	public Integer getzId() {
		return zId;
	}

	public void setzId(Integer zId) {
		this.zId = zId;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public Double getVerbrauch() {
		return verbrauch;
	}

	public void setVerbrauch(Double verbrauch) {
		this.verbrauch = verbrauch;
	}

	public String getUnit() {
		return Unit;
	}

	public void setUnit(String unit) {
		Unit = unit;
	}

	public Verbrauchswert(Integer zId, Date from, Date to, Double verbrauch, String unit) {
		this.zId = zId;
		this.from = from;
		this.to = to;
		this.verbrauch = verbrauch;
		Unit = unit;
	}
	
	

}
