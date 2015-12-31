package com.board;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ArduinoWithoutLcd implements BoardWithoutLcd {

    private InputStream input;
    private OutputStream output;

    public ArduinoWithoutLcd(SerialPortFacade serialPort) throws IOException {
        input = serialPort.getInput();
        output = serialPort.getOutput();
    }

    public void setPin8On() throws IOException {
        sendMessage("L1");
    }

    public void setPin9On() throws IOException {
        sendMessage("L2");
    }

    public void setPin10On() throws IOException {
        sendMessage("L3");
    }

    public void setPin11On() throws IOException {
        sendMessage("L4");
    }

    public void setPin12On() throws IOException {
        sendMessage("L5");
    }

    public void setPin13On() throws IOException {
        sendMessage("L6");
    }

    public boolean isAvailable() {
        /* ToDo: реализовать */
        return true;
    }

    public void sendMessage(String message) throws IOException{
        output.write(message.getBytes());
    }

}