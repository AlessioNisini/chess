package org.game.pieces;

import org.game.Board;
import org.game.Move;
import org.game.enums.Color;

import static org.game.enums.Column.A;
import static org.game.enums.Column.H;
import static org.game.enums.Row.EIGHT;
import static org.game.enums.Row.ONE;

public class Bishop extends Piece {

    public Bishop(Color color) {
        super(color, "B");
    }

    @Override
    public Bishop clone() {
        return new Bishop(color);
    }

    @Override
    public String isLegalMove(Board board, Move move, boolean checkCastle) {
        int indexFromColum = move.fromColumn().i;
        int indexFromRow = move.fromRow().i;
        int indexToColum = move.toColumn().i;
        int indexToRow = move.toRow().i;

        if(indexFromColum - indexFromRow == indexToColum - indexToRow) {
            for (int y=ONE.i; y<=EIGHT.i; y++) {
                for (int x=A.i; x<=H.i; x++) {
                    if(x - y == indexFromColum - indexFromRow && x > Math.min(indexFromColum, indexToColum) && x < Math.max(indexFromColum, indexToColum)) {
                       if(board.board[y][x].getPiece().isPresent())
                           return "Invalid Bishop move";
                    }
                }
            }
            return isSameColorPieceOnACell(board, move.getTo()) ? "Invalid Bishop move" : "";
        }
        if(indexFromColum + indexFromRow == indexToColum + indexToRow) {
            for (int y=ONE.i; y<=EIGHT.i; y++) {
                for (int x=A.i; x<=H.i; x++) {
                    if(x + y == indexFromColum + indexFromRow && x > Math.min(indexFromColum, indexToColum) && x < Math.max(indexFromColum, indexToColum)) {
                        if(board.board[y][x].getPiece().isPresent())
                            return "Invalid Bishop move";
                    }
                }
            }
            return isSameColorPieceOnACell(board, move.getTo()) ? "Invalid Bishop move" : "";
        }
        return "Invalid Bishop move";
    }

}
