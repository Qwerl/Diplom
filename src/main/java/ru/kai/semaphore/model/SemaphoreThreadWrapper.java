package ru.kai.semaphore.model;

import ru.kai.AbstractThreadWrapper;
import ru.kai.dekker.model.message.Message;

import java.util.concurrent.Semaphore;

public class SemaphoreThreadWrapper extends AbstractThreadWrapper implements Runnable {

    private static int threadsCount = -1;

    private final int id;

    int maxConcurrentRequests = 2;
    public Semaphore semaphore = new Semaphore(maxConcurrentRequests); //todo: to controller

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
        try {
            semaphore.acquire();
        } catch (Exception ignore) {/* NOP */}
    }

    @Override
    protected boolean checkEmploymentResource() {
        return semaphore.availablePermits() > 0;
    }

    @Override
    protected void startWorkWithResource() {
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

}