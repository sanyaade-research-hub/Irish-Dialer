/**
 * 
 */
package com.prevost.irishdialer;

import java.util.ArrayList;

/**
 * @author titoune
 *
 */
public class NumericPadContactAdapter implements IContact {

	public static final String IMPOSSIBLE_TO_MATCH_CHAR = "^";
	protected String _numerified = new String("");
	protected IContact _contact = null;
	protected NumericPadContactMatch _match = new NumericPadContactMatch(); 
	
	/**
	 * @param id
	 * @param displayName
	 * @param contactInfos
	 */
	public NumericPadContactAdapter(IContact contact) {
		this._contact = contact;
		for (String field : contact.getFields()) {
			this._numerified += _match.numerify(field);
			this._numerified += IMPOSSIBLE_TO_MATCH_CHAR;
		}
	}
	
	public String getNumerifiedString() {
		return _numerified;
	}

	@Override
	public ArrayList<String> getFields() {
		return _contact.getFields();
	}

	@Override
	public String getFullName() {
		return _contact.getFullName();
	}

	@Override
	public String getId() {
		return _contact.getId();
	}

}
