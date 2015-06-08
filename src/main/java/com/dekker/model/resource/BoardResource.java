package com.dekker.model.resource;

import com.MicrocontrollerBoard.ArduinoWithoutLcd;
import com.MicrocontrollerBoard.SerialPortFacade;

public class BoardResource implements Resource {

    private SerialPortFacade port;
    private ArduinoWithoutLcd board;

    public BoardResource(String portName) throws Exception {
        port = new SerialPortFacade(9600, 1000, portName);
        board = new ArduinoWithoutLcd(port);
    }

    public void work(int threadId) throws Exception {
        switch (threadId) {
            case 0 :
                board.setPin8On();
                break;
            case 1 :
                board.setPin9On();
                break;
            case 2 :
                board.setPin10On();
                break;
            case 3 :
                board.setPin11On();
                break;
            case 4 :
                board.setPin12On();
                break;
            default:
                board.setPin13On();
                break;
        }
    }

}
