package de.fom.kp.persistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Verbrauchsrechner {
	
	public static final String con_mode_month = "month"; //Berechne Verbauch je Monat 
	public static final String con_mode_for_each = "each"; //Berechne Verbrauch zwischen den einzelnen Messwerten
	public static final String con_mode_for_year = "year"; //Berechne Verbrauch zwischen den einzelnen Messwerten
	
	private static final Integer con_brennwert = 8;
	private static final Double con_zustandszahl = 0.95;
	
	private Zaehler z;
	private Date from;
	private Date to;
	private String u;
	private String mode;
	
	public Verbrauchsrechner() {
		// TODO Auto-generated constructor stub
	}
	
	public Zaehler getZ() {
		return z;
	}

	public void setZ(Zaehler z) {
		this.z = z;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public Verbrauchsrechner(Zaehler z, Date from, Date to, String u) {
		this.z = z;
		this.from = from;
		this.to = to;
		this.u = u;
	}

	public List<Verbrauchswert> ListVerbrauch(String mode){
		List<Verbrauchswert> vList = new ArrayList<Verbrauchswert>();
		List<Messwert> mList = z.getmList(this.from, this.to);
		Collections.sort(mList, new MesswertAblesdatumComparator());
		
		switch (mode) {	
		case  con_mode_month:
			vList = this.calc_by_month(mList);
			break;
		case  con_mode_for_each:
			vList = this.calc_for_each(mList);
			break;
		case con_mode_for_year:
			vList = this.calc_for_year(mList);
			break;
		default:
			break;
		}
		return vList;
		
	}
	
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public List<Verbrauchswert> ListVerbrauch(){
	
		if (this.mode == null){
			this.mode = con_mode_for_each;
		}
		return this.ListVerbrauch(this.mode);
		
	}
	
	
	private List<Verbrauchswert> calc_for_year(List<Messwert> mList) {
		//Hier wird der Verbrauch je Jahr berechnet.
		//dazu wird für jedes Jahr der frühete und älteste Messwert ermittelt
		//und zwischen dieses Messerten die Differenz der Zählerstände gebildet über methode
		//this.berechneVerbrauch.
		//Bsp:: 01.01.2017 - 10 kWh; 31.12.2017 - 10000 kWh
		//Ergebnis: Zeitraum = 01.01.-31.12.2017,  Verbrauch = 9990 kWh.
		
		List<Verbrauchswert> vList = new ArrayList<Verbrauchswert>();
		List<Integer> jahre = new ArrayList<Integer>();
		
		//Zunächst die Verschiedenen Jahre im zeitraum bestimmen
		//z.B. 2017, 2016, 2015, ...
		for (Messwert m : mList) {
			Integer year = (m.getAblesedatum().getYear()+1990); //Im Integer steht jetzt z.B: 201705
			if (jahre.contains(year)==false){
				jahre.add(year);
			};
		}
		
		//Für jedes Jahr den ältesten und jüngsten Messwert bestimmen und dazu den Verbrauch berechnen
		for (Integer integer : jahre) {
			Messwert min  = null;
			Messwert max  = null;
			//jüngsten und ältesten Messwert je Jahr bestimmen
			for (Messwert m : mList) {
				//Wenn der Messwert zum aktuellen Jahr gehört
				if ((m.getAblesedatum().getYear()+1990) == integer){
					if ((min==null)||(max==null)){
						min = m;
						max= m;
					}else{
						if (min.getAblesedatum().after(m.getAblesedatum())==true){
							min = m;
						}
						if (max.getAblesedatum().before(m.getAblesedatum())==true){
							max = m;
						}
					}
				}
			}
			//Verbrauchswert berechnen
			Verbrauchswert e = new Verbrauchswert();
			e.setzId(this.z.getId());
			e.setFrom(min.getAblesedatum());
			e.setTo(max.getAblesedatum());
			e.setVerbrauch(this.berechneVerbrauch(max.getMesswert(), min.getMesswert()));
			e.setUnit(EnergieArt.getEnergieArt(this.z.getEnergieArt()).getUnitVerbrauch());
			vList.add(e);
		}
		
		return vList;
	}

	@SuppressWarnings("deprecation")
	private List<Verbrauchswert> calc_by_month(List<Messwert> mList){
		//Hier wird der Verbrauch je Monat berechnet.
		//dazu wird für jeden Monat der frühete und älteste Messwert ermittelt
		//und zwischen dieses Messerten die Differenz der Zählerstände gebildet über methode
		//this.berechneVerbrauch.
		//Bsp:: 01.01.2017 - 10 kWh; 31.01.2017 - 100 kWh
		//Ergebnis: Zeitraum = 01.01.-31.01.2017,  Verbrauch = 90 kWh.
		List<Verbrauchswert> vList = new ArrayList<Verbrauchswert>();
		List<Integer> monate = new ArrayList<Integer>();
		
		//Welche Monate gibt es im Zeitraum?
		//Diese werden als Integer gepspeichert und sind wie folgt aufgebaut: JAHR*100+MONAT
		//z.Bp.: 201702, 201701, .... 201602, 201601.
		for (Messwert m : mList) {
			Integer month = (m.getAblesedatum().getYear()+1990)*100+(m.getAblesedatum().getMonth()+1); //Im Integer steht jetzt z.B: 201705
			if (monate.contains(month)==false){
				monate.add(month);
			};
		}
		
		//Für jeden Monat den ältesten und jüngsten Messwert bestimmen und dazu den Verbrauch berechnen
		for (Integer integer : monate) {
			Messwert min  = null;
			Messwert max  = null;
			//jüngsten und ältesten Messwert je Monat bestimmen
			for (Messwert m : mList) {
				//Wenn der Messwert zum aktuellen Monat gehört
				if ((m.getAblesedatum().getYear()+1990)*100+(m.getAblesedatum().getMonth()+1) == integer){
					if ((min==null)||(max==null)){
						min = m;
						max= m;
					}else{
						if (min.getAblesedatum().after(m.getAblesedatum())==true){
							min = m;
						}
						if (max.getAblesedatum().before(m.getAblesedatum())==true){
							max = m;
						}
					}
				}
			}
			//Verbrauchswert berechnen
			Verbrauchswert e = new Verbrauchswert();
			e.setzId(this.z.getId());
			e.setFrom(min.getAblesedatum());
			e.setTo(max.getAblesedatum());
			e.setVerbrauch(this.berechneVerbrauch(max.getMesswert(), min.getMesswert()));
			e.setUnit(EnergieArt.getEnergieArt(this.z.getEnergieArt()).getUnitVerbrauch());
			vList.add(e);
		}
		
		
		return vList;
	}
	
	private List<Verbrauchswert> calc_for_each(List<Messwert> mList){
		//Hier wird der Verbauch zwischen dein einzelnnen Messwerten ermittelt.
		//dazu einfach über alle Messwerte gehen und jeweils den Verbrauch ermitteln.
		List<Verbrauchswert> vList = new ArrayList<Verbrauchswert>();
		Messwert last = null;		
		for (Messwert m : mList) {
			if (last != null){
				Verbrauchswert e = new Verbrauchswert();
				e.setzId(this.z.getId());
				e.setFrom(last.getAblesedatum());
				e.setTo(m.getAblesedatum());
				e.setVerbrauch(this.berechneVerbrauch(m.getMesswert(), last.getMesswert()));
				e.setUnit(EnergieArt.getEnergieArt(this.z.getEnergieArt()).getUnitVerbrauch());
				vList.add(e);
				last = m;
			}else{ // beim 1. Messwert kann noch kein Verbrauch berechnet werden
				last = m;
			}
		}
		
		return vList;
	}
	
	private Double berechneVerbrauch(Double high, Double low){
		Double verbrauch = null;
		verbrauch = high - low;
		if (this.z.getEnergieArt()==EnergieArt.eart_gas){
			//Im fall von Energie-Art gas muss von einem Messwert in m³ in einen
			//Verbrauchswert von kWh umgerechnet werden. Daher muss diese 
			//Formel verwendet werden
			verbrauch = verbrauch * con_brennwert * con_zustandszahl;
		}
		return verbrauch;
	}
	
}
