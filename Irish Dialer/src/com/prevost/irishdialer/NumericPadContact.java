/**
 * 
 */
package com.prevost.irishdialer;

import java.util.ArrayList;

/**
 * @author titoune
 *
 */
public class NumericPadContact extends Contact {

	public static final String IMPOSSIBLE_TO_MATCH_CHAR = "^";
	protected String _numerified = "";
	protected NumericPadContactMatch _match = new NumericPadContactMatch(); 
	
	/**
	 * @param id
	 * @param displayName
	 * @param contactInfos
	 */
	public NumericPadContact(int id, String displayName, ArrayList<String> contactInfos) {
		super(id, displayName, contactInfos);
		for (String field : this.getFields()) {
			this._numerified += _match.numerify(field);
			this._numerified += IMPOSSIBLE_TO_MATCH_CHAR;
		}
	}
	
	public String getNumerifiedString() {
		return _numerified;
	}
}
