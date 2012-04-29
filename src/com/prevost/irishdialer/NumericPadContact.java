/**
 * 
 */
package com.prevost.irishdialer;

import java.util.List;

import android.content.SharedPreferences;

/**
 * @author titoune
 *
 */
public class NumericPadContact extends Contact {

	public static final String IMPOSSIBLE_TO_MATCH_CHAR = "^";
	protected String _numerified = "";
	protected NumericPadContactMatch _match = null;

	public NumericPadContact(int id, String displayName, List<ContactField> infos, SharedPreferences settings) {
		super(id, displayName, infos, settings);
		_match = new NumericPadContactMatch();
	}
	
	public String getNumerifiedString() {
		return _numerified;
	}
	
	@Override
	public void reloadEnabledFields(SharedPreferences settings) {
		super.reloadEnabledFields(settings);
		_computeNumerified();
	}
	
	protected void _computeNumerified() {
		NumericPadContactMatch match = (_match == null) ? new NumericPadContactMatch() : _match; 
		for (ContactField field : this.getEnabledFields()) {
			this._numerified += match.numerify(field.getValue());
			this._numerified += IMPOSSIBLE_TO_MATCH_CHAR;
		}
	}
}
