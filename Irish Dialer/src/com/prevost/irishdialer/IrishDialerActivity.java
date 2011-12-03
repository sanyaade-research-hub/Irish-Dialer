package com.prevost.irishdialer;

import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
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
import android.provider.ContactsContract.Contacts;



public class IrishDialerActivity extends Activity {
	
	protected EditText _search = null;
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
    		infos = Contact.getInfosFromCursorAndPreferences(getContentResolver(), getBaseContext(), cur, displayName);
    		Contact contact = new Contact(id, displayName, infos);
    		_contactList.add(contact);
    		cur.moveToNext();
        }
    	
    	ContactAdapter contactAdapter =  new ContactAdapter(getBaseContext(), _contactList);
    	
    	_contacsView.setAdapter(contactAdapter);
    	cur.close();
    }
    
    
}