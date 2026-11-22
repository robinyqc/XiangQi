package org.universe912.demo.board;

public final class Piece {
    public final PieceType pieceType;
    public final Side pieceSide;
    public Piece(PieceType pieceType, Side pieceSide) {
        this.pieceType = pieceType;
        this.pieceSide = pieceSide;
    }
    static Piece of(PieceType pieceType, Side pieceSide) {
        return new Piece(pieceType, pieceSide);
    }
}
