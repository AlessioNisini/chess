package org.game;

import org.game.enums.Color;
import org.game.enums.PieceName;

import static org.game.enums.Color.*;

public class Piece {

    private final Color color;
    private final PieceName name;

    public Piece(Color color, PieceName name) {
        this.color = color;
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public PieceName getName() {
        return name;
    }

    @Override
    public String toString() {
        return color == WHITE ? name.v : name.v.toLowerCase();
    }
}
