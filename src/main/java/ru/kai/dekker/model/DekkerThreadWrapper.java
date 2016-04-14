package ru.kai.dekker.model;

import ru.kai.AbstractThreadWrapper;
import ru.kai.dekker.model.message.Message;

public class DekkerThreadWrapper extends AbstractThreadWrapper {

    private final int priority;

    public DekkerThreadWrapper(ThreadModel model, int priority) {
        this.model = model;
        this.priority = priority;
        synchronized (this) {
            threadsCount++;
        }
        id = threadsCount;
    }

    protected void printStartMessage() {
        updateMessage(
                Message.NEUTRAL,
                "Поток№" + id + " стартовал " + "№потока = " + thread.getId() + ", приоритет = " + this.getPriority()
        );
    }

    /**
     * желает получить доступ к ресурсу , вешает флаг
     */
    public void enterToCriticalZone() {
        setInCriticalZone(true);
        updateMessage(
                Message.CRITICAL_ZONE,
                "поток №" + id + " хочет войти в критическую зону"
        );
    }

    /**
     * проверяет использует ли кто-то ресурс
     *
     * @return свободность ресурса
     */
    public boolean checkEmploymentResource() {
        ThreadList list = model.getDekkerThreads();
        for (int i = 0; i < list.size(); i++) {
            DekkerThreadWrapper threadWrapper = (DekkerThreadWrapper)list.getById(i);
            if (threadWrapper.getId() != this.id) {
                if (threadWrapper.isWorkWithResource()) {
                    updateMessage(message.getType(), "поток № " + threadWrapper.getId() + " уже работает с ресурсом");
                    return false;
                } else if (threadWrapper.isInCriticalZone()) {
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

    /**
     * выставляет флаг работы с ресурсом
     */
    public void startWorkWithResource() {
        updateMessage(Message.WORK_WITH_RESOURCE, "начинаю работать с ресурсом");
    }

    public void doWork() {
        try {
            resource.work(id);
        } catch (Exception e) {
            System.out.println("Ресурс выдал исключение во время работы.");
            e.printStackTrace();
        }
    }

    /**
     * снимает флаг работы с ресурсом
     */
    public void endWorkWithResource() {
        setWorkWithResource(false);
        updateMessage(Message.WORK_WITH_RESOURCE, "освободил ресурс");
    }

    /**
     * освобождает ресурс, снимает флаг
     */
    public void exitFromCriticalZone() {
        setInCriticalZone(false);
        updateMessage(Message.REST, "поток №" + id + " вышел из критической зоны");
    }

    public int getPriority() {
        return priority;
    }

}