package com.dekker.model;

import com.dekker.view.swing.MyLoggerPanel;

import java.util.ArrayList;
import java.util.List;

public class ThreadsFactory { //singleton factory

    private static ThreadsFactory threadsFactory = null;

    public static ThreadsFactory getInstance() {
        if (threadsFactory == null)
            threadsFactory = new ThreadsFactory();
        return threadsFactory;
    }

    private List<ThreadStarter> threads = new ArrayList<ThreadStarter>();

    //закрытый конструктор
    private ThreadsFactory() {
    }

    /**
     * создаёт экземпляр класса ThreadStarter, кладет его в список и возвращает
     * @param priority приопитет потока
     * @return  возвращает созданный экземпляр класса ThreadStarter
     */
    public ThreadStarter newThreadStarter(int priority) {
        ThreadStarter threadStarter = new ThreadStarter(priority);
        threadStarter.setLogger(new MyLoggerPanel(threadStarter.getId()));
        //ToDo: тут можно добавить взаимодействие с файлом конфигов
        threads.add(threadStarter);
        return threadStarter;
    }

    /**
     * создаёт экземпляр класса ThreadStarter с контролируемым положением на экране, кладет его в список и возвращает
     * @param priority приопитет потока
     * @param x координаты логгера по x
     * @param y координаты логгера по y
     * @return возвращает созданный экземпляр класса ThreadStarter
     *//*
    public ThreadStarter newThreadStarter(int priority, int x, int y, boolean commandMode) {
        ThreadStarter threadStarter = new ThreadStarter(priority);
        threadStarter.setCommandMode(commandMode);
        MyLoggerPanel logger = new MyLoggerPanel(threadStarter.getId(), x, y);
        logger.setState(MyLoggerPanel.REST);
        threadStarter.setLogger(logger);
        //ToDo: тут можно добавить взаимодействие с файлом конфигов
        threads.add(threadStarter);
        return threadStarter;
    }*/

    /**
     * @return возвращает список созданных ThreadStarter'ов
     */
    public List<ThreadStarter> getThreadsList () {
        return threads;
    }

    public void clearThreadStarterList() {
        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).getLogger().closeFrame();
        }
        ThreadStarter.clearThreadsCount();
        threads = new ArrayList<ThreadStarter>();
    }
}