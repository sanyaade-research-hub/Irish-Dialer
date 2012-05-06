package com.prevost.irishdialer;

import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.provider.ContactsContract;

public class Contact implements IContact{

	protected final String TAG = "IrishDialer";

	protected List<ContactField> _fields = null; 
	protected List<ContactField> _enabledFields = null;
	protected String _displayName = null;
	protected String _id = null;
	
	public String getFullName() {
		return _displayName;
	}
	
	public String getId() {
		return _id;
	}
	
	public List<ContactField> getFields() {
		return _fields;
	}
	
	public List<ContactField> getEnabledFields() {
		return _enabledFields;
	}
	
	public Contact(int id, String displayName, List<ContactField> infos, SharedPreferences settings) {
		_fields = infos;
		_displayName = displayName;
		_id = Integer.toString(id);
		_fields.add(new ContactField("full_name", displayName));
		this.reloadEnabledFields(settings);
	}
	
	
	
	@Override
	public void reloadEnabledFields(SharedPreferences settings) {
		// TODO Auto-generated method stub
		ContactFieldPreferencesMap prefMap = new ContactFieldPreferencesMap(settings);
		_enabledFields = prefMap.filter(_fields);
	}

	@Override
	public List<String> getPhoneNumbers() {
		List<String> result = new ArrayList<String>();
		for (ContactField field : _fields) {
			if (field.getMimetype().equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
				result.add(field.getValue());
			}
		}
		return result;
	}
	
}
