package com.arisvn.arissmarthiddenbox.dialog;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.arisvn.arissmarthiddenbox.R;
import com.arisvn.arissmarthiddenbox.listener.SortDialogListener;
import com.arisvn.arissmarthiddenbox.listener.SortDialogListener.SortCondition;
import com.arisvn.arissmarthiddenbox.listener.SortDialogListener.SortDirection;
import com.arisvn.arissmarthiddenbox.util.Utils;

@SuppressLint("ValidFragment")
public class SortDialogFragment extends DialogFragment implements
		OnClickListener {

	public static final String TAG = "SortDialogFragment";
	private SortDialogListener listener;
	private View view;
	private SortDialogListener.SortCondition sortCondition;
	private SortDialogListener.SortDirection sortDirection;
	private RadioButton sortAscRadio, sortDesRadio, sortNameRadio,
			sortSizeRadio, sortDateRadio;
	private boolean isSortDate,isSortName, isSortSize;

	/**
	 * Instantiates a new select file dialog.
	 * 
	 * @param listener
	 *            the listener
	 */
	public SortDialogFragment(SortDialogListener listener) {
		// Empty constructor required for DialogFragment
		this.listener = listener;
	}

	/**
	 * New instance.
	 * 
	 * @param mode
	 *            the mode
	 * @param listener
	 *            the listener
	 * @return the select file dialog
	 */
	public static SortDialogFragment newInstance(SortDialogListener listener, boolean isEnableName, boolean isEnableSize, boolean isEnableDate) {
		SortDialogFragment f = new SortDialogFragment(listener);
		Bundle args = new Bundle();
		args.putBoolean(Utils.SORT_SIZE_ENABLE, isEnableSize);
		args.putBoolean(Utils.SORT_DATE_ENABLE, isEnableDate);
		args.putBoolean(Utils.SORT_NAME_ENABLE, isEnableName);
		f.setArguments(args);
		return f;
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
		Bundle bundle=getArguments();
		if (bundle!=null) {
			isSortDate=bundle.getBoolean(Utils.SORT_DATE_ENABLE);
			isSortName=bundle.getBoolean(Utils.SORT_NAME_ENABLE);
			isSortSize=bundle.getBoolean(Utils.SORT_SIZE_ENABLE);
		}
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getDialog().getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		view = inflater.inflate(R.layout.dialog_sort_layout, container);
		Utils.setTypeface(getActivity(),
				(TextView) view.findViewById(R.id.sort_title),
				"Roboto-Medium.ttf");
		Utils.setTypeface(getActivity(),
				(TextView) view.findViewById(R.id.sort_asc_tv),
				"Roboto-Light.ttf");
		Utils.setTypeface(getActivity(),
				(TextView) view.findViewById(R.id.sort_des_tv),
				"Roboto-Light.ttf");
		Utils.setTypeface(getActivity(),
				(TextView) view.findViewById(R.id.sort_name_tv),
				"Roboto-Light.ttf");
		Utils.setTypeface(getActivity(),
				(TextView) view.findViewById(R.id.sort_size_tv),
				"Roboto-Light.ttf");
		Utils.setTypeface(getActivity(),
				(TextView) view.findViewById(R.id.sort_date_tv),
				"Roboto-Light.ttf");
		Typeface font_button = Typeface.createFromAsset(getActivity()
				.getAssets(), "Roboto-Medium.ttf");
		((Button) view.findViewById(R.id.sort_ok_btn)).setTypeface(font_button);
		((Button) view.findViewById(R.id.sort_cancel_btn))
				.setTypeface(font_button);
		view.findViewById(R.id.sort_ok_btn).setOnClickListener(this);
		view.findViewById(R.id.sort_cancel_btn).setOnClickListener(this);
		view.findViewById(R.id.sort_asc_group).setOnClickListener(this);
		view.findViewById(R.id.sort_des_group).setOnClickListener(this);
		view.findViewById(R.id.sort_name_group).setVisibility(isSortName?View.VISIBLE:View.GONE);
		view.findViewById(R.id.sort_size_group).setVisibility(isSortSize?View.VISIBLE:View.GONE);
		view.findViewById(R.id.sort_date_group).setVisibility(isSortDate?View.VISIBLE:View.GONE);
		view.findViewById(R.id.sort_name_group).setOnClickListener(this);
		view.findViewById(R.id.sort_size_group).setOnClickListener(this);
		view.findViewById(R.id.sort_date_group).setOnClickListener(this);
		sortDirection = SortDirection.DESC;
		sortCondition = SortCondition.NAME;
		sortAscRadio = (RadioButton) view.findViewById(R.id.sort_asc_radio);
		sortDesRadio = (RadioButton) view.findViewById(R.id.sort_des_radio);
		sortNameRadio = (RadioButton) view.findViewById(R.id.sort_name_radio);
		sortSizeRadio = (RadioButton) view.findViewById(R.id.sort_size_radio);
		sortDateRadio = (RadioButton) view.findViewById(R.id.sort_date_radio);
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.sort_cancel_btn:
			dismiss();
			break;
		case R.id.sort_ok_btn:
			if (listener != null) {
				listener.onStartSorting(sortDirection, sortCondition);
			}
			dismiss();
			break;
		case R.id.sort_asc_group:
			if (!sortAscRadio.isChecked()) {
				sortDirection=SortDirection.ASC;
				sortAscRadio.setChecked(true);
				sortDesRadio.setChecked(false);
			}
			break;
		case R.id.sort_des_group:
			if (!sortDesRadio.isChecked()) {
				sortDirection=SortDirection.DESC;
				sortDesRadio.setChecked(true);
				sortAscRadio.setChecked(false);
			}
			break;
		case R.id.sort_name_group:
			if (!sortNameRadio.isChecked()) {
				sortCondition=SortCondition.NAME;
				sortNameRadio.setChecked(true);
				sortSizeRadio.setChecked(false);
				sortDateRadio.setChecked(false);
			}
			break;
		case R.id.sort_size_group:
			if (!sortSizeRadio.isChecked()) {
				sortCondition=SortCondition.SIZE;
				sortSizeRadio.setChecked(true);
				sortNameRadio.setChecked(false);
				sortDateRadio.setChecked(false);
			}
			break;
		case R.id.sort_date_group:
			if (!sortDateRadio.isChecked()) {
				sortCondition=SortCondition.DATE;
				sortDateRadio.setChecked(true);
				sortNameRadio.setChecked(false);
				sortSizeRadio.setChecked(false);
			}
			break;
		default:
			break;
		}

	}

}
