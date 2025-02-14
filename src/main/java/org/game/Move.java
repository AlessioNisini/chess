package org.game;

import org.game.enums.Column;
import org.game.enums.Row;

public class Move {

    private Coordinate from;
    private Coordinate to;

    public Move() {
    }

    public Move(Coordinate from, Coordinate to) {
        this.from = from;
        this.to = to;
    }

    public Row fromRow() {
        return from.getRow();
    }

    public Row toRow() {
        return to.getRow();
    }

    public Column fromColumn() {
        return from.getColumn();
    }

    public Column toColumn() {
        return to.getColumn();
    }

    public Coordinate getFrom() {
        return from;
    }

    public void setFrom(Coordinate from) {
        this.from = from;
    }

    public Coordinate getTo() {
        return to;
    }

    public void setTo(Coordinate to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return from + " -> " + to;
    }
}
