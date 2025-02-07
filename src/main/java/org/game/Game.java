package org.game;

import org.game.enums.Color;
import org.game.enums.Column;
import org.game.enums.Row;

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

            String error = isLegalMove(piece, fromColumn, fromRow, toColumn, toRow);
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

    private String isLegalMove(Piece piece, Column fromColumn, Row fromRow, Column toColumn, Row toRow) {
        return switch (piece.getName()){
            case PAWN -> isPawnLegalMove(piece.getColor(), fromColumn, fromRow, toColumn, toRow);
            case ROCK -> isRockLegalMove(piece.getColor(), fromColumn, fromRow, toColumn, toRow);
            case KNIGHT -> isKnightLegalMove(piece.getColor(), fromColumn, fromRow, toColumn, toRow);
            case BISHOP -> isBishopLegalMove(piece.getColor(), fromColumn, fromRow, toColumn, toRow);
            case QUEEN -> isQueenLegalMove(piece.getColor(), fromColumn, fromRow, toColumn, toRow);
            case KING -> isKingLegalMove(piece.getColor(), fromColumn, fromRow, toColumn, toRow);
        };
    }

    private String isPawnLegalMove(Color color, Column fromColumn, Row fromRow, Column toColumn, Row toRow) {
        if (color == WHITE){
            if(fromColumn == toColumn){
                if(fromRow == TWO) {
                    boolean condValidMove = toRow == THREE || toRow == FOUR;
                    boolean condNoCollision = !isThereACollision(fromColumn, fromRow, toColumn, toRow);
                    boolean condEmptyDestination = board.getPiece(fromColumn, toRow).isEmpty();
                    return condValidMove && condNoCollision && condEmptyDestination ? "" : "Invalid Pawn move";
                }
                else if(fromRow.v > TWO.v && fromRow.v < EIGHT.v) {
                    boolean condValidMove = toRow.v == fromRow.v + 1;
                    boolean condEmptyDestination = board.getPiece(fromColumn, toRow).isEmpty();
                    return condValidMove && condEmptyDestination ? "" : "Invalid Pawn move";
                }
                else {
                    return "Invalid Pawn move";
                }
            } else if (areAdjacent(fromColumn, toColumn)) {
                return toRow.v == fromRow.v + 1 && board.isEating(BLACK, toColumn, toRow)? "" : "Invalid Pawn move";
            } else {
                return "Invalid Pawn move";
            }
        } else {
            if(fromColumn.equals(toColumn)){
                if(fromRow == SEVEN) {
                    boolean condValidMove = toRow == SIX || toRow == FIVE;
                    boolean condNoCollision = !isThereACollision(fromColumn, fromRow, toColumn, toRow);
                    boolean condEmptyDestination = board.getPiece(fromColumn, toRow).isEmpty();
                    return  condValidMove && condNoCollision && condEmptyDestination ? "" : "Invalid Pawn move";
                }
                else if(fromRow.v < SEVEN.v && fromRow.v > ONE.v) {
                    boolean condValidMove = toRow.v == fromRow.v - 1;
                    boolean condEmptyDestination = board.getPiece(fromColumn, toRow).isEmpty();
                    return condValidMove && condEmptyDestination ? "" : "Invalid Pawn move";
                }
                else
                    return "Invalid Pawn move";
            } else if (areAdjacent(fromColumn, toColumn)) {
                return toRow.v == fromRow.v - 1 && board.isEating(WHITE, toColumn, toRow)? "" : "Invalid Pawn move";
            } else {
                return "Invalid Pawn move";
            }
        }
    }

    private String isKingLegalMove(Color color, Column fromColumn, Row fromRow, Column toColumn, Row toRow) {
        return "";
    }

    private String isQueenLegalMove(Color color, Column fromColumn, Row fromRow, Column toColumn, Row toRow) {
        return "";
    }

    private String isBishopLegalMove(Color color, Column fromColumn, Row fromRow, Column toColumn, Row toRow) {
        return "";
    }

    private String isKnightLegalMove(Color color, Column fromColumn, Row fromRow, Column toColumn, Row toRow) {
        return "";
    }

    private String isRockLegalMove(Color color, Column fromColumn, Row fromRow, Column toColumn, Row toRow) {
        return "";
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
