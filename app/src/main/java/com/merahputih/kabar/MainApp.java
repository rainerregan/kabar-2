package com.merahputih.kabar;

import android.app.Application;
import android.util.Base64;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.PhoneAuthProvider;
import com.merahputih.kabar.custom_activities.CustomPostRegistrationActivity;
import com.merahputih.kabar.custom_fragments.CustomShowQrFragment;
import com.merahputih.kabar.interface_adapter.MyAppInterfaceAdapter;
import com.merahputih.kabar.settings.Constants;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.concurrent.TimeUnit;

import sdk.chat.app.firebase.ChatSDKFirebase;
import sdk.chat.core.Tab;
import sdk.chat.core.interfaces.ChatOption;
import sdk.chat.core.session.ChatSDK;
import sdk.chat.firebase.adapter.module.FirebaseModule;
import sdk.chat.firebase.push.FirebasePushModule;
import sdk.chat.firebase.ui.FirebaseUIModule;
import sdk.chat.firebase.upload.FirebaseUploadModule;
import sdk.chat.message.location.LocationChatOption;
import sdk.chat.message.location.LocationMessageModule;
import sdk.chat.ui.activities.ChatActivityWrapper;
import sdk.chat.ui.activities.MainActivity;
import sdk.chat.ui.activities.MainAppBarActivity;
import sdk.chat.ui.activities.ProfileActivity;
import sdk.chat.ui.extras.MainDrawerActivity;
import sdk.chat.ui.extras.ScanQRCodeActivity;
import sdk.chat.ui.extras.ShowQRCodeActivity;
import sdk.chat.ui.fragments.ProfileFragment;
import sdk.chat.ui.module.UIModule;

public class MainApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Chat SDK
        try {

            byte[] data = Base64.decode(Constants.MapsSettings.STATIC_MAP_KEY, Base64.DEFAULT);
            String KEY = new String(data, "UTF-8");

            ChatSDK.builder()
                    .setGoogleMaps(KEY)
                    .setPublicChatRoomLifetimeMinutes(TimeUnit.HOURS.toMinutes(24))
                    .build()

                    // Add the Firebase network adapter module
                    .addModule(
                            FirebaseModule.builder()
                                    .setFirebaseRootPath("pre_1")
                                    .setDevelopmentModeEnabled(true)
                                    .build()
                    )

                    // Add the UI module
                    .addModule(UIModule.builder()
                            .setPublicRoomCreationEnabled(false)
                            .setPublicRoomsEnabled(false)
                            .setShowNamesInGroupChatView(true)
                            .build()
                    )

                    // Add modules to handle file uploads, push notifications
                    .addModule(FirebaseUploadModule.shared())
                    .addModule(FirebasePushModule.shared())
                    .addModule(LocationMessageModule.shared())

                    // Enable Firebase UI with phone and email auth
                    .addModule(FirebaseUIModule.builder()
                            .setProviders(EmailAuthProvider.PROVIDER_ID, PhoneAuthProvider.PROVIDER_ID)
                            .build()
                    )

                    // Activate
                    .build()
                    .activate(this);

            ChatSDK.config().setLogoDrawableResourceID(R.drawable.kabar_icon_2);
            ChatSDK.config().setShowLocalNotifications(true);


            // Set Interface Adapter untuk setting custom layout.
            // Custom layout dapat berupa custom class yang inherit class aslinya.
            // Override pada adapter akan mengganti layout default.
            ChatSDK.shared().setInterfaceAdapter(new MyAppInterfaceAdapter(getApplicationContext()));
            ChatSDK.ui().setPostRegistrationActivity(CustomPostRegistrationActivity.class);

//            Log.i("ProfileActivity", ChatSDK.ui().getProfileActivity().toString());

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
//        ChatSDK.ui().removeTab(1);
        ChatSDK.ui().tabs().get(0).title = "Percakapan";
        ChatSDK.ui().tabs().get(1).title = "Kontak";


        ChatSDK.ui().setTab("Tampilkan QR Code Anda", getDrawable(R.drawable.ic_outline_local_phone_24), new CustomShowQrFragment(), 2);
    }
}
