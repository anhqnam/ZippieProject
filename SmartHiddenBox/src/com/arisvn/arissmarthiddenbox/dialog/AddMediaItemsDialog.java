package com.arisvn.arissmarthiddenbox.dialog;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.arisvn.arissmarthiddenbox.R;
import com.arisvn.arissmarthiddenbox.listener.OnItemsAddListener;
import com.arisvn.arissmarthiddenbox.listener.OnItemsAddListener.AddFileMode;
import com.arisvn.arissmarthiddenbox.util.SaveData;
import com.arisvn.arissmarthiddenbox.util.Utils;

public class AddMediaItemsDialog extends DialogFragment implements
		OnClickListener {
	/** The Constant SELECT_FILE_DIALOG. */
	public static final String TAG = "AddMediaItemsDialog";
	/** The listener. */
	private OnItemsAddListener listener;

	/**
	 * Instantiates a new camera dialog fragment.
	 * 
	 * @param listener
	 *            the listener
	 */
	public AddMediaItemsDialog(OnItemsAddListener listener) {
		super();
		setCancelable(true);
		this.listener = listener;
	}

	/**
	 * New instance.
	 * 
	 * @param listener
	 *            the listener
	 * @return the camera dialog fragment
	 */
	public static AddMediaItemsDialog newInstance(OnItemsAddListener listener) {
		AddMediaItemsDialog f = new AddMediaItemsDialog(listener);
		return f;
	}
    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        if (getActivity().getResources().getBoolean(R.bool.is_large)) {
            if (getDialog() == null)
                return;
            int width = SaveData.getInstance(getActivity()).getDeviceWidth()
                    * getActivity().getResources()
                    .getInteger(R.integer.number_width_dialog_select_mode)
                    / getActivity().getResources().getInteger(R.integer.number_total);
            LayoutParams params = getDialog().getWindow().getAttributes();
            params.height = width;
            params.width = width;
            getDialog().getWindow().setAttributes(params);
        }
    }
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getDialog().getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		View view = inflater.inflate(R.layout.add_media_dialog, container);
		Utils.setTypeface(getActivity(),
				(TextView) view.findViewById(R.id.add_file_title),
				"Roboto-Light.ttf");
		Utils.setTypeface(getActivity(),
				(TextView) view.findViewById(R.id.add_all), "Roboto-Light.ttf");
		Utils.setTypeface(getActivity(),
				(TextView) view.findViewById(R.id.add_folder),
				"Roboto-Light.ttf");
		view.findViewById(R.id.add_all_radio).setOnClickListener(this);
		view.findViewById(R.id.add_folder_radio).setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		dismiss();
		switch (v.getId()) {
		case R.id.add_all_radio:
			if (listener!=null) {
				listener.onAddItemsModeSelected(AddFileMode.ALL);
			}
			break;
		case R.id.add_folder_radio:
			if (listener!=null) {
				listener.onAddItemsModeSelected(AddFileMode.FOLDER);
			}
			break;

		default:
			break;
		}

	}

}
