package com.example.user.with_family.Interfaces;

import com.example.user.with_family.ui.ChatActivity.Chat_item;
import com.example.user.with_family.ui.f_chat.ChatRoom_item;

/**
 * Created by choi on 2017-12-05.
 */

public interface ChatRoom_Refresh_callback {
    public void refresh(Chat_item o, int position);
}
