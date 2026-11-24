package org.universe912.demo.board;

public record MoveResult(Board board, MoveSignal message) {

    public static MoveResult accepted(Board board) {
        return new MoveResult(board, null);
    }

    public static MoveResult rejected(MoveSignal message) {
        return new MoveResult(null, message);
    }
}