package de.fom.kp.persistence;

import java.util.*;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Person {

	private Integer id;
	private String email;
	private String passwort;
	private String vorname;
	private String nachname;
	private String anrede;
	private Date geburtsdatum;
	private String straﬂe;
	private String hausNr;
	private Integer plz;
	private String ort;
	private String land;
	private boolean adminrechte;
	
	public Person(String email, String passwort, String vorname, String nachname, String anrede, Date geburtsdatum,
			String straﬂe, String hausNr, Integer plz, String ort, String land, boolean adminrechte) {
		this.email = email;
		this.passwort = passwort;
		this.vorname = vorname;
		this.nachname = nachname;
		this.anrede = anrede;
		this.geburtsdatum = geburtsdatum;
		this.straﬂe = straﬂe;
		this.hausNr = hausNr;
		this.plz = plz;
		this.ort = ort;
		this.land = land;
		this.adminrechte = adminrechte;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPasswort() {
		return passwort;
	}
	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	public String getNachname() {
		return nachname;
	}
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	public String getAnrede() {
		return anrede;
	}
	public void setAnrede(String anrede) {
		this.anrede = anrede;
	}
	public Date getGeburtsdatum() {
		return geburtsdatum;
	}
	public void setGeburtsdatum(Date geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}
	public String getStraﬂe() {
		return straﬂe;
	}
	public void setStraﬂe(String straﬂe) {
		this.straﬂe = straﬂe;
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
	public boolean isAdminrechte() {
		return adminrechte;
	}
	public void setAdminrechte(boolean adminrechte) {
		this.adminrechte = adminrechte;
	}


	
	
}
