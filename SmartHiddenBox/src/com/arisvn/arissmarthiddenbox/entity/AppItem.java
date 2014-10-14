package com.arisvn.arissmarthiddenbox.entity;

import android.graphics.drawable.Drawable;

public class AppItem extends BaseItemEntity {

	

	/**
	 * @param label
	 * @param name
	 * @param packageName
	 * @param icon
	 * @param size
	 */
	public AppItem(String label, String name, String packageName,
			Drawable icon, long size) {
		super();
		this.label = label;
		this.name = name;
		this.packageName = packageName;
		this.icon = icon;
		this.size = size;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the packageName
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @param packageName
	 *            the packageName to set
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	/**
	 * @return the size
	 */
	public long getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(long size) {
		this.size = size;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String label;
	private String name;
	private String packageName;
	private Drawable icon;
	private long size;
}
