package com.dekker.model.resource;

import com.board.Board;

public interface BoardResource extends Resource {

    String getPortName();

    Board getBoard();

    void setBoard(Board board);

}
