package de.fom.kp.model;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import de.fom.kp.persistence.Verbrauchswert;

public class VerbrauchswertForm {
	
	private String from;
	private String to;
	private String verbrauch;
	private String unit;
	private Double verbrauchD; //ben�tigt f�r Google Chart Api!

	
	public VerbrauchswertForm(Verbrauchswert v, DateFormat df, NumberFormat d) {
		NumberFormat nf = (NumberFormat) d.clone();
		SimpleDateFormat sdf = (SimpleDateFormat) df.clone();
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
		this.from = sdf.format(v.getFrom());
		this.to   = sdf.format(v.getTo());
		this.verbrauch = nf.format(v.getVerbrauch());
		this.unit = v.getUnit();
		this.verbrauchD = v.getVerbrauch();
	}

	public Double getVerbrauchD() {
		return verbrauchD;
	}

	public void setVerbrauchD(Double verbrauchD) {
		this.verbrauchD = verbrauchD;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getVerbrauch() {
		return verbrauch;
	}

	public void setVerbrauch(String verbrauch) {
		this.verbrauch = verbrauch;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

}
