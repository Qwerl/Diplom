package com.dekker.model;

import com.dekker.model.resource.Resource;

public class ThreadModelImpl implements ThreadModel{

    private ThreadList threadList = new ThreadList();

    private ThreadsFactory factory = ThreadsFactory.getInstance();

    private Resource resource;

    public Resource getResource() {
        return resource;
    }
    public void setResource(Resource resource){
        this.resource = resource;
    }

    public void addThread(int priority, ThreadObserver observer) {
        ThreadStarter newThread = factory.newThreadStarter(priority);
        newThread.addObserver(observer);
        newThread.setResource(resource);
        threadList.add(newThread);
    }

    public void setCommand(int threadId, Command command) {
        threadList.setCommand(threadId, command);
    }

    public void startThread(int threadId) {
        threadList.startThread(threadId);
    }

    public void addObserver() {
    //todo
    }

    public void removeObserver() {
    //todo
    }

}
