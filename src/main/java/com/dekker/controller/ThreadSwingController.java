package com.dekker.controller;

import com.dekker.model.ThreadModel;
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

    public void researchByStep() {
        model.setMode();
        view.createModellingTypeChoseView();
    }

    public void researchByRealTime() {
        view.createModellingTypeChoseView();
    }

    public void researchWithEmptyResource() {
        model.setResource();
    }

    public void researchWithArduinoResource() {

    }

}
