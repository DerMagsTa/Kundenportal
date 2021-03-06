package de.fom.kp.model;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import de.fom.kp.persistence.Verbrauchsrechner;
import de.fom.kp.persistence.Verbrauchswert;
import de.fom.kp.view.Message;

public class VerbrauchsRechnerForm {
	
	private SimpleDateFormat df;
	private NumberFormat d;
	private String from;
	private String to;
	private String mode;
	private List<VerbrauchswertForm> vl;
	private List<VerbrauchswertForm> vlchart;
	
	public List<VerbrauchswertForm> getVlchart() {
		return vlchart;
	}

	public VerbrauchsRechnerForm(SimpleDateFormat df, NumberFormat d) {
		this.d = d;
		this.df = df;
	}

	public VerbrauchsRechnerForm(HttpServletRequest request, SimpleDateFormat df, NumberFormat d) {
		this.d = (NumberFormat) d.clone();
		this.df = (SimpleDateFormat) df.clone();
		this.from = request.getParameter("Datumvon");
		this.to = request.getParameter("Datumbis");
		this.mode = request.getParameter("mode");
	
	}
	
	public VerbrauchsRechnerForm(Verbrauchsrechner v) {
		// TODO Auto-generated constructor stub
	}
	
	public Verbrauchsrechner getVerbrauchsrechner(){
		Verbrauchsrechner v = new Verbrauchsrechner();
		if (from!=null){
			try {
				v.setFrom(df.parse(from));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else {
			//Standardm��ig wird Datum nicht eingegrenzt 
			v.setFrom(new Date(1901-1900,0,1));
		}
		if(to!=null){
			try {
				v.setTo(df.parse(to));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else {
			//Standardm��ig wird Datum nicht eingegrenzt 
			v.setTo(new Date(2099-1900,11,31));
		}
		v.setMode(this.mode);
		return v;
	}

	public SimpleDateFormat getDf() {
		return df;
	}

	public void setDf(SimpleDateFormat df) {
		this.df = (SimpleDateFormat) df.clone();
	}

	public NumberFormat getD() {
		return d;
	}

	public void setD(NumberFormat d) {
		this.d = (NumberFormat) d.clone();
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public List<VerbrauchswertForm> getVl() {
		return vl;
	}

	public void setVl(List<Verbrauchswert> vl) {
		this.vl = new ArrayList<VerbrauchswertForm>();
		for (Verbrauchswert verbrauchswert : vl) {
			this.vl.add(new VerbrauchswertForm(verbrauchswert, df, d));
		}
	}
	
	public void setVlchart(List<Verbrauchswert> vl) {
		this.vlchart = new ArrayList<VerbrauchswertForm>();
		for (Verbrauchswert verbrauchswert : vl) {
			this.vlchart.add(new VerbrauchswertForm(verbrauchswert, df, d));
		}
	}

	public void validate(List<Message> errors){
		//Bei der Verbrauchsanzeige muss das Von datum kleiner dem Bis Datum sein...
		Date d_from = null;
		Date d_to = null;
		if (StringUtils.isNotBlank(from)){
			try {
				d_from = df.parse(from);
			} catch (ParseException e) {
				//Datum von kein g�ltiges Datum
				errors.add(new Message("Datumvon", "i18n.Fehler_DatumVon"));
			}
		if(StringUtils.isNotBlank(to)){
			try {
				d_to = df.parse(to);
			} catch (ParseException e) {
				//Datum bis kein g�ltiges Datum
				errors.add(new Message("Datumbis", "i18n.Fehler_DatumBis"));
			}
		}
		}
		if ((d_to != null && d_from != null)){
			
			if (d_to.compareTo(d_from) < 1){
				//das to datum ist kleiner dem from datum!	
				//Datum bis muss gr��er Datum von sein
				errors.add(new Message("Datumbis", "i18n.Fehler_DatumVonBis"));
					}
		}
	}
	
}
