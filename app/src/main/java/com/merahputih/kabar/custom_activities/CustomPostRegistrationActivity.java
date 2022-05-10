package com.merahputih.kabar.custom_activities;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.merahputih.kabar.R;

import com.bumptech.glide.RequestBuilder;

import java.io.File;

import butterknife.BindView;
import sdk.chat.core.dao.User;
import sdk.chat.core.events.NetworkEvent;
import sdk.chat.core.image.ImageUploadResult;
import sdk.chat.core.session.ChatSDK;
import sdk.chat.core.utils.Dimen;
import sdk.chat.core.utils.StringChecker;
import sdk.chat.ui.activities.BaseActivity;
import sdk.chat.ui.activities.PostRegistrationActivity;
import sdk.chat.ui.chat.MediaSelector;
import sdk.chat.ui.icons.Icons;
import sdk.chat.ui.module.UIModule;
import sdk.chat.ui.utils.ImagePickerUploader;
import sdk.chat.ui.utils.UserImageBuilder;
import sdk.guru.common.RX;

public class CustomPostRegistrationActivity extends PostRegistrationActivity {

    @BindView(R.id.nameTextInput)
    protected TextInputEditText nameEditText;

    @BindView(R.id.locationTextInput)
    protected TextInputEditText locationEditText;

    @BindView(R.id.nomorTeleponTextInput)
    protected TextInputEditText phoneNumberEditText;

    @BindView(R.id.emailTextInput)
    protected TextInputEditText emailEditText;

    @BindView(R.id.nameTextInputLayout)
    protected TextInputLayout nameTextInputLayout;

    public CustomPostRegistrationActivity(){

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.avatarImageView.setImageResource(R.drawable.placeholder_profile);
    }


    @Override
    protected int getLayout() {
        return R.layout.custom_activity_post_registration;
    }

    @Override
    protected void next() {

        this.showProgressDialog("Loading...");

        nameTextInputLayout.setError(null);
        User currentUser = ChatSDK.currentUser();

        String name = this.nameEditText.getText().toString();
        String location = this.locationEditText.getText().toString();
        String phoneNumber = this.phoneNumberEditText.getText().toString();
        String email = this.emailEditText.getText().toString();

        if (StringChecker.isNullOrEmpty(name)) {
//            this.showToast("Nama harus diisi");
            nameTextInputLayout.setError("Nama wajib diisi!");
        } else {
            currentUser.setName(name, false);
            currentUser.setLocation(location, false);
            currentUser.setPhoneNumber(phoneNumber, false);
            currentUser.setEmail(email, false);
            if (this.avatarImageURL != null) {
                currentUser.setAvatarURL(this.avatarImageURL, (String)null, false);
            }

            currentUser.update();
            View v = this.getCurrentFocus();
            if (v instanceof EditText) {
                InputMethodManager imm = (InputMethodManager)this.getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }

                ChatSDK.events().source().accept(NetworkEvent.userMetaUpdated(currentUser));
            }

            this.dm.add(ChatSDK.core().pushUser().observeOn(RX.main()).subscribe(() -> {
                ChatSDK.ui().startMainActivity(this);
            }));
        }
        this.dismissProgressDialog();
        this.doneFab.setEnabled(true);
    }

    public void setNextFocusDown(TextInputEditText editText, int id) {
        editText.setNextFocusDownId(id);
    }

    @Override
    protected void reloadData() {
        this.showProgressDialog("Mendapatkan data...");
        User currentUser = ChatSDK.currentUser();
        String name = currentUser.getName();
        String location = currentUser.getLocation();
        String phoneNumber = currentUser.getPhoneNumber();
        String email = currentUser.getEmail();

        int width = 150;
        int height = 150;
        ((RequestBuilder)((RequestBuilder) Glide.with(this).load(currentUser.getAvatarURL()).dontAnimate()).placeholder(UIModule.config().defaultProfilePlaceholder)).into(this.avatarImageView);
        UserImageBuilder.loadAvatar(currentUser, this.avatarImageView, width, height);

        this.nameEditText.setText(name);
        setNextFocusDown(nameEditText, locationEditText.getId());
        this.locationEditText.setText(location);
        setNextFocusDown(locationEditText, phoneNumberEditText.getId());
        this.phoneNumberEditText.setText(phoneNumber);
        setNextFocusDown(locationEditText, phoneNumberEditText.getId());
        this.emailEditText.setText(email);

        this.dismissProgressDialog();
    }

}
