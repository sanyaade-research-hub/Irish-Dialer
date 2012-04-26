package com.prevost.irishdialer;

public interface IContactMatch{
	
	/**
	 * @param c Contact Contact to match 
	 * @param s String to be matched
	 * @return true if s matches c, false otherwise
	 */
	public boolean match(IContact c, String s);
}
