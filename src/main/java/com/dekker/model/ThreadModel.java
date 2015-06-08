package com.dekker.model;

public interface ThreadModel {
    void addThread(int priority, ThreadObserver observer);
    void setCommand(int threadId, Command command);
    void startThread(int threadId);

    void removeObserver();
    void addObserver();
}
