package ru.kai;

import ru.kai.dekker.controller.ThreadSwingController;
import ru.kai.dekker.model.ThreadModel;
import ru.kai.dekker.model.ThreadModelImpl;

public class MVC {

    ThreadModel model = new ThreadModelImpl();
    ThreadSwingController controller = new ThreadSwingController(model);

    public static void main(String[] args) {
        new MVC();
    }

}
