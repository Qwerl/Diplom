package ru.kai.dekker.view.swing;

import ru.kai.dekker.model.resource.ResourceType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ResourceTypeChoseFrame {

    private ThreadView threadView;
    private ResourceType[] types;
    private JFrame frame;
    private JPanel panel;

    public ResourceTypeChoseFrame(ResourceType[] types, ThreadView threadView) {
        this.types = types;
        this.threadView = threadView;
        init();
    }

    private void init() {
        frame = new JFrame("Выбор ресурса");
        panel = new JPanel(new GridLayout(0, 1));
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        for (final ResourceType type : types) {
            JButton button = new JButton(type.getValue());
            button.addActionListener(e -> {
                returnResourceType(type);
                frame.dispose();
            });
            panel.add(button);
        }
        frame.setMinimumSize(new Dimension(300, 300));
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    private void returnResourceType(ResourceType type) {
            threadView.resourceSelected(type);
    }

}
