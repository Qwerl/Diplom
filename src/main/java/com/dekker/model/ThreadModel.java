package com.dekker.model;

import com.dekker.model.resource.Resource;

public interface ThreadModel {
    ThreadWrapper addThread(int priority);
    void setCommand(int threadId, Command command);
    void startThread(int threadId);

    Resource getResource();
    void setResource(Resource resource);

    void addObserver(int id, ThreadObserver observer);

    Mode getMode();
    void setMode(Mode mode);

    void startThreads();

    void removeThread(int id);

    ThreadList getThreadList();
}
