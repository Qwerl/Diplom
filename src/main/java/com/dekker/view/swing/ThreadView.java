package com.dekker.view.swing;

import com.board.Board;
import com.board.BoardType;
import com.dekker.controller.ThreadController;
import com.dekker.controller.ThreadSwingController;
import com.dekker.model.Mode;
import com.dekker.model.ThreadModel;
import com.dekker.model.resource.ResourceType;

import java.awt.*;
import java.util.List;


public class ThreadView {
    private ThreadModel model;
    private ThreadController controller;

    private Frame mainFrame;
    private Frame controlFrame;
    private Frame logerFrame;

    private ModeChoseFrame modeChoseFrame;
    private ResourceTypeChoseFrame resourceTypeChoseFrame;
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
        modeChoseFrame = new ModeChoseFrame(Mode.values(), this);
    }

    public void modeSelected(Mode mode) {
        if (mode.equals(Mode.REAL_TIME)) {
            researchByRealTimeSelected();
        } else if (mode.equals(Mode.STEP_BY_STEP)) {
            researchByStepSelected();
        }
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
    public void createResourceTypeChoseView() {
        resourceTypeChoseFrame = new ResourceTypeChoseFrame(ResourceType.values());
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
    public void modellingWithResourceSelected(String portName) throws Exception {
        controller.researchWithArduinoResource(portName); //todo resource chosing
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
    public void boardSelected(BoardType boardType, String portName) throws InstantiationException, IllegalAccessException {
        controller.setBoardInfo(boardType, portName);
    }
}
