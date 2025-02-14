package org.game.pieces;

import org.game.Board;
import org.game.Coordinate;
import org.game.Move;
import org.game.enums.Color;
import org.game.enums.Column;
import org.game.enums.Row;

import java.util.List;
import java.util.Optional;

import static org.game.enums.Color.*;
import static org.game.enums.Column.areAdjacent;
import static org.game.enums.Row.*;
import static org.game.enums.Row.rowsInTheMiddle;

public class Pawn extends Piece {

    public Pawn(Color color) {
        super(color, "P");
    }

    @Override
    public Pawn clone() {
        return new Pawn(color);
    }

    @Override
    public String isLegalMove(Board board, Move move) {
        Column fromColumn = move.fromColumn();
        Column toColumn = move.toColumn();
        Row fromRow = move.fromRow();
        Row toRow = move.toRow();

        Row startPawnRow = color == WHITE ? TWO : SEVEN;
        Row endPawnRow = color == WHITE ? EIGHT : ONE;
        Row doubleStepPawnRow = color == WHITE ? FOUR : FIVE;
        int pawnDirection = color == WHITE ? 1 : -1;
        boolean isPawnOneStepMoving = toRow.v == fromRow.v + pawnDirection;
        boolean isPawnMovingBetweenStartAndEnd =
            color == WHITE
                ? fromRow.v >= startPawnRow.v && fromRow.v < endPawnRow.v && isPawnOneStepMoving
                : fromRow.v <= startPawnRow.v && fromRow.v > endPawnRow.v && isPawnOneStepMoving;

        if(fromColumn == toColumn){
            boolean isValidMove = fromRow == startPawnRow && toRow == doubleStepPawnRow || isPawnMovingBetweenStartAndEnd;
            boolean noCollision = !isThereACollisionWhileMoving(board, fromColumn, fromRow, toRow);
            return  isValidMove && noCollision ? "" : "Invalid Pawn move";
        } else if (areAdjacent(fromColumn, toColumn)) {
            return isPawnOneStepMoving && isEating(board, nextPlayer(color), toColumn, toRow) ? "" : "Invalid Pawn move";
        } else {
            return "Invalid Pawn move";
        }

    }

    private boolean isThereACollisionWhileMoving(Board board, Column column, Row fromRow, Row toRow) {
        List<Row> rows = rowsInTheMiddle(fromRow, toRow);
        for(Row row : rows) {
            if(board.getPiece(new Coordinate(column, row)).isPresent())
                return true;
        }
        return board.getPiece(new Coordinate(column, toRow)).isPresent();
    }

    private boolean isEating(Board board, Color color, Column column, Row row) {
        Optional<Piece> piece = board.getPiece(new Coordinate(column, row));
        return piece.isPresent() && piece.get().getColor() == color;
    }
}
