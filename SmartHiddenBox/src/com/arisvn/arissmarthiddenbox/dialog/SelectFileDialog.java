/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Aris-vn, Inc. All rights reserved.
 * Author: Tam-LT
 * Location: ARISSmartHiddenBox - com.arisvn.arissmarthiddenbox.fragment - SelectFileDialog.java
 * Date create: 10:10:53 AM - Oct 16, 2013 - 2013
 * 
 * 
 */

package com.arisvn.arissmarthiddenbox.dialog;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.arisvn.arissmarthiddenbox.App;
import com.arisvn.arissmarthiddenbox.FileBrowserActivity;
import com.arisvn.arissmarthiddenbox.R;
import com.arisvn.arissmarthiddenbox.listener.FileDialogListener;
import com.arisvn.arissmarthiddenbox.util.SaveData;
import com.arisvn.arissmarthiddenbox.util.Utils;

/**
 * The Class SelectFileDialog.
 */
@SuppressLint("ValidFragment")
public class SelectFileDialog extends DialogFragment implements OnClickListener {
    int mode;
    private EditText filePath;
    private EditText fileName;
    private TextView fileExtension;
    private FileDialogListener listener;
    private TextView text_title;
    private Button btn_ok;
    private Button btn_cancel;
    private View view;

    private TextView showPath;
	/**
	 * Instantiates a new select file dialog.
	 *
	 * @param listener the listener
	 */
	public SelectFileDialog(FileDialogListener listener) {
		// Empty constructor required for DialogFragment
		setCancelable(false);
		this.listener = listener;
	}

	/**
	 * New instance.
	 *
	 * @param mode the mode
	 * @param listener the listener
	 * @return the select file dialog
	 */
	public static SelectFileDialog newInstance(int mode,
			FileDialogListener listener) {
		SelectFileDialog f = new SelectFileDialog(listener);
		// // Supply num input as an argument.
		Bundle args = new Bundle();
		args.putInt(Utils.FILE_MODE, mode);
		f.setArguments(args);

		return f;
	}

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null)
            return;
        int width = SaveData.getInstance(getActivity()).getDeviceWidth()
                * getActivity().getResources().getInteger(R.integer.number_with)
                / getActivity().getResources().getInteger(R.integer.number_total);
        LayoutParams params = getDialog().getWindow().getAttributes();
        params.height = LayoutParams.WRAP_CONTENT;
        params.width = width;
        getDialog().getWindow().setAttributes(params);

    }
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		mode = getArguments().getInt(Utils.FILE_MODE);
        view = inflater.inflate(R.layout.dialog_select_file, container);
		text_title = (TextView)view.findViewById(R.id.text_title);
		btn_ok = (Button)view.findViewById(R.id.btn_ok);
		btn_cancel = (Button)view.findViewById(R.id.btn_cancel);
		filePath = (EditText) view.findViewById(R.id.filePath);
		fileName = (EditText) view.findViewById(R.id.filename);
		fileExtension = (TextView) view.findViewById(R.id.file_extension);
        showPath = (TextView)view.findViewById(R.id.path);
        showPath.setSelected(true);
        filePath.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.onTouchEvent(event);
                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        });

        filePath.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Light.ttf");
        Typeface font_edittext = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Medium.ttf");
        text_title.setTypeface(font);
        btn_ok.setTypeface(font_edittext);
        btn_cancel.setTypeface(font_edittext);
        fileName.setTypeface(font_edittext);
        filePath.setTypeface(font_edittext);
        fileExtension.setTypeface(font);
        showPath.setTypeface(font);
        ((TextView)view.findViewById(R.id.title_path)).setTypeface(font);
        fileName.setText("");
        filePath.setText("");
        fileName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (filePath.getVisibility() == View.VISIBLE) {
                    if (filePath.getText().toString() != null
                            && !filePath.getText().toString().equals("")) {
                        if (fileName.getText().toString().trim().length() > 0) {
                            showPath.setText(filePath.getText().toString().trim() + "/" + s.toString().trim()
                                    + getActivity().getResources().getString(R.string.bak));
                        } else {
                            showPath.setText("");
                        }
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });
        switch (mode) {
            case Utils.EXPORT:
                filePath.setHint(R.string.file_path);
                text_title.setText(getString(R.string.export_title));
                view.findViewById(R.id.layout_filename).setVisibility(View.VISIBLE);
                break;
            case Utils.IMPORT:
                view.findViewById(R.id.layout_filename).setVisibility(View.GONE);
                filePath.setHint(R.string.file);
                text_title.setText(getString(R.string.import_title));
                break;

		default:
			break;
		}
		
		return view;
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
            case R.id.filePath:
                if (Utils.isSDCardExist()) {
                    App.needShowLogin = false;
                    if (getActivity() != null) {
                        Intent intent = new Intent(getActivity(), FileBrowserActivity.class);
                        intent.putExtra(Utils.FILE_MODE, mode);
                        startActivityForResult(intent, Utils.REQUEST_FILE);
                    }
                } else {
                    ConfirmDialogFragment.show(getActivity(), getString(R.string.unmount_sdcard));
                }

                break;
            case R.id.btn_ok:
                if (Utils.isSDCardExist()) {
                    if (listener != null) {
                        if (mode == Utils.EXPORT) {
                            if (filePath.getText().toString().trim().length() == 0
                                    || fileName.getText().toString().trim().length() == 0) {
                                ConfirmDialogFragment.show(getActivity(),
                                        getString(R.string.export_empty));
                            } else {
                                File file = new File(filePath.getText().toString().trim());
                                if (!file.exists()) {
                                    ConfirmDialogFragment.show(getActivity(),
                                            getString(R.string.export_folder_not_exit));
                                } else {
                                    listener.onFileSelected(filePath.getText().toString().trim() + "/"
                                            + fileName.getText().toString().trim() + ".bak", mode);
                                    dismiss();
                                }

                            }
                        } else if (mode == Utils.IMPORT) {
                            if (filePath.getText().toString().trim().length() == 0) {
                                ConfirmDialogFragment.show(getActivity(),
                                        getString(R.string.import_empty));
                            } else {
                                File file = new File(filePath.getText().toString().trim());
                                if (!file.exists()) {
                                    ConfirmDialogFragment.show(getActivity(),
                                            getString(R.string.import_file_not_exit));
                                } else {
                                    listener.onFileSelected(filePath.getText().toString().trim(), mode);
                                    dismiss();
                                }

                            }
                        }
                    }
                } else {
                    ConfirmDialogFragment.show(getActivity(), getString(R.string.unmount_sdcard));
                }
                break;
		case R.id.btn_cancel:
			dismiss();
			break;

		default:
			break;
		}

	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		App.needShowLogin=false;
		super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Utils.REQUEST_FILE && resultCode == Activity.RESULT_OK) {
            if (view.findViewById(R.id.layout_filename).getVisibility() == View.VISIBLE) {
                filePath.setText(data.getStringExtra(Utils.FILE_NAME));
                if (fileName.getText().toString() != null
                        && fileName.getText().toString().trim().length() != 0) {
                    showPath.setText(data.getStringExtra(Utils.FILE_NAME) + "/"
                            + fileName.getText().toString().trim()
                            + getActivity().getResources().getString(R.string.bak));
                } else {
                    showPath.setText("");
                }
            } else {
                filePath.setText(data.getStringExtra(Utils.FILE_NAME));
                showPath.setText(data.getStringExtra(Utils.FILE_NAME));
            }
        }
    }
}
