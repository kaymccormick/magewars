package us.heptet.magewars.webapp.client;

import java.io.Serializable;

/* Created by kay on 5/16/2014. */
/**
 * User name checked event
 */
public class UsernameCheckedEvent implements Serializable {
    /**
     * username
     */
    String username;
    /**
     * availability
     */
    boolean available;
}
