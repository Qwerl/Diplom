package com.dekker.controller;

import com.board.BoardType;

public interface ThreadController {
    void researchByStep();
    void researchByRealTime();
    void researchWithEmptyResource();
    void researchWithArduinoResource(String portName);

    void setPort(String port);
    void setBoardInfo(BoardType boardType, String portName);
}
