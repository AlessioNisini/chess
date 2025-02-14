package org.game.pieces;

import org.game.Board;
import org.game.Move;
import org.game.enums.Color;

public class Knight extends Piece {

    public Knight(Color color) {
        super(color, "N");
    }

    @Override
    public Knight clone() {
        return new Knight(color);
    }

    @Override
    public String isLegalMove(Board board, Move move, boolean checkCastle) {
        int indexFromColum = move.fromColumn().i;
        int indexFromRow = move.fromRow().i;
        int indexToColum = move.toColumn().i;
        int indexToRow = move.toRow().i;

        boolean validKnightMove = Math.abs(indexFromColum - indexToColum) == 1 && Math.abs(indexFromRow - indexToRow) == 2 ||
            Math.abs(indexFromColum - indexToColum) == 2 && Math.abs(indexFromRow - indexToRow) == 1;
        return validKnightMove && !isSameColorPieceOnACell(board, move.getTo()) ? "" : "Invalid Knight move";
    }
}
