package ru.kai.dekker.view.swing;

import ru.kai.SynchronizationPrimitives;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PrimitiveChoseFrame {

    private JFrame primitiveChoseFrame = new JFrame();
    private ThreadView threadView;
    private SynchronizationPrimitives[] primitives;

    public PrimitiveChoseFrame(SynchronizationPrimitives[] primitives, ThreadView threadView) {
        this.primitives = primitives;
        this.threadView = threadView;
        init();
    }

    private void init() {
        primitiveChoseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        for (final SynchronizationPrimitives primitive : primitives) {
            JButton button = new JButton(primitive.name());
            button.addActionListener(e -> returnPrimitive(primitive));
            panel.add(button);
        }
        primitiveChoseFrame.setMinimumSize(new Dimension(300, 300));
        primitiveChoseFrame.add(panel);
        primitiveChoseFrame.pack();
        primitiveChoseFrame.setVisible(true);
    }

    private void returnPrimitive(SynchronizationPrimitives primitive) {
        threadView.primitiveSelected(primitive);
        primitiveChoseFrame.dispose();
    }
}
