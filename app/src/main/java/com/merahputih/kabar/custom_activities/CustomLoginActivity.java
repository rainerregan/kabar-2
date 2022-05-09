package com.merahputih.kabar.custom_activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.merahputih.kabar.R;

import sdk.chat.ui.activities.LoginActivity;

public class CustomLoginActivity extends LoginActivity {

    @Override
    protected int getLayout() {
        return R.layout.custom_activity_login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
