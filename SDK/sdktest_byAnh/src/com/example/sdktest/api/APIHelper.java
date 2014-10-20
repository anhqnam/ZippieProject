package com.example.sdktest.api;

import unique.packagename.sdkwrapper.avatars.AvatarWrapper;
import unique.packagename.sdkwrapper.callslog.CallsLogWrapper;
import unique.packagename.sdkwrapper.contacts.ContactsWrapper;
import unique.packagename.sdkwrapper.registration.RegistrationWrapper;
import unique.packagename.sdkwrapper.registration.id.IRegistrationStateProvider;
import unique.packagename.sdkwrapper.settings.SettingsWrapper;
import android.content.Context;

public class APIHelper {

    private static APIHelper mAPIHelper;
    private RegistrationWrapper mRegistrationWrapper;
    private ContactsWrapper mContactsWrapper;
    private SettingsWrapper mSettingsWrapper;
    private AvatarWrapper mAvatarWrapper;
    private CallsLogWrapper mCallslogWrapper;

    private APIHelper() {
        // TODO Auto-generated constructor stub

    }

    public static APIHelper getInstance()
    {
        if (mAPIHelper == null) {
            synchronized (APIHelper.class) {
                if (mAPIHelper == null) {
                    mAPIHelper = new APIHelper();
                }
            }
        }
        return mAPIHelper;
    }

    public RegistrationWrapper getRegistrationWrapper(Context pContext,IRegistrationStateProvider iIRegistrationStateProviderID,
            unique.packagename.sdkwrapper.registration.number.IRegistrationStateProvider iIRegistrationStateProviderNumber)
    {
        if(mRegistrationWrapper == null)
        {
            mRegistrationWrapper = new RegistrationWrapper(pContext, iIRegistrationStateProviderID, iIRegistrationStateProviderNumber);

        }
        return mRegistrationWrapper;
    }

    public ContactsWrapper getContactsWrapper()
    {
        if(mContactsWrapper == null)
        {
            mContactsWrapper = new ContactsWrapper();
        }
        return mContactsWrapper;
    }

    public SettingsWrapper getSettingsWrapper()
    {
        if(mSettingsWrapper == null)
        {
            mSettingsWrapper = new SettingsWrapper();
        }
        return mSettingsWrapper;
    }

    public AvatarWrapper getAvatarWrapper()
    {
        if(mAvatarWrapper == null)
        {
            mAvatarWrapper = new AvatarWrapper();
        }
        return mAvatarWrapper;
    }

    public CallsLogWrapper getCallslogWrapper()
    {
        if(mCallslogWrapper == null)
        {
            mCallslogWrapper = new CallsLogWrapper();
        }
        return mCallslogWrapper;
    }


}