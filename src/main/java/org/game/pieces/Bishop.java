package org.game.pieces;

import org.game.Board;
import org.game.enums.Color;
import org.game.enums.Column;
import org.game.enums.Row;

public class Bishop extends Piece {

    public Bishop(Color color) {
        super(color, "B");
    }

    @Override
    public String isLegalMove(Board board, Column fromColumn, Row fromRow, Column toColumn, Row toRow) {
        return "";
    }
}
