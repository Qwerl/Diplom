package com.dekker.view.swing;

import com.board.Board;
import com.dekker.controller.ThreadController;
import com.dekker.controller.ThreadSwingController;
import com.dekker.model.ThreadModel;

import java.awt.*;
import java.util.List;


public class ThreadView {
    private ThreadModel model;
    private ThreadController controller;

    private Frame mainFrame;
    private Frame controlFrame;
    private Frame logerFrame;

    private ModellingTypeChoseFrame modellingTypeChoseFrame;
    private BoardTypeAndPortNameChoseFrame boardTypeAndPortNameChoseFrame;

    public ThreadView(ThreadSwingController controller, ThreadModel model) {
        this.model = model;
        this.controller = controller;
    }

    /**
     * Создать форму выбора режима моделирования:
     * 1) пошагово
     * 2) в реальном времени
     */
    public void createModeChoseView() {
        mainFrame = new MainFrame(this);
    }

    /**
     * Выбрать режим пошагового исследования
     */
    public void researchByStepSelected() {
        controller.researchByStep();
    }

    /**
     * Выбрать режим исследования в реальном времени
     */
    public void researchByRealTimeSelected() {
        controller.researchByRealTime();
    }

    /**
     * Создать форму выбора режим типа ресурса:
     * 1) пустой ресурс
     * 2) ПлатаРесурс
     */
    public void createModellingTypeChoseView() {
        modellingTypeChoseFrame = new ModellingTypeChoseFrame();
    }

    /**
     * Выбрать режим без ресурса
     * ресурс заменяется задержкой времени
     */
    public void modellingWithoutResourceSelected() {
        controller.researchWithEmptyResource();
    }

    /**
     * Выбрать режим с ресурсом
     */
    public void modellingWithResourceSelected() {
        controller.researchWithArduinoResource();
    }

    /**
     * Создать форму с выбором устройства и порта к которому оно подключено
     * @param boards список устройств
     */
    public void createBoardTypeAndPortNameChoseView(List<Board> boards, List<String> ports) {
        boardTypeAndPortNameChoseFrame = new BoardTypeAndPortNameChoseFrame();
    }

    /**
     * Выбрать устройство
     * @param boardType тип устройства
     * @param portName порт устройства
     */
    public void boardSelected(Class boardType, String portName)  {
        controller.setBoardInfo(boardType, portName);
    }

}
