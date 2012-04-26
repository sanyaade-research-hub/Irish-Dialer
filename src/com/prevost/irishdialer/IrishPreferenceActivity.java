package com.prevost.irishdialer;
import android.preference.PreferenceActivity;
import android.os.Bundle;


public class IrishPreferenceActivity extends PreferenceActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    addPreferencesFromResource(R.xml.preferences);
	}
}
