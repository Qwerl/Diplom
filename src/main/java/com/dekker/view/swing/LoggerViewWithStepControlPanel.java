package com.dekker.view.swing;

import com.dekker.controller.ThreadController;
import com.dekker.model.Command;

import javax.swing.*;
import java.awt.*;

public class LoggerViewWithStepControlPanel extends LoggerView {

    public LoggerViewWithStepControlPanel(ThreadController controller, int id) {
        super(controller, id);
        buttonInit();
    }

    private void buttonInit() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1));
        buttonPanel.add(getButton("+flag", Command.START));
        buttonPanel.add(getButton("needRes", Command.REQUEST_RESOURCE));
        buttonPanel.add(getButton("work", Command.START_WORK));
        buttonPanel.add(getButton("end", Command.END_WORK));
        buttonPanel.add(getButton("-flag", Command.EXIT_FROM_CRITICAL_ZONE));
        buttonPanel.add(getButton("run", Command.RUN));
        buttonPanel.add(getButton("exit", Command.EXIT));
        panel.add(buttonPanel, BorderLayout.EAST);
    }

    private JButton getButton(String buttonText, Command command) {
        JButton buttonStart = new JButton();
        buttonStart.setText(buttonText);
        buttonStart.addActionListener(e -> controller.setCommand(id, command));
        return buttonStart;
    }

}
