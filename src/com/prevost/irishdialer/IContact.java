package com.prevost.irishdialer;

import java.util.List;

import android.content.SharedPreferences;

public interface IContact {
	public List<ContactField> getFields();
	public List<ContactField> getEnabledFields();
	public void reloadEnabledFields(SharedPreferences settings);
	public String getFullName();
	public String getId();
	public List<String> getPhoneNumbers();
}
