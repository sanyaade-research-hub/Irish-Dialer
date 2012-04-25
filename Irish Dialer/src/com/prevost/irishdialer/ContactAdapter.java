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
    protected ViewHolder _viewHolder = null;
 
    public ContactAdapter(Context context) {
        this._contactList = new ArrayList<IContact>();
        this._initViewHolder(context);
    }
 
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        IContact contact = _contactList.get(position);
     
        this._viewHolder.displayName.setText(contact.getFullName());
        
        List<String> fields = contact.getFields();
        this._viewHolder.infos.setText(this._implodeStringList(fields, ";").toString());

        return this._viewHolder.contactView;
    }
 
    public void setContactList(List<IContact> contactList) {
    	this.notifyDataSetInvalidated();
    	this._contactList = contactList;
    	this.notifyDataSetChanged();
    }
    
    protected String _implodeStringList(List<String> strLst, String glue) {

		StringBuilder sb = new StringBuilder();
		sb.append(glue);
		for (String str : strLst) {
    		sb.append(str);
			sb.append(glue);
		}
		return sb.toString();
    }
    
    protected void _initViewHolder(Context context) {
    	this._viewHolder = new ViewHolder();
    	
    	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	this._viewHolder.contactView = inflater.inflate(R.layout.contact_adapter_view, null);
        
        this._viewHolder.displayName = (TextView) this._viewHolder.contactView.findViewById(R.id.textViewDisplayName);
        this._viewHolder.infos = (TextView) this._viewHolder.contactView.findViewById(R.id.textViewInfos);
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
 