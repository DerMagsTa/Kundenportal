package de.fom.kp.model;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import de.fom.kp.view.Message;

public class PasswortForm {
	
	private String passwort_alt;
	private String passwort_neu;
	private String passwort_wiederholung;

	
	public PasswortForm() {
		
	}

	
	public PasswortForm(HttpServletRequest request) {

		this.passwort_alt = request.getParameter("passwort_alt");
		this.passwort_neu = request.getParameter("passwort_neu");
		this.passwort_wiederholung = request.getParameter("passwort_wiederholung");

	}

	public void validate(List<Message> errors) {
		
		if(StringUtils.isBlank(passwort_alt)){
			errors.add(new Message("passwort_alt", "i18n.Fehler_Passwort_alt"));
		}
		if(StringUtils.isBlank(passwort_neu)){
			errors.add(new Message("passwort_neu", "i18n.Fehler_Passwort_neu"));
		}
		if(StringUtils.isBlank(passwort_wiederholung)){
			errors.add(new Message("passwort_wiederholung", "i18n.Fehler_Passwort_wiederholung"));
		}
		if(passwort_neu.length()<6){
			errors.add(new Message("passwort_neu", "i18n.Fehler_Passwort2"));
		}
		if(!passwort_neu.equals(passwort_wiederholung)){
			errors.add(new Message("passwort_wiederholung", "i18n.Fehler_Passwort_neu_wiederholung"));
		}
	}

	public String getPasswort_alt() {
		return passwort_alt;
	}

	public void setPasswort_alt(String passwort_alt) {
		this.passwort_alt = passwort_alt;
	}

	public String getPasswort_neu() {
		return passwort_neu;
	}

	public void setPasswort_neu(String passwort_neu) {
		this.passwort_neu = passwort_neu;
	}

	public String getPasswort_wiederholung() {
		return passwort_wiederholung;
	}

	public void setPasswort_wiederholung(String passwort_wiederholung) {
		this.passwort_wiederholung = passwort_wiederholung;
	}
		
}
