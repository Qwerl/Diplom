package ru.kai.dekker.controller;

import ru.kai.SynchronizationPrimitives;
import ru.kai.dekker.model.resource.board.BoardType;
import ru.kai.dekker.model.Command;
import ru.kai.dekker.model.ThreadList;

public interface ThreadController {

    void researchByStep();

    void researchByRealTime();

    void researchWithEmptyResource();

    void researchWithFileResource();

    void researchWithBoardResource();

    void setBoardInfo(BoardType boardType, String portName);

    void setCommand(int id, Command command);

    void addThread();

    void addThread(int priority);

    void startAllThreads();

    void removeThread(int id);

    ThreadList getThreadList();

    void cleanLoggers();

    void dekkerPrimitiveResearch();

    void semaphorePrimitiveResearch();

    SynchronizationPrimitives getCurrentSynchronizationPrimitive();

    int getSemaphoreState();

}
