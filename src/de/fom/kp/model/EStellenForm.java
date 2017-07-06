package de.fom.kp.model;



import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import de.fom.kp.persistence.Entnahmestelle;
import de.fom.kp.view.Message;

public class EStellenForm {
	
	private Integer id;
	private Integer personId;
	private String straﬂe;
	private String hausNr;
	private String plz;
	private String ort;
	private String land;
	private String hinweis;
	
	//Regex f¸r: 0 darf nur im ersten Zeichen ODER im zweiten Zeichen vorkommen (00123 ist keine PLZ 01234 schon und 10234 auch)
	private String plzRegex = "^([0]{1}[1-9]{1}|[1-9]{1}[0-9]{1})[0-9]{3}$";
	//Regex f¸r: Am Anfang muss eine Zahl stehen, rest beliebige Zeichen
	private String hausNrRegex = "^[\\d].*";
	
	
	public EStellenForm() {
		// TODO Auto-generated constructor stub
	}
	
	public EStellenForm(de.fom.kp.persistence.Entnahmestelle e) {
		this.id = e.getId();
		this.personId = e.getPersonId();
		this.straﬂe = e.getStraﬂe();
		this.hausNr =  e.getHausNr();
		this.plz = Integer.toString(e.getPlz());
		this.ort = e.getOrt();
		this.land = e.getLand();
		this.hinweis = e.getHinweis();
	}
	
	public EStellenForm(HttpServletRequest request) {
		if(StringUtils.isNotBlank(request.getParameter("id"))){
			this.id = Integer.parseInt(request.getParameter("id"));
		} else {
			this.id = null;
		}
		this.personId = Integer.parseInt(request.getParameter("personId"));
		this.straﬂe = request.getParameter("straﬂe");
		this.hausNr =  request.getParameter("hausNr");
		this.plz = request.getParameter("plz");
		this.ort = request.getParameter("ort");
		this.land = request.getParameter("land");
		this.hinweis =request.getParameter("hinweis");
	}
	
	public Entnahmestelle getEntnahmestelle(){
		Entnahmestelle e = new Entnahmestelle();
		e.setId(id);
		e.setPersonId(personId);
		e.setStraﬂe(straﬂe);
		e.setHausNr(hausNr);
		e.setOrt(ort);
		e.setPlz(Integer.parseInt(plz));
		e.setLand(land);
		e.setHinweis(hinweis);
		return e;
	}
	
	public void validate(List<Message> errors) {
		
		//Zur besseren Lesbarkeit sind die deutschen Fehlermeldungen jeweils als Kommentar angegeben.
		
		if(StringUtils.isBlank(straﬂe)){
			//Straﬂe nicht angegeben
			errors.add(new Message("straﬂe", "i18n.Fehler_Straﬂe"));
		}
		if(StringUtils.isBlank(hausNr)){
			//Haus-Nr nicht angegeben
			errors.add(new Message("hausNr", "i18n.Fehler_HausNr"));
		}
		if(!hausNr.matches(hausNrRegex)){
			//Haus-Nr nicht g¸ltig
			errors.add(new Message("hausNr", "i18n.Fehler_HausNr2"));
		}
		if(StringUtils.isBlank(plz)){
			//PLZ nicht angegeben
			errors.add(new Message("plz", "i18n.Fehler_PLZ"));
		}
		if(StringUtils.isBlank(ort)){
			//Ort nicht angegeben
			errors.add(new Message("ort", "i18n.Fehler_Ort"));
		}
		if(StringUtils.isBlank(land)){
			//Land nicht angegeben
			errors.add(new Message("land", "i18n.Fehler_Land"));
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
			//PLZ ist keine g¸ltige Zahl
			errors.add(new Message("plz", "i18n.Fehler_PLZ3"));
		} 
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
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

	public String getHinweis() {
		return hinweis;
	}

	public void setHinweis(String hinweis) {
		this.hinweis = hinweis;
	}
	
	
}
