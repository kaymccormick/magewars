package us.heptet.magewars.game.events;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 6/20/2014. */
/**
 * EventType, modeled after JavaFX event types.
 * @param <T> Type of event object
 */
public class EventType<T extends BaseEvent> implements Serializable {
    private static transient Logger logger = Logger.getLogger(EventType.class.getName());
    public static final EventType<BaseEvent> ROOT = new EventType<>("EVENT", null);
    private boolean forwarding = true;
    private String name;
    // these were transient .. help!
    private EventType<? super T> superType;

    static {
        logger.setLevel(Level.FINEST);
    }

    /**
     * Event Subtypes
     */
    @JsonIgnore
    public transient Map<EventType<? extends T>, Void> subTypes;

    public EventType() {
        /* Default constructor */
    }

    EventType(final String name,
                     final EventType<? super T> superType) {
        logger.finest("in protected constructor with " + name + ", " + superType);
        this.name = name;
        this.superType = superType;
        if (superType != null) {
            if (superType.subTypes != null) {
                for (Iterator i = superType.subTypes.keySet().iterator(); i.hasNext();) {
                    EventType t  = (EventType) i.next();
                    if (name == null && t.name == null || (name != null && name.equals(t.name))) {
                        logger.fine("removing " + t + " " + Integer.toHexString(t.hashCode()));
                        i.remove();
                    }
                }
            }
            superType.register(this);
        }
    }

    /**
     * Create an event type with the given supertype and name
     * @param superType Super type of event type
     * @param name Name of event type
     */
    public EventType(final EventType<? super T> superType,
                     final String name) {
        logger.finest("in public constructor with " + superType + ", " + name);
        assert superType != null;
        this.name = name;
        this.superType = superType;
        superType.register(this);
    }

    /**
     * Create an event type with the given supertype, name, and forwarding flag
     * @param superType Supertype of event type
     * @param name Name of event type
     * @param forwarding Whether this event type is forwarding or not
     */
    public EventType(EventType<BaseEvent> superType, String name, boolean forwarding) {
        this(superType, name);
        this.forwarding = forwarding;
    }

    private void register(EventType<? extends T> subType) {
        if (subTypes == null) {
            subTypes = new HashMap<>();
        }
        for (EventType<? extends T> t : subTypes.keySet()) {
            if (((t.name == null) && (subType.name == null)) || ((t.name != null) && t.name.equals(subType.name))) {
                throw new IllegalArgumentException("EventType \"" + subType + "\""
                        + "with parent \"" + subType.getSuperType()+"\" already exists");
            }
        }
        subTypes.put(subType, null);
    }

    public String getName() {
        return name;
    }

    @JsonIgnore
    public EventType<? super T> getSuperType() {
        return superType;
    }

    @Override
    public String toString() {
        return (name != null) ? (name + " [" + Integer.toHexString(hashCode())  + "]"): super.toString();
    }

    private Object writeReplace() {
        LinkedList<String> path = new LinkedList<>();
        EventType<?> t = this;
        while (t != ROOT) {
            path.addFirst(t.name);
            t = t.superType;
        }
        return new EventTypeSerialization(new ArrayList<String>(path));
    }

    /**
     * Event type serialization class
     */
    public static class EventTypeSerialization implements Serializable {
        private List<String> path;

        EventTypeSerialization() {
        }

        /**
         * Create serialization type
         * @param path Path
         */
        public EventTypeSerialization(List<String> path) {
            this.path = path;
        }

        /**
         * Read resolve
         * @return Object
         */
        @SuppressWarnings("unchecked")
        public Object readResolve() {
            EventType t = ROOT;
            for (int i = 0; i < path.size(); ++i) {
                String p = path.get(i);
                logger.severe("path " + i + " = " + p);
                if (t.subTypes != null) {
                    EventType s = findSubType(t.subTypes.keySet(), p);
                    if (s == null) {
                        throw new IllegalArgumentException("Cannot find event type \"" + p + "\" (of " + t + ")");
                    }
                    t = s;
                } else {
                    throw new IllegalArgumentException("Cannot find event type \"" + p + "\" (of " + t + ")");
                }
            }
            return t;
        }

        private EventType findSubType(Set<EventType> subTypes, String name) {
            for (EventType t : subTypes) {
                if ((t.name == null && name == null) || (t.name != null && t.name.equals(name))) {
                    return t;
                }
            }
            return null;
        }

    }

    /**
     * Find sub type
     * @param name Name of subtype
     * @return EventType instance
     */
    public EventType findSubType(String name) {
        for (EventType t : subTypes.keySet()) {
            if ((t.name == null && name == null) || (t.name != null && t.name.equals(name))) {
                return t;
            }
        }
        return null;
    }

    public boolean isForwarding() {
        return forwarding;
    }
}
