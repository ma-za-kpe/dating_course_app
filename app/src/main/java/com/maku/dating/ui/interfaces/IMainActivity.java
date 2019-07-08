package com.maku.dating.ui.interfaces;

import com.maku.dating.ui.models.Message;
import com.maku.dating.ui.models.User;

public interface IMainActivity {

    void inflateViewProfileFragment(User user);
    void onMessageSelected(Message message);
    void onBackPressed();

}
