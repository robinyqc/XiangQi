package org.universe912.demo.match.gameevent;

import org.universe912.demo.board.Board;

public class SideSwitchEvent extends GameEvent {
    @Override 
    public boolean isBlocking() {
        return false;
    }

    @Override 
    public GameEventType eventType() {
        return GameEventType.SIDE_SWITCHED;
    }

    @Override 
    public void run(Board board) {
        board.switchCurrentSide();
    }

    @Override 
    public void revert(Board board) {
        board.switchCurrentSide();
    }
}
