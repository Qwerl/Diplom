import com.dekker.controller.ThreadSwingController;
import com.dekker.model.Mode;
import com.dekker.model.ThreadModelImpl;
import com.dekker.view.swing.ModeChoseFrame;
import com.dekker.view.swing.ThreadView;

public class FrameTest {
//    @Test
//    public void modeChoseFrame() {
//        ModeChoseFrame frame = new ModeChoseFrame(Mode.values());
//        String info = frame.getInfo();
//        info = info.trim();
//        assertEquals("Выбор режима моделирования:STEP_BY_STEP REAL_TIME", info);
//    }

    public static void main(String[] args) {
        new ModeChoseFrame(Mode.values(), new ThreadView(new ThreadSwingController(new ThreadModelImpl()),new ThreadModelImpl()));
    }
}
