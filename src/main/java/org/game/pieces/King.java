package org.game.pieces;

import org.game.Board;
import org.game.enums.Color;
import org.game.enums.Column;
import org.game.enums.Row;

public class King extends Piece {

    private final Queen queen = new Queen(color);

    public King(Color color) {
        super(color, "K");
    }

    @Override
    public String isLegalMove(Board board, Column fromColumn, Row fromRow, Column toColumn, Row toRow) {
        boolean isQueenMove = queen.isLegalMove(board, fromColumn, fromRow, toColumn, toRow).isEmpty();
        boolean isSingleStep = Math.abs(fromColumn.i - toColumn.i) <= 1 && Math.abs(fromRow.i - toRow.i) <= 1;
        return  isQueenMove && isSingleStep ? "" : "Invalid King move";
    }
}
