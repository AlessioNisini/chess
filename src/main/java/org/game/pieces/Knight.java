package org.game.pieces;

import org.game.Board;
import org.game.enums.Color;
import org.game.enums.Column;
import org.game.enums.Row;

public class Knight extends Piece {

    public Knight(Color color) {
        super(color, "N");
    }

    @Override
    public Knight clone() {
        return new Knight(color);
    }

    @Override
    public String isLegalMove(Board board, Column fromColumn, Row fromRow, Column toColumn, Row toRow) {
        boolean validKnightMove = Math.abs(fromColumn.i - toColumn.i) == 1 && Math.abs(fromRow.i - toRow.i) == 2 ||
            Math.abs(fromColumn.i - toColumn.i) == 2 && Math.abs(fromRow.i - toRow.i) == 1;
        return validKnightMove && !isSameColorPieceOnACell(board, toColumn, toRow) ? "" : "Invalid Knight move";
    }
}
