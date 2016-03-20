package ru.kai.dekker.model.resource;

public interface Resource {

    void work(int threadId) throws Exception;

}
