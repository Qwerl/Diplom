package com.dekker.model;

import com.dekker.model.resource.Resource;
import com.dekker.view.swing.MyLoggerPanel;

import java.util.ArrayList;
import java.util.List;

public class ThreadStarter implements Runnable{

    private static int threadsCount = 0;//-1;//старт с -1 так как 0 будет выполнять роль контроллера


    public static void clearThreadsCount() {
        threadsCount = 0;
    }

    private Resource resource;
    private Command command;
    private int id;
    private final int priority;
    private boolean inCriticalZone;
    private boolean workWithResource;
    private boolean workDone;

    private MyLoggerPanel logger = null;
    List<ThreadObserver> observers = new ArrayList<ThreadObserver>();//todo


    private Thread thread = new Thread(this);
    private boolean isWithoutCommands = true;

    //конструктор
    public ThreadStarter (int priority) {
        this.priority = priority;
        synchronized (this){threadsCount++;}
        id = threadsCount;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public MyLoggerPanel getLogger() {
        return logger;
    }

    public void setLogger(MyLoggerPanel logger) {
        this.logger = logger;
        logger.setThreadStarter(this);
    }

    /**
     * работает ли в данный момент времени поток с ресурсом
     */
    public boolean isWorkWithResource() {
        return workWithResource;
    }

    /**
     * выставляет флаг работы с ресурсом
     */
    public void startWorkWithResource() {
        workWithResource = true;
        logger.setState(MyLoggerPanel.WORK_WITH_RESOURCE);
        logger.addLog("начинаю работать с ресурсом");
    }

    /**
     * снимает флаг работы с ресурсом
     */
    public void endWorkWithResource() {

        logger.setState(MyLoggerPanel.WORK_WITH_RESOURCE);
        logger.addLog("освободил ресурс");
        workWithResource = false;
    }

    public int getId() {
        return id;
    }

    public int getPriority() {
        return priority;
    }

    /**
     * находиться ли ресурс в критической зоне
     */
    public boolean InCriticalZone() {
        return inCriticalZone;
    }

    /**
     * желает получить доступ к ресурсу , вешает флаг
     */
    public void enterToCriticalZone() {
        logger.setState(MyLoggerPanel.CRITICAL_ZONE);
        logger.addLog("поток №" + id + " хочет войти в критическую зону");
        inCriticalZone = true;
    }

    /**
     * освобождает ресурс, снимает флаг
     */
    public void exitFromCriticalZone() {
        logger.addLog("поток №" + id + " вышел из критической зоны");
        logger.updateTitle(id, "хорошо поработал");
        logger.setState(MyLoggerPanel.REST);
        inCriticalZone = false;
    }

    public boolean isThreadAlive(){
        return thread.isAlive();
    }
    /**
     * стартует поток
     */
    public void startThread () {
        thread = new Thread(this);
        thread.start();
    }

    /**
     * проверяет использует ли кто-то ресурс
     *
     * @return свободность ресурса
     */
    private boolean checkEmploymentResource() { //todo переписать
        List <ThreadStarter> list = ThreadsFactory.getInstance().getThreadsList(); //получить список всех потоков
        for (int i = 0; i < list.size(); i++) {
            ThreadStarter threadStarter = list.get(i);
            if (threadStarter.getId() != this.id) //если сравнивается сам с собой
                if (threadStarter.isWorkWithResource()) { //если кто-то уже работает с ресурсом, то
                    logger.addLog("поток № " + threadStarter.getId() + " уже работает с ресурсом");
                    return false; //никто другой, до освобождения, с ним работать не может
                }
                else if (threadStarter.InCriticalZone()) { //если кто-то другой изъявил желание воспользоваться ресурсом , то
                    logger.addLog("поток № " + threadStarter.getId() + " тоже находиться в критическом участке");
                    if (threadStarter.getPriority() > this.getPriority()) {//сравнить приоритеты, если приоритет другого потока выше
                        logger.addLog("но его приоритет выше");
                        return false; //никто другой, до освобождения, с ним работать не может
                    }
                    else if (threadStarter.getId() < this.id && threadStarter.getPriority() >= this.getPriority()) {//если поток с таким-же приоритетом раньше изъявил желание //TODO багуля
                        logger.addLog("но он раньше подал заявку");
                        return false;
                    }
                }
        }
        //прошли по всем потокам и не встретили ниодного работающего с ресурсом
        //и желающего работать с ресурсом выше приоритетом
        logger.addLog("получил ресурс");
        logger.updateTitle(id, "ресурс получен");
        return true; //ресурс свободен
    }

    private void doSomething() {//получили ресурс
        logger.updateTitle(id, "работаю с ресурсом");
        delay(1000);
    }

    private void delay(long delayMs) {
        try {
            Thread.sleep(delayMs);
        } catch (InterruptedException e) {
            System.out.println("упали во время задержки");
        }
    }

    private void waitForCommand() {
        if (isWithoutCommands) {
            delay(2000 * ThreadStarter.threadsCount);
        } else {
            logger.addLog("работа выполнена, что делать дальше?\n" +
                    "    введите команду run для повторного выполнения работы\n" +
                    "    или команду exit для завершения работы потока");
            while (command.equals(Command.RUN)) {
                if(command.equals(Command.EXIT)) {
                    workDone = true;
                    break;
                }
                delay(100);
            }
        }
        logger.addLog("Поток запущен повторно");
        command = Command.NULL;
    }

    /**
     * Организует ожидание до тех пор,
     * пока поле command не станет эквивалентно поступившей комманде
     * @param expectedCommand ожидаемая команда
     */
    public void waitForCommand (Command expectedCommand) {
        if (isWithoutCommands) {
            command = expectedCommand;
            delay(500);
        }

        while (command != expectedCommand) {
            delay(50);
        }

        command = Command.NULL;
    }

    public void run() {
        logger.addLog("Поток№" + id + " стартовал " + "№потока = " + thread.getId() + ", приоритет = " + this.getPriority());
        //фабричный метод
        while (!workDone) {
            waitForCommand(Command.START);
            enterToCriticalZone();
            waitForCommand(Command.REQUEST_RESOURCE);
            while (!checkEmploymentResource()) {
                waitForCommand(Command.REQUEST_RESOURCE);
                logger.updateTitle(id, "ресурс занят");
            }
            waitForCommand(Command.START_WORK);
            startWorkWithResource();    //начать работу с ресурсом
            doSomething();              //получили контроль над ресурсом, делаем с ним что-то
            waitForCommand(Command.END_WORK);
            endWorkWithResource();      //закончить работу с ресурсом
            waitForCommand(Command.EXIT_FROM_CRITICAL_ZONE);
            exitFromCriticalZone();     //освобождаем ресурс , снимаем флаг
            waitForCommand();           //выполнить работу ещё раз или закончить
        }
        logger.addLog("END");
    }

    public void setCommandMode(boolean commandMode) {
        this.isWithoutCommands = commandMode;
    }

    public void addObserver(ThreadObserver observer) {
        observers.add(observer);
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}