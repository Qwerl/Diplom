package com.dekker.view.swing;

import com.dekker.model.Command;
import com.dekker.model.ThreadStarter;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.Random;

public class MyLoggerPanel extends JFrame {

    public final static Color REST = Color.GREEN;               //состояние покоя
    public final static Color CRITICAL_ZONE = Color.YELLOW;     //нахождение в критической зоне
    public final static Color WORK_WITH_RESOURCE = Color.RED;   //работа с ресурсом

    private final int width = 380+108;
    private final int height = 300;

    private JTextArea textArea = null;
    private JScrollPane pane = null;
    private DefaultCaret caret = null;
    private ThreadStarter threadStarter = null;

    public void setThreadStarter(ThreadStarter threadStarter) {
        this.threadStarter = threadStarter;
    }

    /**
     * конструктор
     *
     * создаёт логгер в случайном положении на экране(в пределах 1000, 500)
     * @param id порядковый номер ThreadStarter' а
     */
    public MyLoggerPanel (int id) {
        setTitle(Integer.toString(id));
        setSize(width, height);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(new Random().nextInt(1000), new Random().nextInt(500));
        textArea = new JTextArea();
        textArea.setEditable(false);
        pane = new JScrollPane(textArea);
        caret =(DefaultCaret)textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        getContentPane().add(pane);
        setVisible(true);
    }

    /**
     * конструктор
     *
     * создаёт логгер с координатами locationX, locationY
     * @param id порядковый номер ThreadStarter' а
     * @param locationX координата по x
     * @param locationY координата по y
     */
    public MyLoggerPanel (int id, int locationX, int locationY) {
        setTitle(Integer.toString(id));
        setLayout(null);
        setSize(width, height);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocation(locationX, locationY);
        textArea = new JTextArea();
        textArea.setEditable(true);
        //textArea.setSize(380, 300);
        textArea.setLocation(0, 0);
        pane = new JScrollPane(textArea);
        pane.setSize(380,262);
        caret =(DefaultCaret)textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        getContentPane().add(pane);
        buttonInit();
        setVisible(true);
    }

    private void buttonInit() {
        JButton buttonStart = new JButton();
        buttonStart.setText("+flag");
        buttonStart.setLocation(385, 20);
        buttonStart.setSize(80, 20);
        buttonStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                threadStarter.setCommand(Command.START);
            }
        });
        add(buttonStart);

        JButton buttonWork = new JButton();
        buttonWork.setText("work");
        buttonWork.setLocation(385, 60);
        buttonWork.setSize(80, 20);
        buttonWork.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                threadStarter.setCommand(Command.START_WORK);
            }
        });
        add(buttonWork);

        JButton buttonNeedRes = new JButton();
        buttonNeedRes.setText("needRes");
        buttonNeedRes.setLocation(385, 40);
        buttonNeedRes.setSize(80, 20);
        buttonNeedRes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                threadStarter.setCommand(Command.REQUEST_RESOURCE);
            }
        });
        add(buttonNeedRes);

        JButton buttonEndWork = new JButton();
        buttonEndWork.setText("end");
        buttonEndWork.setLocation(385, 100);
        buttonEndWork.setSize(80, 20);
        buttonEndWork.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                threadStarter.setCommand(Command.END_WORK);
            }
        });
        add(buttonEndWork);

        JButton buttonExitCrit = new JButton();
        buttonExitCrit.setText("-flag");
        buttonExitCrit.setLocation(385, 120);
        buttonExitCrit.setSize(80, 20);
        buttonExitCrit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                threadStarter.setCommand(Command.EXIT_FROM_CRITICAL_ZONE);
            }
        });
        add(buttonExitCrit);

        JButton buttonExit = new JButton();
        buttonExit.setText("exit");
        buttonExit.setLocation(385, 180);
        buttonExit.setSize(80, 20);
        buttonExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                threadStarter.setCommand(Command.EXIT);
            }
        });
        add(buttonExit);

        JButton buttonRun = new JButton();
        buttonRun.setText("run");
        buttonRun.setLocation(385, 140);
        buttonRun.setSize(80, 20);
        buttonRun.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                threadStarter.setCommand(Command.RUN);
            }
        });
        add(buttonRun);
    }

    /**
     * добавить строку в лог
     */
    public void addLog(String str) {
        Date date = new Date();
        textArea.append(date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + "   " +  str + "\n");
    }
    public void updateTitle(int id, String str) {
        this.setTitle(id + " " + str);
    }
    public void updateTitle(String str) {
        this.setTitle(str);
    }
    public void cleanLogger(){
        textArea.setText(null);
    }
    public void setState (Color color) {
        textArea.setBackground(color);
    }

    public void closeFrame(){
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
}