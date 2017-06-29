package de.fom.kp.persistence;

import java.util.Comparator;

public class VerbrauchswertComparator implements Comparator<Verbrauchswert> {
	
	public static final String ascending = "asc";
	public static final String descending = "desc";
	
	public static final String field_date_from = "date_from";
	public static final String field_verbrauch = "verbrauch";
	
	private String aufab;
	private String field;
	
	public VerbrauchswertComparator(String aufab, String field) {
		// TODO Auto-generated constructor stub
		this.aufab = aufab;
		this.field = field;
	}
	
	public VerbrauchswertComparator( ) {
		// TODO Auto-generated constructor stub
		this.aufab = descending;
		this.field = field_date_from;
	}

	@Override
	public int compare(Verbrauchswert arg0, Verbrauchswert arg1) {
		// TODO Auto-generated method stub
		Integer comp;
		Verbrauchswert v0;
		Verbrauchswert v1;
		switch (aufab) {
		case ascending:
			v0 = arg0;
			v1 = arg1;
			break;
		case descending:
			v0 = arg1;
			v1 = arg0;
			break;
		default:
			v0 = arg0;
			v1 = arg1;
			break;
		}
		
		switch (field) {
		case field_date_from:
			comp = compareByDateFrom(v0, v1);
			break;
		case field_verbrauch:
			comp = compareByVerbrauch(v0, v1);
			break;
		default:
			comp = compareByDateFrom(v0, v1);
			break;
		}
		
		return comp;
	}
	
	private int compareByVerbrauch(Verbrauchswert arg0, Verbrauchswert arg1){
		return arg0.getVerbrauch().compareTo(arg1.getVerbrauch());
	}
	
	private int compareByDateFrom(Verbrauchswert arg0, Verbrauchswert arg1){
		return arg0.getFrom().compareTo(arg1.getFrom());
	}

}
