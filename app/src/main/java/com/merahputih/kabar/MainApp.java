package com.merahputih.kabar;

import android.app.Activity;
import android.app.Application;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.merahputih.kabar.settings.Constants;

import io.reactivex.Completable;
import sdk.chat.app.firebase.ChatSDKFirebase;
import sdk.chat.core.dao.Thread;
import sdk.chat.core.interfaces.ChatOption;
import sdk.chat.core.session.ChatSDK;
import sdk.chat.ui.chat.options.BaseChatOption;
import sdk.chat.ui.chat.options.ChatOptionBuilder;

public class MainApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Chat SDK
        try {
            ChatSDKFirebase.quickStart(this, "pre_1", Constants.MapsSettings.STATIC_MAP_API_KEY, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
