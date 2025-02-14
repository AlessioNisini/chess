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

    @Override
    public String isLegalMove(Board board, Move move, boolean checkCastle) {
        if (checkCastle && isCastlingLong(board, move))
            return "";
        if (checkCastle && isCastlingShort(board, move))
            return "";

        boolean isQueenMove = queen.isLegalMove(board, move, checkCastle).isEmpty();
        boolean isSingleStep = Math.abs(move.fromColumn().i - move.toColumn().i) <= 1 && Math.abs(move.fromRow().i - move.toRow().i) <= 1;
        return  isQueenMove && isSingleStep ? "" : "Invalid King move";
    }

    private boolean isCastlingLong(Board board, Move move) {
        Column fromColumn = move.fromColumn();
        Column toColumn = move.toColumn();
        Row fromRow = move.fromRow();
        Row toRow = move.toRow();

        Row castleRow = color == WHITE ? ONE : EIGHT;
        Color enemyColor = nextPlayer(color);

        Coordinate coordA = new Coordinate(A, castleRow);
        Coordinate coordB = new Coordinate(B, castleRow);
        Coordinate coordC = new Coordinate(C, castleRow);
        Coordinate coordD = new Coordinate(D, castleRow);
        Coordinate coordE = new Coordinate(E, castleRow);

        boolean isLongCastleMove =
            fromColumn == E && fromRow == castleRow && toColumn == C && toRow == castleRow;
        boolean rockCond =
            board.getPiece(coordA).isPresent() && board.getPiece(coordA).get() instanceof Rock rock && rock.getType() == A;
        boolean emptyCellCond =
            board.getPiece(coordB).isEmpty() && board.getPiece(coordC).isEmpty() && board.getPiece(coordD).isEmpty();
        boolean noCheckCond =
            !board.isCellSeenByAnyEnemyPieces(enemyColor, coordC) && !board.isCellSeenByAnyEnemyPieces(enemyColor, coordD) && !board.isCellSeenByAnyEnemyPieces(enemyColor, coordE);

        return isLongCastleMove && canCastleLong && rockCond && emptyCellCond && noCheckCond;
    }

    private boolean isCastlingShort(Board board, Move move) {
        Column fromColumn = move.fromColumn();
        Column toColumn = move.toColumn();
        Row fromRow = move.fromRow();
        Row toRow = move.toRow();

        Row castleRow = color == WHITE ? ONE : EIGHT;
        Color enemyColor = nextPlayer(color);

        Coordinate coordE = new Coordinate(E, castleRow);
        Coordinate coordF = new Coordinate(F, castleRow);
        Coordinate coordG = new Coordinate(G, castleRow);
        Coordinate coordH = new Coordinate(H, castleRow);

        boolean isShortCastleMove =
            fromColumn == E && fromRow == castleRow && toColumn == G && toRow == castleRow;
        boolean rockCond =
            board.getPiece(coordH).isPresent() && board.getPiece(coordH).get() instanceof Rock rock && rock.getType() == H;
        boolean emptyCellCond =
            board.getPiece(coordF).isEmpty() && board.getPiece(coordG).isEmpty();
        boolean noCheckCond =
            !board.isCellSeenByAnyEnemyPieces(enemyColor, coordE) && !board.isCellSeenByAnyEnemyPieces(enemyColor, coordF) && !board.isCellSeenByAnyEnemyPieces(enemyColor, coordG);

        return isShortCastleMove && canCastleShort && rockCond && emptyCellCond && noCheckCond;
    }

}
