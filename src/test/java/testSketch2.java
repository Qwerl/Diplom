import ru.kai.dekker.model.resource.board.ArduinoWithoutLcd;
import ru.kai.dekker.model.resource.board.SerialPortFacade;

public class testSketch2 {

    public static void main(String[] args) {
        SerialPortFacade port = null;
        try {
            port = new SerialPortFacade(9600, 1000, "COM4");
            ArduinoWithoutLcd board = new ArduinoWithoutLcd(port);
            board.setPin8On();
            int currentPin = 8;
            while(true) {
                Thread.sleep(1000);
                switch (currentPin) {
                    case 8  : board.setPin8On();  break;
                    case 9  : board.setPin9On();  break;
                    case 10 : board.setPin10On(); break;
                    case 11 : board.setPin11On(); break;
                    case 12 : board.setPin12On(); break;
                    case 13 : board.setPin13On();
                    default: currentPin = 7; break;
                }
                currentPin++;
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
