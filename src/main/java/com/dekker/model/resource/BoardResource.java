package com.dekker.model.resource;

import com.board.Board;

public interface BoardResource extends Resource {
    String getPortName();

    void setPortName(String portName);

    Board getBoard();

    void setBoard(Board board);
}
