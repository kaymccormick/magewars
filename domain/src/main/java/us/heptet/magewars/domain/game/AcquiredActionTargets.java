package us.heptet.magewars.domain.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/* Created by kay on 7/3/2014. */
/**
 *
 * A structure for a series of action targets and game elements that have been selected.
 */
public class AcquiredActionTargets implements Serializable {
    private List<AcquiredActionTarget> targets = new ArrayList<>();

    public AcquiredActionTargets() {
        /* Default constructor */
    }

    /***
     * Create an instance with the specified targets.
     * @param targets Targets to initialize the instance with.
     */
    public AcquiredActionTargets(AcquiredActionTarget... targets)
    {
        this.targets.addAll(Arrays.asList(targets));
    }

    public AcquiredActionTarget getPrimaryTarget()
    {
        return targets.get(0);
    }

    public boolean isEmpty()
    {
        return targets.isEmpty();
    }

    /***
     * Add the specified acquired action target.
     * @param acquiredActionTarget Acquired action target.
     */
    public void add(AcquiredActionTarget acquiredActionTarget) {
        targets.add(acquiredActionTarget);
    }

    /***
     * Add the collection of acquired action targets.
     * @param actionTargets Collection of acquired action targets.
     */
    public void addAll(Collection<AcquiredActionTarget> actionTargets) {
        targets.addAll(actionTargets);
    }

    public List<AcquiredActionTarget> getTargets() {
        return targets;
    }

    @Override
    public String toString() {
        return "AcquiredActionTargets{" +
                "targets=" + targets +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AcquiredActionTargets that = (AcquiredActionTargets) o;

        return targets.equals(that.targets);

    }

    @Override
    public int hashCode() {
        return targets.hashCode();
    }
}
