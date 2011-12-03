package com.prevost.irishdialer;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class ContactAdapter extends BaseAdapter /*implements OnClickListener*/ {
 
	/*private class OnItemClickListener implements OnClickListener{
	    private int mPosition;
	    OnItemClickListener(int position){
	            mPosition = position;
	    }
	    public void onClick(View arg0) {
	            Log.v("ddd", "onItemClick at position" + mPosition);
	    }
	}*/
 
    protected Context _context;
    protected List<Contact> _contactList;
 
    public ContactAdapter(Context context, List<Contact> contactList ) {
        this._context = context;
        this._contactList = contactList;
    }
 
    public int getCount() {
        return _contactList.size();
    }
 
    public Object getItem(int position) {
        return _contactList.get(position);
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Contact contact = _contactList.get(position);
        
        LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.contact_adapter_view, null); 
        
        TextView textViewDisplayName = (TextView) v.findViewById(R.id.textViewDisplayName);
        textViewDisplayName.setText(contact.getDisplayName());
        
        TextView textViewInfos = (TextView) v.findViewById(R.id.textViewInfos);
        textViewInfos.setText(contact.getInfos().toString());
        //v.setBackgroundColor((position % 2) == 1 ? Color.rgb(50,50,50) : Color.BLACK);
 
        /*v.setOnClickListener(new OnItemClickListener(position));*/
        return v;
    }

    /*public void onClick(View v) {
            Log.v(LOG_TAG, "Row button clicked");
    }*/
 
}
 