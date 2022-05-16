package com.merahputih.kabar.custom_activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.merahputih.kabar.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import sdk.chat.core.session.ChatSDK;
import sdk.chat.core.types.AccountDetails;
import sdk.chat.ui.R2;
import sdk.chat.ui.activities.LoginActivity;
import sdk.guru.common.RX;

public class CustomLoginActivity extends LoginActivity {

    @BindView(R.id.loginButton)
    protected MaterialButton loginButton;

    @BindView(R.id.registerButton)
    protected MaterialButton registerButton;

    @BindView(R.id.resetPasswordButton)
    protected MaterialButton resetPasswordButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ButterKnife.bind(this);
    }

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

    protected void showRegisterDialog(){
        AlertDialog.Builder builderRegister = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.register_dialog_layout, null);

        TextInputEditText username = dialogLayout.findViewById(R.id.daftarEmailEditText);
        TextInputEditText password = dialogLayout.findViewById(R.id.daftarPasswordEditText);
        TextInputLayout usernameLayout = dialogLayout.findViewById(R.id.daftarEmailLayout);
        TextInputLayout passwordLayout = dialogLayout.findViewById(R.id.daftarPasswordLayout);

        builderRegister.setView(dialogLayout);
        builderRegister.setCancelable(false);
        builderRegister.setPositiveButton("Daftar", (dialog, which) -> {

        });
        builderRegister.setNegativeButton("Batal", (dialog, which) -> {
            dialog.cancel();
            this.dismissProgressDialog();
        });

        final AlertDialog dialog = builderRegister.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            this.showOrUpdateProgressDialog("Melakukan Pendaftaran...");
            this.register(username, password, usernameLayout, passwordLayout, dialog);
        });

        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getColor(sdk.chat.ui.R.color.red));
    }

    public void register(TextInputEditText usernameTextInputEditText, TextInputEditText passwordTextInputEditText, TextInputLayout usernameLayout, TextInputLayout passwordLayout, AlertDialog dialog) {
        if (!this.checkFieldsRegister(usernameTextInputEditText, passwordTextInputEditText, usernameLayout, passwordLayout)) {
            this.dismissProgressDialog();
        } else {
            dialog.dismiss();
            AccountDetails details = new AccountDetails();
            details.type = AccountDetails.Type.Register;
            details.username = usernameTextInputEditText.getText().toString();
            details.password = passwordTextInputEditText.getText().toString();
            this.authenticateWithDetails(details);
        }
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

    protected boolean checkFieldsRegister(TextInputEditText usernameTextInputEditText, TextInputEditText passwordTextInputEditText, TextInputLayout usernameLayout, TextInputLayout passwordLayout){
        usernameLayout.setError(null);
        passwordLayout.setError(null);
        if (usernameTextInputEditText.getText().toString().isEmpty()) {
            usernameTextInputEditText.requestFocus();
            usernameLayout.setError("Mohon isi email");
            return false;
        } else if (passwordTextInputEditText.getText().toString().isEmpty()) {
            passwordTextInputEditText.requestFocus();
            passwordLayout.setError("Mohon isi password");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        v.setEnabled(false);
        int i = v.getId();
        this.showProgressDialog("Mengautentikasi");
        this.getProgressDialog().setOnDismissListener((dialog) -> {
            v.setEnabled(true);
            this.dm.dispose();
            ChatSDK.auth().cancel();
        });
        if (i == loginButton.getId()) {
            this.passwordLogin();
        } else if (i == registerButton.getId()) {
            this.showRegisterDialog();
        } else if (i == resetPasswordButton.getId()) {
            this.showForgotPasswordDialog();
        }
    }
}
