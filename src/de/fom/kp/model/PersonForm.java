package de.fom.kp.model;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import de.fom.kp.controller.DaoException;
import de.fom.kp.dao.PersonDao;
import de.fom.kp.persistence.Person;
import de.fom.kp.view.Message;

public class PersonForm {
	
	private SimpleDateFormat dateFormat;
	private NumberFormat decimalFormat;
	private Integer id;
	private String anrede;
	private String vorname;
	private String nachname;
	private String email;
	private String geburtsdatum;
	private String straße;
	private String hausNr;
	private String plz;
	private String ort;
	private String land;
	private String passwort;
	
	//Regex für: 0 darf nur im ersten Zeichen ODER im zweiten Zeichen vorkommen (00123 ist keine PLZ 01234 schon und 10234 auch)
	private String plzRegex = "^([0]{1}[1-9]{1}|[1-9]{1}[0-9]{1})[0-9]{3}$";
	//Regex für: Am Anfang muss eine Zahl stehen, rest beliebige Zeichen
	private String hausNrRegex = "^[\\d].*";

	
	public PersonForm() {
		
	}

	public PersonForm(SimpleDateFormat dateFormat, NumberFormat decimalFormat) {
		this.dateFormat 	= dateFormat;
		this.decimalFormat 	= decimalFormat;
	}
	
	public PersonForm(de.fom.kp.persistence.Person p, SimpleDateFormat dateFormat, NumberFormat decimalFormat) {
		this.dateFormat 	= dateFormat;
		this.decimalFormat 	= decimalFormat;
		this.id 			= p.getId();
		this.anrede 		= p.getAnrede();
		this.vorname 		= p.getVorname();
		this.nachname 		= p.getNachname();
		this.email 			= p.getEmail();
		this.geburtsdatum 	= dateFormat.format(p.getGeburtsdatum());
		this.straße 		= p.getStraße();
		this.hausNr 		= p.getHausNr();
		this.plz 			= Integer.toString(p.getPlz());
		this.ort 			= p.getOrt();
		this.land			= p.getLand();
		this.passwort		= p.getPasswort();
	}
	
	public PersonForm(HttpServletRequest request, SimpleDateFormat dateFormat, NumberFormat decimalFormat) {
		this.dateFormat 	= dateFormat;
		this.decimalFormat 	= decimalFormat;
		if(StringUtils.isNotBlank(request.getParameter("id"))){
			this.id = Integer.parseInt(request.getParameter("id"));
		} else {
			this.id = null;
		}
		this.anrede 		= request.getParameter("anrede");
		this.vorname 		= request.getParameter("vorname");
		this.nachname 		= request.getParameter("nachname");
		this.email 			= request.getParameter("email");
		this.geburtsdatum 	= request.getParameter("geburtsdatum");
		this.straße 		= request.getParameter("straße");
		this.hausNr 		= request.getParameter("hausNr");
		this.plz 			= request.getParameter("plz");
		this.ort 			= request.getParameter("ort");
		this.land 			= request.getParameter("land");
		this.passwort		= request.getParameter("passwort");

	}

