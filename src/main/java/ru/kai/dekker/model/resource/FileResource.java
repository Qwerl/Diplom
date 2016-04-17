package ru.kai.dekker.model.resource;

import java.io.FileWriter;

public class FileResource implements Resource {

    public FileResource() {
    }

    public void work(int threadId) throws Exception {
        try(FileWriter writer = new FileWriter("concurrency.txt", true)) {
            String text = "Контроль над ресурсом осуществляет поток №" + threadId;
            writer.write(text);
            writer.append('\n');
            writer.flush();
        }
    }
}
