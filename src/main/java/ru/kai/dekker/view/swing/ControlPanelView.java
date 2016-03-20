package ru.kai.dekker.view.swing;


import ru.kai.dekker.controller.ThreadController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ControlPanelView extends JFrame {

    private JPanel contentPane;
    private JTextField txtPriority;
    private ThreadController controller;

    public ControlPanelView(ThreadController controller) {
        this.controller = controller;
        init();
    }

    private void init() {
        setTitle("ControlPanel");
        setResizable(false);
        setSize(150, 270);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setLayout(new GridLayout(0, 1));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JButton addButton = new JButton("add");
        addButton.addActionListener((e) -> addClick());
        contentPane.add(addButton);

        JPanel priorityPanel = new JPanel();
        priorityPanel.setLayout(new GridLayout(1, 2));
        JLabel lblPriority = new JLabel("Приоритет:");
        priorityPanel.add(lblPriority);
        txtPriority = new JTextField();
        priorityPanel.add(txtPriority);
        contentPane.add(priorityPanel);

        JButton startButton = new JButton("start all");
        startButton.addActionListener((e) -> startClick());
        contentPane.add(startButton);

        JButton cleanButton = new JButton("clean");
        cleanButton.addActionListener((e) -> cleanClick());
        contentPane.add(cleanButton);

        setVisible(true);
    }


    private void addClick() {
        if (!"".equals(txtPriority.getText())) {
            try {
                controller.addThread(Integer.parseInt(txtPriority.getText()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(contentPane, "В строке задания приоритета содержутся символы.", "Приоритет не задан некорректно!", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(contentPane, "Приоритет не задан!", "Задайте приоритет", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void startClick() {
        controller.startAllThreads();
    }

    private void cleanClick() {
        controller.cleanLoggers();
    }

}