package ru.kai.dekker.view.swing;

import ru.kai.dekker.model.resource.board.BoardType;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BoardTypeAndPortNameChoseFrame {

    private ThreadView threadView;
    private JFrame frame;

    private BoardType board;
    private String port;

    public BoardTypeAndPortNameChoseFrame(List<BoardType> boards, List<String> ports, ThreadView threadView) {
        this.threadView = threadView;
        init(boards, ports);
    }

    private void init(List<BoardType> boards, List<String> ports) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 3));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(0, 1));
        leftPanel.add(new JLabel("Выберите плату:"));
        ButtonGroup boardButtons = new ButtonGroup();
        for (BoardType board : boards) {
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout());
            JRadioButton button = new JRadioButton();
            panel.add(button, FlowLayout.LEFT);
            panel.add(new JLabel(board.name()), FlowLayout.CENTER);
            button.addActionListener(e -> setBoard(board));
            boardButtons.add(button);
            leftPanel.add(panel);
        }
        frame.add(leftPanel);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(0, 1));
        rightPanel.add(new JLabel("Выберите порт:"));
        ButtonGroup portButtons = new ButtonGroup();
        for (String port : ports) {
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout());
            JRadioButton button = new JRadioButton();
            panel.add(button, FlowLayout.LEFT);
            panel.add(new JLabel(port), FlowLayout.CENTER);
            button.addActionListener(e -> setPort(port));
            portButtons.add(button);
            rightPanel.add(panel);
        }
        frame.add(rightPanel);

        JButton button = new JButton(">>");
        button.addActionListener(e -> returnValue(board, port));
        frame.add(button);

        frame.pack();
        frame.setVisible(true);
    }


    private void returnValue(BoardType board, String port) {
        if (board == null) {
            JOptionPane.showMessageDialog(frame, "Не выбран флаг типа платы.", "Плата не выбрана!", JOptionPane.WARNING_MESSAGE);
        } else if ("".equals(port) || port == null) {
            JOptionPane.showMessageDialog(frame, "Не выбран флаг порта.", "Порт не выбран!", JOptionPane.WARNING_MESSAGE);
        } else {
            threadView.boardSelected(board, port);
            frame.dispose();
        }
    }

    public void setBoard(BoardType board) {
        this.board = board;
    }

    public void setPort(String port) {
        this.port = port;
    }

}
