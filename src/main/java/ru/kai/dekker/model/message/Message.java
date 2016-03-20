package ru.kai.dekker.model.message;

import java.awt.*;

public class Message {

    public static final Color NEUTRAL = Color.WHITE;
    public static final Color REST = new Color(0x8c, 0xc5, 0x40);               //состояние покоя
    public static final Color CRITICAL_ZONE = new Color(0xfe, 0xf2, 0x00);      //нахождение в критической зоне
    public static final Color WORK_WITH_RESOURCE = new Color(0xf1, 0x59, 0x2a); //работа с ресурсом

    private Color type;
    private String message;
    private String title;

    public Message(String message) {
        this.message = message;
        this.type = NEUTRAL;
    }

    public Message(Color color, String message, String title) {
        this.type = color;
        this.message = message;
        this.title = title;
    }

    public Color getType() {
        return type;
    }

    public void setType(Color type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}