package ru.kai.dekker.model;

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
    public ThreadWrapper newThreadStarter(int priority) {
        ThreadWrapper threadWrapper = new ThreadWrapper(model, priority);
        if (model.getMode().equals(Mode.STEP_BY_STEP)) {
            threadWrapper.setCommandMode(false);
        }
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
    public ThreadWrapper newThreadStarter(int priority, int x, int y, Mode mode) {
        ThreadWrapper threadWrapper = new ThreadWrapper(model, priority);
        threadWrapper.setCommandMode(true);
        return threadWrapper;
    }
}