package com.prevost.irishdialer;

public abstract class AbstractMatch{
	
	/**
	 * Match string
	 */
	protected String _match = null; 
	
	public String getMatch() {
		return _match;
	}

	/**
	 * @param s String to be matched
	 * @return true if s matches, false otherwise 
	 */
	public abstract boolean match(String s);
	
	public AbstractMatch(String match) {
		_match = match;
	}
	
}
