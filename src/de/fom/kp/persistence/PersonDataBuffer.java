package de.fom.kp.persistence;

import java.util.ArrayList;
import java.util.List;


public class PersonDataBuffer {
	
	private Person p;
	private List<Entnahmestelle> es = new ArrayList<Entnahmestelle>();
	private List<Zaehler> zs = new ArrayList<Zaehler>();
	private List<Messwert> ms = new ArrayList<Messwert>();
	
	public PersonDataBuffer() {

	}

	public Person getP() {
		return p;
	}

	public void setP(Person p) {
		this.p = p;
	}

	public List<Entnahmestelle> getEs() {
		return es;
	}

	public void setEs(List<Entnahmestelle> es) {
		this.es = es;
	}

	public List<Zaehler> getZs() {
		return zs;
	}

	public void setZs(List<Zaehler> zs) {
		this.zs = zs;
	}

	public List<Messwert> getMs() {
		return ms;
	}

	public void setMs(List<Messwert> ms) {
		this.ms = ms;
	}
	
	public Entnahmestelle getEntnahmestelle(Integer eID){
		Entnahmestelle e = null; 
		for (Entnahmestelle entnahmestelle : es) {
			if (entnahmestelle.getId()==eID){
				e = entnahmestelle;
			}
		}
		return e;
	}
	
	public Zaehler getZaehler(Integer zID){
		Zaehler z = null; 
		for (Zaehler zaehler : zs) {
			if (zaehler.getId()==zID){
				z = zaehler;
			}
		}
		return z;
	}
	
	public boolean checkPerson(String id) {
		try {
			Integer.parseInt(id);
		} catch (NumberFormatException e) {
			return false;
		}
		if (this.p == null) {
			return false;
		} else if (this.p.getId().equals(Integer.parseInt(id))){
			return true;
		} else
			return false;
	}
	
	public boolean checkZaehler(String id) {
		try {
			Integer.parseInt(id);
		} catch (NumberFormatException e) {
			return false;
		}
		if (this.zs == null) {
			return false;
		} else if ( getZaehler(Integer.parseInt(id)) != null  ){
			return true;
		} else
			return false;
	}
	
	public boolean checkEntnahmestelle(String id) {
		try {
			Integer.parseInt(id);
		} catch (NumberFormatException e) {
			return false;
		}
		if (this.es == null) {
			return false;
		} else if ( getEntnahmestelle(Integer.parseInt(id)) != null  ){
			return true;
		} else
			return false;
	}
	
	public boolean checkMesswert(String id, String zid) {
		try {
			Integer.parseInt(id);
			Integer.parseInt(zid);
		} catch (NumberFormatException e) {
			return false;
		}
		if (this.zs == null) {
			return false;
		} else if ( getZaehler(Integer.parseInt(zid)) != null  ){
			 for (Messwert m : getZaehler(Integer.parseInt(zid)).getmList()) {
				if (m.getId() == Integer.parseInt(id)){
					return true;
				}
			}
		    return false;
		} else
			return false;
	}
	
}
