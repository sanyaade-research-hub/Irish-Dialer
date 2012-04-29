package com.prevost.irishdialer;

import java.util.List;

import android.content.SharedPreferences;

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
	
	/*
	public static ArrayList<String> getInfosFromCursorAndPreferences(ContentResolver cr, SharedPreferences settings, String contactId, String fullName) {
    	ArrayList<String> infos = new ArrayList<String>();
    	
    	HashSet<String> projection = new HashSet<String>();
    	StringBuilder where = new StringBuilder();
    	ArrayList<String> whereArgs = new ArrayList<String>();
    	ArrayList<String> dataFields = new ArrayList<String>();
    	
    	if (settings.getBoolean("full_name", true)) {
        	if (null != fullName) {
        		infos.add(fullName);
        	} 
        }
    	
    	if (settings.getBoolean("email", true)) {
    		whereArgs.add(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
    		dataFields.add(ContactsContract.CommonDataKinds.Email.DATA);
    	}
    	
    	if (settings.getBoolean("phone", true)) {
    		whereArgs.add(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
    		dataFields.add(ContactsContract.CommonDataKinds.Phone.NUMBER);
    	}
    	
    	if (settings.getBoolean("nickname", true)) {
    		whereArgs.add(ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE);
    		dataFields.add(ContactsContract.CommonDataKinds.Nickname.NAME);
    	}
    	
    	if (settings.getBoolean("url", true)) {
    		whereArgs.add(ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
    		dataFields.add(ContactsContract.CommonDataKinds.Website.URL);
    	}
    	
    	if (settings.getBoolean("instant_message", true)) {
    		whereArgs.add(ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
    		dataFields.add(ContactsContract.CommonDataKinds.Im.DATA);
    	}
    	
    	if (settings.getBoolean("note", true)) {
    		whereArgs.add(ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE);
    		dataFields.add(ContactsContract.CommonDataKinds.Note.NOTE);
    	}
    	
    	
    	if (whereArgs.size() > 0){
    		where.append(ContactsContract.Data.CONTACT_ID + " = ? ");
    		where.append("AND ( ");
    		where.append(ContactsContract.Data.MIMETYPE + " = ? ");
    		for (int i = 1 ; i < whereArgs.size() ; ++i) {
    			where.append(" OR ");
    			where.append(ContactsContract.Data.MIMETYPE + " = ? ");
    		}
    		where.append(")");
    	
	    	projection.addAll(dataFields); // remove duplicates, push into a hashset
	    	projection.add(ContactsContract.Data.MIMETYPE);
	    	whereArgs.add(0, contactId); // put contact id in first position
	    	
	    	Cursor infoCur = cr.query(
				ContactsContract.Data.CONTENT_URI, projection.toArray(new String[projection.size()]), 
				where.toString(), whereArgs.toArray(new String[whereArgs.size()]), null);
			
	    	while (infoCur.moveToNext()) {
				String mimetype = infoCur.getString(infoCur.getColumnIndex(ContactsContract.Data.MIMETYPE));
				for (int i = 1 ; i < whereArgs.size() ; ++i) {
					if (mimetype.equals(whereArgs.get(i))) {
						String info = infoCur.getString(infoCur.getColumnIndex(dataFields.get(i-1)));
						if (null != info) {
							infos.add(info);
						}
						i++;
						break;
					}
				}
	        }
	        infoCur.close();
    	}
        return infos;
    }
	*/

}
