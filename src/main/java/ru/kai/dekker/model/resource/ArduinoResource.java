package ru.kai.dekker.model.resource;

import ru.kai.dekker.model.resource.board.Arduino;
import ru.kai.dekker.model.resource.board.Board;
import ru.kai.dekker.model.resource.board.SerialPortFacade;

import java.util.Observable;
import java.util.Observer;

public class ArduinoResource implements BoardResource, Observer {

    private SerialPortFacade port;
    private Arduino board;
    private String lastMessage = "";

    public ArduinoResource(String portName) throws Exception {
        this.port = new SerialPortFacade(portName);
        this.board = new Arduino(port);
        board.sendMessage("start");
    }

    public String getPortName() {
        return port.getPortName();
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = (Arduino) board;
    }

    public void work(int threadId) throws Exception {
        board.sendMessage("work" + threadId);
        port.addObserver(this);
        while(!isWorkDone()) Thread.sleep(100);
        System.out.println("WorkDone by thread №" + threadId);
        board.sendMessage("free");
        port.deleteObserver(this);
    }

    private boolean isWorkDone() {
        boolean isWorkDone = false;
        if (lastMessage.equals("workDone")) {
            isWorkDone = true;
            lastMessage = "";
        }
        return isWorkDone;
    }

    @Override
    public void update(Observable o, Object arg) {
        lastMessage = (String) arg;
    }
}
