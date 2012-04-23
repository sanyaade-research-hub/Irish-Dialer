package com.prevost.irishdialer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.SharedPreferences;

public class NumericPadContactSearchIndex implements IContactSearchIndex {
	protected final String TAG = "IrishDialer";

	protected HashMap<String, NumericPadContactAdapter> _contactMap = new HashMap<String, NumericPadContactAdapter>();
	protected SharedPreferences _settings = null;
	protected NumericPadContactMatch _match = new NumericPadContactMatch(); 
	
	public NumericPadContactSearchIndex(SharedPreferences settings) {
		this._settings = settings;
	}
	
	public List<IContact> search(String querystr) {
		List<IContact> res = new ArrayList<IContact>(); 
		for (Map.Entry<String, NumericPadContactAdapter> entry : _contactMap.entrySet()){
			if (this._match.match(entry.getValue(), querystr)) {
				res.add(entry.getValue());
			}
		}
		return res;
	}
	
	
	
	public void addContact(IContact c, SharedPreferences settings) {
		NumericPadContactAdapter contactAdapter = new NumericPadContactAdapter(c);
		_contactMap.put(contactAdapter.getId(), contactAdapter);
	}
	
}
