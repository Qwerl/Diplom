package com.dekker.model;

import com.dekker.model.message.Message;
import com.dekker.model.resource.Resource;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ThreadWrapper implements Runnable {

    private static int threadsCount = -1;

    public static void clearThreadsCount() {
        threadsCount = 0;
    }

    private final int id;
    private final int priority;

    private Resource resource;
    private Message message;
    private Command command;
    private boolean inCriticalZone;
    private boolean isWorkWithResource;
    private boolean isWorkDone;

    ThreadModel model;

    List<ThreadObserver> observers = new ArrayList<ThreadObserver>();

    private Thread thread = new Thread(this);
    private boolean isWithoutCommands = true;

    public ThreadWrapper(ThreadModel model, int priority) {
        this.model = model;
        this.priority = priority;
        synchronized (this) {
            threadsCount++;
        }
        id = threadsCount;
        System.out.println("thread " + id + "created");
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    /**
     * работает ли в данный момент времени поток с ресурсом
     */
    public boolean isWorkWithResource() {
        return isWorkWithResource;
    }

    /**
     * выставляет флаг работы с ресурсом
     */
    public void startWorkWithResource() {
        isWorkWithResource = true;
        updateMessage(Message.WORK_WITH_RESOURCE, "начинаю работать с ресурсом");
    }

    /**
     * снимает флаг работы с ресурсом
     */
    public void endWorkWithResource() {
        isWorkWithResource = false;
        updateMessage(Message.WORK_WITH_RESOURCE, "освободил ресурс");
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
        inCriticalZone = true;
        updateMessage(Message.CRITICAL_ZONE, "поток №" + id + " хочет войти в критическую зону");
    }

    /**
     * освобождает ресурс, снимает флаг
     */
    public void exitFromCriticalZone() {
        inCriticalZone = false;
        updateMessage(Message.REST, "поток №" + id + " вышел из критической зоны");
    }

    public boolean isThreadAlive() {
        return thread.isAlive();
    }

    /**
     * стартует поток
     */
    public void startThread() {
        thread = new Thread(this);
        thread.start();
    }

    /**
     * проверяет использует ли кто-то ресурс
     *
     * @return свободность ресурса
     */
    private boolean checkEmploymentResource() {
        ThreadList list = model.getThreadList();
        for (int i = 0; i < list.size(); i++) {
            ThreadWrapper threadWrapper = list.getById(i);
            if (threadWrapper.getId() != this.id) {
                if (threadWrapper.isWorkWithResource()) {
                    updateMessage(message.getType(), "поток № " + threadWrapper.getId() + " уже работает с ресурсом");
                    return false;
                } else if (threadWrapper.InCriticalZone()) {
                    updateMessage(message.getType(), "поток № " + threadWrapper.getId() + " тоже находиться в критическом участке");
                    if (threadWrapper.getPriority() > this.getPriority()) {
                        updateMessage(message.getType(), "но его приоритет выше");
                        return false;
                    } else if (threadWrapper.getId() < this.id && threadWrapper.getPriority() >= this.getPriority()) {
                        updateMessage(message.getType(), "но он раньше подал заявку");
                        return false;
                    }
                }
            }
        }
        updateMessage(message.getType(), "получил ресурс");
        return true;
    }

    private void doWork() {
        try {
            resource.work(id);
        } catch (Exception e) {
            System.out.println("Ресурс выдал исключение во время работы.");
            e.printStackTrace();
        }
    }

    private void delay(long delayMs) {
        try {
            Thread.sleep(delayMs);
        } catch (InterruptedException e) {
            System.out.println("упали во время задержки");
        }
    }

    private void waitForCommand() {
        //todo в отдельный класс
        if (isWithoutCommands) {
            delay(2000 * ThreadWrapper.threadsCount);
        } else {
            updateMessage(message.getType(), "работа выполнена, что делать дальше?\n" +
                    "    введите команду run для повторного выполнения работы\n" +
                    "    или команду exit для завершения работы потока");
            while (command.equals(Command.RUN)) {
                if (command.equals(Command.EXIT)) {
                    isWorkDone = true;
                    break;
                }
                delay(100);
            }
        }
        updateMessage(message.getType(), "Поток запущен повторно");
        command = Command.NULL;
    }

    /**
     * Организует ожидание до тех пор,
     * пока поле command не станет эквивалентно поступившей комманде
     *
     * @param expectedCommand ожидаемая команда
     */
    public void waitForCommand(Command expectedCommand) {
        //todo в отдельный класс
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
        updateMessage(
                Message.NEUTRAL,
                "Поток№" + id + " стартовал " + "№потока = " + thread.getId() + ", приоритет = " + this.getPriority()
        );
        while (!isWorkDone) {
            waitForCommand(Command.START);
            enterToCriticalZone();
            waitForCommand(Command.REQUEST_RESOURCE);
            while (!checkEmploymentResource()) {
                waitForCommand(Command.REQUEST_RESOURCE);
            }
            waitForCommand(Command.START_WORK);
            startWorkWithResource();    //начать работу с ресурсом
            doWork();                   //работаем с ресурсом
            waitForCommand(Command.END_WORK);
            endWorkWithResource();      //закончить работу с ресурсом
            waitForCommand(Command.EXIT_FROM_CRITICAL_ZONE);
            exitFromCriticalZone();     //освобождаем ресурс, снимаем флаг
            waitForCommand();           //выполнить работу ещё раз или закончить
        }
        updateMessage(Message.NEUTRAL, "END");
    }

    //todo в отдельный класс
    public void setCommandMode(boolean commandMode) {
        this.isWithoutCommands = commandMode;
    }

    public void addObserver(ThreadObserver observer) {
        observers.add(observer);
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    private void updateMessage(Color type, String messageValue) {
        message = new Message(type, messageValue, "Поток №" + String.valueOf(id));
        notifyObservers();
    }

    private void notifyObservers() {
        for (ThreadObserver observer : observers) {
            observer.updateThreadInfo(message);
        }
    }
}