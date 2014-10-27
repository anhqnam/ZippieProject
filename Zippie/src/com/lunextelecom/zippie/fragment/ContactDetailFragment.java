/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Lunextelecom, Inc. All rights reserved.
 * Author: AnhBui
 * Location: Zippie - com.lunextelecom.zippie - SignUpActivity.java
 * 
 */
package com.lunextelecom.zippie.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lunextelecom.zippie.R;
import com.lunextelecom.zippie.bean.ContactObject;

// TODO: Auto-generated Javadoc
/**
 * The Class ContactDetailFragment.
 */
public class ContactDetailFragment extends Fragment implements OnClickListener {

    /** The m contact. */
    private ContactObject mContact;

    /** The m paybill active. */
    private RelativeLayout mPremiumCall, mPremiumCallActive, mEgift, mEgiftActive, mTopup,
    mTopupActive, mMoneyTransfer, mMoneyTransferActive, mPaybill, mPaybillActive;

    /** The m contact menu. */
    private ImageView mContactMenu;

    /** The m pre. */
    private int mPre = 0;

    /** The m eg. */
    private int mEg = 1;

    /** The m top. */
    private int mTop = 2;

    /** The m mo. */
    private int mMo = 3;

    /** The m pay. */
    private int mPay = 4;

    /**
     * New instance.
     *
     * @param contactDetail the contact detail
     * @return the contact detail fragment
     */
    public static ContactDetailFragment newDetailFragment(ContactObject contactDetail) {
        ContactDetailFragment frag = new ContactDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("contactDetail", contactDetail);
        frag.setArguments(args);
        return frag;
    }

