package org.game.pieces;

import org.game.Board;
import org.game.Coordinate;
import org.game.Move;
import org.game.enums.Color;

import java.util.Optional;

import static org.game.enums.Color.*;

public abstract class Piece {

    protected Color color;
    protected String name;

    public Piece(Color color, String name) {
        this.color = color;
        this.name = name;
    }

    public abstract Piece clone();

    public abstract String isLegalMove(Board board, Move move, boolean checkCastle);

    protected boolean isSameColorPieceOnACell(Board board, Coordinate coordinate) {
        Optional<Piece> pieceOnDestination = board.getPiece(coordinate);
        return pieceOnDestination.isPresent() && pieceOnDestination.get().getColor() == color;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return color == WHITE ? name : name.toLowerCase();
    }

}
