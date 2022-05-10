package com.merahputih.kabar.custom_activities;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.merahputih.kabar.R;

import sdk.chat.core.session.ChatSDK;
import sdk.chat.ui.activities.SplashScreenActivity;

public class CustomSplashScreenActivity extends SplashScreenActivity {

    @Override
    protected int getLayout() {
        return R.layout.custom_activity_splash_screen;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.imageView.setImageResource(R.drawable.kabar_icon_1);
    }
}
