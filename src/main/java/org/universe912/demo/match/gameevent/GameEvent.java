package org.universe912.demo.match.gameevent;

import java.util.UUID;

import org.universe912.demo.board.Board;

public abstract class GameEvent {
    private UUID uuid;
    
    public abstract void run(Board board);

    public abstract void revert(Board board);

    public abstract GameEventType eventType();

    public abstract boolean isBlocking();
}