package com.dekker.model;

import java.util.*;

public class ThreadList {
    //ToDo: choose the best
    //final Set threads = new TreeSet<ThreadStarter>(new ThreadPriorityComparator());
    //final ArrayDeque<ThreadStarter> threadStarterArrayDeque = new ArrayDeque<ThreadStarter>();
    //final PriorityQueue<ThreadStarter> priorityQueue = new PriorityQueue<ThreadStarter>(new ThreadPriorityComparator());
    private final List<ThreadStarter> threads = new ArrayList<ThreadStarter>();
    private final HashMap<Integer,ThreadStarter> threadsMap = new HashMap<Integer,ThreadStarter>();

    public ThreadList() {}

    public void add(ThreadStarter threadStarter){
        threads.add(threadStarter);
        threadsMap.put(threadsMap.size(), threadStarter);
        sort();
    }

    public int size() {
        return threads.size();
    }

    public void setCommand(int threadId, Command command) {
        threadsMap.get(threadId).setCommand(command);
    }

    private void sort(){
        Collections.sort(threads, new ThreadPriorityComparator());
    }

    public void startThread(int threadId) {

    }

    public void get(int id){

    }
}
