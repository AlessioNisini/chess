package org.game.pieces;

import org.game.Board;
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
    public String isLegalMove(Board board, Column fromColumn, Row fromRow, Column toColumn, Row toRow) {
        Row castleRow = color == WHITE ? ONE : EIGHT;
        if (canCastleLong) {
            boolean rockCond = board.getPiece(A, castleRow).isPresent() && board.getPiece(A, castleRow).get() instanceof Rock rock && rock.getType() == A;
            boolean moveCond = fromColumn == E && fromRow == castleRow && toColumn == C && toRow == castleRow;
            boolean emptyCellCond = board.getPiece(B, castleRow).isEmpty() && board.getPiece(C, castleRow).isEmpty() && board.getPiece(D, castleRow).isEmpty();
            if(rockCond && moveCond && emptyCellCond)
                return "";
        }
        if (canCastleShort) {
            boolean rockCond = board.getPiece(H, castleRow).isPresent() && board.getPiece(H, castleRow).get() instanceof Rock rock && rock.getType() == H;
            boolean moveCond = fromColumn == E && fromRow == castleRow && toColumn == G && toRow == castleRow;
            boolean emptyCellCond = board.getPiece(F, castleRow).isEmpty() && board.getPiece(G, castleRow).isEmpty();
            if(rockCond && moveCond && emptyCellCond)
                return "";
        }

        boolean isQueenMove = queen.isLegalMove(board, fromColumn, fromRow, toColumn, toRow).isEmpty();
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
