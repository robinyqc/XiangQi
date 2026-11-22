package org.universe912.demo.board;

import java.util.Optional;

public class Board {
    static final int ROW_COUNT = 10;
    static final int COLUMN_COUNT = 9;

    private final Piece[][] pieces;
    public Side currentSide;

    public Board(Piece[][] pieces, Side currentSide) {
        this.pieces = copyPieces(pieces);
        this.currentSide = currentSide;
    }

    public Board(Board source, Side currentSide) {
        this(source.pieces, currentSide);
    }

    public Optional<Piece> pieceAt(int x, int y) {
        return pieceAt(Position.of(x, y));
    }

    Optional<Piece> pieceAt(Position position) {
        return Optional.ofNullable(pieces[position.x()][position.y()]);
    }

    public Board copy() {
        return new Board(pieces, currentSide);
    }

    void setPieceAt(Position position, Piece piece) {
        pieces[position.x()][position.y()] = piece;
    }

    public Side getCurrentSide() {
        return currentSide;
    }

    public void setCurrentSide(Side currentSide) {
        this.currentSide = currentSide;
    }

    public void switchCurrentSide() {
        if (currentSide == Side.RED) currentSide = Side.BLACK;
        else currentSide = Side.RED;
    }

    private static Piece[][] copyPieces(Piece[][] source) {
        Piece[][] copy = new Piece[ROW_COUNT][COLUMN_COUNT];
        for (int row = 0; row < ROW_COUNT; row++) {
            System.arraycopy(source[row], 0, copy[row], 0, COLUMN_COUNT);
        }
        return copy;
    }
}
