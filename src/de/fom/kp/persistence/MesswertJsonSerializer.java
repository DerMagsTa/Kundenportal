package de.fom.kp.persistence;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.NumberFormat;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class MesswertJsonSerializer implements JsonSerializer<Messwert> {
	
	private DateFormat df;
	private NumberFormat nf;
	
	public MesswertJsonSerializer(DateFormat df, NumberFormat nf) {
		// TODO Auto-generated constructor stub
		this.df = (DateFormat) df.clone();
		this.nf = (NumberFormat) nf.clone();
	}

	@Override
	public JsonElement serialize(Messwert m, Type arg1, JsonSerializationContext arg2) {
		// TODO Auto-generated method stub
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
		JsonObject result = new JsonObject();
        result.add("ablesedatum", new JsonPrimitive(df.format(m.getAblesedatum())));
        result.add("messwert", new JsonPrimitive(nf.format(m.getMesswert())));
        result.add("id", new JsonPrimitive(m.getId()));
        return result;
	}

}
