package org.game.pieces;

import org.game.Board;
import org.game.enums.Color;
import org.game.enums.Column;
import org.game.enums.Row;

public class Queen extends Piece {

    private final Rock rock = new Rock(color, Column.A);
    private final Bishop bishop = new Bishop(color);

    public Queen(Color color) {
        super(color, "Q");
    }

    @Override
    public Queen clone() {
        return new Queen(color);
    }

    @Override
    public String isLegalMove(Board board, Column fromColumn, Row fromRow, Column toColumn, Row toRow) {
        return rock.isLegalMove(board, fromColumn, fromRow, toColumn, toRow).isEmpty() ||
            bishop.isLegalMove(board, fromColumn, fromRow, toColumn, toRow).isEmpty() ? "" : "Invalid Queen move";
    }
}
