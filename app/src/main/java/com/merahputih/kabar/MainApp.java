package com.merahputih.kabar;

import android.app.Activity;
import android.app.Application;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import com.merahputih.kabar.custom_activities.CustomLoginActivity;
import com.merahputih.kabar.custom_activities.CustomMainAppBarActivity;
import com.merahputih.kabar.interface_adapter.MyAppInterfaceAdapter;
import com.merahputih.kabar.settings.Constants;

import sdk.chat.app.firebase.ChatSDKFirebase;
import sdk.chat.core.session.ChatSDK;

public class MainApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Chat SDK
        try {
            ChatSDKFirebase.quickStart(this, "pre_1", Constants.MapsSettings.STATIC_MAP_API_KEY, true);
            ChatSDK.config().setLogoDrawableResourceID(R.drawable.kabar_icon_high);
//            ChatSDK.ui().setLoginActivity(CustomLoginActivity.class);
//            ChatSDK.ui().setMainActivity(CustomMainAppBarActivity.class);
//            Log.e("LOGIN ACTIVITY CLASS", ChatSDK.ui().getLoginIntent(this, null).toString());
            ChatSDK.shared().setInterfaceAdapter(new MyAppInterfaceAdapter(getApplicationContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
