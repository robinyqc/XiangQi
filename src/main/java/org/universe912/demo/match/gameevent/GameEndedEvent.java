package org.universe912.demo.match.gameevent;

import org.universe912.demo.board.Board;

public class GameEndedEvent extends GameEvent {
    @Override 
    public boolean isBlocking() {
        return true;
    }

    @Override 
    public GameEventType eventType() {
        return GameEventType.GAME_ENDED;
    }

    @Override
    public void run(Board board) {};

    @Override 
    public void revert(Board board) {};
    
}
