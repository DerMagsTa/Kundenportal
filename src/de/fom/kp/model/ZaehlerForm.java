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
		// TODO Auto-generated constructor stub
	}

	public ZaehlerForm(HttpServletRequest request) {
		// TODO Auto-generated constructor stub
		id = Integer.parseInt(request.getParameter("id"));
		entnahmestellenId = Integer.parseInt(request.getParameter("eid"));
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
		return null;	
	}
	
	public void validate(List<Message> errors){
		if(StringUtils.isBlank(energieArt)){
			errors.add(new Message("energieart", "Energieart nicht angegeben"));
		}else{
		if (EnergieArt.getEnergieArten().contains(energieArt)==false){
			errors.add(new Message("energieart", (energieArt+" ist keine gültige Energieart")));
		}
		if(StringUtils.isBlank(zaehlerNr)){
			errors.add(new Message("zaehlerNr", "Zähler-Nr. nicht angegeben"));
		}
		if(entnahmestellenId==null){
			errors.add(new Message("entnahemstellenId", "Zähler ist keiner Entnahmestelle zugeordnet"));
		}
		}
		
	}
	
}
