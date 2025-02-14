package org.game.pieces;

import org.game.Board;
import org.game.Move;
import org.game.enums.Color;
import org.game.enums.Column;

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
    public String isLegalMove(Board board, Move move) {
        return rock.isLegalMove(board, move).isEmpty() ||
            bishop.isLegalMove(board, move).isEmpty() ? "" : "Invalid Queen move";
    }
}
