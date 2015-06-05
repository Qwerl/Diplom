import com.MicrocontrollerBoard.ArduinoWithLcd;
import com.MicrocontrollerBoard.BoardWithLcd;
import com.MicrocontrollerBoard.SerialPortFacade;

public class testConnection {
    public static void main(String[] args) {
        SerialPortFacade port = null;
        try {
            port = new SerialPortFacade(9600, 1000, "COM4");
            BoardWithLcd board = new ArduinoWithLcd(port);
            board.sendMessage("hi");
            while(true) {
                Thread.sleep(1000);
            }
        }
        catch (Exception ignore) {/* NOP */}
        finally {
            if (port != null) {
                port.close();
            }
        }
    }
}
