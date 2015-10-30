package gui;

import com.dekker.controller.ThreadController;
import com.dekker.controller.ThreadSwingController;
import com.dekker.model.ThreadModel;
import com.dekker.model.ThreadModelImpl;

public class FrameTest {

    private ThreadModel model = new ThreadModelImpl();
    private ThreadController controller = new ThreadSwingController(model);


//    @Test
//    public void modeChoseFrame() {
//        ModeChoseFrame frame = new ModeChoseFrame(Mode.values());
//        String info = frame.getInfo();
//        info = info.trim();
//        assertEquals("Выбор режима моделирования:STEP_BY_STEP REAL_TIME", info);
//    }

    public static void main(String[] args) {
        new FrameTest();
        //new ModeChoseFrame(Mode.values(), new ThreadView(new ThreadSwingController(new ThreadModelImpl()),new ThreadModelImpl()));
    }
}
