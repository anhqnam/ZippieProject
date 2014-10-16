package com.lunextelecom.zippie.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class Utils {

    public static void setTypeface(Context context, TextView text, String fontName) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/"+fontName);
        text.setTypeface(font);
    }
	 /** The fragment category. */
    public static String FRAGMENT_CONTACT = "FRAGMENT_CONTACT";
    public static String FRAGMENT_CONTACT_ALL ="CONTACT_ALL";
    public static String FRAGMENT_CONTACT_VIPPE ="CONTACT_VIPPE";
    public static String FRAGMENT_CONTACT_FAVORITES ="CONTACT_FAVORITES";
    public static String FRAGMENT_CONTACT_SEARCH ="CONTACT_SEARCH";
}
