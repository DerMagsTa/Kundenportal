package de.fom.kp.persistence;

import java.util.Comparator;

public class MesswertAblesdatumComparator implements Comparator<Messwert> {
	
	public static final String ascending = "asc";
	public static final String descending = "desc";
	
	private String aufab;
	
	public MesswertAblesdatumComparator(String aufab) {
		this.aufab = aufab;
	}
	
	public MesswertAblesdatumComparator( ) {
		this.aufab = ascending;
	}

	@Override
	public int compare(Messwert arg0, Messwert arg1) {
		Integer comp;
		switch (aufab) {
		case ascending:
			comp = arg0.getAblesedatum().compareTo(arg1.getAblesedatum());
			break;
		case descending:
			comp = arg1.getAblesedatum().compareTo(arg0.getAblesedatum());
			break;
		default:
			comp = arg0.getAblesedatum().compareTo(arg1.getAblesedatum());
			break;
		}
		return comp;
		
	}

}
