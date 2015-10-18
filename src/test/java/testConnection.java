import com.board.Arduino;
import com.board.Board;
import com.board.SerialPortFacade;

public class testConnection {
    public static void main(String[] args) {
        SerialPortFacade port = null;
        try {
            port = new SerialPortFacade(9600, 1000, "COM4");
            Board board = new Arduino(port);
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
