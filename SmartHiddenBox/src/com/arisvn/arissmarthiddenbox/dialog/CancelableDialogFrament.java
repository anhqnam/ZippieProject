
package com.arisvn.arissmarthiddenbox.dialog;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import com.arisvn.arissmarthiddenbox.R;
import com.arisvn.arissmarthiddenbox.listener.OnCancelListener;
import com.arisvn.arissmarthiddenbox.util.SaveData;

// TODO: Auto-generated Javadoc
/**
 * The Class DialogPickerFrament.
 */
public class CancelableDialogFrament extends DialogFragment implements OnClickListener {

    /** The Constant TAG. */
    public static final String TAG = "DialogPickerFrament";

    /** The message. */
    private String message;

    /** The layout. */
    private View layout;

    /** The waiting dismiss. */
    private static boolean waitingDismiss = false;

    /** The listener. */
    private static OnCancelListener listener;

    /**
     * Gets the single instance of DialogPickerFrament.
     *
     * @return single instance of DialogPickerFrament
     */
    @SuppressWarnings("unused")
    private static CancelableDialogFrament getInstance() {
        CancelableDialogFrament f = new CancelableDialogFrament();
        return f;
    }

    /**
     * Gets the single instance of DialogPickerFrament.
     *
     * @param message the message
     * @param listen the listen
     * @return single instance of DialogPickerFrament
     */
    public static CancelableDialogFrament getInstance(String message,OnCancelListener listen) {
        CancelableDialogFrament f = new CancelableDialogFrament(message);
        listener = listen;
        return f;
    }

    /**
     * Instantiates a new syn dialog fragment.
     * 
     * @param message the message
     */
    public CancelableDialogFrament(String message) {
        this.message = message;
    }

    /**
     * Instantiates a new syn dialog fragment.
     */
    public CancelableDialogFrament() {
        super();
    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.app.DialogFragment#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Panel);
        setCancelable(false);

    }

    /*
     * (non-Javadoc)
     * @see
     * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
     * android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.cancel_dialog_layout, container, false);
        TextView text_content = (TextView) layout.findViewById(R.id.message);
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(),
	                "Roboto-Light.ttf");
		text_content.setTypeface(font);
		Typeface btnFont = Typeface.createFromAsset(getActivity().getAssets(),
                "Roboto-Medium.ttf");
		Button button=(Button) layout.findViewById(R.id.cancel);
		button.setTypeface(btnFont);
        if (message != null) {
            layout.findViewById(R.id.message).setVisibility(View.VISIBLE);
            ((TextView)layout.findViewById(R.id.message)).setText(message);
            layout.findViewById(R.id.cancel).setOnClickListener(this);
        }
        return layout;
    }

    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.cancel:
                if (listener != null) {
                    listener.onCancel();
                    Log.e("", "dismiss");
                }
                dismiss();
                break;
        }
    }


    /*
     * (non-Javadoc)
     * @see android.support.v4.app.DialogFragment#show(android.support.v4.app.
     * FragmentManager, java.lang.String)
     */
    @Override
    public void show(FragmentManager arg0, String arg1) {
        waitingDismiss = false;
        try {
            super.show(arg0, arg1);
        } catch (IllegalStateException e) {
        }
    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.app.DialogFragment#dismiss()
     */
    @Override
    public void dismiss() {
        try {
            super.dismiss();
        } catch (IllegalStateException e) {
            waitingDismiss = true;
        } catch (NullPointerException e) {
            waitingDismiss = true;
        }
    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.app.DialogFragment#onStart()
     */
    @Override
    public void onStart() {
        super.onStart();
        if (getActivity().getResources().getBoolean(R.bool.is_large)) {
            if (getDialog() == null)
                return;
            int width = SaveData.getInstance(getActivity()).getDeviceWidth()
                    * getActivity().getResources()
                    .getInteger(R.integer.width_dialog_loading)
                    / getActivity().getResources().getInteger(R.integer.number_total);
            LayoutParams params = getDialog().getWindow().getAttributes();
            params.height = width;
            params.width = width;
            getDialog().getWindow().setAttributes(params);
        }
        if (waitingDismiss) {
            dismiss();
            waitingDismiss = false;
        }
    }

    /**
     * Dismiss dialog for activity.
     * 
     * @param activity the activity
     */
    public void dismiss(FragmentActivity activity) {
        if (activity != null) {
            DialogFragment progressDiag = ((DialogFragment)(activity.getSupportFragmentManager()
                    .findFragmentByTag(CancelableDialogFrament.TAG)));
            if (progressDiag != null) {
                progressDiag.dismiss();
            } else {
                waitingDismiss = true;
            }
        }

    }

    /**
     * Show dialog with a default message for activity.
     * 
     * @param activity the activity
     */
    public static void show(FragmentActivity activity) {
        //        getInstance().show(activity.getSupportFragmentManager(), DialogPickerFrament.TAG);
    }

    /**
     * Show.
     *
     * @param activity the activity
     * @param message the message
     * @param listener the listener
     */
    public void show(FragmentActivity activity, String message, OnCancelListener listener) {
        getInstance(message,listener).show(activity.getSupportFragmentManager(), CancelableDialogFrament.TAG);
    }
}
