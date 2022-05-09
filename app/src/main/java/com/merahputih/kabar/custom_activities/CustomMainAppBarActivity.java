package com.merahputih.kabar.custom_activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.merahputih.kabar.R;

import sdk.chat.ui.activities.MainAppBarActivity;
import sdk.chat.ui.activities.MainAppBarActivity_ViewBinding;
import sdk.chat.ui.extras.MainDrawerActivity;

public class CustomMainAppBarActivity extends MainDrawerActivity {
//    @Override
//    protected int getLayout() {
//        return R.layout.activity_main;
//    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
    }
}
