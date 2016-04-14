package ru.kai.dekker.model;

import ru.kai.semaphore.model.SemaphoreThreadWrapper;

public class ThreadsFactory {

    private ThreadModel model;

    public ThreadsFactory(ThreadModel model) {
        this.model = model;
    }

    /**
     * создаёт экземпляр класса ThreadStarter, кладет его в список и возвращает
     *
     * @param priority приопитет потока
     * @return возвращает созданный экземпляр класса ThreadStarter
     */
    public DekkerThreadWrapper newThreadStarter(int priority) {
        DekkerThreadWrapper threadWrapper = new DekkerThreadWrapper(model, priority);
        threadWrapper.setCommandMode(model.getMode());
        return threadWrapper;
    }

    /**
     * создаёт экземпляр класса ThreadStarter с контролируемым положением на экране, кладет его в список и возвращает
     *
     * @param priority приопитет потока
     * @param x        координаты логгера по x
     * @param y        координаты логгера по y
     * @return возвращает созданный экземпляр класса ThreadStarter
     */
    public DekkerThreadWrapper newThreadStarter(int priority, int x, int y, Mode mode) {
        DekkerThreadWrapper threadWrapper = new DekkerThreadWrapper(model, priority);
        threadWrapper.setCommandMode(mode);
        return threadWrapper;
    }

    public SemaphoreThreadWrapper newThreadStarter() {
        SemaphoreThreadWrapper threadWrapper = new SemaphoreThreadWrapper();
        threadWrapper.setCommandMode(model.getMode());
        threadWrapper.setSemaphore(model.getSemaphore());
        return threadWrapper;
    }
}