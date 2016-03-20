package ru.kai.dekker.model.resource;

import ru.kai.dekker.model.resource.board.ArduinoWithoutLcd;
import ru.kai.dekker.model.resource.board.Board;
import ru.kai.dekker.model.resource.board.SerialPortFacade;

public class ArduinoWithoutLcdResource implements BoardResource {

    private SerialPortFacade port;
    private ArduinoWithoutLcd board;

    public ArduinoWithoutLcdResource(String portName) throws Exception {
        init(portName);
    }

    private void init(String portName) throws Exception {
        port = new SerialPortFacade(portName);
        board = new ArduinoWithoutLcd(port);
    }

    public void work(int threadId) throws Exception {
        switch (threadId) {
            case 0:
                board.setPin8On();
                break;
            case 1:
                board.setPin9On();
                break;
            case 2:
                board.setPin10On();
                break;
            case 3:
                board.setPin11On();
                break;
            case 4:
                board.setPin12On();
                break;
            default:
                board.setPin13On();
                break;
        }
    }

    public String getPortName() {
        return port.getPortName();
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = (ArduinoWithoutLcd) board;
    }

}
