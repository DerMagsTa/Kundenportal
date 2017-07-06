package de.fom.kp.model;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import de.fom.kp.persistence.EnergieArt;
import de.fom.kp.persistence.Zaehler;
import de.fom.kp.view.Message;

public class ZaehlerForm {
	
	private Integer id;
	private Integer entnahmestellenId;
	private String  energieArt;
	private String  zaehlerNr;
	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEntnahmestellenId() {
		return entnahmestellenId;
	}

	public void setEntnahmestellenId(Integer entnahmestellenId) {
		this.entnahmestellenId = entnahmestellenId;
	}

	public String getEnergieArt() {
		return energieArt;
	}

	public void setEnergieArt(String energieArt) {
		this.energieArt = energieArt;
	}

	public String getZaehlerNr() {
		return zaehlerNr;
	}

	public void setZaehlerNr(String zaehlerNr) {
		this.zaehlerNr = zaehlerNr;
	}

	public ZaehlerForm() {

	}

	public ZaehlerForm(HttpServletRequest request) {
		if(StringUtils.isNotBlank(request.getParameter("id"))){
			id = Integer.parseInt(request.getParameter("id"));
		}else{
			id = null;
		}
		entnahmestellenId = Integer.parseInt(request.getParameter("entnahmestellenId"));
		energieArt = request.getParameter("energieArt");
		zaehlerNr =  request.getParameter("zaehlerNr");
	}

	public ZaehlerForm(Zaehler z) {
		id = z.getId();
		entnahmestellenId = z.getEntnahmestelleId();
		energieArt = z.getEnergieArt();
		zaehlerNr = z.getZaehlerNr();
	}
	
	public Zaehler getZaehler(){
		Zaehler z = new Zaehler();
		z.setId(id);
		z.setEntnahmestelleId(entnahmestellenId);
		z.setEnergieArt(energieArt);
		z.setZaehlerNr(zaehlerNr);
		return z;	
	}
	
	public void validate(List<Message> errors){
		if(StringUtils.isBlank(energieArt)){
			//Energieart nicht angegeben
			errors.add(new Message("energieart", "i18n.Fehler_Energieart"));
		}else{
		if (EnergieArt.getEnergieArten().contains(energieArt)==false){
			//Energieart ist nicht gültig
			errors.add(new Message("energieart", ("i18n.Fehler_Energieart2")));
		}
		if(StringUtils.isBlank(zaehlerNr)){
			//Zähler-Nr. nicht angegeben
			errors.add(new Message("zaehlerNr", "i18n.Fehler_ZählerNr"));
		}
		if(entnahmestellenId==null){
			//Zähler ist keiner Entnahmestelle zugeordnet
			errors.add(new Message("entnahemstellenId", "i18n.Fehler_Zuordnung"));
		}
		}
		
	}
	
}
