package com.merahputih.kabar;

import android.app.Activity;
import android.app.Application;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.merahputih.kabar.custom_activities.CustomLoginActivity;
import com.merahputih.kabar.custom_activities.CustomMainAppBarActivity;
import com.merahputih.kabar.custom_activities.CustomPostRegistrationActivity;
import com.merahputih.kabar.custom_activities.CustomSplashScreenActivity;
import com.merahputih.kabar.interface_adapter.MyAppInterfaceAdapter;
import com.merahputih.kabar.settings.Constants;
import com.mikepenz.iconics.IconicsDrawable;

import sdk.chat.app.firebase.ChatSDKFirebase;
import sdk.chat.core.interfaces.ChatOption;
import sdk.chat.core.session.ChatSDK;
import sdk.chat.message.location.LocationChatOption;
import sdk.chat.ui.chat.options.ChatOptionBuilder;

public class MainApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Chat SDK
        try {

            byte[] data = Base64.decode(Constants.MapsSettings.STATIC_MAP_KEY, Base64.DEFAULT);
            String KEY = new String(data, "UTF-8");

            ChatSDKFirebase.quickStart(this, "pre_1", KEY, true);
            ChatSDK.config().setLogoDrawableResourceID(R.drawable.kabar_icon_2);

            // Set Interface Adapter untuk setting custom layout.
            // Custom layout dapat berupa custom class yang inherit class aslinya.
            // Override pada adapter akan mengganti layout default.
            ChatSDK.shared().setInterfaceAdapter(new MyAppInterfaceAdapter(getApplicationContext()));
            ChatSDK.ui().setPostRegistrationActivity(CustomPostRegistrationActivity.class);

            setChatOptions();
            setTabsProperties();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setChatOptions() {
        ChatOption option = new LocationChatOption("📍 Location");
        ChatSDK.ui().addChatOption(option);
    }

    private void setTabsProperties() {
        ChatSDK.ui().removeTab(1);
        ChatSDK.ui().tabs().get(0).title = "Percakapan";
        ChatSDK.ui().tabs().get(1).title = "Kontak";
    }
}
