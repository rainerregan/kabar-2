package com.merahputih.kabar;

import android.app.Activity;
import android.app.Application;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import com.merahputih.kabar.custom_activities.CustomLoginActivity;
import com.merahputih.kabar.custom_activities.CustomMainAppBarActivity;
import com.merahputih.kabar.custom_activities.CustomPostRegistrationActivity;
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
            ChatSDK.config().setLogoDrawableResourceID(R.drawable.kabar_icon_2);

            // Set Interface Adapter untuk setting custom layout.
            // Custom layout dapat berupa custom class yang inherit class aslinya.
            // Override pada adapter akan mengganti layout default.
            ChatSDK.shared().setInterfaceAdapter(new MyAppInterfaceAdapter(getApplicationContext()));

            ChatSDK.ui().setPostRegistrationActivity(CustomPostRegistrationActivity.class);

            ChatSDK.ui().removeTab(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
