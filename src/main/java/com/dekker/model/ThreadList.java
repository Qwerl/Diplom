package com.dekker.model;

import java.util.*;

public class ThreadList {
    //ToDo: choose the best
    //final Set threads = new TreeSet<ThreadStarter>(new ThreadPriorityComparator());
    //final ArrayDeque<ThreadStarter> threadStarterArrayDeque = new ArrayDeque<ThreadStarter>();
    //final PriorityQueue<ThreadStarter> priorityQueue = new PriorityQueue<ThreadStarter>(new ThreadPriorityComparator());
    private final List<ThreadWrapper> threads = new ArrayList<>();
    private final HashMap<Integer, ThreadWrapper> threadsMap = new HashMap<Integer, ThreadWrapper>();

    public ThreadList() {
    }

    public void add(ThreadWrapper threadWrapper) {
        threads.add(threadWrapper);
        threadsMap.put(ThreadWrapper.getThreadsCount(), threadWrapper);
        sort();
    }

    public int size() {
        return threads.size();
    }

    public void setCommand(int threadId, Command command) {
        getByKey(threadId).setCommand(command);
    }

    private void sort() {
        Collections.sort(threads, new ThreadPriorityComparator());
    }

    public void startThread(int id) {
        ThreadWrapper thread = getByKey(id);
        if (!thread.isThreadAlive()) {
            thread.startThread();
        }
    }

    public ThreadWrapper getByKey(int id) {
        return threadsMap.get(id);
    }

    public ThreadWrapper getById(int id) {
        return threads.get(id);
    }

    public void remove(int id) {
        ThreadWrapper thread = getByKey(id);
        thread.stop();
        threads.remove(thread);
        threadsMap.remove(thread);
    }
}
