/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Lunextelecom, Inc. All rights reserved.
 * Author: AnhBui
 * Location: Zippie - com.lunextelecom.zippie - Utils.java
 * created Date: 2014-10-24
 * 
 */
package com.lunextelecom.zippie.utils;

import java.util.Locale;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class Utils.
 */
public class Utils {

    /** The fragment category. */
    public static String FRAGMENT_CONTACT = "FRAGMENT_CONTACT";
    
    /** The fragment contact all. */
    public static String FRAGMENT_CONTACT_ALL ="CONTACT_ALL";
    
    /** The fragment contact vippe. */
    public static String FRAGMENT_CONTACT_VIPPE ="CONTACT_VIPPE";
    
    /** The fragment contact favorites. */
    public static String FRAGMENT_CONTACT_FAVORITES ="CONTACT_FAVORITES";
    
    /** The fragment contact search. */
    public static String FRAGMENT_CONTACT_SEARCH ="CONTACT_SEARCH";

    /** The Constant FONT_ROBOTO_NAME. */
    public static final String FONT_ROBOTO_NAME = "Roboto.ttf";

    /** The Constant FONT_FACEBOLF_NAME. */
    public static final String FONT_FACEBOLF_NAME = "FACEBOLF.OTF";

    /**
     * Sets the typeface.
     * 
     * @param context the context
     * @param fontName the font name
     * @param views the views
     */
    public static void setTypeface(Context context, String fontName, View... views) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/"+fontName);
        if(views != null && views.length >0){
            for(View view : views){
                if(view instanceof TextView){
                    ((TextView)view).setTypeface(font);
                }else if(view instanceof EditText){
                    ((EditText)view).setTypeface(font);
                }else if(view instanceof Button){
                    ((Button)view).setTypeface(font);
                }
            }
        }
    }

    /**
     * Sets the typeface roboto.
     * 
     * @param context the context
     * @param views the views
     */
    public static void setTypefaceRoboto(Context context, View... views) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto.ttf");
        if(views != null && views.length >0){
            for(View view : views){
                if(view instanceof TextView){
                    ((TextView)view).setTypeface(font);
                }else if(view instanceof EditText){
                    ((EditText)view).setTypeface(font);
                }else if(view instanceof Button){
                    ((Button)view).setTypeface(font);
                }
            }
        }
    }
    
    /**
     * Uses static final constants to detect if the device's platform version is Gingerbread or
     * later.
     *
     * @return true, if successful
     */
    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    /**
     * Uses static final constants to detect if the device's platform version is Honeycomb or
     * later.
     *
     * @return true, if successful
     */
    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * Uses static final constants to detect if the device's platform version is Honeycomb MR1 or
     * later.
     *
     * @return true, if successful
     */
    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    /**
     * Uses static final constants to detect if the device's platform version is ICS or
     * later.
     *
     * @return true, if successful
     */
    public static boolean hasICS() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    /**
     * Get ISO 3166-1 alpha-2 country code for this device (or null if not available).
     *
     * @param context Context reference to get the TelephonyManager instance from
     * @return country code or null
     */
    public static String getCountryCode(Context context) {
        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            final String simCountry = tm.getSimCountryIso();
            if (simCountry != null && simCountry.length() == 2) { // SIM country code is available
                return simCountry.toLowerCase(Locale.US);
            }
            else if (tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)
                String networkCountry = tm.getNetworkCountryIso();
                if (networkCountry != null && networkCountry.length() == 2) { // network country code is available
                    return networkCountry.toLowerCase(Locale.US);
                }
            }
        }
        catch (Exception e) { }
        return null;
    }
}
