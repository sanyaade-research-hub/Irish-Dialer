package com.prevost.irishdialer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.SharedPreferences;

public class NumericPadContactSearchIndex implements ISearchIndex<IContact> {
	protected final String TAG = "IrishDialer";

	protected HashMap<String, NumericPadContact> _contactMap = new HashMap<String, NumericPadContact>();
	protected NumericPadContactMatch _match = new NumericPadContactMatch(); 
	
	public List<IContact> search(String querystr) {
		List<IContact> res = new ArrayList<IContact>(); 
		for (Map.Entry<String, NumericPadContact> entry : _contactMap.entrySet()){
			if (this._match.match(entry.getValue(), querystr)) {
				res.add(entry.getValue());
			}
		}
		return res;
	}
	
	@Override
	public void add(IContact c) {
		NumericPadContact contact = (NumericPadContact) c;
		_contactMap.put(contact.getId(), contact);
	}

	@Override
	public void reloadEnabledContactsFields(SharedPreferences settings) {
		for (IContact contact : _contactMap.values()) {
			contact.reloadEnabledFields(settings);
		}
	}
	
}
