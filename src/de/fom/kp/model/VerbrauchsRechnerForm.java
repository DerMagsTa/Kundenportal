package de.fom.kp.model;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.openejb.server.httpd.HttpRequest;

import de.fom.kp.persistence.Verbrauchsrechner;
import de.fom.kp.persistence.Verbrauchswert;

public class VerbrauchsRechnerForm {
	
	private DateFormat df;
	private NumberFormat d;
	private String from;
	private String to;
	private String mode;
	private List<VerbrauchswertForm> vl;
	
	public VerbrauchsRechnerForm(DateFormat df, DecimalFormat d) {
		// TODO Auto-generated constructor stub
		this.d = d;
		this.df = df;
	}

	public VerbrauchsRechnerForm(HttpServletRequest request, DateFormat df, NumberFormat d) {
		// TODO Auto-generated constructor stub
		this.d = d;
		this.df = df;
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
			v.setFrom(new Date(1901-1900,0,1));
		}
		if(to!=null){
			try {
				v.setTo(df.parse(to));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else {
			v.setTo(new Date(2099-1900,11,31));
		}
		v.setMode(this.mode);
		return v;
	}

	public DateFormat getDf() {
		return df;
	}

	public void setDf(DateFormat df) {
		this.df = df;
	}

	public NumberFormat getD() {
		return d;
	}

	public void setD(NumberFormat d) {
		this.d = d;
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
	
}
