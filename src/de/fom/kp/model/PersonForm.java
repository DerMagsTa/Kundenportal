package de.fom.kp.model;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import de.fom.kp.persistence.Gender;
import de.fom.kp.persistence.Interest;
import de.fom.kp.persistence.Person;

public class PersonForm {
	
	private DateFormat dateFormat;
	private NumberFormat decimalFormat;
	private Integer id;
	private String anrede;
	private String vorname;
	private String nachname;
	private String email;
	private Date geburtsdatum;
	private String straﬂe;
	private String hausNr;
	private Integer plz;
	private String ort;
	private String land;
	
	
	public PersonForm() {
		// TODO Auto-generated constructor stub
	}

	public PersonForm(de.fom.kp.persistence.Person p, DateFormat dateFormat, NumberFormat decimalFormat) {
		this.dateFormat = dateFormat;
		this.decimalFormat = decimalFormat;
		this.id =p.getId();
		this.anrede = p.getAnrede();
		this.vorname = p.getVorname();
		this.nachname = p.getNachname();
		this.email = p.getEmail();
		this.straﬂe = p.getStraﬂe();
		this.hausNr = p.getHausNr();
		this.plz = p.getPlz();
		this.ort = p.getOrt();
		this.land = p.getLand();
		
	}
	
	public PersonForm(HttpServletRequest request, DateFormat dateFormat, NumberFormat decimalFormat) {
		this.dateFormat = dateFormat;
		this.decimalFormat = decimalFormat;
		this.id = Integer.parseInt(request.getParameter("id"));
		this.anrede = request.getParameter("anrede");
		this.vorname = request.getParameter("vorname");
		this.nachname = request.getParameter("nachname");
		this.email = request.getParameter("email");
		try {
			this.geburtsdatum = this.dateFormat.parse(request.getParameter("geburtsdatum"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.straﬂe = request.getParameter("strasse");
		this.hausNr =  request.getParameter("hausnr");
		this.plz = Integer.parseInt(request.getParameter("plz"));
		this.ort = request.getParameter("ort");
		this.land = request.getParameter("land");
		
	}
	
	public Person getPerson(){
		Person p =  new Person();
		p.setId(id);
		p.setVorname(vorname);
		p.setNachname(nachname);
		p.setGeburtsdatum(geburtsdatum);
		p.setEmail(email);
		p.setPlz(plz);
		p.setStraﬂe(straﬂe);
		p.setHausNr(hausNr);
		p.setOrt(ort);
		p.setLand(land);
		return p;
	}
	
	

	
}