    /* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.contact_fragment_contactdetail_layout, container,
                false);
        mContact = (ContactObject)getArguments().getSerializable("contactDetail");
        TextView mPhoneName = (TextView)view.findViewById(R.id.contactdetail_contactname_tv_id);
        ImageView mStatus = (ImageView)view.findViewById(R.id.contactdetail_status_iv_id);
        mContactMenu = (ImageView)view.findViewById(R.id.contactdetail_information_iv_id);
        mPremiumCall = (RelativeLayout)view.findViewById(R.id.contactdetail_callpremium_rl_id);
        mPremiumCallActive = (RelativeLayout)view
                .findViewById(R.id.contactdetail_callpremium_active_rl_id);

        mEgift = (RelativeLayout)view.findViewById(R.id.contactdetail_egift_rl_id);
        mEgiftActive = (RelativeLayout)view.findViewById(R.id.contactdetail_egift_active_rl_id);

        mTopup = (RelativeLayout)view.findViewById(R.id.contactdetail_topup_rl_id);
        mTopupActive = (RelativeLayout)view.findViewById(R.id.contactdetail_topup_active_rl_id);

        mMoneyTransfer = (RelativeLayout)view.findViewById(R.id.contactdetail_money_transfer_rl_id);
        mMoneyTransferActive = (RelativeLayout)view
                .findViewById(R.id.contactdetail_money_transfer_active_rl_id);

        mPaybill = (RelativeLayout)view.findViewById(R.id.contactdetail_pay_bill_rl_id);
        mPaybillActive = (RelativeLayout)view
                .findViewById(R.id.contactdetail_pay_bill_active_rl_id);
        view.findViewById(R.id.contactdetail_editcontact_iv_id).setOnClickListener(this);
        view.findViewById(R.id.contactdetail_back_iv_id).setOnClickListener(this);
        mPremiumCall.setOnClickListener(this);
        mPremiumCallActive.setOnClickListener(this);
        mEgift.setOnClickListener(this);
        mEgiftActive.setOnClickListener(this);
        mTopup.setOnClickListener(this);
        mTopupActive.setOnClickListener(this);
        mMoneyTransfer.setOnClickListener(this);
        mMoneyTransferActive.setOnClickListener(this);
        mPaybill.setOnClickListener(this);
        mPaybillActive.setOnClickListener(this);
        if (mContact.getcStatus()) {
            mStatus.setVisibility(View.VISIBLE);
        } else {
            mStatus.setVisibility(View.GONE);
        }
        mPhoneName.setText(mContact.getcName().toString());
        setVisibleAction(2);
        return view;
    }

    /**
     * Sets the visible action.
     *
     * @param id the new visible action
     */
    private void setVisibleAction(int id) {
        if (id == mPre) {
            mPremiumCall.setVisibility(View.GONE);
            mPremiumCallActive.setVisibility(View.VISIBLE);

            mEgift.setVisibility(View.VISIBLE);
            mEgiftActive.setVisibility(View.GONE);

            mTopup.setVisibility(View.VISIBLE);
            mTopupActive.setVisibility(View.GONE);

            mMoneyTransfer.setVisibility(View.VISIBLE);
            mMoneyTransferActive.setVisibility(View.GONE);

            mPaybill.setVisibility(View.VISIBLE);
            mPaybillActive.setVisibility(View.GONE);
            Bitmap myImg = BitmapFactory.decodeResource(getResources(),
                    R.drawable.contact_detail_background_premium_dra);
            mContactMenu.setImageBitmap(myImg);
        } else if (id == mEg) {
            mPremiumCall.setVisibility(View.VISIBLE);
            mPremiumCallActive.setVisibility(View.GONE);

            mEgift.setVisibility(View.GONE);
            mEgiftActive.setVisibility(View.VISIBLE);

            mTopup.setVisibility(View.VISIBLE);
            mTopupActive.setVisibility(View.GONE);

            mMoneyTransfer.setVisibility(View.VISIBLE);
            mMoneyTransferActive.setVisibility(View.GONE);

            mPaybill.setVisibility(View.VISIBLE);
            mPaybillActive.setVisibility(View.GONE);
            Bitmap myImg = BitmapFactory.decodeResource(getResources(),
                    R.drawable.contact_detail_background_egift_dra);
            mContactMenu.setImageBitmap(myImg);
        } else if (id == mTop) {
            mPremiumCall.setVisibility(View.VISIBLE);
            mPremiumCallActive.setVisibility(View.GONE);

            mEgift.setVisibility(View.VISIBLE);
            mEgiftActive.setVisibility(View.GONE);

            mTopup.setVisibility(View.GONE);
            mTopupActive.setVisibility(View.VISIBLE);

            mMoneyTransfer.setVisibility(View.VISIBLE);
            mMoneyTransferActive.setVisibility(View.GONE);

            mPaybill.setVisibility(View.VISIBLE);
            mPaybillActive.setVisibility(View.GONE);
            Bitmap myImg = BitmapFactory.decodeResource(getResources(),
                    R.drawable.contact_detail_background_topup_dra);
            mContactMenu.setImageBitmap(myImg);
        } else if (id == mMo) {
            mPremiumCall.setVisibility(View.VISIBLE);
            mPremiumCallActive.setVisibility(View.GONE);

            mEgift.setVisibility(View.VISIBLE);
            mEgiftActive.setVisibility(View.GONE);

            mTopup.setVisibility(View.VISIBLE);
            mTopupActive.setVisibility(View.GONE);

            mMoneyTransfer.setVisibility(View.GONE);
            mMoneyTransferActive.setVisibility(View.VISIBLE);

            mPaybill.setVisibility(View.VISIBLE);
            mPaybillActive.setVisibility(View.GONE);
            Bitmap myImg = BitmapFactory.decodeResource(getResources(),
                    R.drawable.contact_detail_background_money_dra);
            mContactMenu.setImageBitmap(myImg);
        } else if (id == mPay) {
            mPremiumCall.setVisibility(View.VISIBLE);
            mPremiumCallActive.setVisibility(View.GONE);

            mEgift.setVisibility(View.VISIBLE);
            mEgiftActive.setVisibility(View.GONE);

            mTopup.setVisibility(View.VISIBLE);
            mTopupActive.setVisibility(View.GONE);

            mMoneyTransfer.setVisibility(View.VISIBLE);
            mMoneyTransferActive.setVisibility(View.GONE);

            mPaybill.setVisibility(View.GONE);
            mPaybillActive.setVisibility(View.VISIBLE);
            Bitmap myImg = BitmapFactory.decodeResource(getResources(),
                    R.drawable.contact_detail_background_paybill_dra);
            mContactMenu.setImageBitmap(myImg);
        }
    }

    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.contactdetail_callpremium_rl_id:
                setVisibleAction(0);
                break;
            case R.id.contactdetail_egift_rl_id:
                setVisibleAction(1);

                break;
            case R.id.contactdetail_topup_rl_id:
                setVisibleAction(2);

                break;
            case R.id.contactdetail_money_transfer_rl_id:
                setVisibleAction(3);

                break;
            case R.id.contactdetail_pay_bill_rl_id:
                setVisibleAction(4);
                break;
            case R.id.contactdetail_editcontact_iv_id:
            	int method = 10;
            	if(mContactDetailCallBack != null){
            		mContactDetailCallBack.callbackEditContact(mContact, method);
            	}
                break;
            case R.id.contactdetail_back_iv_id:
            	int methodBack = 11;
            	ContactObject contact = new ContactObject();
            	if(mContactDetailCallBack != null){
            		mContactDetailCallBack.callbackEditContact(contact, methodBack);
            	}
            default:
                break;
        }
    }
    private ContactDetailListener mContactDetailCallBack;

   
    public interface ContactDetailListener {
        public void callbackEditContact(ContactObject result,Integer method);
    }

    public void setOnClickEditContact(ContactDetailListener listener){
        this.mContactDetailCallBack = listener;
    }
}
