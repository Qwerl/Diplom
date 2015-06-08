package com.dekker.model;

import java.util.Comparator;

public class ThreadPriorityComparator implements Comparator<ThreadStarter> {

    public int compare(ThreadStarter threadStarter1, ThreadStarter threadStarter2) {
        return threadStarter1.getPriority() - threadStarter2.getPriority();
    }
}
