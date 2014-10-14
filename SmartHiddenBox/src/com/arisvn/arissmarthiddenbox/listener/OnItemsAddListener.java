package com.arisvn.arissmarthiddenbox.listener;

public interface OnItemsAddListener {
	public void onAddItemsModeSelected(AddFileMode addFileMode);

	public enum AddFileMode {
		ALL, FOLDER
	}
}
