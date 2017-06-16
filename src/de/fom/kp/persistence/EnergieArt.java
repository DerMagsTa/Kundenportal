package de.fom.kp.persistence;

import java.util.ArrayList;
import java.util.List;

public class EnergieArt {
	
	public static final String eart_strom = "Strom";
	public static final String eart_gas = "Gas";
	public static final String eart_wasser= "Wasser";
	
	public static final String unit_kwh ="kWh";
	public static final String unit_m3 ="m3";
	
	private String energieArt;
	private String unitVerbrauch;
	private String unitMesswert;
	
	private static List<String> EnergieArten;
	private static List<EnergieArt> EnergieArtenObj;
		
	static{
		EnergieArten = new ArrayList<String>();
		EnergieArten.add(eart_gas);
		EnergieArten.add(eart_wasser);
		EnergieArten.add(eart_strom);
		
		EnergieArtenObj = new ArrayList<EnergieArt>();
		EnergieArtenObj.add(new EnergieArt(eart_gas,unit_kwh,unit_m3));
		EnergieArtenObj.add(new EnergieArt(eart_strom,unit_kwh,unit_kwh));
		EnergieArtenObj.add(new EnergieArt(eart_wasser,unit_m3,unit_m3));
	}
	
	public static List<String> getEnergieArten(){
		return EnergieArten;
	}
	
	private EnergieArt(){
		
	}
	
	public static EnergieArt getEnergieArt(String eart){
		EnergieArt ea = new EnergieArt();
		for (EnergieArt e : EnergieArtenObj) {
			if (e.getEnergieart().equals(eart)){
				ea = e;
			}
		}
		return ea;
	}
	private EnergieArt(String energieArt, String unitVerbrauch, String unitMesswert) {
		this.energieArt = energieArt;
		this.unitVerbrauch = unitVerbrauch;
		this.unitMesswert = unitMesswert;
	}

	public String getEnergieart() {
		return energieArt;
	}

	public String getUnitVerbrauch() {
		return unitVerbrauch;
	}

	public String getUnitMesswert() {
		return unitMesswert;
	}	
	
}
