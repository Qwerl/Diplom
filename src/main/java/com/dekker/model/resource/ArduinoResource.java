package com.dekker.model.resource;

import com.board.Arduino;
import com.board.Board;
import com.board.SerialPortFacade;

import java.util.Observable;
import java.util.Observer;

public class ArduinoResource implements BoardResource, Observer {

    SerialPortFacade port;
    Arduino board;
    String lastMessage = "";

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
        port.deleteObserver(this);
    }

    private boolean isWorkDone() {
        boolean isWorkDone = false;
        if (lastMessage.equals("workDone")) {
            isWorkDone = true;
        }
        return isWorkDone;
    }

    @Override
    public void update(Observable o, Object arg) {
        lastMessage = (String) arg;
    }
}
