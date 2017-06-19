package de.fom.kp.model;



import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import de.fom.kp.persistence.Messwert;
import de.fom.kp.persistence.MesswertAblesdatumComparator;
import de.fom.kp.view.Message;

public class MesswerteForm {
	
	private SimpleDateFormat dateFormat;
	private NumberFormat decimalFormat;
	
	private Integer id;
	private Integer zaehlerId;
	private String messwert;
	private String ablesedatum;
	
	
	
	public MesswerteForm(SimpleDateFormat dateFormat, NumberFormat decimalFormat) {
		this.dateFormat = dateFormat;
		this.decimalFormat = decimalFormat;
		this.id = null;
		this.zaehlerId = null;
		this.messwert = "0";
		this.ablesedatum = null;
	}
	
	public MesswerteForm(de.fom.kp.persistence.Messwert m, SimpleDateFormat dateFormat, NumberFormat decimalFormat) {
		this.dateFormat = dateFormat;
		this.decimalFormat = decimalFormat;
		
		this.id = m.getId();
		this.zaehlerId = m.getZaehlerId();
		this.messwert = decimalFormat.format(m.getMesswert());
		this.ablesedatum = dateFormat.format(m.getAblesedatum());
		
	}
	
	public MesswerteForm(HttpServletRequest request, SimpleDateFormat dateFormat, NumberFormat decimalFormat) {
		
		this.dateFormat = dateFormat;
		this.decimalFormat = decimalFormat;
		
		if(StringUtils.isNotBlank(request.getParameter("id"))){
			this.id = Integer.parseInt(request.getParameter("id"));
		} else {
			this.id = null;
		}
		this.zaehlerId = Integer.parseInt(request.getParameter("zaehlerId"));
		this.messwert = request.getParameter("messwert");
		this.ablesedatum = request.getParameter("ablesedatum");

	}
	
	public Messwert getMesswertClass(){
		Messwert m = new Messwert();
		m.setId(id);
		m.setZaehlerId(zaehlerId);
		try {
			m.setAblesedatum(dateFormat.parse(this.ablesedatum));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			m.setMesswert((double) decimalFormat.parse(this.messwert).doubleValue());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
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

	public String getAblesedatum() {
		return ablesedatum;
	}

	public void setAblesedatum(String ablesedatum) {
		this.ablesedatum = ablesedatum;
	}

	public String getMesswert () {
		return messwert;
	}
	
	public void setMesswert(String messwert) {
		this.messwert = messwert;
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
	
	public void validate(List<Message> errors, List<Messwert> mList){ 
		if(StringUtils.isBlank(messwert)){
			errors.add(new Message("messwert", "Messwert nicht angegeben"));
		}
		if(StringUtils.isBlank(ablesedatum)){
			errors.add(new Message("ablesedatum", "Ablesedatum nicht angegeben"));
		}
		
		if(StringUtils.isNotBlank(messwert)){
			try {
				decimalFormat.parse(messwert);
			} catch (ParseException e) {
				errors.add(new Message("messwert", "Messwert ist keine gültige Zahl / im falschem Format"));
			}
		}
		if(StringUtils.isNotBlank(ablesedatum)){
			try {
				dateFormat.parse(ablesedatum);
			} catch (ParseException e) {
				errors.add(new Message("ablesedatum", "Ablesedatum ist nicht im richtigen Format"));
			}
		}

		if(errors.isEmpty()){
				
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
}
