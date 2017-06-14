package de.fom.kp.model;



import javax.servlet.http.HttpServletRequest;

import de.fom.kp.persistence.Entnahmestelle;

public class EStellenForm {
	
	private Integer id;
	private Integer personId;
	private String straﬂe;
	private String hausNr;
	private Integer plz;
	private String ort;
	private String land;
	private String hinweis;

	
	
	public EStellenForm() {
		// TODO Auto-generated constructor stub
	}
	
	public EStellenForm(de.fom.kp.persistence.Entnahmestelle e) {
		this.id = e.getId();
		this.personId = e.getPersonId();
		this.straﬂe = e.getStraﬂe();
		this.hausNr =  e.getHausNr();
		this.plz = e.getPlz();
		this.ort = e.getOrt();
		this.land = e.getLand();
		this.hinweis = e.getHinweis();
	}
	
	public EStellenForm(HttpServletRequest request) {
		this.id = Integer.parseInt(request.getParameter("id"));
		this.personId = Integer.parseInt(request.getParameter("personId"));
		this.straﬂe = request.getParameter("strasse");
		this.hausNr =  request.getParameter("hausnr");
		this.plz = Integer.parseInt(request.getParameter("plz"));
		this.ort = request.getParameter("ort");
		this.land = request.getParameter("land");
		this.hinweis =request.getParameter("hinweis");
	}
	
	public Entnahmestelle getEntnahmestelle(){
		Entnahmestelle e = new Entnahmestelle();
		e.setId(this.id);
		e.setPersonId(this.personId);
		e.setStraﬂe(this.straﬂe);
		e.setHausNr(hausNr);
		e.setOrt(ort);
		e.setPlz(plz);
		e.setLand(land);
		e.setHinweis(hinweis);
		return e;
		
	}
}
