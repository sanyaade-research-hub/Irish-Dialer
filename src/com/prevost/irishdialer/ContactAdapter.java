package com.prevost.irishdialer;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;


public class ContactAdapter extends BaseAdapter{
 
    protected static final String LOG = "IrishDialer";
	protected List<IContact> _contactList = null;
    protected LayoutInflater _layoutInflater = null;
    protected IClickListenerProvider _clickListenerProvider = null;
    
    
    public ContactAdapter(IClickListenerProvider provider, Context context) {
        this._contactList = new ArrayList<IContact>();
        this._layoutInflater = LayoutInflater.from(context);
        this._clickListenerProvider = provider;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
    	View v = null;
        IContact contact = _contactList.get(position);
        
    	// reuse views
    	if (convertView == null) {
    		ViewHolder vh = new ViewHolder();
    	
	    	v = this._layoutInflater.inflate(R.layout.contact_adapter_view, null);
	        vh.contactView = v;
	        vh.displayName = (TextView) v.findViewById(R.id.textViewDisplayName);
	        vh.infos = (TextView) v.findViewById(R.id.textViewInfos);
	        vh.sms = (ImageButton) v.findViewById(R.id.imageButtonSms);
	        vh.call = (ImageButton) v.findViewById(R.id.imageButtonCall);
	        
	        final String mainPhoneNumber = contact.getPhoneNumbers().get(0);
	        vh.sms.setOnClickListener(this._clickListenerProvider.getSmsClickListener(mainPhoneNumber));
	        vh.call.setOnClickListener(this._clickListenerProvider.getCallClickListener(mainPhoneNumber));
	        v.setTag(vh);
    	} else {
    		v = convertView;
    	}
    	
        ViewHolder vh = (ViewHolder) v.getTag();
        
        vh.displayName.setText(contact.getFullName());
        
        //List<ContactField> fields = contact.getFields();
        //List<ContactField> enabledFields = contact.getEnabledFields();
        
        //this._implodeStringList(fields, ";").toString() + " ======= " + this._implodeStringList(enabledFields, "|").toString() + " ======= " + ((NumericPadContact)contact).getNumerifiedString()
        
        String text = "";
        for (String phone : contact.getPhoneNumbers()) {
			text += phone;
			text += "|";
		}
        
        vh.infos.setText(text);

        return v;
    }
 
    public void setContactList(List<IContact> contactList) {
    	this.notifyDataSetInvalidated();
    	this._contactList = contactList;
    	this.notifyDataSetChanged();
    }
    
    protected String _implodeStringList(List<ContactField> fields, String glue) {

		StringBuilder sb = new StringBuilder();
		sb.append(glue);
		for (ContactField field : fields) {
    		sb.append(field.getValue());
			sb.append(glue);
		}
		return sb.toString();
    }
    
    protected static class ViewHolder {
    	View contactView;
    	TextView displayName;
    	TextView infos;
    	ImageButton sms;
    	ImageButton call;
    }

	@Override
    public int getCount() {
        return _contactList.size();
    }

	@Override
    public Object getItem(int position) {
        return _contactList.get(position);
    }
 

	@Override
    public long getItemId(int position) {
        return position;
    }
 
}
 