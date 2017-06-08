package de.fom.kp.persistence;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Verbrauchsrechner {
	
	public static final String con_mode_month = "month"; //Berechne Verbauch je Monat 
	public static final String con_mode_for_each = "each"; //Berechne Verbrauch zwischen den einzelnen Messwerten
	
	private static final Integer con_brennwert = 8;
	private static final Double con_zustandszahl = 0.95;
	
	private Zaehler z;
	private Date from;
	private Date to;
	private String u;
	
	public Verbrauchsrechner() {
		// TODO Auto-generated constructor stub
	}
	
	public List<Verbrauchswert> ListVerbrauch(String mode){
		List<Verbrauchswert> vList = new ArrayList<Verbrauchswert>();
				
		switch (mode) {	
		case  con_mode_month:
			vList = this.calc_by_month();
			break;
		case  con_mode_for_each:
			vList = this.calc_for_each();
			break;
		default:
			break;
		}
		return vList;
		
	}
	
	@SuppressWarnings("deprecation")
	private List<Verbrauchswert> calc_by_month(){
		List<Verbrauchswert> vList = new ArrayList<Verbrauchswert>();
		List<Messwert> mList = z.getmList(this.from, this.to);
		List<Integer> monate = new ArrayList<Integer>();
		
		//Welche Monate gibt es im Zeitraum?
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
			e.setUnit(this.getUnit());
			vList.add(e);
		}
		
		
		return vList;
	}
	
	private List<Verbrauchswert> calc_for_each(){
		List<Verbrauchswert> vList = new ArrayList<Verbrauchswert>();
		List<Messwert> mList = z.getmList(this.from, this.to);
		Messwert last = null;		
		for (Messwert m : mList) {
			if (last != null){
				Verbrauchswert e = new Verbrauchswert();
				e.setzId(this.z.getId());
				e.setFrom(last.getAblesedatum());
				e.setTo(m.getAblesedatum());
				e.setVerbrauch(this.berechneVerbrauch(m.getMesswert(), last.getMesswert()));
				e.setUnit(this.getUnit());
				vList.add(e);
				last = m;
			}else{ // beim 1. Messwert kann noch kein Verbrauch berechnet werden
				last = m;
			}
		}
		
		return vList;
	}

	private String getUnit(){
		String unit = null;
		if (u == null){

		switch (z.getEnergieArt()){
		case "Strom":
			unit = "kWh";
			break;
		case "Gas":
			unit = "kWh"; //Messwerte für Gas werden m3 angegeben aber der Verbrauch in kWh umgerechnet.
			break;
		case "Wasser":
			unit = "m3";
			break;
		default:
			break;
		}
		}
		this.u = unit;
	return u;
	}
	
	private Double berechneVerbrauch(Double high, Double low){
		Double verbrauch = null;
		verbrauch = high - low;
		if (this.z.getEnergieArt()=="Gas"){
			verbrauch = verbrauch * con_brennwert * con_zustandszahl;
		}
		return verbrauch;
	}
	
}
