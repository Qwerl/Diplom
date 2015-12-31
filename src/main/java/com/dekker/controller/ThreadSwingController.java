package com.dekker.controller;

import com.board.BoardType;
import com.dekker.model.*;
import com.dekker.model.resource.ArduinoResource;
import com.dekker.model.resource.ArduinoWithoutLcdResource;
import com.dekker.model.resource.BoardResource;
import com.dekker.model.resource.EmptyResource;
import com.dekker.view.swing.LoggerView;
import com.dekker.view.swing.ThreadView;
import gnu.io.CommPortIdentifier;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

import static com.board.BoardType.*;

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
        model.setResource(new EmptyResource(1000));
        view.createControlPanelView();
    }

    /**
     * Режим изучения с ресурсом
     * в качестве ресурса выступает Board
     */
    public void researchWithBoardResource() {
        if (model.getResource() == null) {
            Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
            List<String> ports = new ArrayList<>();
            while (portEnum.hasMoreElements()) {
                CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
                System.out.println("port" + currPortId.getName());
                ports.add(currPortId.getName());
            }
            view.createBoardTypeAndPortNameChoseView(Arrays.asList(BoardType.values()), ports);
        } else {
            System.out.println("исследования с ресурсом начать");
            view.createControlPanelView();
        }
    }

    /**
     * Получить плату, которая будет использоваться для создания ресурса
     *
     * @param boardType тип устройства
     * @param portName  порт устройства
     */
    public void setBoardInfo(BoardType boardType, String portName) {
        BoardResource resource = null;
        try {
            if (boardType.equals(ARDUINO)) {
                resource = new ArduinoResource(portName);
            } else if (boardType.equals(ARDUINO_WITHOUT_LCD)) {
                resource = new ArduinoWithoutLcdResource(portName);
            } else if (boardType.equals(RASPBERRY_PI)) {
                throw new NotImplementedException(); //todo: do then rasp
            }
        } catch (Exception e) {
            System.out.println("Не удалось создать ресурс");
            e.printStackTrace();
        }
        model.setResource(resource);
    }

    public void setCommand(int id, Command command) {
        model.setCommand(id, command);
    }

    public void addThread(int priority) {
        ThreadWrapper thread = model.addThread(priority);
        LoggerView logger = null;
        if (model.getMode().equals(Mode.REAL_TIME)) {
            logger = view.createLoggerView(thread.getId());
        } else if (model.getMode().equals(Mode.STEP_BY_STEP)){
            logger = view.createLoggerViewWithStepControl(thread.getId());
        }
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
