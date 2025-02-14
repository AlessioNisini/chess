package org.game.pieces;

import org.game.Board;
import org.game.Coordinate;
import org.game.Move;
import org.game.enums.Color;
import org.game.enums.Column;
import org.game.enums.Row;

import java.util.List;

import static org.game.enums.Column.columnsInTheMiddle;
import static org.game.enums.Row.rowsInTheMiddle;

public class Rock extends Piece {

    private final Column type;

    public Rock(Color color, Column column) {
        super(color, "R");
        this.type = column;
    }

    @Override
    public Rock clone() {
        return new Rock(color, type);
    }

    @Override
    public String isLegalMove(Board board, Move move) {
        Column fromColumn = move.fromColumn();
        Column toColumn = move.toColumn();
        Row fromRow = move.fromRow();
        Row toRow = move.toRow();

        if(fromColumn == toColumn){
            List<Row> rows = rowsInTheMiddle(fromRow, toRow);
            for(Row row : rows) {
                if(board.getPiece(new Coordinate(fromColumn, row)).isPresent())
                    return "Invalid Rock move";
            }
            return isSameColorPieceOnACell(board, move.getTo()) ? "Invalid Rock move" : "";
        }
        if(fromRow == toRow) {
            List<Column> columns = columnsInTheMiddle(fromColumn, toColumn);
            for(Column column : columns) {
                if(board.getPiece(new Coordinate(column, fromRow)).isPresent())
                    return "Invalid Rock move";
            }
            return isSameColorPieceOnACell(board, move.getTo()) ? "Invalid Rock move" : "";
        }
        return "Invalid Rock move";
    }

    public Column getType() {
        return type;
    }

}
