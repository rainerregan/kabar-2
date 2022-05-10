package com.merahputih.kabar.custom_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.merahputih.kabar.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import sdk.chat.ui.extras.ScanQRCodeActivity;
import sdk.chat.ui.fragments.BaseFragment;

public class CustomScanQrFragment extends BaseFragment {

    @BindView(R.id.openScannerButton)
    protected MaterialButton openScannerButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(this.getLayout(), container, false);
        ButterKnife.bind(this, view);
        this.initViews();
        return view;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_custom_scan_qr;
    }

    @Override
    protected void initViews() {
        openScannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ScanQRCodeActivity.class));
            }
        });
    }

    @Override
    public void clearData() {

    }

    @Override
    public void reloadData() {

    }
}
