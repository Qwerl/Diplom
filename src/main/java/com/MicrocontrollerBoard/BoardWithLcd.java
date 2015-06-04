package com.MicrocontrollerBoard;

import java.io.IOException;

public interface BoardWithLcd {

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
