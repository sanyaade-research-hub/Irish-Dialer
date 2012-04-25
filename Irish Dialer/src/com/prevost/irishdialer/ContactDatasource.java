package com.prevost.irishdialer;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.util.Log;

public class ContactDatasource{
	protected final String TAG = "IrishDialer";
	protected ArrayList<String> _contacts = null;
	protected Context _context = null;
	
	public ContactDatasource(Context context) {
		this._context = context;
	}
	
	public List<IContact> getAllContacts() {
		Log.v(TAG, "Loading all contacts");
    	Cursor cur = this._context.getContentResolver().query(Contacts.CONTENT_URI, 
    			new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME}, 
    			null, null, ContactsContract.Contacts.DISPLAY_NAME);
    	
    	int n = cur.getCount();
    	int id = 0;
    	ArrayList<String> infos = null;
    	String displayName = null;
    	cur.moveToFirst();
    	IContact contact = null;
    	List<IContact> contactList = new ArrayList<IContact>();
    	
    	for (int i = 0 ; (i < n) ; i++) {
    		displayName = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
    		id = cur.getInt(cur.getColumnIndex(ContactsContract.Contacts._ID));
    		//infos = Contact.getInfosFromCursorAndPreferences(getContentResolver(), settings, String.valueOf(id), displayName);
    		infos = new ArrayList<String>();
    		infos.add("OMG");
    		contact = new NumericPadContact(id, displayName, infos);
    		contactList.add(contact);
    		cur.moveToNext();
        }
    	Log.v(TAG, "Loaded " + contactList.size() + " contacts");
    	return contactList;
	}
	
}
