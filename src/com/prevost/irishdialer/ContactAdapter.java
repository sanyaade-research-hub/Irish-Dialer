package com.prevost.irishdialer;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class ContactAdapter extends BaseAdapter{
 
    protected List<IContact> _contactList = null;
    protected LayoutInflater _layoutInflater = null;
 
    public ContactAdapter(Context context) {
        this._contactList = new ArrayList<IContact>();
        this._layoutInflater = LayoutInflater.from(context);
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
    	View v = null;
    	// reuse views
    	if (convertView == null) {
    		ViewHolder vh = new ViewHolder();
    	
	    	v = this._layoutInflater.inflate(R.layout.contact_adapter_view, null);
	        vh.contactView = v;
	        vh.displayName = (TextView) v.findViewById(R.id.textViewDisplayName);
	        vh.infos = (TextView) v.findViewById(R.id.textViewInfos);
	        v.setTag(vh);
    	} else {
    		v = convertView;
    	}
    	
        IContact contact = _contactList.get(position);
        ViewHolder vh = (ViewHolder) v.getTag();
        
        vh.displayName.setText(contact.getFullName());
        
        List<ContactField> fields = contact.getFields();
        List<ContactField> enabledFields = contact.getEnabledFields();
        
        vh.infos.setText(this._implodeStringList(fields, ";").toString() + " ======= " + this._implodeStringList(enabledFields, "|").toString() + " ======= " + ((NumericPadContact)contact).getNumerifiedString());

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
 