package de.fom.kp.persistence;

public class Zaehler {

	private Integer id;
	private String zaehlerNr;
	private String energieArt;
	private Integer entnahmestelleId;
	
	
	public Zaehler() {
	}
	
	public Zaehler(String zaehlerNr, String energieArt) {
		this.zaehlerNr = zaehlerNr;
		this.energieArt = energieArt;
	}
	
	public Zaehler(Integer id, String zaehlerNr, String energieArt, Integer entnahmestelleId) {
		this.id = id;
		this.zaehlerNr = zaehlerNr;
		this.energieArt = energieArt;
		this.entnahmestelleId = entnahmestelleId;
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getZaehlerNr() {
		return zaehlerNr;
	}
	public void setZaehlerNr(String zaehlerNr) {
		this.zaehlerNr = zaehlerNr;
	}
	public String getEnergieArt() {
		return energieArt;
	}
	public void setEnergieArt(String energieArt) {
		this.energieArt = energieArt;
	}
	public Integer getEntnahmestelleId() {
		return entnahmestelleId;
	}
	public void setEntnahmestelleId(Integer entnahmestelleId) {
		this.entnahmestelleId = entnahmestelleId;
	}	
}
