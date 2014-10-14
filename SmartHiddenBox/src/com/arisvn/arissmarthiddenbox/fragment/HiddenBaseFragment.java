
package com.arisvn.arissmarthiddenbox.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.arisvn.arissmarthiddenbox.R;
import com.arisvn.arissmarthiddenbox.adapter.HiddenBaseAdapter;
import com.arisvn.arissmarthiddenbox.dialog.ConfirmDialogFragment;
import com.arisvn.arissmarthiddenbox.dialog.PrivacyDialog;
import com.arisvn.arissmarthiddenbox.dialog.SelectFileDialog;
import com.arisvn.arissmarthiddenbox.dialog.UserGuideDialog;
import com.arisvn.arissmarthiddenbox.entity.BaseItemEntity;
import com.arisvn.arissmarthiddenbox.listener.DialogListener;
import com.arisvn.arissmarthiddenbox.listener.FileDialogListener;
import com.arisvn.arissmarthiddenbox.listener.OnSelectedItemChangeListener;
import com.arisvn.arissmarthiddenbox.util.SaveData;
import com.arisvn.arissmarthiddenbox.util.Utils;

public abstract class HiddenBaseFragment extends Fragment implements
		OnScrollListener, OnItemClickListener, FileDialogListener,
		OnClickListener {
    /** The objs. */
     protected ArrayList< BaseItemEntity> objs;

    /** The grid view. */
    protected GridView gridView;

    /** The list view. */
    protected ListView listView;

    /** The m adapter. */
    protected HiddenBaseAdapter mAdapter;

    /** The is edit. */
    protected boolean isEdit;

    /** The item change listener. */
    protected OnSelectedItemChangeListener itemChangeListener;

	private LinearLayout menuContent;

	private ImageView showMenu;
	private LinearLayout showMenuPanel;
	private ToggleButton modeView;
	private View view;
    private PrivacyDialog confirmDialog;

	/*
	 * (non-Javadoc)
	 * 
     * @see
     * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
     * android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        makeFolder();
        view = inflater.inflate(R.layout.layout_base_box, container, false);
        gridView = (GridView)view.findViewById(R.id.gridView1);
        listView = (ListView)view.findViewById(R.id.listView1);
        gridView.setOnItemClickListener(this);
        listView.setOnItemClickListener(this);
        listView.setOnScrollListener(this);
        gridView.setOnScrollListener(this);
        showMode();
        onLoadHiddenItems();
		menuContent = (LinearLayout) view.findViewById(R.id.showmenu_content);
		showMenu = (ImageView) view.findViewById(R.id.show_menu_btn);
		showMenu.setBackgroundResource(menuContent.getVisibility() == View.VISIBLE ? R.drawable.btn_hidemenu
				: R.drawable.btn_showmenu);
		showMenuPanel = (LinearLayout) view.findViewById(R.id.show_menu);
		showMenuPanel.setOnClickListener(this);
		TextView title = (TextView) view.findViewById(R.id.hidden_title_tv);
		title.setText(getTitle());
		Utils.setTypeface(getActivity(), title, "Roboto-Medium.ttf");
		Utils.setTypeface(getActivity(),
				(TextView) view.findViewById(R.id.menu_tv_Add),
				"Roboto-Light.ttf");
		Utils.setTypeface(getActivity(),
				(TextView) view.findViewById(R.id.menu_tv_backup),
				"Roboto-Light.ttf");
		Utils.setTypeface(getActivity(),
				(TextView) view.findViewById(R.id.menu_tv_delete),
				"Roboto-Light.ttf");
		Utils.setTypeface(getActivity(),
				(TextView) view.findViewById(R.id.menu_tv_restore),
				"Roboto-Light.ttf");
		Utils.setTypeface(getActivity(),
				(TextView) view.findViewById(R.id.menu_tv_select),
				"Roboto-Light.ttf");
		Utils.setTypeface(getActivity(),
				(TextView) view.findViewById(R.id.menu_tv_unhide),
				"Roboto-Light.ttf");
		view.findViewById(R.id.add).setOnClickListener(this);
		view.findViewById(R.id.unhide).setOnClickListener(this);
		view.findViewById(R.id.delete).setOnClickListener(this);
		view.findViewById(R.id.restore).setOnClickListener(this);
		view.findViewById(R.id.backup).setOnClickListener(this);
		view.findViewById(R.id.selectAll).setOnClickListener(this);
		modeView=(ToggleButton) view.findViewById(R.id.mode_view);
		modeView.setOnClickListener(this);
		modeView.setChecked(SaveData.getInstance(getActivity()).isShowList());
		disableModeView(modeView);
		((ImageView)view.findViewById(R.id.base_check_icon)).setBackgroundResource(R.drawable.selector_deselected);
		gridView.setNumColumns(SaveData.getInstance(getActivity()).getGridColumn());
        cancel();
        return view;
    }



    protected void disableModeView(ToggleButton modeView) {
		// TODO Auto-generated method stub
		
	}



	@Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
            int totalItemCount) {
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
        // TODO Auto-generated method stub
        mAdapter.setLoadThumbnail(true);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * @return the mAdapter
     */
    public HiddenBaseAdapter getmAdapter() {
        return mAdapter;
    }

    public boolean flagChangeData;

    /**
     * @param mAdapter the mAdapter to set
     */
    public void setmAdapter(HiddenBaseAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    /**
     * Checks if is edits the.
     * 
     * @return the isEdit
     */
    public boolean isEdit() {
        return isEdit;
    }

    /**
     * Sets the edits the.
     * 
     * @param isEdit the isEdit to set
     */
    public void setEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }
    /**
     * Cancel.
     */
    public void cancel() {
        isEdit = false;
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
        Log.e(Utils.TAG, "onAttach");
        try {
			itemChangeListener = (OnSelectedItemChangeListener) activity;

        } catch (Exception e) {
            // TODO: handle exception
            Log.e(Utils.TAG, activity.getClass().getName()
                    + " have to implement OnSelectedItemChangeListener");
        }
        objs = new ArrayList<BaseItemEntity>();
    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        flagChangeData = true;
        showUserGuide();
    }
    private void showUserGuide() {
        // TODO Auto-generated method stub
        if (SaveData.getInstance(getActivity()).getFirstTimeHideItem()) {
            List<Integer> list = new ArrayList<Integer>();
            list.add(R.drawable.file_menu);
            list.add(R.drawable.file_menu1);
            list.add(R.drawable.grid_list);
            if (list != null && list.size() > 0) {
                UserGuideDialog userGuideDialog = new UserGuideDialog(getActivity(),
                        R.style.CustomDialogTheme, list);
                userGuideDialog.show();
                SaveData.getInstance(getActivity()).setFirstTimeHideItem(false);
            }
        }
    }



    /**
     * Show manage.
     */
    public void manage() {
        isEdit = true;
    }
    /*
     * (non-Javadoc)
     * @see android.support.v4.app.Fragment#onDestroy()
     */
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    /**
     * Display format's gridview.
     */


    /**
     * Show mode.
     */
    protected void showMode() {

        if (SaveData.getInstance(getActivity()).isShowList()) {
            showListMode();
        } else {
            showGridMode();
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
     * @see
     * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
     * .AdapterView, android.view.View, int, long)
     */
    @Override
    public void onItemClick(AdapterView<?> apdater, View view, int position, long id) {

        if (isEdit) {
            BaseItemEntity fileItem = objs.get(position);
            boolean isCheck = fileItem.isCheck();
            fileItem.setCheck(!isCheck);
            ImageView image_selected = (ImageView)view.findViewById(R.id.image_selected);
            if (image_selected != null) {
                if (fileItem.isCheck()) {
                    image_selected.setVisibility(View.VISIBLE);
                } else {
                    image_selected.setVisibility(View.GONE);
                }
            }
            // just update action when selected items turn from 0 to 1 or vice
            // versa.
            int numSelectedItems = mAdapter.getSelectedItem().size();
            if (numSelectedItems == 0 || numSelectedItems == 1) {
                if (itemChangeListener != null) {
                    itemChangeListener.onSelectedItemChanged(numSelectedItems);
                }
            }
            setBackgroundDeselectAll();

        } else {
            onOpen(position);
        }

    }
    /**
     * Start backup progress.
     */
    public void startBackupProgress() {
        // TODO Auto-generated method stub
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag(Utils.SELECT_FILE_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = SelectFileDialog.newInstance(Utils.EXPORT, this);
        newFragment.show(ft, Utils.SELECT_FILE_DIALOG);

    }

    /**
     * Start import progress.
     */
    public void startRestoreFilesProgress() {
        // TODO Auto-generated method stub
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag(Utils.SELECT_FILE_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        // Create and show the dialog.
        DialogFragment newFragment = SelectFileDialog.newInstance(Utils.IMPORT, this);
        newFragment.show(ft, Utils.SELECT_FILE_DIALOG);
    }
    /*
     * (non-Javadoc)
     * @see
     * com.arisvn.arissmarthiddenbox.listener.FileDialogListener#onFileSelected
     * (java.lang.String, int)
     */
    @Override
    public void onFileSelected(String path, int mode) {
        // TODO Auto-generated method stub
        String[] params = {
                path, "" + mode
        };
        switch (mode) {
            case Utils.EXPORT:
                onBackup(params);
                break;
            case Utils.IMPORT:
                onRestore(params);
            default:
                break;
        }

    }
    protected abstract void onOpen(int position);
    public abstract void unHide();
    public abstract void delete();
    public abstract void pickerAction();
    public abstract void makeFolder();
    public abstract void onLoadHiddenItems();
    public abstract void showListMode();
    public abstract void showGridMode();
    protected abstract void onBackup(String[] params);
    protected abstract void onRestore(String[] params);

	protected abstract String getTitle();

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.show_menu:
			switch (menuContent.getVisibility()) {
			case View.GONE:
				// show menu panel
				menuContent.setVisibility(View.VISIBLE);
				showMenu.setBackgroundResource(R.drawable.btn_hidemenu);
				manage();
				break;
			case View.VISIBLE:
				// hide menu panel
				menuContent.setVisibility(View.GONE);
				showMenu.setBackgroundResource(R.drawable.btn_showmenu);
				cancel();
				break;

			default:
				break;
			}

			break;
		case R.id.add:
			pickerAction();
			break;
		case R.id.unhide:
			unHide();
			break;
		case R.id.delete:
//			delete();
			showDeleteConfirmDialog();
			break;
		case R.id.backup:
			 if (Utils.isSDCardExist()) {
				 startBackupProgress();
             } else {
                 ConfirmDialogFragment.show(getActivity(), getString(R.string.unmount_sdcard));
             }
			break;
		case R.id.restore:
			  if (Utils.isSDCardExist()) {
				  startRestoreFilesProgress();
              } else {
                  ConfirmDialogFragment.show(getActivity(), getString(R.string.unmount_sdcard));
              }
			break;
		case R.id.selectAll:
			 if (isAllSelected()) {
                 deselectAll();
                 ((ImageView)view.findViewById(R.id.base_check_icon)).setBackgroundResource(R.drawable.selector_deselected);
                 
              } else {
                 selectAll();
                 ((ImageView)view.findViewById(R.id.base_check_icon)).setBackgroundResource(R.drawable.selector_check_items);
              }
			break;
		case R.id.mode_view:
			boolean isShowGrid=modeView.isChecked();
			if (isShowGrid) {
				//turn grid to list
				showListMode();
			} else {
				//turn list to grid
				showGridMode();
			}
			break;

		default:
			break;
		}

	}
    protected void setBackgroundDeselectAll() {
        if (mAdapter.getSelectedItem().size() > 0
                && mAdapter.getSelectedItem().size() == objs.size()) {
            ((ImageView)this.view.findViewById(R.id.base_check_icon))
            .setBackgroundResource(R.drawable.selector_check_items);
        } else {
            ((ImageView)this.view.findViewById(R.id.base_check_icon))
            .setBackgroundResource(R.drawable.selector_deselected);
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

    protected void showDeleteConfirmDialog() {
    	confirmDialog = new PrivacyDialog(getActivity(), new DialogListener() {

            @Override
            public void onCancel() {
                // TODO Auto-generated method stub
                confirmDialog.dismiss();
            }

            @Override
            public void onAccept() {
                // TODO Auto-generated method stub
            	confirmDialog.dismiss();
            	delete();

            }
        },getString(R.string.confirmation),getString(R.string.delete_confirmation),getString(R.string.cancel),getString(R.string.delete));
        confirmDialog.show();
    }

}
