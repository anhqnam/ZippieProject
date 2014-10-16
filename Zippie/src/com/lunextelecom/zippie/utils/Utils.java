/*
 * @author Vuong Huynh
 * Copyright (C) 2014 Lunextelecom
 */
package com.lunextelecom.zippie.utils;

import android.content.Context;
import android.graphics.Typeface;
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
    public static String FRAGMENT_CONTACT_ALL ="CONTACT_ALL";
    public static String FRAGMENT_CONTACT_VIPPE ="CONTACT_VIPPE";
    public static String FRAGMENT_CONTACT_FAVORITES ="CONTACT_FAVORITES";
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
}
