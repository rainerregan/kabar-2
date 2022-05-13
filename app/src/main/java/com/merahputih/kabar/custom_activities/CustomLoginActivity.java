package com.merahputih.kabar.custom_activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.merahputih.kabar.R;

import sdk.chat.core.session.ChatSDK;
import sdk.chat.ui.activities.LoginActivity;
import sdk.guru.common.RX;

public class CustomLoginActivity extends LoginActivity {

    @Override
    protected int getLayout() {
        return R.layout.custom_activity_login;
    }

    @Override
    protected void showForgotPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Lupa password?");

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.lupa_password_dialog_layout, null);

        EditText input = dialogView.findViewById(R.id.lupaPasswordEmailEditText);

        builder.setView(dialogView);
        builder.setCancelable(false);
        builder.setPositiveButton("Kirim", (dialog, which) -> {
            this.showOrUpdateProgressDialog("Mengirim...");
            this.dm.add(this.requestNewPassword(input.getText().toString()).observeOn(RX.main()).subscribe(() -> {
                this.dismissProgressDialog();
                this.showToast("Email untuk reset password telah dikirim ke email anda.");
            }, (throwable) -> {
                this.showToast(throwable.getLocalizedMessage());
                this.dismissProgressDialog();
            }));
        });
        builder.setNegativeButton("Batal", (dialog, which) -> {
            dialog.cancel();
            this.dismissProgressDialog();
        });
        builder.show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected boolean checkFields() {
        this.usernameTextInputLayout.setError(null);
        this.passwordTextInputLayout.setError(null);
        if (this.usernameTextInput.getText().toString().isEmpty()) {
            this.usernameTextInput.requestFocus();
            this.usernameTextInputLayout.setError("Mohon isi email");
            return false;
        } else if (this.passwordTextInput.getText().toString().isEmpty()) {
            this.passwordTextInput.requestFocus();
            this.passwordTextInputLayout.setError("Mohon isi password");
            return false;
        } else {
            return true;
        }
    }

}
