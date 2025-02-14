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

import java.util.ArrayList;
import java.util.List;
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

    public Board clone() {
        Board clonedBoard = new Board();
        for (int y=ONE.i; y<=EIGHT.i; y++) {
            for (int x=A.i; x<=H.i; x++) {
                clonedBoard.board[y][x] = board[y][x].clone();
            }
        }
        return clonedBoard;
    }

    public Optional<Piece> getPiece(Coordinate coordinate) {
        return board[coordinate.getRow().i][coordinate.getColumn().i].getPiece();
    }

    public void updateBoard(Coordinate coordinate, Optional<Piece> piece) {
        board[coordinate.getRow().i][coordinate.getColumn().i].setPiece(piece);
    }

    public boolean isPlayerUnderCheck(Color playerColor) {
        Cell kingCell = findTheKingCell(playerColor);
        return isCellSeenByAnyEnemyPieces(nextPlayer(playerColor), new Coordinate(kingCell.getColumn(), kingCell.getRow()));
    }

    public King findTheKing(Color color) {
        Cell kingCell = findTheKingCell(color);
        return (King) kingCell.getPiece().orElseThrow(() -> new IllegalStateException(""));
    }

    public boolean isCellSeenByAnyEnemyPieces(Color color, Coordinate coordinate) {
        return getAllCellsWithAPiece(color).stream().anyMatch(cell ->
            cell.getPiece().map(piece -> {
                Move move = new Move(new Coordinate(cell.getColumn(), cell.getRow()), coordinate);
                return piece.isLegalMove(this, move, false).isEmpty();
            }).orElse(false)
        );
    }

    public List<PieceOut> buildResponse() {
        List<PieceOut> pieces = new ArrayList<>();
        for (int y=ONE.i; y<=EIGHT.i; y++) {
            for (int x=A.i; x<=H.i; x++) {
                if(board[y][x].getPiece().isPresent()) {
                    Cell cell = board[y][x];
                    Piece piece = board[y][x].getPiece().get();
                    pieces.add(new PieceOut(piece.getColor().name().charAt(0) + piece.getName(), cell.getColumn().v + cell.getRow().v));
                }
            }
        }
        return pieces;
    }

    private Cell findTheKingCell(Color color) {
        for (int y=ONE.i; y<=EIGHT.i; y++) {
            for (int x=A.i; x<=H.i; x++) {
                if(board[y][x].getPiece().isPresent()
                    && board[y][x].getPiece().get() instanceof King
                    && board[y][x].getPiece().get().getColor() == color
                ) return board[y][x];
            }
        }
        throw new IllegalStateException("No king found");
    }

    private List<Cell> getAllCellsWithAPiece(Color color) {
        List<Cell> cells = new ArrayList<>();
        for (int y=ONE.i; y<=EIGHT.i; y++) {
            for (int x=A.i; x<=H.i; x++) {
                if(board[y][x].getPiece().isPresent() && board[y][x].getPiece().get().getColor() == color) {
                    cells.add(board[y][x]);
                }
            }
        }
        return cells;
    }

    private Optional<Piece> initialPieceFromCoordinate(Row row, Column column) {
        if (row == ONE || row == EIGHT) {
            Color color = row == ONE ? WHITE : BLACK;
            return switch (column){
                case A, H -> Optional.of(new Rock(color, column));
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

}
