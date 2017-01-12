/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package javafx.collections;

import javafx.beans.NamedArg;
import javafx.beans.WeakListener;


/**
 * A {@code WeakSetChangeListener} can be used, if an {@link javafx.collections.ObservableSet}
 * should only maintain a weak reference to the listener. This helps to avoid
 * memory leaks, that can occur if observers are not unregistered from observed
 * objects after use.
 * <p>
 * {@code WeakSetChangeListener} are created by passing in the original
 * {@link SetChangeListener}. The {@code WeakSetChangeListener} should then be
 * registered to listen for changes of the observed object.
 * <p>
 * Note: You have to keep a reference to the {@code SetChangeListener}, that
 * was passed in as long as it is in use, otherwise it will be garbage collected
 * to soon.
 *
 * @see SetChangeListener
 * @see ObservableSet
 * @see javafx.beans.WeakListener
 *
 * @param <E>
 *            The type of the observed value
 *
 * @since JavaFX 2.1
 */
public final class WeakSetChangeListener<E> implements SetChangeListener<E>, WeakListener {

    private final SetChangeListener<E> ref;

    /**
     * The constructor of {@code WeakSetChangeListener}.
     *
     * @param listener
     *            The original listener that should be notified
     */
    public WeakSetChangeListener(@NamedArg("listener") SetChangeListener<E> listener) {
        if (listener == null) {
            throw new NullPointerException("Listener must be specified.");
        }
        this.ref = listener;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean wasGarbageCollected() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onChanged(Change<? extends E> change) {
        final SetChangeListener<E> listener = ref;
        if (listener != null) {
            listener.onChanged(change);
        } else {
            // The weakly reference listener has been garbage collected,
            // so this WeakListener will now unhook itself from the
            // source bean
            change.getSet().removeListener(this);
        }
    }
}
