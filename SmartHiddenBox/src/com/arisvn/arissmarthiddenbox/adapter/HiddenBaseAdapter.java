
package com.arisvn.arissmarthiddenbox.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arisvn.arissmarthiddenbox.entity.BaseItemEntity;
import com.arisvn.arissmarthiddenbox.entity.FileItem;
import com.arisvn.arissmarthiddenbox.util.lazyloading.ImageLoader;

public abstract class HiddenBaseAdapter extends BaseAdapter {
    /** The activity. */
    protected Activity activity;
    /** The objs. */
    protected ArrayList<? extends BaseItemEntity> objs;
    /** The image loader. */
    protected ImageLoader imageLoader;
    /** The type. */
    protected int type;

    protected boolean isLoadThumbnail;

    /** The is show list. */
    protected boolean isShowList;

    public HiddenBaseAdapter(Activity activity, ArrayList<? extends BaseItemEntity> fileItems,
            int type) {
        super();
        this.activity = activity;
        this.type = type;
        imageLoader = new ImageLoader(activity, this.type);
        imageLoader.memoryCache.clearCache();
        setLoadThumbnail(true);
        this.objs = fileItems;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (objs != null) {
            return objs.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return objs.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * The Class ViewHolder.
     */
    public static class ViewHolder {
        /** The img view flag. */
        public ImageView imgViewFlag;
        /** The image_selected. */
        public ImageView image_selected;
        /** The txt view title. */
        public TextView txtViewTitle;
        /** The txt size. */
        public TextView txtSize;
    }

    /**
     * Show list or Grid.
     * 
     * @param isShowList the new show list
     */
    public void setShowList(boolean isShowList) {
        this.isShowList = isShowList;
    }

    /**
     * @return the isLoadThumbnail
     */
    public boolean isLoadThumbnail() {
        return isLoadThumbnail;
    }

    /**
     * @param isLoadThumbnail the isLoadThumbnail to set
     */
    public void setLoadThumbnail(boolean isLoadThumbnail) {
        this.isLoadThumbnail = isLoadThumbnail;
    }

    /**
     * Select objects.
     */
    public void checkedAll() {
        if (getSelectedItem().size() == objs.size()) {
            return;
        }
        for (int i = 0; i < objs.size(); i++) {
            setChecked(i, true);
        }
        this.notifyDataSetChanged();
    }

    /**
     * De-select objects.
     */
    public void uncheckedAll() {
        if (getSelectedItem().size() == 0) {
            return;
        }
        for (int i = 0; i < objs.size(); i++) {
            setChecked(i, false);
        }
        this.notifyDataSetChanged();
    }

    /**
     * Store checked position.
     * 
     * @param pos the pos
     * @param isCheck the is check
     */
    public void setChecked(int pos, boolean isCheck) {
        objs.get(pos).setCheck(isCheck);
    }

    /**
     * Get selected objects.
     * 
     * @return the selected item
     */
    public abstract ArrayList<? extends BaseItemEntity> getSelectedItem();

    /**
     * Remove a object in adapter.
     * 
     * @param obj the obj
     */
    public void remove(FileItem obj) {
        this.objs.remove(obj);
    }

    /**
     * Remove objects in adapter.
     * 
     * @param objs the objs
     */
    public void remove(ArrayList<FileItem> objs) {
        for (FileItem fileItem : objs) {
            this.objs.remove(fileItem);
        }
    }

    /**
     * Gets the type.
     * 
     * @return the type
     */
    public int getType() {
        return type;
    }
}
