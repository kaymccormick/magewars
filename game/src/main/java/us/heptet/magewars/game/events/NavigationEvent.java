package us.heptet.magewars.game.events;

/* Created by kay on 6/26/2014. */
/**
 * Navigation event - mostly used in GWT app
 */
public class NavigationEvent extends BaseEvent {
    public static final EventType<NavigationEvent> ANY =
            new EventType<>(BaseEvent.ANY, "NAV", false);

    private String token;

    /**
     * create a navigation event
     */
    public NavigationEvent() {
        super(ANY);
    }

    /**
     * Create a navigation event
     * @param token The token (for GWT history handling)
     */
    public NavigationEvent(String token) {
        this();
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "NavigationEvent{" +
                "token='" + token + '\'' +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NavigationEvent that = (NavigationEvent) o;

        if (token != null ? !token.equals(that.token) : that.token != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return token != null ? token.hashCode() : 0;
    }
}
