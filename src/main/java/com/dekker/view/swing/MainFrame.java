package com.dekker.view.swing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private ThreadView threadView;

    private JButton researchByRealTime, researchByStep;

    public MainFrame(ThreadView threadView) {
        this.threadView = threadView;
        init();
    }

    public void init() {
        setSize(300, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        researchByStep = new JButton("Пошаговый разбор");
        researchByStep.setSize(200, 30);
        researchByStep.setLocation(
                this.getSize().height / 2 - researchByStep.getSize().width / 2,
                this.getSize().width * 1 / 3 - researchByStep.getSize().height
        );
        researchByStep.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                threadView.researchByStepSelected();
            }
        });
        this.add(researchByStep);

        researchByRealTime = new JButton("Разбор в реальном времени");
        researchByRealTime.setSize(200, 30);
        researchByRealTime.setLocation(
                this.getSize().width/2 - researchByRealTime.getSize().width/2,
                this.getSize().height * 2/3 - researchByRealTime.getSize().height
        );
        researchByRealTime.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                threadView.researchByRealTimeSelected();
            }
        });
        this.add(researchByRealTime);
        setVisible(true);
    }

}
