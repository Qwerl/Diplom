package com.dekker.view.swing;

import com.dekker.model.Mode;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ModeChoseFrame {

    private JFrame modeChoseFrame = new JFrame();
    private ThreadView threadView;
    private Mode[] modes;

    public ModeChoseFrame(Mode[] modes, ThreadView threadView) {
        this.modes = modes;
        this.threadView = threadView;
        init();
    }

    private void init() {
        modeChoseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        for (final Mode mode : modes) {
            JButton button = new JButton(mode.getValue());
            button.addActionListener(e -> returnMode(mode));
            panel.add(button);
        }
        modeChoseFrame.setMinimumSize(new Dimension(300, 300));
        modeChoseFrame.add(panel);
        modeChoseFrame.pack();
        modeChoseFrame.setVisible(true);
    }

    private void returnMode(Mode mode) {
        threadView.modeSelected(mode);
        modeChoseFrame.dispose();
    }

}
