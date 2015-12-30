package com.dekker.model;

import java.util.Comparator;

public class ThreadPriorityComparator implements Comparator<ThreadWrapper> {

    public int compare(ThreadWrapper threadWrapper1, ThreadWrapper threadWrapper2) {
        return threadWrapper1.getPriority() - threadWrapper2.getPriority();
    }
}
