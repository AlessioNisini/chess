package org.game;

import org.game.enums.Color;
import org.game.enums.Column;
import org.game.enums.Row;

import java.util.Optional;

import static org.game.enums.Color.*;
import static org.game.enums.PieceName.*;
import static org.game.enums.Row.*;
import static org.game.enums.Column.*;


public class Board {

    Cell[][] board = new Cell[8][8];

    public Board() {
        for (int y=ONE.i; y<=EIGHT.i; y++) {
            for (int x=A.i; x<=H.i; x++) {
                Row row = rowFromIndex(y);
                Column column = columFromIndex(x);
                Optional<Piece> piece = initialPieceFromCoordinate(row, column);
                board[y][x] = new Cell(row, column, piece);
            }
        }
    }

    public Optional<Piece> getPiece(Column column, Row row) {
        return board[row.i][column.i].getPiece();
    }

    public boolean isEating(Color color, Column column, Row row) {
        Optional<Piece> piece = getPiece(column, row);
        return piece.isPresent() && piece.get().getColor() == color;
    }

    public void updateBoard(Column column, Row row, Optional<Piece> piece) {
        board[row.i][column.i].setPiece(piece);
    }

    public void print() {
        System.out.println();
        System.out.println("---------------------------------");
        for (int i = 0; i<8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print("| " + board[7-i][j] + " ");
            }
            System.out.println("|");
            System.out.println("---------------------------------");
        }
    }

    static Optional<Piece> initialPieceFromCoordinate(Row row, Column column) {
        if (row == ONE || row == EIGHT) {
            Color color = row == ONE ? WHITE : BLACK;
            return switch (column){
                case A, H -> Optional.of(new Piece(color, ROCK));
                case B, G -> Optional.of(new Piece(color, KNIGHT));
                case C, F -> Optional.of(new Piece(color, BISHOP));
                case D -> Optional.of(new Piece(color, QUEEN));
                case E -> Optional.of(new Piece(color, KING));
            };
        } else if (row == TWO || row == SEVEN){
            Color color = row == TWO ? WHITE : BLACK;
            return Optional.of(new Piece(color, PAWN));
        } else {
            return Optional.empty();
        }
    }
}
