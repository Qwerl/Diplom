package com.dekker.model.resource;

public class EmptyResource implements Resource {

    private int delay = 1000;

    public EmptyResource() {
    }

    public EmptyResource(int delay) {
        this.delay = delay;
    }

    public void work(int threadId) throws Exception {
        Thread.sleep(delay);
    }
}
