package net.osomahe.photorenamer.util;

import java.util.prefs.Preferences;

public class PreferencesUtils {

	public static String getValue(String key){
		Preferences pref = Preferences.userRoot().node("PhotoRenamer");
		return pref.get(key, null);
	}
	
	public static void saveValue(String key, String value){
		Preferences.userRoot().node("PhotoRenamer").put(key, value);
	}
	
}
