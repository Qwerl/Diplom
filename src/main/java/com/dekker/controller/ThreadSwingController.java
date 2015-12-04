package com.dekker.controller;

import com.board.BoardType;
import com.dekker.model.*;
import com.dekker.model.resource.ArduinoWithoutLcdResource;
import com.dekker.model.resource.BoardResource;
import com.dekker.model.resource.EmptyResource;
import com.dekker.view.swing.LoggerView;
import com.dekker.view.swing.ThreadView;

public class ThreadSwingController implements ThreadController {

    private ThreadModel model;
    private ThreadView view;

    public ThreadSwingController(ThreadModel model) {
        this.model = model;
        view = new ThreadView(this, model);
        view.createModeChoseView();
    }

    /**
     * Пошаговый разбор
     */
    public void researchByStep() {
        model.setMode(Mode.STEP_BY_STEP);
        view.createResourceTypeChoseView();
    }

    /**
     * Разбор в реальном времени
     */
    public void researchByRealTime() {
        model.setMode(Mode.REAL_TIME);
        view.createResourceTypeChoseView();
    }

    /**
     * Режим изучения без ресурса
     * вместо взаимодействия с ресурсом вставляется определенная задержка
     */
    public void researchWithEmptyResource() {
        System.out.println("исследования с пустым ресурсом начать");
        model.setResource(new EmptyResource(100));
        view.createControlPanelView();
    }

    /**
     * Режим изучения с ресурсом
     * в качестве ресурса выступает Arduino //todo спустить до Board
     */
    public void researchWithArduinoResource(String portName) {
        ArduinoWithoutLcdResource arduinoResource = null;
        try {
            arduinoResource = new ArduinoWithoutLcdResource(portName);
        } catch (Exception e) {
            System.out.println("Не удалось получить ресурс.");
        }
        model.setResource(arduinoResource);
        //view.createBoardTypeAndPortNameChoseView();  //todo получить список всех поддерживаемых устройств и доступных портов
        // todo переименовать
        view.createControlPanelView();
    }

    /**
     * Получить плату, которая будет использоваться для создания ресурса
     *
     * @param boardType тип устройства
     * @param portName  порт устройства
     */
    public void setBoardInfo(BoardType boardType, String portName) {
        BoardResource resource = null; //todo do it
        model.setResource(resource);
    }

    public void setCommand(int id, Command command) {
        model.setCommand(id, command);
    }

    /**
     * Получить порт, через который подключена плата
     *
     * @param port
     */
    public void setPort(String port) {
        BoardResource resource = null;
        try {
            resource = new ArduinoWithoutLcdResource(port);//todo снизить до board
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.setResource(resource);
    }

    public void addThread(int priority) {
        ThreadWrapper thread = model.addThread(priority);
        LoggerView logger = view.createLoggerView(thread.getId());
        thread.addObserver(logger);
    }

    public void startAllThreads() {
        model.startThreads();
    }

    public void removeThread(int id) {
        model.removeThread(id);
    }

    public ThreadList getThreadList() {
        return model.getThreadList();
    }

    public void cleanLoggers() {
        view.cleanLoggers();
    }
}
