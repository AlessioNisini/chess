package org.game.enums;

public enum Color {

    BLACK,
    WHITE;

    public static Color nextPlayer(Color color) {
        return color == WHITE ? BLACK : WHITE;
    }

}
