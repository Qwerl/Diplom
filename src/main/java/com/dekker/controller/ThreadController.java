package com.dekker.controller;

import com.board.Board;

public interface ThreadController {
    void researchByStep();
    void researchByRealTime();
    void researchWithEmptyResource();
    void researchWithArduinoResource();

    void setPort(String port) throws Exception;
    void setBoardInfo(Class boardType, String portName) throws IllegalAccessException, InstantiationException;
}
