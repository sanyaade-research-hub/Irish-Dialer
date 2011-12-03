package com.prevost.irishdialer;

import java.util.HashMap;
import java.text.Normalizer;

public class NumericPadMatch extends AbstractMatch {

	protected static HashMap<String, String> _pad = null;
	
	public NumericPadMatch(String match) {
		super(match);
	}
	
	@Override
	public boolean match(String s) {
		return (_numerify(s).indexOf(getMatch()) >= 0);
	}

	protected String _numerify(CharSequence s) {
		CharSequence norm = Normalizer
				.normalize(s, Normalizer.Form.NFD) 
				.replaceAll("[^\\p{ASCII}]", "")
				.toUpperCase();
		
		StringBuilder res = new StringBuilder();
		String c = null;
		
		for (int i = 0 ; i < norm.length() ; i++) {
			c = _pad.get(norm.charAt(i));
			if (c != null) {
				res.append(c);
			}
		}
		
		return res.toString();
	}
	
	protected void _initPadHashMap() {
		_pad =  new HashMap<String, String>();
		_pad.put(".", "1"); _pad.put(",", "1"); _pad.put("'", "1");
		_pad.put("A", "2"); _pad.put("B", "2"); _pad.put("C", "2");
		_pad.put("D", "3"); _pad.put("E", "3"); _pad.put("F", "3");
		_pad.put("G", "4"); _pad.put("H", "4"); _pad.put("I", "4");
		_pad.put("J", "5"); _pad.put("K", "5"); _pad.put("L", "5");
		_pad.put("M", "6"); _pad.put("N", "6"); _pad.put("O", "6");
		_pad.put("P", "7"); _pad.put("Q", "7"); _pad.put("R", "7"); _pad.put("S", "7");
		_pad.put("T", "8"); _pad.put("U", "8"); _pad.put("V", "8");
		_pad.put("W", "9"); _pad.put("X", "9"); _pad.put("Y", "9"); _pad.put("Z", "9");
	}
}
