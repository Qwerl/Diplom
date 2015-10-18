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
     * ������� ����� ������ ������ �������������:
     * 1) ��������
     * 2) � �������� �������
     */
    public void createModeChoseView() {
        mainFrame = new MainFrame(this);
    }

    /**
     * ������� ����� ���������� ������������
     */
    public void researchByStepSelected() {
        controller.researchByStep();
    }

    /**
     * ������� ����� ������������ � �������� �������
     */
    public void researchByRealTimeSelected() {
        controller.researchByRealTime();
    }

    /**
     * ������� ����� ������ ����� ���� �������:
     * 1) ������ ������
     * 2) �����������
     */
    public void createModellingTypeChoseView() {
        modellingTypeChoseFrame = new ModellingTypeChoseFrame();
    }

    /**
     * ������� ����� ��� �������
     * ������ ���������� ��������� �������
     */
    public void modellingWithoutResourceSelected() {
        controller.researchWithEmptyResource();
    }

    /**
     * ������� ����� � ��������
     */
    public void modellingWithResourceSelected() {
        controller.researchWithArduinoResource();
    }

    /**
     * ������� ����� � ������� ���������� � ����� � �������� ��� ����������
     * @param boards ������ ���������
     */
    public void createBoardTypeAndPortNameChoseView(List<Board> boards, List<String> ports) {
        boardTypeAndPortNameChoseFrame = new BoardTypeAndPortNameChoseFrame();
    }

    /**
     * ������� ����������
     * @param boardType ��� ����������
     * @param portName ���� ����������
     */
    public void boardSelected(Class boardType, String portName)  {
        controller.setBoardInfo(boardType, portName);
    }

}
