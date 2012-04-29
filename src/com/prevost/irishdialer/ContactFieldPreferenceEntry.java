package com.prevost.irishdialer;

public class ContactFieldPreferenceEntry {
	protected String _preferenceKey;
	protected String _mimetype;
	
	public ContactFieldPreferenceEntry(String mimetype, String preferenceKey) {
		this._preferenceKey = preferenceKey;
		this._mimetype = mimetype;
	}
	
	public String getPreferenceKey() {
		return _preferenceKey;
	}
	public String getMimetype() {
		return _mimetype;
	}
}
