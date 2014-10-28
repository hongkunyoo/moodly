package com.pinthecloud.moodly.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonUtil {
	public static String getString(JsonObject jo, String key) {
		JsonElement je = jo.get(key);
		if (je == null) return "";
		return je.getAsString();
	}
	public static int getInt(JsonObject jo, String key) {
		JsonElement je = jo.get(key);
		if (je == null) return -1;
		return je.getAsInt();
	}
	public static float getFloat(JsonObject jo, String key) {
		JsonElement je = jo.get(key);
		if (je == null) return 0;
		return je.getAsFloat();
	}
	public static double getDouble(JsonObject jo, String key) {
		JsonElement je = jo.get(key);
		if (je == null) return 0;
		return je.getAsDouble();
	}
	public static boolean getBoolean(JsonObject jo, String key) {
		JsonElement je = jo.get(key);
		if (je == null) return false;
		return je.getAsBoolean();
	}
}
