package com.dekker.model;

import com.dekker.model.message.Message;

public interface ThreadObserver {
    void updateThreadInfo(Message message);
}
