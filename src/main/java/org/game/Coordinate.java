package org.game;

import org.game.enums.Column;
import org.game.enums.Row;

public class Coordinate {

    private Column column;
    private Row row;

    public Coordinate() {
    }

    public Coordinate(Column column, Row row) {
        this.column = column;
        this.row = row;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public Row getRow() {
        return row;
    }

    public void setRow(Row row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return column.v + row.v;
    }
}
