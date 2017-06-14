package de.fom.kp.model;



import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import de.fom.kp.persistence.Entnahmestelle;
import de.fom.kp.persistence.Messwert;
import de.fom.kp.persistence.MesswertAblesdatumComparator;
import de.fom.kp.view.Message;

public class MesstellenForm {
	
	private DateFormat dateFormat;
	private NumberFormat decimalFormat;
	
	private Integer id;
	private Integer zaehlerId;
	private String messwert;
	private String ablesedatum;
	
	
	
	public MesstellenForm(DateFormat dateFormat, NumberFormat decimalFormat) {
		this.dateFormat = dateFormat;
		this.decimalFormat = decimalFormat;
	}
	
	public MesstellenForm(de.fom.kp.persistence.Messwert m, DateFormat dateFormat, NumberFormat decimalFormat) {
		this.dateFormat = dateFormat;
		this.decimalFormat = decimalFormat;
		
		this.id = m.getId();
		this.zaehlerId = m.getZaehlerId();
		this.messwert = decimalFormat.format(m.getMesswert());
		this.ablesedatum = dateFormat.format(m.getAblesedatum());
	}
	
	public MesstellenForm(HttpServletRequest request, DateFormat dateFormat, NumberFormat decimalFormat) {
		this.id = Integer.parseInt(request.getParameter("id"));
		this.zaehlerId = Integer.parseInt(request.getParameter("zaehlerId"));
		this.messwert = request.getParameter("messwert");
		this.ablesedatum = request.getParameter("ablesedatum");
	}
	
	public Messwert getMesswert(){
		Messwert m = new Messwert();
		m.setId(id);
		m.setZaehlerId(zaehlerId);
		try {
			m.setAblesedatum(dateFormat.parse(this.ablesedatum));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			m.setMesswert((Double) decimalFormat.parse(this.messwert));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return m;
		
	}
	
	public void validate(List<Message> errors, List<Messwert> mList){ 
		Messwert w = this.getMesswert();
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
				if (next == null ){
					next = m;
				}
			}
			if (m.getAblesedatum().equals(w.getAblesedatum())&&w.getId()==null){
				//der Messwert soll neu angelegt werden, aber zu dem Datum gibt es schon einen Wert!
				errors.add(new Message("ablesedatum", "Zum angegebene Datum ist bereits ein Messwert gepflegt"));
			}
		}
		if(last!=null){
			if (last.getMesswert() > w.getMesswert()){
				errors.add(new Message("messwert", "Der angegebene Messwert ist kleiner dem vorherigen"));
			}
		}
		if(next!=null){
			if (next.getMesswert() < w.getMesswert()){
				errors.add(new Message("messwert", "Der angegebene Messwert ist größer dem nachfolgenden"));
			}
		}
	}
}
