package com.merahputih.kabar.interface_adapter;

import android.app.Activity;
import android.content.Context;

import com.merahputih.kabar.custom_activities.CustomLoginActivity;

import sdk.chat.ui.BaseInterfaceAdapter;
import sdk.chat.ui.activities.LoginActivity;
import sdk.chat.ui.extras.MainDrawerActivity;

public class MyAppInterfaceAdapter extends BaseInterfaceAdapter {
    public MyAppInterfaceAdapter(Context context) {
        super(context);
    }

    @Override
    public Class<? extends Activity> getLoginActivity() {
        return CustomLoginActivity.class;
    }

    @Override
    public Class<? extends Activity> getMainActivity() {
        return MainDrawerActivity.class;
    }
}
