package com.mygdx.game.events;

public class EventData {

    public enum EVENT_TYPE {KEYDOWN_EVENT, KEYUP_EVENT, COLLISION_EVENT, ADD_SCORE_EVENT, GAME_EVENT}

    public Object eventData;
    public EVENT_TYPE eventType;

    public EventData(EVENT_TYPE eventType, Object eventData) {
        this.eventType = eventType;
        this.eventData = eventData;
    }

}
