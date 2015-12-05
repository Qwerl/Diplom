package com.dekker.view.swing;

import com.dekker.controller.ThreadController;

public class LoggerViewWithStepControlPanel extends LoggerView {
    public LoggerViewWithStepControlPanel(ThreadController controller, int id) {
        super(controller, id);
        super.buttonInit();
    }

}
