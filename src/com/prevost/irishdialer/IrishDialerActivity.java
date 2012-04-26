package com.prevost.irishdialer;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
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
	protected EditText _searchBox = null;
	protected ListView _contactListView = null;
	protected ISearchIndex<IContact> _contactSearchIndex = null;
	
	// search related properties
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this._initStrictMode();
    	
        setContentView(R.layout.main);
        // load search view only once
        this._searchBox 		= (EditText)findViewById(R.id.search);
        
        // init contact list view
        this._contactListView	= (ListView)findViewById(R.id.contacts);
    	ContactAdapter contactAdapter = new ContactAdapter(getBaseContext());
    	this._contactListView.setAdapter(contactAdapter);
		
        
        // load all contacts
        this._initSearchIndex();
        
        // display loaded contacts
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
    	return _searchBox.getText().toString();
    }
    
    
    public void numberClicked(View view) {
    	 Button b = (Button) view;
    	 _searchBox.append(b.getText().subSequence(0, 1));
    	 reloadContactListView();
    }
    
    public void deleteClicked(View view) {
    	CharSequence s = _searchBox.getText();
    	int l = s.length();
    	if (l > 0) {
    		_searchBox.setTextKeepState(s.subSequence(0, l - 1));
    	}
   	 	reloadContactListView();
   }
    
    public void reloadContactListView() {
    	Log.v(TAG, "reloading");
    	List<IContact> newContactList = this._contactSearchIndex.search(this.getQueryString());
    	ContactAdapter contactAdapter = (ContactAdapter) this._contactListView.getAdapter();
    	contactAdapter.setContactList(newContactList);
    }
    
    protected void _initSearchIndex() {
    	final Object data = getLastNonConfigurationInstance();
        
        // The activity is starting for the first time, load the photos from Flickr
        if (data == null) {
            _contactSearchIndex = new NumericPadContactSearchIndex();
            ContactDatasource dataSource = new ContactDatasource(this.getBaseContext());
            
            for (IContact contact: dataSource.getAllContacts()) {
            	_contactSearchIndex.add(contact);
    		}
        } else {
        	// The activity was destroyed/created automatically
        	_contactSearchIndex = (NumericPadContactSearchIndex) data;
        }
    }
    
    @Override
    public Object onRetainNonConfigurationInstance() {
    	// when the activity is destroyed, save the search index
    	// on rotate, low battery shut down, ...
        return _contactSearchIndex;
    }
    
    protected void _initStrictMode() {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
    }
}