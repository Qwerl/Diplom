package ru.kai.dekker.model.resource;

import ru.kai.dekker.model.resource.board.Board;

public interface BoardResource extends Resource {

    String getPortName();

    Board getBoard();

    void setBoard(Board board);

}
