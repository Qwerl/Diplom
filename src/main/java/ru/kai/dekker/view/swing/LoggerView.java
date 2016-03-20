package ru.kai.dekker.view.swing;

import ru.kai.dekker.controller.ThreadController;
import ru.kai.dekker.model.ThreadObserver;
import ru.kai.dekker.model.message.Message;

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

    protected ThreadController controller;
    private JTextArea textArea;
    private JFrame frame;
    protected JPanel panel;
    protected int id;

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
        frame.setLayout(new GridLayout(1, 1));
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                closeFrame();
                super.windowClosed(e);
            }
        });

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        panel.add(scrollPane, BorderLayout.CENTER);

        frame.add(panel);
        frame.setVisible(true);
    }

    public void cleanLogger() {
        textArea.setText(null);
    }

    private void setState(Color color) {
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