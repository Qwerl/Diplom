package com.dekker.controller;

import com.board.BoardType;
import com.dekker.model.Command;
import com.dekker.model.ThreadList;

public interface ThreadController {
    void researchByStep();
    void researchByRealTime();
    void researchWithEmptyResource();
    void researchWithBoardResource();

    void setBoardInfo(BoardType boardType, String portName);

    void setCommand(int id, Command command);

    void addThread(int priority);

    void startAllThreads();

    void removeThread(int id);

    ThreadList getThreadList();

    void cleanLoggers();
}
