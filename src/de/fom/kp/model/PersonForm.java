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
	private String straﬂe;
	private String hausNr;
	private Integer plz;
	private String ort;
	private String land;
	private Integer admin;
	private boolean changemode = false;
	private String passwort;
	
	public Integer getAdmin() {
		return admin;
	}

	public void setAdmin(Integer admin) {
		this.admin = admin;
	}

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
		this.straﬂe 		= p.getStraﬂe();
		this.hausNr 		= p.getHausNr();
		this.plz 			= p.getPlz();
		this.ort 			= p.getOrt();
		this.land			= p.getLand();
		this.geburtsdatum 	= dateFormat.format(p.getGeburtsdatum());
		this.admin 			= p.isAdminrechte() == true ? 1 : 0;
		this.passwort		= p.getPasswort();
	}
	
	public PersonForm(HttpServletRequest request, SimpleDateFormat dateFormat, NumberFormat decimalFormat) {
		this.dateFormat 	= dateFormat;
		this.decimalFormat 	= decimalFormat;
		this.id 			= Integer.parseInt(request.getParameter("id"));
		this.anrede 		= request.getParameter("anrede");
		this.vorname 		= request.getParameter("vorname");
		this.nachname 		= request.getParameter("nachname");
		this.email 			= request.getParameter("email");
		this.geburtsdatum 	= request.getParameter("geburtstag");
		this.straﬂe 		= request.getParameter("strasse");
		this.hausNr 		= request.getParameter("hausnr");
		this.plz 			= Integer.parseInt(request.getParameter("plz"));
		this.ort 			= request.getParameter("ort");
		this.land 			= request.getParameter("land");
		this.admin 			= Integer.parseInt(request.getParameter("admin"));
		this.changemode 	= Boolean.parseBoolean(request.getParameter("changemode"));
		this.passwort		= request.getParameter("passwort");

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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		p.setEmail(email);
		p.setPlz(plz);
		p.setStraﬂe(straﬂe);
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

	public void validate(List<Message> errors) {
		// TODO Auto-generated method stub
		
	}
	
	

	
}
