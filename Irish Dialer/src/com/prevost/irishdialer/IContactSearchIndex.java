package com.prevost.irishdialer;

import java.util.List;
import android.content.SharedPreferences;

public interface IContactSearchIndex {
	public List<IContact> search(String querystr);	
	public void addContact(IContact c, SharedPreferences settings);	
}
