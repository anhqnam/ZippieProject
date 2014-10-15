package com.lunextelecom.zippie.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class Utils {

    public static void setTypeface(Context context, TextView text, String fontName) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/"+fontName);
        text.setTypeface(font);
    }
}
