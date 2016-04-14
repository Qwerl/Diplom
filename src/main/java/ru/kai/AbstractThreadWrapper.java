package ru.kai;

import ru.kai.dekker.model.Command;
import ru.kai.dekker.model.Mode;
import ru.kai.dekker.model.ThreadModel;
import ru.kai.dekker.model.ThreadObserver;
import ru.kai.dekker.model.message.Message;
import ru.kai.dekker.model.resource.Resource;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractThreadWrapper implements Runnable {

    protected static int threadsCount = -1;

    private boolean isWorkDone = false;
    private boolean inCriticalZone;
    private boolean isWorkWithResource;

    protected int id;
    protected ThreadModel model;

    protected Resource resource;
    protected Mode mode;
    protected Command command;
    protected Message message;
    protected Thread thread = new Thread(this);

    private List<ThreadObserver> observers = new ArrayList<ThreadObserver>();

    public static int getThreadsCount() {
        return threadsCount;
    }

    public void run() {
        printStartMessage();
        while (!isWorkDone()) {
            waitForCommand(Command.START);
            enterToCriticalZone();
            waitForCommand(Command.REQUEST_RESOURCE);
            while (!checkEmploymentResource()) {
                waitForCommand(Command.REQUEST_RESOURCE);
            }
            setWorkWithResource(true);
            waitForCommand(Command.START_WORK);
            startWorkWithResource();
            doWork();
            waitForCommand(Command.END_WORK);
            endWorkWithResource();
            waitForCommand(Command.EXIT_FROM_CRITICAL_ZONE);
            exitFromCriticalZone();
            waitForCommandRunOrExit();
        }
    }

    public void stop() {
        setWorkDone(true);
    }

    protected abstract void printStartMessage();

    protected abstract void enterToCriticalZone();

    protected abstract boolean checkEmploymentResource();

    protected abstract void startWorkWithResource();

    protected abstract void doWork();

    protected abstract void endWorkWithResource();

    protected abstract void exitFromCriticalZone();

    protected void waitForCommandRunOrExit() {
        if (mode.equals(Mode.STEP_BY_STEP)) {
            updateMessage(message.getType(), "работа выполнена, что делать дальше?\n" +
                    "    введите команду run для повторного выполнения работы\n" +
                    "    или команду exit для завершения работы потока");

            setWorkDone(false);
            while (!command.equals(Command.RUN)) {
                if (command.equals(Command.EXIT)) {
                    setWorkDone(true);
                    command = Command.NULL;
                    updateMessage(Message.NEUTRAL, "END");
                    model.removeThread(id);
                    return;
                }
                delay(100);
            }
            updateMessage(message.getType(), "Поток запущен повторно");
            command = Command.NULL;
        } else if (mode.equals(Mode.REAL_TIME)) {
            delay(500);
        }
    }

    private void waitForCommand(Command expectedCommand) {
        if (mode.equals(Mode.STEP_BY_STEP)) {
            while (command != expectedCommand) {
                delay(50);
            }
            command = Command.NULL;
        } else if (mode.equals(Mode.REAL_TIME)) {
            delay(500);
        }
    }

    private void delay(long delayMs) {
        try {
            Thread.sleep(delayMs);
        } catch (InterruptedException e) {
            System.out.println("упали во время задержки");
        }
    }

    protected void updateMessage(Color type, String messageValue) {
        message = new Message(type, messageValue, "Поток №" + String.valueOf(id));
        notifyObservers();
    }

    public void addObserver(ThreadObserver observer) {
        observers.add(observer);
    }

    public void startThread() {
        thread = new Thread(this);
        thread.start();
    }

    public boolean isThreadAlive() {
        return thread.isAlive();
    }

    private void notifyObservers() {
        for (ThreadObserver observer : observers) {
            observer.updateThreadInfo(message);
        }
    }

    public boolean isWorkDone() {
        return isWorkDone;
    }

    public void setWorkDone(boolean workDone) {
        isWorkDone = workDone;
    }

    public boolean isInCriticalZone() {
        return inCriticalZone;
    }

    public void setInCriticalZone(boolean inCriticalZone) {
        this.inCriticalZone = inCriticalZone;
    }

    public boolean isWorkWithResource() {
        return isWorkWithResource;
    }

    public void setWorkWithResource(boolean workWithResource) {
        isWorkWithResource = workWithResource;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public void setCommandMode(Mode mode) {
        this.mode = mode;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}