package ru.kai.dekker.view.swing;


import ru.kai.SynchronizationPrimitives;
import ru.kai.dekker.controller.ThreadController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ControlPanelView extends JFrame {

    private JPanel contentPane;
    private JTextField txtPriority;
    private ThreadController controller;
    private SynchronizationPrimitives primitive;

    public ControlPanelView(ThreadController controller) {
        this.controller = controller;
        this.primitive = controller.getCurrentSynchronizationPrimitive();
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

        if (primitive.equals(SynchronizationPrimitives.Dekker)) {
            JPanel priorityPanel = new JPanel();
            priorityPanel.setLayout(new GridLayout(1, 2));
            JLabel lblPriority = new JLabel("Приоритет:");
            priorityPanel.add(lblPriority);
            txtPriority = new JTextField();
            priorityPanel.add(txtPriority);
            contentPane.add(priorityPanel);
        } else if (primitive.equals(SynchronizationPrimitives.Semaphore)) {
            JButton semaphoreButton = new JButton("show semaphore state");
            semaphoreButton.addActionListener((e) -> semaphoreClick());
            contentPane.add(semaphoreButton);
        }

        JButton startButton = new JButton("start all");
        startButton.addActionListener((e) -> startClick());
        contentPane.add(startButton);

        JButton cleanButton = new JButton("clean");
        cleanButton.addActionListener((e) -> cleanClick());
        contentPane.add(cleanButton);

        setVisible(true);
    }

    private void semaphoreClick() {
        JOptionPane.showMessageDialog(contentPane, String.valueOf(controller.getSemaphoreState()), "Текущее состояние семафора", JOptionPane.INFORMATION_MESSAGE);
    }

    private void addClick() {
        if (primitive.equals(SynchronizationPrimitives.Dekker)) {
            if (!"".equals(txtPriority.getText())) {
                try {
                    controller.addThread(Integer.parseInt(txtPriority.getText()));
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(contentPane, "В строке задания приоритета содержутся символы.", "Приоритет не задан некорректно!", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(contentPane, "Приоритет не задан!", "Задайте приоритет", JOptionPane.WARNING_MESSAGE);
            }
        } else if (primitive.equals(SynchronizationPrimitives.Semaphore)) {
            controller.addThread();
        }
    }

    private void startClick() {
        controller.startAllThreads();
    }

    private void cleanClick() {
        controller.cleanLoggers();
    }

}