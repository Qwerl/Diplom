package ru.kai.dekker.model;

import ru.kai.AbstractThreadWrapper;

import java.util.*;

public class ThreadList<E extends AbstractThreadWrapper> {

    private final List<E> threads = new ArrayList<>();
    private final HashMap<Integer, E> threadsMap = new HashMap<>();

    public ThreadList() {
    }

    public void add(E threadWrapper) {
        threads.add(threadWrapper);
        threadsMap.put(AbstractThreadWrapper.getThreadsCount(), threadWrapper);
        //сортировка требуется только в случае с алгоритмом деккера
        if (threadWrapper instanceof DekkerThreadWrapper) {
            sort();
        }
    }

    public int size() {
        return threads.size();
    }

    public void setCommand(int threadId, Command command) {
        getByKey(threadId).setCommand(command);
    }

    private void sort() {
        Collections.sort((List<DekkerThreadWrapper>) threads, new ThreadPriorityComparator());
        Collections.reverse(threads);
    }

    public void startThread(int id) {
        AbstractThreadWrapper thread = getByKey(id);
        if (!thread.isThreadAlive()) {
            thread.startThread();
        }
    }

    public E getByKey(int id) {
        return threadsMap.get(id);
    }

    public E getById(int id) {
        return threads.get(id);
    }

    public void remove(int id) {
        E thread = getByKey(id);
        thread.stop();
        threads.remove(thread);
        threadsMap.remove(thread);
    }

    public void startAll() {
        for (E thread : threads) {
            if (!thread.isThreadAlive()) {
                thread.startThread();
            }
        }
    }

}
