/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Lunextelecom, Inc. All rights reserved.
 * Author: AnhBui
 * Location: Zippie - com.lunextelecom.zippie - ContactAPIHelper.java
 * created Date: 2014-10-24
 * 
 */
package com.lunextelecom.zippie.sdk;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.lunextelecom.zippie.bean.ContactObject;


// TODO: Auto-generated Javadoc
/**
 * The Class ContactAPIHelper.
 */
public class ContactAPIHelper extends APIHelper {

    /** The instance. */
    private static ContactAPIHelper instance = null;
    
    /** The m contact list. */
    private List<ContactObject> mContactList;
    
    /** The m vippie contac list. */
    private List<ContactObject> mVippieContacList;
    /** The m context. */
    private Context mContext;

    /**
     * Gets the m context.
     *
     * @return the m context
     */
    public Context getmContext() {
        return mContext;
    }

    /**
     * Sets the m context.
     *
     * @param mContext the new m context
     */
    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * Instantiates a new contact api helper.
     *
     * @param context the context
     */
    private ContactAPIHelper(Context context) {
        // TODO Auto-generated constructor stub
        this.mContext = context;
    }

    /**
     * Gets the single instance of ContactAPIHelper.
     *
     * @param context the context
     * @return single instance of ContactAPIHelper
     */
    public static ContactAPIHelper getInstance(Context context){
        if(instance == null){
            instance = new ContactAPIHelper(context);
        }
        instance.setmContext(context);
        return instance;
    }
    
    /**
     * Gets the contact all.
     *
     * @param forceLoad the force load
     * @return the list
     */
    public List<ContactObject> GetContactAll(boolean forceLoad){
        if (mContactList != null && !forceLoad) {
            return mContactList;
        }
        mContactList = new ArrayList<ContactObject>();
        for(int i =0 ;i<5 ;i++){
            if(i ==0){
                ContactObject contact = new ContactObject();
                String name ="ABC";
                Integer id =1;
                String avatar ="";
                String sipFreeId = "123456";
                String sipPaidId ="0933551923";
                contact.setcId(id);
                contact.setcName(name);
                contact.setcAvartar(avatar);
                contact.setVippeFreeId(sipFreeId);
                contact.setVippePaidId(sipPaidId);
                contact.setcStatus(true);
                mContactList.add(contact);
            }
            if(i==1){
                ContactObject contact = new ContactObject();
                String name1 ="EGH";
                Integer id1 =2;
                String avatar1 ="";
                String sipFreeId1 = "23456789";
                String sipPaidId1 ="01677113348";
                contact.setcId(id1);
                contact.setcName(name1);
                contact.setcAvartar(avatar1);
                contact.setVippeFreeId(sipFreeId1);
                contact.setVippePaidId(sipPaidId1);
                contact.setcStatus(true);
                mContactList.add(contact);
            }
            if(i==2){
                ContactObject contact = new ContactObject();
                String name1 ="ENG IKLM";
                Integer id1 =3;
                String avatar1 ="";
                String sipFreeId1 = "";
                String sipPaidId1 ="01677113348";
                contact.setcId(id1);
                contact.setcName(name1);
                contact.setcAvartar(avatar1);
                contact.setVippeFreeId(sipFreeId1);
                contact.setVippePaidId(sipPaidId1);
                contact.setcStatus(true);
                mContactList.add(contact);
            }
            if(i==3){
                ContactObject contact = new ContactObject();
                String name1 ="ENG IKLM GHDFJF";
                Integer id1 =4;
                String avatar1 ="";
                String sipFreeId1 = "454545454";
                String sipPaidId1 ="01677113348";
                contact.setcId(id1);
                contact.setcName(name1);
                contact.setcAvartar(avatar1);
                contact.setVippeFreeId(sipFreeId1);
                contact.setVippePaidId(sipPaidId1);
                contact.setcStatus(true);
                mContactList.add(contact);
            }
        }



        return mContactList;
    }
    
    /**
     * Gets the contact vippie.
     *
     * @param forceLoad the force load
     * @return the list
     */
    public List<ContactObject> GetContactVippie(boolean forceLoad){
        if(mVippieContacList != null && !forceLoad){
            return mVippieContacList;
        }
        List<ContactObject> allContact = GetContactAll(false);
        if(allContact != null){
            mVippieContacList = new ArrayList<ContactObject>();
            for(ContactObject c: allContact){
                if(c.getVippeFreeId() != ""){
                    mVippieContacList.add(c);
                }
            }
        }
        return mVippieContacList;
    }

    /**
     * Gets the status contact.
     *
     * @param forceLoad the force load
     * @return the status contact
     */
    public void getStatusContact(boolean forceLoad){
        List<ContactObject> vippieContact = GetContactAll(forceLoad);
        if(vippieContact!= null){
            //for (ContactObject c: vippieContact){

            //}
        }
    }

    /**
     * Gets the list contact by name.
     *
     * @param list the list
     * @param str the str
     * @return the list contact by name
     */
    public List<ContactObject> getListContactByName(List<ContactObject> list, String str){
        List<ContactObject> results = new ArrayList<ContactObject>();
        if(list != null){
            for(ContactObject ob: list){
                String name = ob.getcName().toLowerCase();
                str = str.toLowerCase();
                if(name.contains(str)){
                    results.add(ob);
                }
            }
        }
        return results;
    }
}
