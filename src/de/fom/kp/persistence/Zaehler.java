package de.fom.kp.persistence;

public class Zaehler {

	private Integer id;
	private String ZaehlerNr;
	private String EnergieArt;
	private Integer EntnahmestelleId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getZaehlerNr() {
		return ZaehlerNr;
	}
	public void setZaehlerNr(String zaehlerNr) {
		ZaehlerNr = zaehlerNr;
	}
	public String getEnergieArt() {
		return EnergieArt;
	}
	public void setEnergieArt(String energieArt) {
		EnergieArt = energieArt;
	}
	public Integer getEntnahmestelleId() {
		return EntnahmestelleId;
	}
	public void setEntnahmestelleId(Integer entnahmestelleId) {
		EntnahmestelleId = entnahmestelleId;
	}
	
	public Zaehler() {
	}
	
	public Zaehler(String zaehlerNr, String energieArt) {
		ZaehlerNr = zaehlerNr;
		EnergieArt = energieArt;
	}
	
	public Zaehler(Integer id, String zaehlerNr, String energieArt, Integer entnahmestelleId) {
		this.id = id;
		ZaehlerNr = zaehlerNr;
		EnergieArt = energieArt;
		EntnahmestelleId = entnahmestelleId;
	}
	
	
}
