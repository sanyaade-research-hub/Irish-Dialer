package com.prevost.irishdialer;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;

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

	public static ArrayList<String> getInfosFromCursorAndPreferences(ContentResolver cr, Context context, Cursor cur, String fullName) {
    	ArrayList<String> infos = new ArrayList<String>();
    	SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        
    	String contactId = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
    	
    	if (settings.getBoolean("full_name", true)) {
        	if (null != fullName) {
        		infos.add(fullName);
        	} 
        }
    	
    	if (settings.getBoolean("email", true)) {
    		infos.addAll(_getContactData(cr, contactId, 
    				ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE, 
    				ContactsContract.CommonDataKinds.Email.DATA));
    	}
    	
    	if (settings.getBoolean("phone", true)) {
    		infos.addAll(_getContactData(cr, contactId, 
    				ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE, 
    				ContactsContract.CommonDataKinds.Phone.NUMBER));
    	}
    	
    	if (settings.getBoolean("nickname", true)) {
    		infos.addAll(_getContactData(cr, contactId, 
    				ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE, 
    				ContactsContract.CommonDataKinds.Nickname.NAME));
    	}
    	
    	if (settings.getBoolean("url", true)) {
 	       infos.addAll(_getContactData(cr, contactId, 
 	    		   ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE, 
 	    		   ContactsContract.CommonDataKinds.Website.URL));
    	}
    	
    	if (settings.getBoolean("instant_message", true)) {
    		infos.addAll(_getContactData(cr, contactId, 
    				ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE, 
    				ContactsContract.CommonDataKinds.Im.DATA));
    	}
    	
    	if (settings.getBoolean("note", true)) {
    		infos.addAll(_getContactData(cr, contactId, 
    				ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE, 
    				ContactsContract.CommonDataKinds.Note.NOTE));
    	}
    	
    	
        return infos;
    }
    

	protected static ArrayList<String> _getContactData(ContentResolver cr, String contactId, String contentItemType, String dataField) {
		ArrayList<String> infos = new ArrayList<String>();
		Cursor cur = cr.query(
			ContactsContract.Data.CONTENT_URI , null, 
			ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?", 
 		    new String[]{contactId, contentItemType}, null);
        while (cur.moveToNext()) {
        	  String info = cur.getString(cur.getColumnIndex(dataField));
        	  if (null != info) {
        		  infos.add(info);
        	  }
        }
        cur.close();
		return infos;
	}
	
}
