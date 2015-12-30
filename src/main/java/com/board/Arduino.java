package com.board;

import java.io.*;

public class Arduino implements BoardWithLcd {

    public static final int LCD_WIDTH = 16;
    public static final int LCD_HEIGHT = 2;

    private InputStream input;
    private OutputStream output;

    public Arduino(SerialPortFacade serialPort) throws IOException {
        input = serialPort.getInput();
        output = serialPort.getOutput();
    }

    public boolean isAvailable() {
        /* ToDo: реализовать */
        return true;
    }

    public void sendMessage(String message) throws IOException{
        output.write(message.getBytes());
    }

    public void getConnection() {
        /* ToDo: реализовать */
    }
}
