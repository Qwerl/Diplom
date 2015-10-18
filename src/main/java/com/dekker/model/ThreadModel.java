package com.dekker.model;

import com.dekker.model.resource.Resource;

public interface ThreadModel {
    void addThread(int priority, ThreadObserver observer);
    void setCommand(int threadId, Command command);
    void startThread(int threadId);

    Resource getResource();
    void setResource(Resource resource);

    void removeObserver();
    void addObserver();
}
