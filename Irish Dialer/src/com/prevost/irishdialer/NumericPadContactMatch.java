package com.prevost.irishdialer;

import java.text.Normalizer;
import java.util.HashMap;

public class NumericPadContactMatch implements IContactMatch {
	protected final String TAG = "IrishDialer";
	protected HashMap<Character, Character> _pad = null;
	
	public NumericPadContactMatch() {
		_initPadHashMap();
	}
	
	@Override
	public boolean match(IContact contact, String query) {
		NumericPadContact contactAdapter = (NumericPadContact) contact;
		int index = contactAdapter.getNumerifiedString().indexOf(query);
		if (index >= 0) {
			return true;
		}
		return false;
	}

	public String numerify(CharSequence s) {
		String numerified = null;
		if (s != null) {
			CharSequence norm = Normalizer
					.normalize(s, Normalizer.Form.NFKD) 
					.replaceAll("[^\\p{ASCII}]", "")
					.replaceAll("[^a-zA-Z0-9',-]", "")
					.toUpperCase();
			
			StringBuilder res = new StringBuilder();
			Character c = null;
			
			for (int i = 0 ; i < norm.length() ; i++) {
				c = _pad.get(norm.charAt(i));
				if (c != null) {
					res.append(c);
				}
			}
			numerified = res.toString();
		} else {
			numerified = "";
		}
		
		return numerified;
	}
	
	protected void _initPadHashMap() {
		_pad =  new HashMap<Character, Character>();
		_pad.put('.', '1'); _pad.put(',', '1'); _pad.put('\'', '1');
		_pad.put('A', '2'); _pad.put('B', '2'); _pad.put('C', '2');
		_pad.put('D', '3'); _pad.put('E', '3'); _pad.put('F', '3');
		_pad.put('G', '4'); _pad.put('H', '4'); _pad.put('I', '4');
		_pad.put('J', '5'); _pad.put('K', '5'); _pad.put('L', '5');
		_pad.put('M', '6'); _pad.put('N', '6'); _pad.put('O', '6');
		_pad.put('P', '7'); _pad.put('Q', '7'); _pad.put('R', '7'); _pad.put('S', '7');
		_pad.put('T', '8'); _pad.put('U', '8'); _pad.put('V', '8');
		_pad.put('W', '9'); _pad.put('X', '9'); _pad.put('Y', '9'); _pad.put('Z', '9');
	}
	
}
