package com.board;

import java.io.IOException;

public interface Board {

    /**
     * Возвращает:
     * true если передача возможно
     * false если передача невозможна
     */
    boolean isAvailable();

    /**
     * Пересылает сообщение
     */
    void sendMessage(String message) throws IOException;

    /**
     * Получить соединение
     */
    void getConnection();
}
