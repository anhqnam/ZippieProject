
package com.lunextelecom.zippie.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lunextelecom.zippie.R;
import com.lunextelecom.zippie.utils.Utils;

public class ContactListFragment extends Fragment {
    public static ContactListFragment newInstance(String title) {
        ContactListFragment frag = new ContactListFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.layout_contact, container, false);
        TextView text = (TextView)view.findViewById(R.id.textView1);
        String title = getArguments().getString("title", "Enter Name");
        if (title == Utils.FRAGMENT_CONTACT_ALL) {
            text.setText("ABC");
        } else if (title == Utils.FRAGMENT_CONTACT_VIPPE) {
            text.setText("BDF");
        } else if (title == Utils.FRAGMENT_CONTACT_FAVORITES) {
            text.setText("IGH");
        } else if (title == Utils.FRAGMENT_CONTACT_SEARCH) {
            text.setText("KML");
        }
        return view;
    }
}
