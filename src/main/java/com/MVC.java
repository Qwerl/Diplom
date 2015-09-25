package com;

import com.dekker.controller.ThreadSwingController;
import com.dekker.model.ThreadModel;
import com.dekker.model.ThreadModelImpl;

public class MVC {
    ThreadModel model = new ThreadModelImpl();
    ThreadSwingController controller = new ThreadSwingController(model);
}
