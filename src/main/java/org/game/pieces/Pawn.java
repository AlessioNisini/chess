package org.game.pieces;

import org.game.Board;
import org.game.Cell;
import org.game.enums.Color;
import org.game.enums.Column;
import org.game.enums.Row;

import java.util.List;

import static org.game.enums.Color.BLACK;
import static org.game.enums.Color.WHITE;
import static org.game.enums.Column.areAdjacent;
import static org.game.enums.Column.columnsInTheMiddle;
import static org.game.enums.Row.EIGHT;
import static org.game.enums.Row.FIVE;
import static org.game.enums.Row.FOUR;
import static org.game.enums.Row.ONE;
import static org.game.enums.Row.SEVEN;
import static org.game.enums.Row.SIX;
import static org.game.enums.Row.THREE;
import static org.game.enums.Row.TWO;
import static org.game.enums.Row.rowsInTheMiddle;

public class Pawn extends Piece {

    public Pawn(Color color) {
        super(color, "P");
    }

    @Override
    public String isLegalMove(Board board, Column fromColumn, Row fromRow, Column toColumn, Row toRow) {
        if (color == WHITE){
            if(fromColumn == toColumn){
                boolean isValidMove =
                    (fromRow == TWO && (toRow == THREE || toRow == FOUR)) || (fromRow.v > TWO.v && fromRow.v < EIGHT.v && toRow.v == fromRow.v + 1);
                boolean noCollision = !isThereACollisionWhileMoving(board, fromColumn, fromRow, toRow);
                return  isValidMove && noCollision ? "" : "Invalid Pawn move";
            } else if (areAdjacent(fromColumn, toColumn)) {
                return toRow.v == fromRow.v + 1 && board.isEating(BLACK, toColumn, toRow)? "" : "Invalid Pawn move";
            } else {
                return "Invalid Pawn move";
            }
        } else {
            if(fromColumn.equals(toColumn)){
                boolean isValidMove =
                    (fromRow == SEVEN && (toRow == SIX || toRow == FIVE)) || (fromRow.v < SEVEN.v && fromRow.v > ONE.v && toRow.v == fromRow.v - 1);
                boolean noCollision = !isThereACollisionWhileMoving(board, fromColumn, fromRow, toRow);
                return  isValidMove && noCollision ? "" : "Invalid Pawn move";
            } else if (areAdjacent(fromColumn, toColumn)) {
                return toRow.v == fromRow.v - 1 && board.isEating(WHITE, toColumn, toRow)? "" : "Invalid Pawn move";
            } else {
                return "Invalid Pawn move";
            }
        }
    }

    private boolean isThereACollisionWhileMoving(Board board, Column column, Row fromRow, Row toRow) {
        List<Row> rows = rowsInTheMiddle(fromRow, toRow);
        for(Row row : rows) {
            if(board.getPiece(column, row).isPresent())
                return true;
        }
        return board.getPiece(column, toRow).isPresent();
    }
}
