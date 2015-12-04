package com.dekker.model;

import com.dekker.model.resource.Resource;

public class ThreadModelImpl implements ThreadModel {

    private ThreadList threadList = new ThreadList();
    private ThreadsFactory factory = new ThreadsFactory(this);

    private Resource resource;
    private Mode mode;

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public ThreadWrapper addThread(int priority) {
        ThreadWrapper newThread = factory.newThreadStarter(priority);
        newThread.setResource(resource);
        threadList.add(newThread);
        return newThread;
    }

    public void setCommand(int threadId, Command command) {
        threadList.setCommand(threadId, command);
    }

    public void startThread(int threadId) {
        threadList.startThread(threadId);
    }

    public void addObserver(int id, ThreadObserver observer) {
        ThreadWrapper thread = threadList.getByKey(id);
        thread.addObserver(observer);
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void startThreads() {
        for (int i = 0; i < threadList.size(); i++) {
            ThreadWrapper thread = threadList.getByKey(i);
            if (!thread.isThreadAlive()) {
                thread.startThread();
            }
        }
        try {
            Thread.sleep(50);//небольшая пауза, чтоб точно все потоки успели стартовать
        } catch (Exception ignore) {/* NOP */}

        System.out.println("model start");
    }

    public void removeThread(int id) {
        threadList.remove(id);
    }

    public ThreadList getThreadList() {
        return threadList;
    }
}
