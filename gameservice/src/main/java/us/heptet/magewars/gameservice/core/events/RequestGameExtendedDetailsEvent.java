package us.heptet.magewars.gameservice.core.events;

import java.io.Serializable;

/* Created by kay on 5/20/2014. */
/**
 * A class for representing a request for "extended" game details.
 */
public class RequestGameExtendedDetailsEvent implements Serializable {
    private int gameId;
    private String activeUser;

    public RequestGameExtendedDetailsEvent() {
        /* no op */
    }

    /**
     * Construct an instance.
     * @param gameId
     */
    public RequestGameExtendedDetailsEvent(int gameId) {
        this.gameId = gameId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestGameExtendedDetailsEvent that = (RequestGameExtendedDetailsEvent) o;

        if (gameId != that.gameId) return false;
        return activeUser != null ? activeUser.equals(that.activeUser) : that.activeUser == null;

    }

    @Override
    public int hashCode() {
        int result = gameId;
        result = 31 * result + (activeUser != null ? activeUser.hashCode() : 0);
        return result;
    }

    public String getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(String activeUser) {
        this.activeUser = activeUser;
    }
}
