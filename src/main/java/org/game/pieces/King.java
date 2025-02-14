package org.game.pieces;

import org.game.Board;
import org.game.Coordinate;
import org.game.Move;
import org.game.enums.Color;
import org.game.enums.Column;
import org.game.enums.Row;

import static org.game.enums.Color.*;
import static org.game.enums.Column.*;
import static org.game.enums.Row.*;

public class King extends Piece {

    private final Queen queen = new Queen(color);

    private boolean canCastleLong = true;
    private boolean canCastleShort = true;

    public King(Color color) {
        super(color, "K");
    }

    @Override
    public King clone() {
        King king = new King(color);
        king.setCanCastleLong(canCastleLong);
        king.setCanCastleShort(canCastleShort);
        return king;
    }

    @Override
    public String isLegalMove(Board board, Move move) {
        Column fromColumn = move.fromColumn();
        Column toColumn = move.toColumn();
        Row fromRow = move.fromRow();
        Row toRow = move.toRow();

        Row castleRow = color == WHITE ? ONE : EIGHT;

        //Player try to castle long
        if (fromColumn == E && fromRow == castleRow && toColumn == C && toRow == castleRow) {
            boolean rockCond = board.getPiece(new Coordinate(A, castleRow)).isPresent() && board.getPiece(new Coordinate(A, castleRow)).get() instanceof Rock rock && rock.getType() == A;
            boolean emptyCellCond = board.getPiece(new Coordinate(B, castleRow)).isEmpty() && board.getPiece(new Coordinate(C, castleRow)).isEmpty() && board.getPiece(new Coordinate(D, castleRow)).isEmpty();
            if(canCastleLong && rockCond && emptyCellCond)
                return "";
        }

        //Player try to castle short
        if (fromColumn == E && fromRow == castleRow && toColumn == G && toRow == castleRow) {
            boolean rockCond = board.getPiece(new Coordinate(H, castleRow)).isPresent() && board.getPiece(new Coordinate(H, castleRow)).get() instanceof Rock rock && rock.getType() == H;
            boolean emptyCellCond = board.getPiece(new Coordinate(F, castleRow)).isEmpty() && board.getPiece(new Coordinate(G, castleRow)).isEmpty();
            if(canCastleShort && rockCond && emptyCellCond)
                return "";
        }

        boolean isQueenMove = queen.isLegalMove(board, move).isEmpty();
        boolean isSingleStep = Math.abs(fromColumn.i - toColumn.i) <= 1 && Math.abs(fromRow.i - toRow.i) <= 1;
        return  isQueenMove && isSingleStep ? "" : "Invalid King move";
    }

    public boolean getCanCastleShort() {
        return canCastleShort;
    }

    public void setCanCastleShort(boolean canCastleShort) {
        this.canCastleShort = canCastleShort;
    }

    public boolean getCanCastleLong() {
        return canCastleLong;
    }

    public void setCanCastleLong(boolean canCastleLong) {
        this.canCastleLong = canCastleLong;
    }
}
