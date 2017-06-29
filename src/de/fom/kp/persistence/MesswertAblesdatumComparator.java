package de.fom.kp.persistence;

import java.util.Comparator;

public class MesswertAblesdatumComparator implements Comparator<Messwert> {
	
	public static final String ascending = "asc";
	public static final String descending = "desc";
	
	private String aufab;
	
	public MesswertAblesdatumComparator(String aufab) {
		// TODO Auto-generated constructor stub
		this.aufab = aufab;
	}
	
	public MesswertAblesdatumComparator( ) {
		// TODO Auto-generated constructor stub
		this.aufab = ascending;
	}

	@Override
	public int compare(Messwert arg0, Messwert arg1) {
		// TODO Auto-generated method stub
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
