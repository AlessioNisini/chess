package org.game;

import org.game.enums.Color;
import org.game.enums.Column;
import org.game.enums.Row;
import org.game.pieces.Bishop;
import org.game.pieces.King;
import org.game.pieces.Knight;
import org.game.pieces.Pawn;
import org.game.pieces.Piece;
import org.game.pieces.Queen;
import org.game.pieces.Rock;

import java.util.Optional;

import static org.game.enums.Color.*;
import static org.game.enums.Row.*;
import static org.game.enums.Column.*;


public class Board {

    public Cell[][] board = new Cell[8][8];

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

    public void updateBoard(Column column, Row row, Optional<Piece> piece) {
        board[row.i][column.i].setPiece(piece);
    }

    public boolean isPlayerUnderCheck(Color color) {
        Cell kingCell = null;
        for (int y=ONE.i; y<=EIGHT.i; y++) {
            for (int x=A.i; x<=H.i; x++) {
                if(board[y][x].getPiece().isPresent()
                    && board[y][x].getPiece().get() instanceof King
                    && board[y][x].getPiece().get().getColor() == color
                ) kingCell = board[y][x];
            }
        }
        for (int y=ONE.i; y<=EIGHT.i; y++) {
            for (int x=A.i; x<=H.i; x++) {
                if(board[y][x].getPiece().isPresent() && board[y][x].getPiece().get().getColor() != color) {
                    Cell cell = board[y][x];
                    Piece piece = board[y][x].getPiece().get();
                    if(piece.isLegalMove(this, cell.getColumn(), cell.getRow(), kingCell.getColumn(), kingCell.getRow()).isEmpty())
                        return true;
                }
            }
        }
        return false;
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

//    private Optional<Piece> xx(Row row, Column column) {
//        if (row == ONE && column == B) {
//            return Optional.of(new King(WHITE));
//        } else if (row == TWO && column == C){
//            return Optional.of(new Pawn(WHITE));
//        } else if (row == FOUR && column == E){
//            return Optional.of(new Queen(BLACK));
//        } else if (row == EIGHT && column == A){
//            return Optional.of(new King(BLACK));
//        } else {
//            return Optional.empty();
//        }
//    }


    private Optional<Piece> initialPieceFromCoordinate(Row row, Column column) {
        if (row == ONE || row == EIGHT) {
            Color color = row == ONE ? WHITE : BLACK;
            return switch (column){
                case A, H -> Optional.of(new Rock(color));
                case B, G -> Optional.of(new Knight(color));
                case C, F -> Optional.of(new Bishop(color));
                case D -> Optional.of(new Queen(color));
                case E -> Optional.of(new King(color));
            };
        } else if (row == TWO || row == SEVEN){
            Color color = row == TWO ? WHITE : BLACK;
            return Optional.of(new Pawn(color));
        } else {
            return Optional.empty();
        }
    }

    public Board clone() {
        Board clonedBoard = new Board();
        for (int y=ONE.i; y<=EIGHT.i; y++) {
            for (int x=A.i; x<=H.i; x++) {
                clonedBoard.board[y][x] = board[y][x].clone();
            }
        }
        return clonedBoard;
    }
}
