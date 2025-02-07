package org.game.pieces;

import org.game.Board;
import org.game.enums.Color;
import org.game.enums.Column;
import org.game.enums.Row;

import static org.game.enums.Column.A;
import static org.game.enums.Column.H;
import static org.game.enums.Row.EIGHT;
import static org.game.enums.Row.ONE;

public class Bishop extends Piece {

    public Bishop(Color color) {
        super(color, "B");
    }

    @Override
    public String isLegalMove(Board board, Column fromColumn, Row fromRow, Column toColumn, Row toRow) {
        if(fromColumn.i - fromRow.i == toColumn.i - toRow.i) {
            for (int y=ONE.i; y<=EIGHT.i; y++) {
                for (int x=A.i; x<=H.i; x++) {
                    if(x - y == fromColumn.i - fromRow.i && x > Math.min(fromColumn.i, toColumn.i) && x < Math.max(fromColumn.i, toColumn.i)) {
                       if(board.board[y][x].getPiece().isPresent())
                           return "Invalid Bishop move";
                    }
                }
            }
            return isSameColorPieceOnACell(board, toColumn, toRow) ? "Invalid Bishop move" : "";
        }
        if(fromColumn.i + fromRow.i == toColumn.i + toRow.i) {
            for (int y=ONE.i; y<=EIGHT.i; y++) {
                for (int x=A.i; x<=H.i; x++) {
                    if(x + y == fromColumn.i + fromRow.i && x > Math.min(fromColumn.i, toColumn.i) && x < Math.max(fromColumn.i, toColumn.i)) {
                        if(board.board[y][x].getPiece().isPresent())
                            return "Invalid Bishop move";
                    }
                }
            }
            return isSameColorPieceOnACell(board, toColumn, toRow) ? "Invalid Bishop move" : "";
        }
        return "Invalid Bishop move";
    }

}
