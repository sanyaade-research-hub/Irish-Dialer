package com.prevost.irishdialer;

public class ContactField {

	protected String _value;
	protected String _mimetype;
	
	public ContactField(String mimetype, String value) {
		this._value = value;
		this._mimetype = mimetype;
	}
	
	public String getValue() {
		return _value;
	}
	public String getMimetype() {
		return _mimetype;
	}
}
