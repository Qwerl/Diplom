package ru.kai.dekker.model.resource.board;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.*;
import java.net.ConnectException;
import java.util.Enumeration;
import java.util.Observable;

public class SerialPortFacade extends Observable implements SerialPortEventListener {

    public static final int DEFAULT_DATARATE = 9600;
    public static final int DEFAULT_TIMEOUT = 1000;

    private SerialPort serialPort;
    private BufferedReader reader;

    private int dataRate;
    private int timeOut;

    public SerialPortFacade(String portName) throws Exception {
        this(DEFAULT_DATARATE, DEFAULT_TIMEOUT, portName);
    }

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
                System.out.println("пришло сообщение с платы: " + inputLine);
                setChanged();
                notifyObservers(inputLine);
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

    public String getPortName() {
        return serialPort.getName();
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
