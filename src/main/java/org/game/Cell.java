package org.game;

import org.game.enums.Column;
import org.game.enums.Row;
import org.game.pieces.Piece;

import java.util.Optional;

public class Cell {

    private Row row;
    private Column column;
    private Optional<Piece> piece;

    public Cell(Row row, Column column, Optional<Piece> piece) {
        this.row = row;
        this.column = column;
        this.piece = piece;
    }

    public Row getRow() {
        return row;
    }

    public void setRow(Row row) {
        this.row = row;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public Optional<Piece> getPiece() {
        return piece;
    }

    public void setPiece(Optional<Piece> piece) {
        this.piece = piece;
    }

    @Override
    public String toString() {
        return piece.isEmpty() ? " " : piece.get().toString();
    }

    public Cell clone() {
        if (piece.isPresent())
            return new Cell(row, column, Optional.of(piece.get().clone()));
        else
            return new Cell(row, column, Optional.empty());
    }
}
