package com.dekker.view.swing;


import com.dekker.model.ThreadStarter;
import com.dekker.model.ThreadsFactory;
import com.dekker.view.swing.MainFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class ControlPanel extends JFrame {
    private ThreadsFactory factory = ThreadsFactory.getInstance();
    private JPanel contentPane;
    private JButton addButton, startButton, cleanButton;
    private JTextField txtPriority;
    private JLabel lblPriority;
    private int loggersCount = 0;
    private boolean commandMode;

    public ControlPanel(boolean withoutCommands) {
        this.commandMode = withoutCommands;
        init();
    }

    private void init() {
        setTitle("ControlPanel");
        setResizable(false);
        setSize(150, 270);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        addButton = new JButton("add");
        addButton.setLocation(20, 62);
        addButton.setSize(100, 30);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!txtPriority.getText().equals("")) {
                    factory.newThreadStarter(Integer.parseInt(txtPriority.getText()),
                            480 * (loggersCount % 2),
                            300 * (loggersCount / 2),
                            commandMode);//по горизонтали при разрешении 1366x768 помещается 3 логгера
                    loggersCount++;
                }
                else
                    JOptionPane.showMessageDialog(contentPane, "Приоритет не задан!", "Задайте приоритет", JOptionPane.WARNING_MESSAGE);
            }
        });
        contentPane.add(addButton);

        txtPriority = new JTextField();
        txtPriority.setLocation(90, 27);
        txtPriority.setSize(50, 20);
        contentPane.add(txtPriority);

        lblPriority = new JLabel("Приоритет:");
        lblPriority.setLocation(20, 20);
        lblPriority.setSize(70, 30);
        contentPane.add(lblPriority);

        startButton = new JButton("start all");
        startButton.setLocation(20, 120);
        startButton.setSize(100, 30);
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<ThreadStarter> list = factory.getThreadsList();
                for (int i = 0; i < list.size(); i++) {
                    if(!list.get(i).isThreadAlive()) {
                        list.get(i).startThread();
                    }
                }
                try {
                    Thread.sleep(50);//небольшая пауза, чтоб точно все потоки успели стартовать
                } catch (Exception ignore) {/* NOP */}
            }
        });
        contentPane.add(startButton);

        cleanButton = new JButton("clean");
        cleanButton.setLocation(20, 180);
        cleanButton.setSize(100, 30);
        cleanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<ThreadStarter> list = factory.getThreadsList();
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).getLogger().cleanLogger(); //отчистить txtArea i-го логгера
                }
            }
        });
        contentPane.add(cleanButton);

        setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                MainFrame.getInstance().setVisible(true);
                closeFrame();
            }
        });
    }

    private void closeFrame(){
        ThreadsFactory.getInstance().clearThreadStarterList();
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSED));
    }
}