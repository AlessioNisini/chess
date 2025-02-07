package org.game;

import org.game.enums.Color;
import org.game.enums.Column;
import org.game.enums.Row;
import org.game.pieces.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.game.enums.Color.*;
import static org.game.enums.Column.A;
import static org.game.enums.Column.H;
import static org.game.enums.Column.areAdjacent;
import static org.game.enums.Column.columnsInTheMiddle;
import static org.game.enums.Row.*;

public class Game {

    Board board;

    public Game() {
        this.board = new Board();
    }

    public String move(Color color, Column fromColumn, Row fromRow, Column toColumn, Row toRow) {

        try {

            Piece piece = board.getPiece(fromColumn, fromRow).orElseThrow(() -> new RuntimeException("no piece on the selected cell"));

            if (piece.getColor() != color)
                throw new RuntimeException("wrong piece color");

            String error = piece.isLegalMove(board, fromColumn, fromRow, toColumn, toRow);
            if (!error.isEmpty())
                throw new RuntimeException(error);

            makeTheMove(piece, fromColumn, fromRow, toColumn, toRow);

            return "";

        } catch (Exception e) {
            return e.getMessage();
        }

    }

    private void makeTheMove(Piece piece, Column fromColumn, Row fromRow, Column toColumn, Row toRow) {
        board.updateBoard(fromColumn, fromRow, Optional.empty());
        board.updateBoard(toColumn, toRow, Optional.of(piece));
    }

    private boolean isThereACollision(Column fromColumn, Row fromRow, Column toColumn, Row toRow) {
        if(fromColumn == toColumn) {
            List<Row> rows = rowsInTheMiddle(fromRow, toRow);
            for(Row row : rows) {
                if(board.getPiece(fromColumn, row).isPresent())
                    return true;
            }
        }
        if(fromRow == toRow) {
            List<Column> columns = columnsInTheMiddle(fromColumn, toColumn);
            for(Column column : columns) {
                if(board.getPiece(column, fromRow).isPresent())
                    return true;
            }
        }
        List<Cell> cells = cellsInTheMiddle(fromColumn, fromRow, toColumn, toRow);
        for(Cell cell : cells) {
            if(board.getPiece(cell.getColumn(), cell.getRow()).isPresent())
                return true;
        }

        return false;
    }

    public List<Cell> cellsInTheMiddle(Column fromColumn, Row fromRow, Column toColumn, Row toRow){
        List<Cell> result = new ArrayList<>();
        if(fromColumn.i - fromRow.i == toColumn.i - toRow.i) {
            for (int y=ONE.i; y<=EIGHT.i; y++) {
                for (int x=A.i; x<=H.i; x++) {
                    if(x - y == fromColumn.i - fromRow.i && x > Math.min(fromColumn.i, toColumn.i) && x < Math.max(fromColumn.i, toColumn.i)) {
                        result.add(board.board[y][x]);
                    }
                }
            }
        }
        if(fromColumn.i + fromRow.i == toColumn.i + toRow.i) {
            for (int y=ONE.i; y<=EIGHT.i; y++) {
                for (int x=A.i; x<=H.i; x++) {
                    if(x + y == fromColumn.i + fromRow.i && x > Math.min(fromColumn.i, toColumn.i) && x < Math.max(fromColumn.i, toColumn.i)) {
                        result.add(board.board[y][x]);
                    }
                }
            }
        }
        return result;
    }

    public void diagonalMoves(Column column, Row row){
        List<Cell> c = new ArrayList<>();
        for (int y=ONE.i; y<=EIGHT.i; y++) {
            for (int x=A.i; x<=H.i; x++) {
                if(x - y == column.i - row.i) {
                    c.add(board.board[y][x]);
                } else if(x + y == column.i + row.i) {
                    c.add(board.board[y][x]);
                }
            }
        }
        c.remove(board.board[row.i][column.i]);
    }

}
