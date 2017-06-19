package de.fom.kp.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Zaehler {

	private Integer id;
	private String zaehlerNr;
	private String energieArt;
	private Integer entnahmestelleId;
	private List<Messwert> mList = new ArrayList<Messwert>();
	
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

	public List<Messwert> getmList() {
		return mList;
	}

	public List<Messwert> getmList(Date from, Date to) {
		//Liefert Messwerte die zwischen Date from und to liegen
		List<Messwert> mList_date = new ArrayList<Messwert>();
		for (Messwert m : mList) {
			if (((m.getAblesedatum().before(from))==false) && (m.getAblesedatum().after(to)==false)){
				mList_date.add(m);
			}	
		}
		return mList_date;
	}
	
	public void setmList(List<Messwert> mList) {
		this.mList = mList;
	}
	
}
