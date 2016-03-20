package ru.kai.dekker.model;

import ru.kai.dekker.model.message.Message;

public interface ThreadObserver {

    void updateThreadInfo(Message message);

}
