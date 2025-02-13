package org.game;

public class PieceOut {

    private String piece;
    private String position;

    public PieceOut() {
    }

    public PieceOut(String piece, String position) {
        this.piece = piece;
        this.position = position;
    }

    public String getPiece() {
        return piece;
    }

    public void setPiece(String piece) {
        this.piece = piece;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
