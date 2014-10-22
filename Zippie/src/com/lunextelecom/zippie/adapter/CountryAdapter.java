package com.lunextelecom.zippie.adapter;

import java.util.List;

import com.lunextelecom.zippie.R;
import com.lunextelecom.zippie.bean.CountryObject;
import com.lunextelecom.zippie.utils.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class CountryAdapter.
 */
public class CountryAdapter extends ArrayAdapter<CountryObject> {

	/**
	 * Instantiates a new country adapter.
	 *
	 * @param context the context
	 * @param resource the resource
	 * @param object the object
	 */
	public CountryAdapter(Context context, int resource, List<CountryObject> object) {
		super(context, resource, object);
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.signup_custom_item_country_dialog_lay, parent, false);
		}
		CountryObject country = getItem(position);
		TextView country_name = (TextView)convertView.findViewById(R.id.signup_country_title_dialog_id);
		Utils.setTypefaceRoboto(getContext(), country_name);
		country_name.setText(country.getmName() + " ( " + country.getmDialCode() + " ) ");
		return convertView;
	}

}
