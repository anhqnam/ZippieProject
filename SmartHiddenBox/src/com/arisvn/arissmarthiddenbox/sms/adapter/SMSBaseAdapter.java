/*
 * 
 */
package com.arisvn.arissmarthiddenbox.sms.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.arisvn.arissmarthiddenbox.adapter.HiddenBaseAdapter;
import com.arisvn.arissmarthiddenbox.entity.BaseItemEntity;
import com.arisvn.arissmarthiddenbox.entity.SMSData;
import com.arisvn.arissmarthiddenbox.util.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class SMSBaseAdapter.
 */
public class SMSBaseAdapter extends HiddenBaseAdapter{

    /**
     * Instantiates a new sMS base adapter.
     *
     * @param activity the activity
     * @param fileItems the file items
     * @param type the type
     */
    public SMSBaseAdapter(Activity activity, ArrayList<? extends BaseItemEntity> fileItems, int type) {
        super(activity, fileItems, type);
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder view = (ViewHolder) convertView.getTag();
        SMSData obj = (SMSData) objs.get(position);

        if(objs.get(position).isCheck()){
            view.image_selected.setVisibility(View.VISIBLE);
        }else{
            view.image_selected.setVisibility(View.GONE);
        }
        if (isLoadThumbnail) {
            if (obj.getNumber() != null) {
            	String contactName;
            	if (obj.getName()!=null&&obj.getName().trim().length()>0) {
					contactName=obj.getName();
				} else {
					contactName=Utils.getContactName(activity, obj.getNumber());
					obj.setName(contactName);
				}
            	if (contactName!=null&&contactName.trim().length()>0) {
                    view.txtViewTitle.setText(contactName);
				} else {
                view.txtViewTitle.setText(obj.getNumber());
				}
                Utils.setTypeface(activity, view.txtViewTitle,"Roboto-Light.ttf");
                Utils.setTypeface(activity, view.txtSize,"Roboto-Light.ttf");
                // just load thumbnail when there is no thumbnail
                imageLoader.displayImage(obj, view.imgViewFlag);
            }
            view.txtSize.setText(obj.getBody());
        } else {
            view.txtViewTitle.setText(null);
            view.txtSize.setText(null);
            view.imgViewFlag.setImageBitmap(null);
        }
        return convertView;
    }

    /* (non-Javadoc)
     * @see com.arisvn.arissmarthiddenbox.adapter.HiddenBaseAdapter#getSelectedItem()
     */
    @Override
    public ArrayList<SMSData> getSelectedItem() {
        // TODO Auto-generated method stub
        ArrayList<SMSData> smsDatas = new ArrayList<SMSData>();
        if (objs != null && objs.size() > 0) {
            for (int i = 0; i < objs.size(); i++) {
                SMSData sms = (SMSData)objs.get(i);
                if (sms.isCheck()) {
                    smsDatas.add(sms);
                }
            }
        }
        return smsDatas;
    }

}
