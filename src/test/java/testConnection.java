import ru.kai.dekker.model.resource.board.Arduino;
import ru.kai.dekker.model.resource.board.Board;
import ru.kai.dekker.model.resource.board.SerialPortFacade;

public class testConnection {

    public static void main(String[] args) {
        SerialPortFacade port = null;
        try {
            port = new SerialPortFacade(9600, 1000, "COM4");
            Board board = new Arduino(port);
            board.sendMessage("start");
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
