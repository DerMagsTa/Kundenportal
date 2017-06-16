package de.fom.kp.model;



import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import de.fom.kp.persistence.Entnahmestelle;
import de.fom.kp.persistence.Messwert;
import de.fom.kp.persistence.MesswertAblesdatumComparator;
import de.fom.kp.view.Message;

public class MesswerteForm {
	
	private DateFormat dateFormat;
	private NumberFormat decimalFormat;
	
	private Integer id;
	private Integer zaehlerId;
	private double messwert;
	private Date ablesedatum;
	
	
	
	public MesswerteForm(DateFormat dateFormat, NumberFormat decimalFormat) {
		this.dateFormat = dateFormat;
		this.decimalFormat = decimalFormat;
		this.id = null;
		this.zaehlerId = null;
		this.messwert = 0;
		this.ablesedatum = null;
	}
	
	public MesswerteForm(de.fom.kp.persistence.Messwert m, DateFormat dateFormat, NumberFormat decimalFormat) {
		this.dateFormat = dateFormat;
		this.decimalFormat = decimalFormat;
		
		this.id = m.getId();
		this.zaehlerId = m.getZaehlerId();
		this.messwert = m.getMesswert();
		this.ablesedatum = m.getAblesedatum();
	}
	
	public MesswerteForm(HttpServletRequest request, DateFormat dateFormat, NumberFormat decimalFormat) {
		if(StringUtils.isNotBlank(request.getParameter("id"))){
			this.id = Integer.parseInt(request.getParameter("id"));
		} else {
			this.id = null;
		}
		this.zaehlerId = Integer.parseInt(request.getParameter("zaehlerId"));
		this.messwert = Double.parseDouble(request.getParameter("messwert"));
		try {
			this.ablesedatum = dateFormat.parse(request.getParameter("ablesedatum"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public Messwert getMesswertClass(){
		Messwert m = new Messwert();
		m.setId(id);
		m.setZaehlerId(zaehlerId);
		m.setAblesedatum(this.ablesedatum);
		m.setMesswert(this.messwert);
		
		return m;
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getZaehlerId() {
		return zaehlerId;
	}

	public void setZaehlerId(Integer zaehlerId) {
		this.zaehlerId = zaehlerId;
	}

	public Date getAblesedatum() {
		return ablesedatum;
	}

	public void setAblesedatum(Date ablesedatum) {
		this.ablesedatum = ablesedatum;
	}

	public double getMesswert () {
		return messwert;
	}
	
	public void setMesswert(double messwert) {
		this.messwert = messwert;
	}

	public void validate(List<Message> errors, List<Messwert> mList){ 
		Messwert w = this.getMesswertClass();
		// die Liste der Messwerte ist erforderlich, da wir nur im Kontext mit den anderen Werten validierne können
		Collections.sort(mList, new MesswertAblesdatumComparator());
		//nachst älteren und jüngeren Messwert ermitteln und prüfen, ob der Eingegeben Messwert zwischen den beiden liegt
		//wichtig: die Liste muss aufsteigend nach ablesedatum sortiert sein, damit das folgende coding funktioniert.
		Messwert last = new Messwert();
		Messwert next = new Messwert();
		for (Messwert m : mList) {
			if (m.getAblesedatum().before(w.getAblesedatum())){
				last = m;
			}
			if (m.getAblesedatum().after(w.getAblesedatum())){
				if (next.getId() == null ){
					next = m;
				}
			}
			if (m.getAblesedatum().equals(w.getAblesedatum())&&w.getId()==null){
				//der Messwert soll neu angelegt werden, aber zu dem Datum gibt es schon einen Wert!
				errors.add(new Message("ablesedatum", "Zum angegebene Datum ist bereits ein Messwert gepflegt"));
			}
		}
		if(last.getId()!=null){
			if (last.getMesswert() > w.getMesswert()){
				errors.add(new Message("messwert", "Der angegebene Messwert ist kleiner dem vorherigen"));
			}
		}
		if(next.getId()!=null){
			if (next.getMesswert() < w.getMesswert()){
				errors.add(new Message("messwert", "Der angegebene Messwert ist größer dem nachfolgenden"));
			}
		}
	}
}
