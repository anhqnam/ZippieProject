/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2014 Aris-vn, Inc. All rights reserved.
 * Author: Tam-LT
 * Location: ARISSmartHiddenBox - com.arisvn.arissmarthiddenbox.fragment - HiddenBasePickerFragment.java
 * Date create: 3:56:18 PM - Feb 20, 2014 - 2014
 * 
 * 
 */

package com.arisvn.arissmarthiddenbox.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.arisvn.arissmarthiddenbox.R;
import com.arisvn.arissmarthiddenbox.adapter.HiddenBaseAdapter;
import com.arisvn.arissmarthiddenbox.dialog.UserGuideDialog;
import com.arisvn.arissmarthiddenbox.entity.BaseItemEntity;
import com.arisvn.arissmarthiddenbox.listener.OnSelectedItemChangeListener;
import com.arisvn.arissmarthiddenbox.listener.SortDialogListener;
import com.arisvn.arissmarthiddenbox.util.SaveData;
import com.arisvn.arissmarthiddenbox.util.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class HiddenBasePickerFragment.
 */
public abstract class HiddenBasePickerFragment extends Fragment implements OnItemClickListener,
OnScrollListener, OnClickListener, OnItemLongClickListener, SortDialogListener {
    /** The m adapter. */
    protected HiddenBaseAdapter mAdapter;
    /** The grid view. */
    protected GridView gridView;

    /** The list view. */
    protected ListView listView;

    /** The item change listener. */
    protected OnSelectedItemChangeListener itemChangeListener;

    /** The objs. */
    protected ArrayList<BaseItemEntity> objs;
    private LinearLayout menuContent;

	private ImageView showMenu;
	private LinearLayout showMenuPanel;
	private ToggleButton modeView;
	private View view;
    /*
     * (non-Javadoc)
     * @see
     * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
     * android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_picker, container, false);
        objs = new ArrayList<BaseItemEntity>();
        setGridView((GridView) view.findViewById(R.id.gridView1));
        setListView((ListView) view.findViewById(R.id.listView1));
        getListView().setOnItemClickListener(this);
        getGridView().setOnItemClickListener(this);
        getGridView().setOnScrollListener(this);
        getListView().setOnScrollListener(this);
        getListView().setOnItemLongClickListener(this);
        getGridView().setOnItemLongClickListener(this);
    	menuContent = (LinearLayout) view.findViewById(R.id.showmenu_content);
		showMenu = (ImageView) view.findViewById(R.id.picker_show_menu_btn);
		showMenu.setBackgroundResource(menuContent.getVisibility() == View.VISIBLE ? R.drawable.btn_hidemenu
				: R.drawable.btn_showmenu);
		showMenuPanel = (LinearLayout) view.findViewById(R.id.picker_show_menu);
		showMenuPanel.setOnClickListener(this);
		view.findViewById(R.id.hide_items).setOnClickListener(this);
		modeView=(ToggleButton) view.findViewById(R.id.picker_mode_view);
		modeView.setOnClickListener(this);
		modeView.setChecked(SaveData.getInstance(getActivity()).isShowList());
		view.findViewById(R.id.picker_sort).setOnClickListener(this);
		view.findViewById(R.id.picker_selectAll).setOnClickListener(this);
        showMode();
    	TextView title = (TextView) view.findViewById(R.id.picker_title_tv);
		Utils.setTypeface(getActivity(), title, "Roboto-Medium.ttf");
		Utils.setTypeface(getActivity(),
				(TextView) view.findViewById(R.id.menu_tv_hide),
				"Roboto-Light.ttf");
		Utils.setTypeface(getActivity(),
				(TextView) view.findViewById(R.id.menu_tv_select),
				"Roboto-Light.ttf");
		disableModeView(modeView);
		((ImageView)view.findViewById(R.id.check_icon)).setBackgroundResource(R.drawable.selector_deselected);
		gridView.setNumColumns(SaveData.getInstance(getActivity()).getGridColumn());
        return view;
    }

    public void disableModeView(View view) {
        // TODO Auto-generated method stub

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        showUserGuide();
    }

    private void showUserGuide(){
        // TODO Auto-generated method stub
        if (SaveData.getInstance(getActivity()).getFirstTimeUnHideItem()) {
            List<Integer> list = new ArrayList<Integer>();
            list.add(R.drawable.picker1);
            list.add(R.drawable.picker2);
            list.add(R.drawable.sorting);
            if (list != null && list.size() > 0) {
                UserGuideDialog userGuideDialog = new UserGuideDialog(getActivity(),
                        R.style.CustomDialogTheme, list);
                userGuideDialog.show();
                SaveData.getInstance(getActivity()).setFirstTimeUnHideItem(false);
            }
        }
    }
    @Override
    public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long arg3) {
        // TODO Auto-generated method stub
        onOpen(position);
        return true;
    }

    /**
     * Gets the m adapter.
     * 
     * @return the mAdapter
     */
    public HiddenBaseAdapter getmAdapter() {
        return mAdapter;
    }

    /**
     * Sets the m adapter.
     * 
     * @param mAdapter the mAdapter to set
     */
    public void setmAdapter(HiddenBaseAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    /**
     * @return the gridView
     */
    public GridView getGridView() {
        return gridView;
    }

    /**
     * @param gridView the gridView to set
     */
    public void setGridView(GridView gridView) {
        this.gridView = gridView;
    }

    /**
     * @return the listView
     */
    public ListView getListView() {
        return listView;
    }

    /**
     * @param listView the listView to set
     */
    public void setListView(ListView listView) {
        this.listView = listView;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
            int visibleItemCount, int totalItemCount) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub
        int first = view.getFirstVisiblePosition();
        int count = view.getChildCount();
        if (scrollState == SCROLL_STATE_IDLE || (first + count > mAdapter.getCount())) {
            updateUI(view);
        } else {
            mAdapter.setLoadThumbnail(false);
        }
    }

    protected void updateUI(AbsListView absListView) {
        mAdapter.setLoadThumbnail(true);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Show mode.
     */
    public void showMode() {
        if (SaveData.getInstance(getActivity()).isShowList()) {
            showListMode();
        } else {
            showGridMode();
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
     * .AdapterView, android.view.View, int, long)
     */
    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int position,
            long id) {
        BaseItemEntity fileItem =  objs.get(position);
        boolean isCheck = fileItem.isCheck();
        fileItem.setCheck(!isCheck);
        ImageView image_selected = (ImageView) view
                .findViewById(R.id.image_selected);
        if (image_selected != null) {
            if (fileItem.isCheck()) {
                image_selected.setVisibility(View.VISIBLE);
            } else {
                image_selected.setVisibility(View.GONE);
            }
            // just update action when selected items turn from 0 to 1 or vice
            // versa.
            int numselectedItem = mAdapter.getSelectedItem().size();
            if (numselectedItem == 0 || numselectedItem == 1) {
                if (itemChangeListener != null) {
                    itemChangeListener.onSelectedItemChanged(numselectedItem);
                }
            }
        }
        setBackgroundDeselectAll();
    }
    protected void setBackgroundDeselectAll() {
        if (mAdapter.getSelectedItem().size() > 0
                && mAdapter.getSelectedItem().size() == objs.size()) {
            ((ImageView)view.findViewById(R.id.check_icon))
            .setBackgroundResource(R.drawable.selector_check_items);
        } else {
            ((ImageView)view.findViewById(R.id.check_icon))
            .setBackgroundResource(R.drawable.selector_deselected);
        }
    }

    /**
     * Select all.
     */
    public void selectAll() {
        mAdapter.checkedAll();
        if (itemChangeListener != null) {
            itemChangeListener.onSelectedItemChanged(mAdapter.getCount());
        }
    }

    /**
     * De- select all.
     */
    public void deselectAll() {
        mAdapter.uncheckedAll();
        if (itemChangeListener != null) {
            itemChangeListener.onSelectedItemChanged(0);
        }

    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.app.Fragment#onAttach(android.app.Activity)
     */
    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        try {
            itemChangeListener = (OnSelectedItemChangeListener) activity;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e(Utils.TAG, getClass().getName() + "::" + e.toString() + ":  "
                    + activity.getClass().getName()
                    + " have to implement OnSelectedItemChangeListener");
        }
    }

    public abstract void sort(SortDirection sortDirection, SortCondition sortCondition);

    public abstract void showGridMode();

    public abstract void showListMode();

    public abstract void hideFile();

    protected abstract void onOpen(int position);
    protected abstract void showSortDialog();

    @Override
    public void onClick(View v) {
    	// TODO Auto-generated method stub
    	switch (v.getId()) {
		case R.id.picker_show_menu:
			switch (menuContent.getVisibility()) {
			case View.GONE:
				// show menu panel
				menuContent.setVisibility(View.VISIBLE);
				showMenu.setBackgroundResource(R.drawable.btn_hidemenu);
				break;
			case View.VISIBLE:
				// hide menu panel
				menuContent.setVisibility(View.GONE);
				showMenu.setBackgroundResource(R.drawable.btn_showmenu);
				break;
			default:
				break;
			}

			break;
			case R.id.hide_items:
				hideFile();
				break;
			case R.id.picker_mode_view:
				boolean isShowGrid=modeView.isChecked();
				if (isShowGrid) {
					//turn grid to list
					showListMode();
				} else {
					//turn list to grid
					showGridMode();
				}
				break;
			case R.id.picker_sort:
				showSortDialog();
				break;
			case R.id.picker_selectAll:
				 if (isAllSelected()) {
	                   deselectAll();
	                   ((ImageView)view.findViewById(R.id.check_icon)).setBackgroundResource(R.drawable.selector_deselected);
	                   
	                } else {
	                   selectAll();
	                   ((ImageView)view.findViewById(R.id.check_icon)).setBackgroundResource(R.drawable.selector_check_items);
	                }
				break;

		default:
			break;
		}

    	
    }
	/**
	 * Checks if is all selected.
	 * 
	 * @return true, if is all selected
	 */
	private boolean isAllSelected() {
		// TODO Auto-generated method stub
		for (BaseItemEntity baseItemEntity : objs) {
			if (!baseItemEntity.isCheck()) {
				return false;
			}
		}
		return true;
	}
	@Override
	public void onStartSorting(SortDirection sortDirection,
			SortCondition condition) {
		// TODO Auto-generated method stub
		sort(sortDirection, condition);
		
	}

}