	public void validate(List<Message> errors, PersonDao pDao) {
		
		//Zur besseren Lesbarkeit sind die deutschen Fehlermeldungen jeweils als Kommentar angegeben.
		
		if(StringUtils.isBlank(anrede)){
			//Anrede nicht angegeben
			errors.add(new Message("anrede", "i18n.Fehler_Anrede"));
		}
		if(StringUtils.isBlank(vorname)){
			//Vorname nicht angegeben
			errors.add(new Message("vorname", "i18n.Fehler_Vorname"));
		}
		if(StringUtils.isBlank(nachname)){
			//Nachname nicht angegeben
			errors.add(new Message("nachname", "i18n.Fehler_Nachname"));
		}
		if(StringUtils.isBlank(email)){
			//Email nicht angegeben
			errors.add(new Message("email", "i18n.Fehler_Email"));
		}else{
			EmailValidator v = EmailValidator.getInstance();
			if(!v.isValid(email)){
				//Emailadresse nicht gültig
				errors.add(new Message("email", "i18n.Fehler_Email2"));
			}else{
				try {
					if (pDao.checkEmail(email, id)>200){
						//Emailadresse wird bereits verwendet
						errors.add(new Message("email", "i18n.Fehler_Email3"));
					}
				} catch (DaoException e1) {
					//Fehler beim Prüfen der Emailadresse
					errors.add(new Message("email", "i18n.Fehler_Email4"));
				}
			}
		}
		
		if(StringUtils.isBlank(geburtsdatum)){
			//Geburtsdatum nicht angegeben
			errors.add(new Message("geburtsdatum", "i18n.Fehler_Geburtsdatum"));
		}
		if(StringUtils.isNotBlank(geburtsdatum)){
			try {
				dateFormat.parse(geburtsdatum);
			} catch (ParseException e) {
				//Geburtsdatum ist nicht im richtigen Format
				errors.add(new Message("geburtsdatum", "i18n.Fehler_Geburtsdatum2"));
			}
		}
		if(StringUtils.isBlank(straße)){
			//Straße nicht angegeben
			errors.add(new Message("straße", "i18n.Fehler_Straße"));
		}
		if(StringUtils.isBlank(hausNr)){
			//Haus-Nr. nicht angegeben
			errors.add(new Message("hausNr", "i18n.Fehler_HausNr"));
		}
		if(!hausNr.matches(hausNrRegex)){
			//Haus-Nr nicht gültig
			errors.add(new Message("hausNr", "i18n.Fehler_HausNr2"));
		}
		if(StringUtils.isBlank(plz)){
			//PLZ nicht angegeben
			errors.add(new Message("plz", "i18n.Fehler_PLZ"));
		}
		try {
			if(StringUtils.isNotBlank(plz)){
				Integer.parseInt(plz);
			}
		} catch (NumberFormatException e) {
			//PLZ ist keine Zahl
			errors.add(new Message("plz", "i18n.Fehler_PLZ2"));
		}
		if(!plz.matches(plzRegex)){
			//PLZ ist nicht gültig
			errors.add(new Message("plz", "i18n.Fehler_PLZ3"));
		} 
		if(StringUtils.isBlank(ort)){
			//Ort nicht angegeben
			errors.add(new Message("ort", "i18n.Fehler_Ort"));
		}
		if(StringUtils.isBlank(land)){
			errors.add(new Message("land", "i18n.Fehler_Land"));
		}
		if(id == null){ //Passwort nur bei Neuanlage testen
			if(StringUtils.isBlank(passwort)){
				errors.add(new Message("passwort", "i18n.Fehler_Passwort"));
			} else if(passwort.length()<6){
				errors.add(new Message("passwort", "i18n.Fehler_Passwort2"));
			}
		}
	}
	
	public Person getPerson(){
		Person p =  new Person();
		p.setId(id);
		p.setAnrede(anrede);
		p.setVorname(vorname);
		p.setNachname(nachname);
		try {
			p.setGeburtsdatum(this.dateFormat.parse(this.geburtsdatum));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		p.setEmail(email);
		p.setPlz(Integer.parseInt(plz));
		p.setStraße(straße);
		p.setHausNr(hausNr);
		p.setOrt(ort);
		p.setLand(land);
		p.setPasswort(passwort);
		return p;
	}

	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}
	
	public String getDateFormatPattern() {
		return dateFormat.toPattern();
	}

	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public NumberFormat getDecimalFormat() {
		return decimalFormat;
	}

	public void setDecimalFormat(NumberFormat decimalFormat) {
		this.decimalFormat = decimalFormat;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAnrede() {
		return anrede;
	}

	public void setAnrede(String anrede) {
		this.anrede = anrede;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGeburtsdatum() {
		return geburtsdatum;
	}

	public void setGeburtsdatum(String geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}

	public String getStraße() {
		return straße;
	}

	public void setStraße(String straße) {
		this.straße = straße;
	}

	public String getHausNr() {
		return hausNr;
	}

	public void setHausNr(String hausNr) {
		this.hausNr = hausNr;
	}

	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
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
	
	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}
	
}
