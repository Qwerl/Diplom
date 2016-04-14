package ru.kai.semaphore.model;

import ru.kai.AbstractThreadWrapper;
import ru.kai.dekker.model.message.Message;

import java.util.concurrent.Semaphore;

public class SemaphoreThreadWrapper extends AbstractThreadWrapper implements Runnable {

    public Semaphore semaphore;

    public SemaphoreThreadWrapper() {
        synchronized (this) {
            threadsCount++;
        }
        id = threadsCount;
        System.out.println("thread " + id + " created");
    }

    @Override
    protected void printStartMessage() {
        updateMessage(
                Message.NEUTRAL,
                "Поток№" + id + " стартовал " + "№потока = " + thread.getId()
        );
    }

    @Override
    protected void enterToCriticalZone() {
        setInCriticalZone(true);
        updateMessage(
                Message.CRITICAL_ZONE,
                "поток №" + id + " хочет войти в критическую зону"
        );
    }

    @Override
    protected boolean checkEmploymentResource() {
        if (semaphore.availablePermits() > 0) {
            updateMessage(Message.CRITICAL_ZONE,"текущее количество свободных входов" + semaphore.availablePermits() + ", вход разрешен");
            return true;
        } else {
            updateMessage(Message.CRITICAL_ZONE,"текущее количество свободных входов" + semaphore.availablePermits() + ", вход запрещен");
            return false;
        }
    }

    @Override
    protected void startWorkWithResource() {
        try {
            semaphore.acquire();
        } catch (Exception ignore) {
            /* NOP */
        }
        setWorkWithResource(true);
        updateMessage(
                Message.WORK_WITH_RESOURCE,
                "начинаю работать с ресурсом"
        );
    }

    @Override
    protected void doWork() {
        try {
            resource.work(id);
        } catch (Exception e) {
            System.out.println("Ресурс выдал исключение во время работы.");
            e.printStackTrace();
        }
    }

    @Override
    protected void endWorkWithResource() {
        setWorkWithResource(false);
        updateMessage(
                Message.WORK_WITH_RESOURCE,
                "освободил ресурс"
        );
    }

    @Override
    protected void exitFromCriticalZone() {
        setInCriticalZone(false);
        updateMessage(
                Message.REST,
                "поток №" + id + " вышел из критической зоны"
        );
        semaphore.release();
    }

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }
}