package org.game.enums;

import java.util.ArrayList;
import java.util.List;

public enum Column {
    A (0, "a"),
    B (1, "b"),
    C (2, "c"),
    D (3, "d"),
    E (4, "e"),
    F (5, "f"),
    G (6, "g"),
    H (7, "h");

    public final int i;
    public final String v;

    Column(int i, String v) {
        this.i = i;
        this.v = v;
    }

    public static Column columFromIndex(int index) {
        for(Column c : Column.values()) {
            if (c.i == index)
                return c;
        }
        throw new IllegalArgumentException("index not found in the Columns");
    }

    public static Column columFromValue(String value) {
        for(Column c : Column.values()) {
            if (c.v.equals(value))
                return c;
        }
        throw new IllegalArgumentException("value not found in the Columns");
    }

    public static List<Column> getAdjacent(Column column) {
        return switch (column){
            case A -> List.of(B);
            case B -> List.of(A, C);
            case C -> List.of(B, D);
            case D -> List.of(C, E);
            case E -> List.of(D, F);
            case F -> List.of(E, G);
            case G -> List.of(F, H);
            case H -> List.of(G);
        };
    }

    public static boolean areAdjacent(Column c1, Column c2) {
        return switch (c1){
            case A -> c2 == B;
            case B -> c2 == A || c2 == C;
            case C -> c2 == B || c2 == D;
            case D -> c2 == C || c2 == E;
            case E -> c2 == D || c2 == F;
            case F -> c2 == E || c2 == G;
            case G -> c2 == F || c2 == H;
            case H -> c2 == G;
        };
    }

    public static List<Column> columnsInTheMiddle(Column c1, Column c2) {
        List<Column> result = new ArrayList<>();
        if (c1.i < c2.i) {
            for (int i = c1.i+1; i < c2.i; i++){
                result.add(columFromIndex(i));
            }
        } else {
            for (int i = c2.i+1; i < c1.i; i++){
                result.add(columFromIndex(i));
            }
        }
        return result;
    }
}
