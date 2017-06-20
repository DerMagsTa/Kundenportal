package de.fom.kp.model;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import de.fom.kp.persistence.Verbrauchswert;

public class VerbrauchswertForm {
	
	private String from;
	private String to;
	private String verbrauch;
	private String unit;
	
	public VerbrauchswertForm(Verbrauchswert v, DateFormat df, NumberFormat d) {
		this.from = df.format(v.getFrom());
		this.to   = df.format(v.getTo());
		this.verbrauch = d.format(v.getVerbrauch());
		this.unit = v.getUnit();
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
