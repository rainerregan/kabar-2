package com.merahputih.kabar.custom_fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.merahputih.kabar.R;
import com.squareup.picasso.Picasso;

import net.glxn.qrgen.android.QRCode;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import sdk.chat.core.dao.User;
import sdk.chat.core.session.ChatSDK;
import sdk.chat.ui.fragments.BaseFragment;

public class CustomShowQrFragment extends BaseFragment {

    @BindView(R.id.imageView)
    protected ImageView imageView;

    @BindView(R.id.profileQRImageView)
    protected CircleImageView profileQRImageView;

    @BindView(R.id.profileQRName)
    protected TextView profileQRName;

    @BindView(R.id.profileQRStatus)
    protected TextView profileQRStatus;

    protected String userEntityID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(this.getLayout(), container, false);
        ButterKnife.bind(this, view);
        this.initViews();
        return view;
    }

    public CustomShowQrFragment() {
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_custom_show_qr;
    }

    @Override
    protected void initViews() {
        try {
            User user = ChatSDK.currentUser();

            Bitmap myBitmap = QRCode.from(user.getEntityID()).bitmap();
            this.imageView.setImageBitmap(myBitmap);

            Picasso.get().load(user.getAvatarURL()).into(this.profileQRImageView);
            this.profileQRName.setText(user.getName());
            this.profileQRStatus.setText(user.getStatus());
        } catch (Exception var2) {
            var2.printStackTrace();
        }
    }

    @Override
    public void clearData() {

    }

    @Override
    public void reloadData() {

    }
}