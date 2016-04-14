package ru.kai.dekker.model;

import ru.kai.AbstractThreadWrapper;
import ru.kai.SynchronizationPrimitives;
import ru.kai.dekker.model.resource.Resource;
import ru.kai.semaphore.model.SemaphoreThreadWrapper;

import java.util.concurrent.Semaphore;

public class ThreadModelImpl implements ThreadModel {

    private ThreadList<DekkerThreadWrapper> dekkerThreads = new ThreadList<>();
    private ThreadList<SemaphoreThreadWrapper> semaphoreThreads = new ThreadList<>();
    private ThreadsFactory factory = new ThreadsFactory(this);

    private Resource resource;
    private Mode mode;
    private SynchronizationPrimitives primitive;

    int maxConcurrentRequests = 2;
    public Semaphore semaphore = new Semaphore(maxConcurrentRequests);

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public DekkerThreadWrapper addThread(int priority) {
        DekkerThreadWrapper newThread = factory.newThreadStarter(priority);
        newThread.setResource(resource);
        dekkerThreads.add(newThread);
        return newThread;
    }

    public SemaphoreThreadWrapper addThread() {
        SemaphoreThreadWrapper newThread = factory.newThreadStarter();
        newThread.setResource(resource);
        semaphoreThreads.add(newThread);
        return newThread;
    }

    public void setCommand(int threadId, Command command) {
        if (primitive.equals(SynchronizationPrimitives.Dekker)) {
            dekkerThreads.setCommand(threadId, command);
        } else if (primitive.equals(SynchronizationPrimitives.Semaphore)) {
            semaphoreThreads.setCommand(threadId, command);
        }
    }

    public void startThread(int threadId) {
        dekkerThreads.startThread(threadId);
    }

    public void addObserver(int id, ThreadObserver observer) {
        AbstractThreadWrapper thread = null;
        if (primitive.equals(SynchronizationPrimitives.Dekker)) {
            thread = dekkerThreads.getByKey(id);
        } else if (primitive.equals(SynchronizationPrimitives.Semaphore)) {
            thread = semaphoreThreads.getByKey(id);
        }
        assert thread != null;
        thread.addObserver(observer);
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void setPrimitiveType(SynchronizationPrimitives primitive) {
        this.primitive = primitive;
    }

    public void startThreads() {
        if (primitive.equals(SynchronizationPrimitives.Dekker)) {
            dekkerThreads.startAll();
        } else if (primitive.equals(SynchronizationPrimitives.Semaphore)) {
            semaphoreThreads.startAll();
        }
        try {
            Thread.sleep(50);//небольшая пауза, чтоб точно все потоки успели стартовать
        } catch (Exception ignore) {/* NOP */}
    }

    public void removeThread(int id) {
        if (primitive.equals(SynchronizationPrimitives.Dekker)) {
            dekkerThreads.remove(id);
        } else if (primitive.equals(SynchronizationPrimitives.Semaphore)) {
            semaphoreThreads.remove(id);
        }
    }

    public ThreadList getDekkerThreads() {
        return dekkerThreads;
    }

    public SynchronizationPrimitives getPrimitive() {
        return primitive;
    }

    public void setPrimitive(SynchronizationPrimitives primitive) {
        this.primitive = primitive;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }
}
