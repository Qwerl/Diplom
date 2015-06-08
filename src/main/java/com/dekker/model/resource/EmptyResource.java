package com.dekker.model.resource;

public class EmptyResource implements Resource{
    public void work(int threadId) throws Exception {
        Thread.sleep(1000);
    }
}
