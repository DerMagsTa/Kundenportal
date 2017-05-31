package de.fom.kp.persistence;

public enum Gender {
	
	U("symbol_questionmark.png"),F("user3.png"),M("user.png");
	
	public final String icon;
	
	private Gender(String icon){
		this.icon = icon;
	}

	public String getIcon() {
		return icon;
	}
}
