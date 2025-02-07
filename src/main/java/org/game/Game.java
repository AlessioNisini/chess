package org.game;

import org.game.enums.Color;
import org.game.enums.Column;
import org.game.enums.Row;
import org.game.pieces.Pawn;
import org.game.pieces.Piece;
import org.game.pieces.Queen;

import java.util.Optional;

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
        if (piece instanceof Pawn && (toRow == ONE || toRow == EIGHT)) {
            board.updateBoard(toColumn, toRow, Optional.of(new Queen(piece.getColor())));
        } else {
            board.updateBoard(toColumn, toRow, Optional.of(piece));
        }
    }

}
