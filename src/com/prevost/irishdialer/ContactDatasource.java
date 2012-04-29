package com.prevost.irishdialer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

public class ContactDatasource{
	protected final String TAG = "IrishDialer";
	protected ArrayList<String> _contacts = null;
	protected String[] _infoProjection = null;
	protected String _infoSelection = null;
	protected String[] _infoSelectionArgs = null;
	protected HashMap<String, String> _where = null;
	
	public ContactDatasource() {
		reloadQueryArgs();
	}

	public List<IContact> getAllContacts(ContentResolver cr, SharedPreferences settings) {
		Log.v(TAG, "Loading all contacts");
    	Cursor cur = cr.query(
    			ContactsContract.Contacts.CONTENT_URI, 
    			new String[]{ContactsContract.Contacts._ID, 
    				ContactsContract.Contacts.DISPLAY_NAME
    			}, 
    			ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1", 
    			null, 
    			ContactsContract.Contacts.DISPLAY_NAME
    	);
    	
    	List<IContact> contactList = new ArrayList<IContact>();
    	
    	if (cur != null && cur.moveToFirst()) {
	    	List<ContactField> infos;
	    	String displayName;
	    	IContact contact;
	    	Integer id;
	        do {
	        	displayName = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
	    		id          = cur.getInt(cur.getColumnIndex(ContactsContract.Contacts._ID));
	    		infos = this.getInfos(cr, id);
	    		contact = new NumericPadContact((int)id, displayName, infos, settings);
	    		contactList.add(contact);
	        } while (cur.moveToNext());
	    }
	
    	cur.close();
    	Log.v(TAG, "Loaded " + contactList.size() + " contacts");
    	return contactList;
	}
	
	
	public List<ContactField> getInfos(ContentResolver cr, Integer contactId) {
		List<ContactField> infos = new ArrayList<ContactField>();

    	this._setSearchInfosContactId(contactId);
    	
		Cursor cur = cr.query(
			ContactsContract.Data.CONTENT_URI, _infoProjection, _infoSelection, _infoSelectionArgs, null
    	);
		
    	if (cur != null && cur.moveToFirst()) {
        	String mimeType;
        	String mimeTypeColumn;
	        do {
	        	mimeType = cur.getString(cur.getColumnIndex(ContactsContract.Data.MIMETYPE));
	        	mimeTypeColumn = _where.get(mimeType);
				infos.add(new ContactField(mimeType, cur.getString(cur.getColumnIndex(mimeTypeColumn))));
	        }while (cur.moveToNext());
	    }
        cur.close();
        return infos;
    }
	
	public void reloadQueryArgs() {
		HashSet<String> projection = new HashSet<String>();
    	StringBuilder whereStrb = new StringBuilder();
    	HashMap<String, String> where = new HashMap<String, String>(); // mimetype:mimetypecolumn 
    	
		where.put(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE,
			ContactsContract.CommonDataKinds.Email.DATA);
		where.put(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
			ContactsContract.CommonDataKinds.Phone.NUMBER);
		where.put(ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE,
			ContactsContract.CommonDataKinds.Nickname.NAME);
		where.put(ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE,
			ContactsContract.CommonDataKinds.Website.URL);
		where.put(ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE,
				ContactsContract.CommonDataKinds.Im.DATA);
		where.put(ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE,
				ContactsContract.CommonDataKinds.Note.NOTE);
	
    	whereStrb.append("( ");
    	whereStrb.append(this._generateMimetypeQuery(where.size()));
		whereStrb.append(") AND ");
		whereStrb.append(ContactsContract.Data.CONTACT_ID + " = ? ");
		
    	projection.addAll(where.values());
    	projection.add(ContactsContract.Data.MIMETYPE);
    	
    	_infoProjection = projection.toArray(new String[projection.size()]);
    	_infoSelection 	= whereStrb.toString();
    	_infoSelectionArgs = where.keySet().toArray(new String[where.size() + 1]);
    	_where = where;
	}
	
	private void _setSearchInfosContactId(Integer contactId) {
    	_infoSelectionArgs[_infoSelectionArgs.length - 1] = contactId.toString();
	}
	
	private String _generateMimetypeQuery(int n) {
		String query = ContactsContract.Data.MIMETYPE + " = ? ";
		String glue = " OR ";
		String result;
		if (n == 0) {
		    result = "";
		} else {
		    StringBuffer sb = new StringBuffer();
		    for (int i=0;i<n-1;i++) {
		    	sb.append(query);
		        sb.append(glue);
		    }
		    sb.append(query);
		    result = sb.toString();
		}
		return result;
	}
}
