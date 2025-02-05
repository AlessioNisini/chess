package org.game.enums;

public enum PieceName {
    PAWN ("P"),
    ROCK ("R"),
    KNIGHT ("N"),
    BISHOP ("B"),
    QUEEN ("Q"),
    KING ("K");

    public final String v;

    PieceName(String v) {
        this.v = v;
    }

}
