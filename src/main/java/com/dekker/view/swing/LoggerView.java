package com.dekker.view.swing;

import com.dekker.controller.ThreadController;
import com.dekker.model.Command;
import com.dekker.model.ThreadObserver;
import com.dekker.model.message.Message;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.Random;

public class LoggerView implements ThreadObserver {

    private final int width = 380 + 108;
    private final int height = 300;

    private ThreadController controller;
    private JTextArea textArea;
    private JFrame frame;
    protected JPanel panel;
    private int id;

    /**
     * конструктор
     * <p>
     * создаёт логгер в случайном положении на экране(в пределах 1000, 500)
     */
    public LoggerView(ThreadController controller, int id) {
        this.controller = controller;
        this.id = id;
        init();
    }

    private void init() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocation(new Random().nextInt(1000), new Random().nextInt(500));
        frame.setMinimumSize(new Dimension(width, height));

        panel = new JPanel();
        panel.setBackground(Color.black);
        panel.setLayout(new GridLayout(1, 2));
//        panel.setLayout(new FlowLayout(FlowLayout.LEADING));


        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        panel.add(scrollPane);

        frame.add(panel);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                closeFrame();
                super.windowClosed(e);
            }
        });
        buttonInit();
    }

    public JFrame getFrame() {
        return frame;
    }

    private void buttonInit() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1)); //todo вернуть гвозди

        JButton buttonStart = new JButton();
        buttonStart.setText("+flag");
        buttonStart.addActionListener(e -> controller.setCommand(id, Command.START));
        buttonPanel.add(buttonStart);

        JButton buttonWork = new JButton();
        buttonWork.setText("work");
        buttonWork.addActionListener(e ->
                controller.setCommand(id, Command.START_WORK));
        buttonPanel.add(buttonWork);

        JButton buttonNeedRes = new JButton();
        buttonNeedRes.setText("needRes");
        buttonNeedRes.addActionListener(e ->
                controller.setCommand(id, Command.REQUEST_RESOURCE));
        buttonPanel.add(buttonNeedRes);

        JButton buttonEndWork = new JButton();
        buttonEndWork.setText("end");
        buttonEndWork.addActionListener(e ->
                controller.setCommand(id, Command.END_WORK));
        buttonPanel.add(buttonEndWork);

        JButton buttonExitCriticalZone = new JButton();
        buttonExitCriticalZone.setText("-flag");
        buttonExitCriticalZone.addActionListener(e ->
                controller.setCommand(id, Command.EXIT_FROM_CRITICAL_ZONE));
        buttonPanel.add(buttonExitCriticalZone);

        JButton buttonExit = new JButton();
        buttonExit.setText("exit");
        buttonExit.addActionListener(e ->
                controller.setCommand(id, Command.EXIT));
        buttonPanel.add(buttonExit);

        JButton buttonRun = new JButton();
        buttonRun.setText("run");
        buttonRun.addActionListener(e ->
                controller.setCommand(id, Command.RUN));
        buttonPanel.add(buttonRun);


        buttonPanel.validate();
        panel.add(buttonPanel);
    }

    public void cleanLogger() {
        textArea.setText(null);
    }

    public void setState(Color color) {
        textArea.setBackground(color);
    }

    private void closeFrame() {
        System.out.println("closed");
        controller.removeThread(id);
        frame.dispose();
    }

    public void updateThreadInfo(Message message) {
        setState(message.getType());
        Date date = new Date();
        textArea.append(
                date.getHours() + ":" +
                        date.getMinutes() + ":" +
                        date.getSeconds() + "   " +
                        message.getMessage() + "\n"
        );
        frame.setTitle(message.getTitle());
    }
}