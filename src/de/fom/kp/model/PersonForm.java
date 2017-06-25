package de.fom.kp.model;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

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
	private Integer admin;
	private boolean changemode = false;
	private String passwort;
	
	//Regex für: 0 darf nur im ersten Zeichen ODER im zweiten Zeichen vorkommen (00123 ist keine PLZ 01234 schon und 10234 auch)
	private String plzRegex = "^([0]{1}[1-9]{1}|[1-9]{1}[0-9]{1})[0-9]{3}$";
	//Regex für: Am Anfang muss eine Zahl stehen, rest beliebige Zeichen
	private String hausNrRegex = "^[\\d].*";
	//Regex für: E-Mail Adressen. Diese wird auch vom W3C in ihrem email Tag verwendet. 
	//Damit aber alle Prüfungen an der selben Stelle erfolgen und alle Fehlermeldungen gleich aussehen
	//verwenden wir diese hier erst im validate() 
	private String emailRegex = "^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
	
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
		this.admin 			= p.isAdminrechte() == true ? 1 : 0;
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
		this.admin 			= 0;
		this.changemode 	= Boolean.parseBoolean(request.getParameter("changemode"));
		this.passwort		= request.getParameter("passwort");

	}

	public void validate(List<Message> errors) {
		if(StringUtils.isBlank(anrede)){
			errors.add(new Message("anrede", "Anrede nicht angegeben"));
		}
		if(StringUtils.isBlank(vorname)){
			errors.add(new Message("vorname", "Vorname nicht angegeben"));
		}
		if(StringUtils.isBlank(nachname)){
			errors.add(new Message("nachname", "Nachname nicht angegeben"));
		}
		if(StringUtils.isBlank(email)){
			errors.add(new Message("email", "Email nicht angegeben"));
		}
		if(!email.matches(emailRegex)){
			errors.add(new Message("email", "Emailadresse nicht gültig"));
		}
		if(StringUtils.isBlank(geburtsdatum)){
			errors.add(new Message("geburtsdatum", "Geburtsdatum nicht angegeben"));
		}
		if(StringUtils.isNotBlank(geburtsdatum)){
			try {
				dateFormat.parse(geburtsdatum);
			} catch (ParseException e) {
				errors.add(new Message("geburtsdatum", "Geburtsdatum ist nicht im richtigen Format"));
			}
		}
		if(StringUtils.isBlank(straße)){
			errors.add(new Message("straße", "Straße nicht angegeben"));
		}
		if(StringUtils.isBlank(hausNr)){
			errors.add(new Message("hausNr", "Haus-Nr nicht angegeben"));
		}
		if(!hausNr.matches(hausNrRegex)){
			errors.add(new Message("hausNr", "Haus-Nr nicht gültig"));
		}
		if(StringUtils.isBlank(plz)){
			errors.add(new Message("plz", "PLZ nicht angegeben"));
		}
		try {
			if(StringUtils.isNotBlank(plz)){
				Integer.parseInt(plz);
			}
		} catch (NumberFormatException e) {
			errors.add(new Message("plz", "PLZ ist keine Zahl"));
		}
		if(!plz.matches(plzRegex)){
			errors.add(new Message("plz", "PLZ ist keine gültige Zahl"));
		} 
		if(StringUtils.isBlank(ort)){
			errors.add(new Message("ort", "Ort nicht angegeben"));
		}
		if(StringUtils.isBlank(land)){
			errors.add(new Message("land", "Land nicht angegeben"));
		}
		if(StringUtils.isBlank(passwort)){
			errors.add(new Message("passwort", "Passwort nicht angegeben"));
		}
		if(passwort.length()<6){
			errors.add(new Message("passwort", "Passwort braucht mindestens 6 Zeichen"));
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
		p.setAdminrechte(admin == 1 ? true : false);
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
	
	public Integer getAdmin() {
		return admin;
	}

	public void setAdmin(Integer admin) {
		this.admin = admin;
	}

	public boolean getChangemode() {
		return changemode;
	}

	public void setChangemode(boolean changemode) {
		this.changemode = changemode;
	}
	
	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}
	
}
