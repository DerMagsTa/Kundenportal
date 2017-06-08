package de.fom.kp.persistence;

import java.util.Date;

public class Messwert {
	
	private Integer id;
	private Integer zaehlerId;
	private Date ablesedatum;
	private double messwert;
	
	public Messwert() {
		// TODO Auto-generated constructor stub
	
	}

	public Messwert( Integer zaehlerId,Date ablesedatum, double messwert) {
		super();
		this.zaehlerId = zaehlerId;
		this.ablesedatum = ablesedatum;
		this.messwert = messwert;
	}
	
	public Messwert(Integer id, Integer zaehlerId, Date ablesedatum, double messwert) {
		super();
		this.id = id;
		this.zaehlerId = zaehlerId;
		this.ablesedatum = ablesedatum;
		this.messwert = messwert;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getZaehlerId() {
		return zaehlerId;
	}

	public void setZaehlerId(Integer zaehlerId) {
		this.zaehlerId = zaehlerId;
	}

	public Date getAblesedatum() {
		return ablesedatum;
	}

	public void setAblesedatum(Date ablesedatum) {
		this.ablesedatum = ablesedatum;
	}

	public double getMesswert() {
		return messwert;
	}

	public void setMesswert(double messwert) {
		this.messwert = messwert;
	}

}
