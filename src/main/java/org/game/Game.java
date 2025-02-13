package org.game;

import org.game.enums.Color;
import org.game.enums.Column;
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
        eventuallyUpdateCastleRight(piece);
        board.updateBoard(fromColumn, fromRow, Optional.empty());
        if (piece instanceof Pawn && (toRow == ONE || toRow == EIGHT)) {
            board.updateBoard(toColumn, toRow, Optional.of(new Queen(piece.getColor())));
        } else {
            board.updateBoard(toColumn, toRow, Optional.of(piece));
        }
        moveRockIfCastle(piece, fromColumn, fromRow, toColumn, toRow);
    }

    private void eventuallyUpdateCastleRight(Piece piece) {
        if (piece instanceof King king) {
            king.setCanCastleLong(false);
            king.setCanCastleShort(false);
            System.out.println("Color: " + piece.getColor() + " Long: " + king.getCanCastleLong() + " Short: " + king.getCanCastleShort());
        } else if (piece instanceof Rock rock) {
            King king = board.findTheKing(piece.getColor());
            if (king.getCanCastleLong() && rock.getType() == A) {
                king.setCanCastleLong(false);
            }
            if (king.getCanCastleShort() && rock.getType() == H) {
                king.setCanCastleShort(false);
            }
            System.out.println("Color: " + piece.getColor() + " Long: " + king.getCanCastleLong() + " Short: " + king.getCanCastleShort());
        }
    }

    private void moveRockIfCastle(Piece piece, Column fromColumn, Row fromRow, Column toColumn, Row toRow) {
        if (piece instanceof King king) {
            Color color = king.getColor();
            Row castleRow = color == WHITE ? ONE : EIGHT;
            if (fromColumn == E && fromRow == castleRow && toColumn == C && toRow == castleRow) {
                king.setCanCastleShort(false);
                king.setCanCastleLong(false);
                board.updateBoard(A, castleRow, Optional.empty());
                board.updateBoard(D, castleRow, Optional.of(new Rock(color, A)));
                board.updateBoard(toColumn, toRow, Optional.of(piece));
            }
            if (fromColumn == E && fromRow == castleRow && toColumn == G && toRow == castleRow) {
                king.setCanCastleShort(false);
                king.setCanCastleLong(false);
                board.updateBoard(H, castleRow, Optional.empty());
                board.updateBoard(F, castleRow, Optional.of(new Rock(color, H)));
                board.updateBoard(toColumn, toRow, Optional.of(piece));
            }
        }
    }

}
