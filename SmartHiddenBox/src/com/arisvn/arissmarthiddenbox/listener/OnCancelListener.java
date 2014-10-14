package com.arisvn.arissmarthiddenbox.listener;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving picker events.
 * The class that is interested in processing a picker
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addPickerListener<code> method. When
 * the picker event occurs, that object's appropriate
 * method is invoked.
 *
 * @see PickerEvent
 */
public interface OnCancelListener {

    /**
     * On cancel.
     */
    public void onCancel ();
}
