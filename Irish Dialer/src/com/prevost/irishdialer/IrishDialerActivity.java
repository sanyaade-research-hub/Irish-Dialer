package com.prevost.irishdialer;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;



public class IrishDialerActivity extends Activity {
	protected final String TAG = "IrishDialer";
	protected EditText _search = null;
	protected ListView _contacsView = null;
	protected int _maxContact = 30;
	protected List<IContact> _displayContactList = null;
	protected IContactSearchIndex _contactSearchIndex = null;
	
	// search related properties
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        _contactSearchIndex = new NumericPadContactSearchIndex(settings);
        setContentView(R.layout.main);
        // load search view only once
        _search 		= (EditText)findViewById(R.id.search);
        _contacsView	= (ListView)findViewById(R.id.contacts);
        _displayContactList 	= new ArrayList<IContact>();

		loadAllContacts();
        reloadContactListView();
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
    
    
    public String getQueryString() {
    	return _search.getText().toString();
    }
    
    
    public void numberClicked(View view) {
    	 Button b = (Button) view;
    	 _search.append(b.getText().subSequence(0, 1));
    	 reloadContactListView();
    }
    
    public void deleteClicked(View view) {
    	CharSequence s = _search.getText();
    	int l = s.length();
    	if (l > 0) {
    		_search.setTextKeepState(s.subSequence(0, l - 1));
    	}
   	 	reloadContactListView();
   }
    
    public void reloadContactListView() {
    	this._displayContactList = this._contactSearchIndex.search(this.getQueryString());
    	
    	ContactAdapter contactAdapter =  new ContactAdapter(getBaseContext(), _displayContactList);
    	_contacsView.setAdapter(contactAdapter);
    }
    
    
    public void loadAllContacts(){

    	Log.v(TAG, "Loading all contacts");
    	SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
    	Cursor cur = getContentResolver().query(Contacts.CONTENT_URI, 
    			new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME}, 
    			null, null, ContactsContract.Contacts.DISPLAY_NAME);
    	
    	int n = cur.getCount();
    	int id = 0;
    	ArrayList<String> infos = null;
    	String displayName = null;
    	cur.moveToFirst();
    	Contact contact = null;
    	
    	for (int i = 0 ; (i < n) ; i++) {
    		displayName = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
    		id = cur.getInt(cur.getColumnIndex(ContactsContract.Contacts._ID));
    		//infos = Contact.getInfosFromCursorAndPreferences(getContentResolver(), settings, String.valueOf(id), displayName);
    		contact = new Contact(id, displayName, infos);
    		_contactSearchIndex.addContact(contact, settings);
    		_displayContactList.add(contact);
    		cur.moveToNext();
        }
    	Log.v(TAG, "Loaded contacts");
    	Log.v(TAG, "Contact list size : " + Integer.toString(_displayContactList.size()));
    }
    
}