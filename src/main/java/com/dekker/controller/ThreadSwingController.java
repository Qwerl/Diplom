package com.dekker.controller;

import com.board.Board;
import com.dekker.model.ThreadModel;
import com.dekker.model.resource.ArduinoWithoutLcdResource;
import com.dekker.model.resource.ArduinoWithoutResource;
import com.dekker.model.resource.BoardResource;
import com.dekker.model.resource.EmptyResource;
import com.dekker.view.swing.ThreadView;

public class ThreadSwingController implements ThreadController {

    private ThreadModel model;
    private ThreadView view;

    public ThreadSwingController(ThreadModel model) {
        this.model = model;
        view = new ThreadView(this, model);
        view.createModeChoseView();
        //что-то ещё?
    }

    /**
     * Пошаговый разбор
     */
    public void researchByStep() {
        model.setMode(); //todo создай enum или что получше
        view.createModellingTypeChoseView();
    }

    /**
     * Разбор в реальном времени
     */
    public void researchByRealTime() {
        model.setMode();
        view.createModellingTypeChoseView();
    }

    /**
     * Режим изучения без ресурса
     * вместо взаимодействия с ресурсом вставляется определенная задержка
     */
    public void researchWithEmptyResource() {
        model.setResource(new EmptyResource(100)); //todo вставить пустой ресурс
        //view.; //todo начать исследования, вызвать форму
    }

    /**
     * Режим изучения с ресурсом
     * в качестве ресурса выступает Arduino //todo спустить до Board
     */
    public void researchWithArduinoResource() {
        model.setResource(); //todo вставить Arduino ресурс
        view.createBoardTypeAndPortNameChoseView();  //todo получить список всех поддерживаемых устройств и доступных портов
        // todo переименовать
    }

    /**
     * Получить плату, которая будет использоваться для создания ресурса
     * @param boardType тип устройства
     * @param portName порт устройства
     */
    public void setBoardInfo(Class boardType, String portName) throws IllegalAccessException, InstantiationException {
        BoardResource resource = null; //todo do it
        model.setResource(resource);
    }

    /**
     * Получить порт, через который подключена плата
     * @param port
     */
    public void setPort(String port) throws Exception {
        BoardResource resource = new ArduinoWithoutLcdResource(port);
    }

}
