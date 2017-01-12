package us.heptet.magewars.ui;

/* Created by kay on 4/10/2014. */
/**
 * Generic "EventHandler" interface for ui components.
 * TODO: Make this less broken by supplying event information.
 */
@FunctionalInterface
public interface EventHandler {
    /***
     * Handle the given event. Curently no event information is supplied. This should be considered a bug.
     */
    void handle();
}
