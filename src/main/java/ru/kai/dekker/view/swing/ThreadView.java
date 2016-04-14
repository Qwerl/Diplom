package ru.kai.dekker.view.swing;

import ru.kai.SynchronizationPrimitives;
import ru.kai.dekker.model.resource.board.BoardType;
import ru.kai.dekker.controller.ThreadController;
import ru.kai.dekker.controller.ThreadSwingController;
import ru.kai.dekker.model.Mode;
import ru.kai.dekker.model.ThreadModel;
import ru.kai.dekker.model.resource.ResourceType;

import java.util.ArrayList;
import java.util.List;


public class ThreadView {

    private ThreadModel model;
    private ThreadController controller;

    private List<LoggerView> loggers = new ArrayList<>();

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
        new ModeChoseFrame(Mode.values(), this);
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
        new ResourceTypeChoseFrame(ResourceType.values(), this);
    }

    public void resourceSelected(ResourceType type) {
        if (type.equals(ResourceType.EMPTY)) {
            modellingWithoutResourceSelected();
        } else if (type.equals(ResourceType.BOARD)) {
            modellingWithBoardResourceSelected();
        }
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
    public void modellingWithBoardResourceSelected() {
        controller.researchWithBoardResource();
    }

    /**
     * Создать форму с выбором устройства и порта к которому оно подключено
     *
     * @param boards список устройств
     * @param ports список доступных портов
     */
    public void createBoardTypeAndPortNameChoseView(List<BoardType> boards, List<String> ports) {
        new BoardTypeAndPortNameChoseFrame(boards, ports, this);
    }

    /**
     * Выбрать устройство
     *
     * @param boardType тип устройства
     * @param portName  порт устройства
     */
    public void boardSelected(BoardType boardType, String portName) {
        controller.setBoardInfo(boardType, portName);
        controller.researchWithBoardResource();
    }

    public void createControlPanelView() {
        new ControlPanelView(controller);
    }

    public LoggerView createLoggerView(int threadId) {
        LoggerView logger = new LoggerView(controller, threadId);
        loggers.add(logger);
        return logger;
    }

    public void cleanLoggers() {
        loggers.forEach(LoggerView::cleanLogger);
    }

    public LoggerView createLoggerViewWithStepControl(int threadId) {
        LoggerView logger = new LoggerViewWithStepControlPanel(controller, threadId);
        loggers.add(logger);
        return logger;
    }

    public void createPrimitiveChoseView() {
        new PrimitiveChoseFrame(SynchronizationPrimitives.values(), this);
    }

    public void primitiveSelected(SynchronizationPrimitives primitive) {
        if (primitive.equals(SynchronizationPrimitives.Dekker)) {
            controller.dekkerPrimitiveResearch();
        } else if (primitive.equals(SynchronizationPrimitives.Semaphore)) {
            controller.semaphorePrimitiveResearch();
        }
    }
}
