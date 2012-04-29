package com.prevost.irishdialer;

import java.util.List;

import android.content.SharedPreferences;

public interface ISearchIndex<T> {
	public List<T> search(String querystr);	
	public void add(T object);
	public void reloadEnabledContactsFields(SharedPreferences settings);
}
