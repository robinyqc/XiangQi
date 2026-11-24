package org.universe912.demo.match.gameevent;

import org.universe912.demo.board.Board;
import org.universe912.demo.board.Displacement;
import org.universe912.demo.board.Move;

public class ChessMovedEvent extends GameEvent {
    private final Displacement displacement;

    @Override 
    public boolean isBlocking() {
        return true;
    }

    @Override 
    public GameEventType eventType() {
        return GameEventType.CHESS_MOVED;
    }

    @Override 
    public void run(Board board) {
        Move.move(board, displacement);
    }

    @Override 
    public void revert(Board board) {
        
    }

    ChessMovedEvent(Displacement displacement) {
        this.displacement = displacement;
    }
}
