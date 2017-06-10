package de.fom.kp.model;



import javax.servlet.http.HttpServletRequest;

import de.fom.kp.persistence.Entnahmestelle;

public class EStellenForm {

	public EStellenForm() {
		// TODO Auto-generated constructor stub
	}
	
	public EStellenForm(de.fom.kp.persistence.Entnahmestelle e) {
		
	}
	
	public EStellenForm(HttpServletRequest request) {
		
	}
	
	public Entnahmestelle getEntnahmestelle(){
		Entnahmestelle e = new Entnahmestelle();
		return e;
	}
}
