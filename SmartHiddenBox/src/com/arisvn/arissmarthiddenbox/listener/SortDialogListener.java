package com.arisvn.arissmarthiddenbox.listener;

public interface SortDialogListener {
	public void onStartSorting(SortDirection sortDirection,
			SortCondition condition);

	enum SortDirection {
		ASC, DESC
	}

	enum SortCondition {
		NAME, SIZE, DATE
	}

}
