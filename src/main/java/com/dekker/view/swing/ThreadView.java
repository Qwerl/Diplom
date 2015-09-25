package com.dekker.view.swing;

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

    public ThreadView(ThreadSwingController controller, ThreadModel model) {
        this.model = model;
        this.controller = controller;
    }

    public void createModeChoseView(){
        mainFrame = new MainFrame(this);
    }

    public void researchByStepSelected() {
        controller.researchByStep();
    }

    public void researchByRealTimeSelected() {
        controller.researchByRealTime();
    }

    public void createModellingTypeChoseView(){
        modellingTypeChoseFrame = new ModellingTypeChoseFrame();
    }

    public void modellingWithoutResourceSelected() {
        controller.researchWithEmptyResource();
    }

    public void modellingWithResourceSelected() {
        controller.researchWithArduinoResource();
    }

    public void createResourceTypeChoseView(List<String> ports) {
        resourceTypeChoseFrame = new ResourceTypeChoseFrame();
    }

}
