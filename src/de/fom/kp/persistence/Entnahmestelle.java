package de.fom.kp.persistence;

import java.util.ArrayList;
import java.util.List;

public class Entnahmestelle {
	
	private Integer id;
	private String stra�e;
	private String hausNr;
	private Integer plz;
	private String ort;
	private String land;
	private String hinweis;
	private Integer PersonId;
	private List<Zaehler> zaehler = new ArrayList<Zaehler>();
	
	public Entnahmestelle() {
	}

	public Entnahmestelle(String stra�e, String hausNr, Integer plz, String ort, String land, String hinweis,
			Integer personId) {
		this.stra�e = stra�e;
		this.hausNr = hausNr;
		this.plz = plz;
		this.ort = ort;
		this.land = land;
		this.hinweis = hinweis;
		this.PersonId = personId;
	}
	
	public Entnahmestelle(String stra�e, String hausNr, Integer plz, String ort, String land, String hinweis) {
		this.stra�e = stra�e;
		this.hausNr = hausNr;
		this.plz = plz;
		this.ort = ort;
		this.land = land;
		this.hinweis = hinweis;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStra�e() {
		return stra�e;
	}
	public void setStra�e(String stra�e) {
		this.stra�e = stra�e;
	}
	public String getHausNr() {
		return hausNr;
	}
	public void setHausNr(String hausNr) {
		this.hausNr = hausNr;
	}
	public Integer getPlz() {
		return plz;
	}
	public void setPlz(Integer plz) {
		this.plz = plz;
	}
	public String getOrt() {
		return ort;
	}
	public void setOrt(String ort) {
		this.ort = ort;
	}
	public String getLand() {
		return land;
	}
	public void setLand(String land) {
		this.land = land;
	}
	public String getHinweis() {
		return hinweis;
	}
	public void setHinweis(String hinweis) {
		this.hinweis = hinweis;
	}
	public Integer getPersonId() {
		return PersonId;
	}
	public void setPersonId(Integer personId) {
		PersonId = personId;
	}
	public List<Zaehler> getZaehler() {
		return zaehler;
	}

	public void setZaehler(List<Zaehler> zaehler) {
		this.zaehler = zaehler;
	}

	

}
