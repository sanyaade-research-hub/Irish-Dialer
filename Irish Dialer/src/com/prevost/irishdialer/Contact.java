package com.prevost.irishdialer;

import java.util.ArrayList;

public class Contact {

	protected ArrayList<String> _infos = null; 
	protected String _displayName = null;
	protected int _id = -1;
	
	public String getDisplayName() {
		return _displayName;
	}
	
	public int getId() {
		return _id;
	}
	
	public ArrayList<String> getInfos() {
		return _infos;
	}
	
	public Contact(int id, String displayName, ArrayList<String> contactInfos) {
		_infos = contactInfos;
		_displayName = displayName;
		_id = id;
	}
	
	public boolean match(AbstractMatch match) {
		for (String s : _infos) {
			if (match.match(s)) {
				return true;
			}
		}
		return false;
	}
}
