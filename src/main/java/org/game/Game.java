package org.game;

import org.game.enums.Color;
import org.game.enums.Row;
import org.game.pieces.King;
import org.game.pieces.Pawn;
import org.game.pieces.Piece;
import org.game.pieces.Queen;
import org.game.pieces.Rock;

import java.util.Optional;

import static org.game.enums.Color.*;
import static org.game.enums.Column.*;
import static org.game.enums.Row.*;

public class Game {

    public Board board;

    public Game(Board board) {
        this.board = board;
    }

    public String move(Color color, Move move) {

        try {

            Piece piece = board.getPiece(move.getFrom()).orElseThrow(() -> new RuntimeException("No piece on the selected cell"));

            if (piece.getColor() != color)
                throw new RuntimeException("wrong piece color");

            String error = piece.isLegalMove(board, move, true);
            if (!error.isEmpty())
                throw new RuntimeException(error);

            makeTheMove(piece, move);

            return "";

        } catch (Exception e) {
            return e.getMessage();
        }

    }

    private void makeTheMove(Piece piece, Move move) {
        eventuallyUpdateCastleRight(piece);
        board.updateBoard(move.getFrom(), Optional.empty());
        if (piece instanceof Pawn && (move.toRow() == ONE || move.toRow() == EIGHT)) {
            board.updateBoard(move.getTo(), Optional.of(new Queen(piece.getColor())));
        } else {
            board.updateBoard(move.getTo(), Optional.of(piece));
        }
        moveRockIfCastle(piece, move);
    }

    private void eventuallyUpdateCastleRight(Piece piece) {
        if (piece instanceof King king) {
            king.setCanCastleLong(false);
            king.setCanCastleShort(false);
        } else if (piece instanceof Rock rock) {
            King king = board.findTheKing(piece.getColor());
            if (king.getCanCastleLong() && rock.getType() == A) {
                king.setCanCastleLong(false);
            }
            if (king.getCanCastleShort() && rock.getType() == H) {
                king.setCanCastleShort(false);
            }
        }
    }

    private void moveRockIfCastle(Piece piece, Move move) {
        if (piece instanceof King king) {
            Color color = king.getColor();
            Row castleRow = color == WHITE ? ONE : EIGHT;
            if (move.fromColumn() == E && move.fromRow() == castleRow && move.toColumn() == C && move.toRow() == castleRow) {
                king.setCanCastleShort(false);
                king.setCanCastleLong(false);
                board.updateBoard(new Coordinate(A, castleRow), Optional.empty());
                board.updateBoard(new Coordinate(D, castleRow), Optional.of(new Rock(color, A)));
                board.updateBoard(move.getTo(), Optional.of(piece));
            }
            if (move.fromColumn() == E && move.fromRow() == castleRow && move.toColumn() == G && move.toRow() == castleRow) {
                king.setCanCastleShort(false);
                king.setCanCastleLong(false);
                board.updateBoard(new Coordinate(H, castleRow), Optional.empty());
                board.updateBoard(new Coordinate(F, castleRow), Optional.of(new Rock(color, H)));
                board.updateBoard(move.getTo(), Optional.of(piece));
            }
        }
    }

}
