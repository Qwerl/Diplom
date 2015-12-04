package com.dekker.view.swing;

import com.dekker.controller.ThreadController;

import javax.swing.*;
import java.awt.*;

public class LoggerViewWithStepControlPanel extends LoggerView {
    public LoggerViewWithStepControlPanel(ThreadController controller, int id) {
        super(controller, id);
        init();
    }

    private void init() {
        JFrame frame = super.getFrame();
        frame.setLayout(new GridLayout(0, 2));
        frame.pack();
    }
}
