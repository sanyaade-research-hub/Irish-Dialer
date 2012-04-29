package com.prevost.irishdialer;

import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.provider.ContactsContract;

public class ContactFieldPreferencesMap {
	protected final String TAG = "IrishDialer";
	
	protected List<ContactFieldPreferenceEntry> _map = null;
	protected List<ContactFieldPreferenceEntry> _refMap = null;
	
	public ContactFieldPreferencesMap(SharedPreferences settings) {
		this._initRefMap();
		this._initMap(settings);
	}
	
	public boolean isEnabled(ContactField field) {
		for (ContactFieldPreferenceEntry fieldPrefEntry : _map) {
			if (field.getMimetype().equals(fieldPrefEntry.getMimetype())) {
				return true;
			}
		}
		return false;
	}

	public List<ContactField> filter(List<ContactField> list){
		List<ContactField> result = new ArrayList<ContactField>();
		for (ContactField contactField : list) {
			if (this.isEnabled(contactField)) {
				result.add(new ContactField(contactField.getMimetype(), contactField.getValue()));
			}
		}
		return result;
	}
	
	protected void _initMap(SharedPreferences settings) {
		this._map = new ArrayList<ContactFieldPreferenceEntry>();
		for (ContactFieldPreferenceEntry fieldPrefEntry : _refMap) {
			if (settings.getBoolean(fieldPrefEntry.getPreferenceKey(), true)) {
				_map.add(fieldPrefEntry);
			}
		}
	}
	
	protected void _initRefMap() {
		this._refMap = new ArrayList<ContactFieldPreferenceEntry>();
		this._refMap.add(new ContactFieldPreferenceEntry("full_name","full_name"));
		this._refMap.add(new ContactFieldPreferenceEntry(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE,"email"));
		this._refMap.add(new ContactFieldPreferenceEntry(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,"phone"));
		this._refMap.add(new ContactFieldPreferenceEntry(ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE,"nickname"));
		this._refMap.add(new ContactFieldPreferenceEntry(ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE,"url"));
		this._refMap.add(new ContactFieldPreferenceEntry(ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE,"instant_message"));
		this._refMap.add(new ContactFieldPreferenceEntry(ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE,"note"));
	}
}
