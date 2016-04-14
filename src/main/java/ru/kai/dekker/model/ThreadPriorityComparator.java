package ru.kai.dekker.model;

import java.util.Comparator;

public class ThreadPriorityComparator implements Comparator<DekkerThreadWrapper> {

    public int compare(DekkerThreadWrapper threadWrapper1, DekkerThreadWrapper threadWrapper2) {
        return threadWrapper1.getPriority() - threadWrapper2.getPriority();
    }

}
