package ru.kai.dekker.model;

import ru.kai.SynchronizationPrimitives;
import ru.kai.dekker.model.resource.Resource;
import ru.kai.semaphore.model.SemaphoreThreadWrapper;

import java.util.concurrent.Semaphore;

public interface ThreadModel {

    DekkerThreadWrapper addThread(int priority);

    SemaphoreThreadWrapper addThread();

    void setCommand(int threadId, Command command);

    void startThread(int threadId);

    Resource getResource();

    void setResource(Resource resource);

    void addObserver(int id, ThreadObserver observer);

    Mode getMode();

    void setMode(Mode mode);

    void startThreads();

    void removeThread(int id);

    ThreadList getDekkerThreads();

    void setPrimitiveType(SynchronizationPrimitives semaphore);

    SynchronizationPrimitives getPrimitive();

    Semaphore getSemaphore();

}
