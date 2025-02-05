package org.game.enums;

import java.util.ArrayList;
import java.util.List;

public enum Row {
    ONE(0, 1),
    TWO(1, 2),
    THREE(2, 3),
    FOUR(3, 4),
    FIVE(4, 5),
    SIX(5, 6),
    SEVEN(6, 7),
    EIGHT(7, 8);

    public final int i;
    public final int v;

    Row(int i, int v) {
        this.i = i;
        this.v = v;
    }

    public static Row rowFromIndex(int index) {
        for(Row r : Row.values()) {
            if (r.i == index)
                return r;
        }
        throw new IllegalArgumentException("index not found in the Rows");
    }

    public static Row rowFromValue(int value) {
        for(Row r : Row.values()) {
            if (r.v == value)
                return r;
        }
        throw new IllegalArgumentException("value not found in the Rows");
    }

    public static List<Row> rowsInTheMiddle(Row r1, Row r2) {
        List<Row> result = new ArrayList<>();
        if (r1.i < r2.i) {
            for (int i = r1.i+1; i < r2.i; i++){
                result.add(rowFromIndex(i));
            }
        } else {
            for (int i = r2.i+1; i < r1.i; i++){
                result.add(rowFromIndex(i));
            }
        }
        return result;
    }

}
