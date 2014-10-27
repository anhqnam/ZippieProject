/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Lunextelecom, Inc. All rights reserved.
 * Author: AnhBui
 * Location: Zippie - com.lunextelecom.zippie - SignUpActivity.java
 * 
 */
package com.lunextelecom.zippie.database;
import java.util.List;

import android.content.Context;

import com.lunextelecom.zippie.bean.ContactObject;
// TODO: Auto-generated Javadoc
/**
 * The Class ContactDatabaseHelper.
 */
public class ContactDatabaseHelper extends ZippieDatabaseHelper {

    /** The instance. */
    private static ContactDatabaseHelper instance = null;

    /** The m favorite contact list. */
    private List<ContactObject> mFavoriteContactList;
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
    private ContactDatabaseHelper(Context context) {
        // TODO Auto-generated constructor stub
        this.mContext = context;
    }

    /**
     * Gets the single instance of ContactAPIHelper.
     *
     * @param context the context
     * @return single instance of ContactAPIHelper
     */
    public static ContactDatabaseHelper getInstance(Context context){
        if(instance == null){
            instance = new ContactDatabaseHelper(context);
        }
        instance.setmContext(context);
        return instance;
    }
    public List<ContactObject> GetContactFavoriteList(boolean forceLoad){
        if(mFavoriteContactList != null && !forceLoad){
            return mFavoriteContactList;
        }
        return mFavoriteContactList;
    }
}
