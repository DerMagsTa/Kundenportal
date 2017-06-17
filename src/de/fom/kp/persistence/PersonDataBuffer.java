package de.fom.kp.persistence;

import java.util.List;

public class PersonDataBuffer {
	
	private Person p;
	private List<Entnahmestelle> es;
	private List<Zaehler> zs;
	private List<Messwert> ms;
	
	public PersonDataBuffer() {
		// TODO Auto-generated constructor stub
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
	

}