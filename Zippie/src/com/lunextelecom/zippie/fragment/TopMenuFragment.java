/*
 * @author Vuong Huynh
 * Copyright (C) 2014 Lunextelecom
 */
package com.lunextelecom.zippie.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lunextelecom.zippie.R;
import com.lunextelecom.zippie.utils.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class TopMenuFragment.
 */
public class TopMenuFragment extends Fragment implements OnClickListener{

    /** The m tv point. */
    private TextView mTopMenuPointTv;

    /** The m tv item selected header. */
    private TextView mTopMenuItemSelectedDetailHeaderTv;

    /** The m tv item selected content. */
    private TextView mTopMenuItemSelectedDetailContentTv;

    /** The m iv circle selected. */
    private ImageView mTopMenuCircleSelectedIv;

    /** The m premium call. */
    private View mTopMenuPremiumCallView;

    /** The m premium call active. */
    private View mTopMenuPremiumCallActiveView;

    /** The m e gift. */
    private View mTopMenuEGiftView;

    /** The m e gift active. */
    private View mTopMenuEGiftActiveView;

    /** The m money trans. */
    private View mTopMenuMoneyTransView;

    /** The m money trans active. */
    private View mTopMenuMoneyTransActiveView;

    /** The m points. */
    private View mTopMenuPointsView;

    /** The m points active. */
    private View mTopMenuPointsActiveView;

    /** The m pay bill. */
    private View mTopMenuPayBillView;

    /** The m pay bill active. */
    private View mTopMenuPayBillActiveView;

    /** The m top up. */
    private View mTopMenuTopUpView;

    /** The m top up active. */
    private View mTopMenuTopUpActiveView;

    /** The m contacts. */
    private View mTopMenuContactsView;

    /** The m message. */
    private View mTopMenuMessageView;

    /** The m call. */
    private View mTopMenuCallView;

    /** The m current view. */
    private View mCurrentView;

    /** The m current view active. */
    private View mCurrentViewActive;

