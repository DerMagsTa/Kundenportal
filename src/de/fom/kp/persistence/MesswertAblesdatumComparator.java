package de.fom.kp.persistence;

import java.util.Comparator;

public class MesswertAblesdatumComparator implements Comparator<Messwert> {

	public MesswertAblesdatumComparator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Messwert arg0, Messwert arg1) {
		// TODO Auto-generated method stub
		return arg0.getAblesedatum().compareTo(arg1.getAblesedatum());
	}

}
