package com.merahputih.kabar.chat_options;

import android.graphics.drawable.Drawable;

import sdk.chat.ui.chat.options.BaseChatOption;
import sdk.chat.ui.chat.options.MediaType;

public class CustomFileChatOption extends BaseChatOption {
    public CustomFileChatOption(String title, Drawable drawable, Action action) {
        super(title, drawable, action);
    }

    public CustomFileChatOption(String title, Action action, MediaType type) {
        super(title, action, type);
    }
}