    /* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.topmenu_lay, null);
        TextView tvTextPoint = (TextView)view.findViewById(R.id.topmenu_header_text_point_id);
        mTopMenuPointTv = (TextView)view.findViewById(R.id.topmenu_header_point_id);
        mTopMenuItemSelectedDetailHeaderTv = (TextView)view.findViewById(R.id.topmenu_item_selected_detail_header_id);
        mTopMenuItemSelectedDetailContentTv = (TextView)view.findViewById(R.id.topmenu_item_selected_detail_content_id);
        TextView topMenuItemSelectedDetailFooterTv = (TextView)view.findViewById(R.id.topmenu_item_selected_detail_footer_id);
        mTopMenuCircleSelectedIv = (ImageView)view.findViewById(R.id.topmenu_circle_select_id);

        mTopMenuPremiumCallView = view.findViewById(R.id.topmenu_premium_call_id);
        TextView topMenuPremiumCallTextTv = (TextView)view.findViewById(R.id.topmenu_premium_call_text_id);
        mTopMenuPremiumCallActiveView = view.findViewById(R.id.topmenu_premium_call_active_id);
        TextView topMenuPremiumCallTextActiveTv = (TextView)view.findViewById(R.id.topmenu_premium_call_text_active_id);

        mTopMenuEGiftView = view.findViewById(R.id.topmenu_egift_id);
        TextView topMenuEGiftTextTv = (TextView)view.findViewById(R.id.topmenu_egift_text_id);
        mTopMenuEGiftActiveView = view.findViewById(R.id.topmenu_egift_active_id);
        TextView topMenuEGiftTexActiveTv = (TextView)view.findViewById(R.id.topmenu_egift_text_active_id);

        mTopMenuMoneyTransView = view.findViewById(R.id.topmenu_money_transfer_id);
        TextView topMenuMoneyTransTextTv = (TextView)view.findViewById(R.id.topmenu_money_transfer_text_id);
        mTopMenuMoneyTransActiveView = view.findViewById(R.id.topmenu_money_transfer_active_id);
        TextView topMenuMoneyTranstextActiveTv = (TextView)view.findViewById(R.id.topmenu_money_transfer_text_active_id);

        mTopMenuPointsView = view.findViewById(R.id.topmenu_points_id);
        TextView topMenuPointTextTv = (TextView)view.findViewById(R.id.topmenu_points_text_id);
        mTopMenuPointsActiveView = view.findViewById(R.id.topmenu_points_active_id);
        TextView topMenuPointsTextActiveTv = (TextView)view.findViewById(R.id.topmenu_points_text_active_id);

        mTopMenuPayBillView = view.findViewById(R.id.topmenu_pay_bill_id);
        TextView topMenuPayBillTextTv = (TextView)view.findViewById(R.id.topmenu_pay_bill_text_id);
        mTopMenuPayBillActiveView = view.findViewById(R.id.topmenu_pay_bill_active_id);
        TextView topMenuPayBillTextActiveTv = (TextView)view.findViewById(R.id.topmenu_pay_bill_text_active_id);

        mCurrentView = mTopMenuTopUpView = view.findViewById(R.id.topmenu_top_up_id);
        TextView topMenuTopUpTextTv = (TextView)view.findViewById(R.id.topmenu_top_up_text_id);
        mCurrentViewActive = mTopMenuTopUpActiveView = view.findViewById(R.id.topmenu_top_up_active_id);
        TextView topMenuTopUpTextActiveTv = (TextView)view.findViewById(R.id.topmenu_top_up_text_active_id);

        mTopMenuContactsView = view.findViewById(R.id.topmenu_contacts_id);
        TextView topMenuContactsTextTv = (TextView)view.findViewById(R.id.topmenu_contacts_text_id);
        mTopMenuMessageView = view.findViewById(R.id.topmenu_message_id);
        TextView topMenuMessageTextTv = (TextView)view.findViewById(R.id.topmenu_message_text_id);
        mTopMenuCallView = view.findViewById(R.id.topmenu_call_id);
        TextView topMenuCallTextTv = (TextView)view.findViewById(R.id.topmenu_call_text_id);

        Utils.setTypefaceRoboto(getActivity(), tvTextPoint, mTopMenuPointTv, mTopMenuItemSelectedDetailHeaderTv, mTopMenuItemSelectedDetailContentTv, topMenuItemSelectedDetailFooterTv,
                topMenuPremiumCallTextTv, topMenuPremiumCallTextActiveTv, topMenuEGiftTextTv, topMenuEGiftTexActiveTv, topMenuMoneyTransTextTv, topMenuMoneyTranstextActiveTv,
                topMenuPointTextTv, topMenuPointsTextActiveTv, topMenuPayBillTextTv, topMenuPayBillTextActiveTv, topMenuTopUpTextTv, topMenuTopUpTextActiveTv,
                topMenuContactsTextTv, topMenuMessageTextTv, topMenuCallTextTv);
        mTopMenuPremiumCallView.setOnClickListener(this);
        mTopMenuEGiftView.setOnClickListener(this);
        mTopMenuMoneyTransView.setOnClickListener(this);
        mTopMenuPointsView.setOnClickListener(this);
        mTopMenuPayBillView.setOnClickListener(this);
        mTopMenuTopUpView.setOnClickListener(this);
        return view;
    }

    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int id = v.getId();
        switch (id) {
            case R.id.topmenu_premium_call_id:
                selectedItemMenu(R.drawable.topmenu_circle_select_2, mTopMenuPremiumCallView, mTopMenuPremiumCallActiveView);
                break;
            case R.id.topmenu_egift_id:
                selectedItemMenu(R.drawable.topmenu_circle_select_3, mTopMenuEGiftView, mTopMenuEGiftActiveView);
                break;
            case R.id.topmenu_money_transfer_id:
                selectedItemMenu(R.drawable.topmenu_circle_select_4, mTopMenuMoneyTransView, mTopMenuMoneyTransActiveView);
                break;
            case R.id.topmenu_points_id:
                selectedItemMenu(R.drawable.topmenu_circle_select_5, mTopMenuPointsView, mTopMenuPointsActiveView);
                break;
            case R.id.topmenu_pay_bill_id:
                selectedItemMenu(R.drawable.topmenu_circle_select_6, mTopMenuPayBillView, mTopMenuPayBillActiveView);
                break;
            case R.id.topmenu_top_up_id:
                selectedItemMenu(R.drawable.topmenu_circle_select_1, mTopMenuTopUpView, mTopMenuTopUpActiveView);
                break;
            default:
                break;
        }
    }

    /**
     * Selected item menu.
     * 
     * @param idImage the id image
     * @param itemSelect the item select
     * @param itemSelectActive the item select active
     */
    private void selectedItemMenu(int idImage, View itemSelect, View itemSelectActive){
        mTopMenuCircleSelectedIv.setImageResource(idImage);
        mCurrentViewActive.setVisibility(View.GONE);
        mCurrentView.setVisibility(View.VISIBLE);
        itemSelect.setVisibility(View.GONE);
        itemSelectActive.setVisibility(View.VISIBLE);
        mCurrentView = itemSelect;
        mCurrentViewActive = itemSelectActive;
    }
}
