package org.universe912.demo.match;

import org.universe912.demo.db.DBConnection;
import org.universe912.demo.match.gameevent.GameEvent;

public class Match {
    private GameEvent[] game_events;
    
    private Match(GameEvent[] game_events) {
        this.game_events = game_events;
    }

    public Match loadFromDb(DBConnection connection) {
        // TODO
    }
}