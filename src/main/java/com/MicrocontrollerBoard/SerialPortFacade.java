package com.MicrocontrollerBoard;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.*;
import java.net.ConnectException;
import java.util.Enumeration;

public class SerialPortFacade implements SerialPortEventListener{

    private SerialPort serialPort;
    private BufferedReader reader;

    private int dataRate;
    private int timeOut;

    public SerialPortFacade(int dataRate, int timeOut, String portName) throws Exception {
        this.dataRate = dataRate;
        this.timeOut = timeOut;
        init(portName);
    }

    void init(String portName) throws Exception {
        //получаем перечислитель портов
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
        CommPortIdentifier portId = null;
        System.out.println("Trying:");
        //крутимся в цикле, пока не получим порт или не пройдем все порты
        while (portId == null && portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            System.out.println("port" + currPortId.getName());
            if (currPortId.getName().equals(portName) || currPortId.getName().startsWith(portName)) {
                //Пробуем подключиться к порту, открываем порт
                serialPort = (SerialPort) currPortId.open(getClass().getName(), timeOut);
                portId = currPortId;
                System.out.println("Connected on port" + currPortId.getName());
            }
        }

        if (portId == null || serialPort == null) {
            System.out.println("Oops... Could not connect");
            throw new ConnectException();
        }

        //устанавливаем параметры порта
        serialPort.setSerialPortParams(dataRate,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);

        //добавляемся в слушатели
        serialPort.addEventListener(this);
        serialPort.notifyOnDataAvailable(true);

        reader = new BufferedReader(new InputStreamReader(getInput()));
        //Подождать пока плата будет готова
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignore) {/* NOP */}
    }

    /**
     * Сюда поступают данные с платы
     */
    public synchronized void serialEvent(SerialPortEvent event) {
        try {
            if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
                String inputLine = reader.readLine();
                /* ToDo: реализовать принятие данных */
                System.out.println(inputLine);
            }
        }
        catch (Exception ignore) {/*NOP*/}
    }

    public InputStream getInput() throws IOException {
        return serialPort.getInputStream();
    }

    public OutputStream getOutput() throws IOException {
        return serialPort.getOutputStream();
    }

    /**
     * Вызвать для закрытия порта
     */
    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }

}
