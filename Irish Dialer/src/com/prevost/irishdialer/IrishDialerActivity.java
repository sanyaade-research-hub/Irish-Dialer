package com.prevost.irishdialer;

import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.preference.PreferenceManager;



public class IrishDialerActivity extends Activity {
	
	protected EditText _search = null;
	protected String[] _projection = null;
	protected ListView _contacsView = null;
	protected int _maxContact = 30;
	protected ArrayList<Contact> _contactList = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // load search view only once
        _search 		= (EditText)findViewById(R.id.search);
        _contacsView	= (ListView)findViewById(R.id.contacts);
        _contactList 	= new ArrayList<Contact>();
        
        reloadContactList();
    }
    
    // create menu from xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
 
    // This method is called once the menu is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	// We have only one menu option
    	case R.id.preferences:
    		// Launch Preference activity
    		Intent i = new Intent(IrishDialerActivity.this, IrishPreferenceActivity.class);
    		startActivity(i);
    		break;
    	}
    	return true;
    }
    
    public void numberClicked(View view) {
    	 Button b = (Button) view;
    	 _search.append(b.getText().subSequence(0, 1));
    }
    
    public void deleteClicked(View view) {
    	CharSequence s = _search.getText();
    	int l = s.length();
    	if (l > 0) {
    		_search.setTextKeepState(s.subSequence(0, l - 1));
    	}
   }
    
    public void reloadContactList() {
    	_contactList.clear();
    	
    	Cursor cur = getContentResolver().query(Contacts.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME);
    	int n = cur.getCount();
    	ArrayList<String> infos = null;
    	cur.moveToFirst();
    	
    	for (int i = 0 ; (i < n) && (i < _maxContact) ; i++) {
    		String displayName = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
    		int id = cur.getInt(cur.getColumnIndex(ContactsContract.Contacts._ID));
    		infos = getInfosFromCursorAndPreferences(cur, displayName);
    		Contact contact = new Contact(id, displayName, infos);
    		_contactList.add(contact);
    		cur.moveToNext();
        }
    	
    	ContactAdapter contactAdapter =  new ContactAdapter(getBaseContext(), _contactList);
    	
    	_contacsView.setAdapter(contactAdapter);
    	cur.close();
    }
    
    /*
     ContactsContract.Contacts
	     display_name
	     times_contacted
	     last_time_contacted
	     _id
	     photo_id
	     has_phone_number
     
     */
    
    /*
    protected void loadPreferences() {
    	//SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_WORLD_READABLE);
    	SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean s = settings.getBoolean("full_name", true);
        Toast toast = Toast.makeText(getApplicationContext(), "s:" + s, Toast.LENGTH_SHORT);
        toast.show();
    }*/
    
    protected ArrayList<String> getInfosFromCursorAndPreferences(Cursor cur, String fullName) {
    	ArrayList<String> infos = new ArrayList<String>();
    	SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        
    	String contactId = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
    	
    	if (settings.getBoolean("full_name", true)) {
        	if (null != fullName) {
        		infos.add(fullName);
        	} else {
        		// TODO: delete this line, added for debugging purpose
        		// it ensure that every contact have at least one info
        		// we don't want to match this later
        		infos.add("(noname)");
        	}
        }
    	
    	if (settings.getBoolean("email", true)) {
	    	Cursor emailCur = getContentResolver().query(        
	    			ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
	    			ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",  
	    			new String[]{contactId}, null); 
	 		while (emailCur.moveToNext()) {   
	 		    String email = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
	 	 	    if (null != email) {
	 	 	    	infos.add(email);
	 	 	    }
	 		} 
		    emailCur.close();	
    	}
    	
    	if (settings.getBoolean("phone", true)) {
        	Cursor phoneCur = getContentResolver().query(
        			ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, 
         		    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", 
         		    new String[]{contactId}, null);
 	        while (phoneCur.moveToNext()) {
 	        	  String phone = phoneCur.getString(phoneCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
 	        	  if (null != phone) {
 	        		  infos.add(phone);
 	        	  }
 	        } 
 	        phoneCur.close();
    	}
    	
    	if (settings.getBoolean("nickname", true)) {
        	Cursor nickCur = getContentResolver().query(
        			ContactsContract.Data.CONTENT_URI , null, 
         		    ContactsContract.CommonDataKinds.Nickname.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?", 
         		    new String[]{contactId, ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE}, null);
 	        while (nickCur.moveToNext()) {
 	        	  String nick = nickCur.getString(nickCur.getColumnIndex(ContactsContract.CommonDataKinds.Nickname.NAME));
 	        	  if (null != nick) {
 	        		  infos.add(nick);
 	        	  }
 	        }
 	        nickCur.close();
    	}
    	
        return infos;
    }
    
    protected void reloadProjectionFromPreferences() {
    	_projection = null;
    	
    	/*
    	fullName
    	email
    	phone
    	department
    	url
    	instantMessage
    	jobTitle
    	nickname
    	note
    	organization
    	*/
    	
    }
    
}