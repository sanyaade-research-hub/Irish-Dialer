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
	protected NumericPadContactMatch _match = new NumericPadContactMatch();

	public NumericPadContact(int id, String displayName, List<ContactField> infos, SharedPreferences settings) {
		super(id, displayName, infos, settings);
		_computeNumerified();
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
		if (_match != null) {
			StringBuilder sb = new StringBuilder();
			for (ContactField field : this.getEnabledFields()) {
				sb.append(_match.numerify(field.getValue()));
				sb.append(IMPOSSIBLE_TO_MATCH_CHAR);
			}
			this._numerified = sb.toString();
		}
	}
}
