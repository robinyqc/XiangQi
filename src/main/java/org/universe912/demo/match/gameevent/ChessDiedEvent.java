package org.universe912.demo.match.gameevent;

import org.universe912.demo.board.Board;

public class ChessDiedEvent extends GameEvent {
    @Override 
    public boolean isBlocking() {
        return false;
    }

    @Override
    public GameEventType eventType() {
        return GameEventType.CHESS_DIED;
    }

    @Override 
    public void run(Board board) {
        // TODO
    }

    @Override 
    public void revert(Board board) {
        // TODO
    }
}
