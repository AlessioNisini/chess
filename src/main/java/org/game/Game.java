package org.game;

import org.game.enums.Color;
import org.game.enums.Column;
import org.game.enums.Row;

import java.util.Optional;

import static org.game.enums.Color.*;
import static org.game.enums.Column.areAdjacent;
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
                if(fromRow == TWO)
                    return toRow == THREE || toRow == FOUR ? "" : "Invalid Pawn move";
                else if(fromRow.v > TWO.v && fromRow.v < EIGHT.v)
                    return toRow.v == fromRow.v + 1 ? "" : "Invalid Pawn move";
                else
                    return "Invalid Pawn move";
            } else if (areAdjacent(fromColumn, toColumn)) {
                return toRow.v == fromRow.v + 1 && board.isEating(BLACK, toColumn, toRow)? "" : "Invalid Pawn move";
            } else {
                return "Invalid Pawn move";
            }
        } else {
            if(fromColumn.equals(toColumn)){
                if(fromRow == SEVEN)
                    return toRow == SIX || toRow == FIVE ? "" : "Invalid Pawn move";
                else if(fromRow.v < SEVEN.v && fromRow.v > ONE.v)
                    return toRow.v == fromRow.v - 1 ? "" : "Invalid Pawn move";
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

}
