package org.game.pieces;

import org.game.Board;
import org.game.enums.Color;
import org.game.enums.Column;
import org.game.enums.Row;

import java.util.Optional;

import static org.game.enums.Color.*;

public abstract class Piece {

    protected Color color;
    protected String name;

    public Piece(Color color, String name) {
        this.color = color;
        this.name = name;
    }

    public abstract String isLegalMove(Board board, Column fromColumn, Row fromRow, Column toColumn, Row toRow);

    protected boolean isSameColorPieceOnACell(Board board, Column column, Row row) {
        Optional<Piece> pieceOnDestination = board.getPiece(column, row);
        return pieceOnDestination.isPresent() && pieceOnDestination.get().getColor() == color;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return color == WHITE ? name : name.toLowerCase();
    }
}
