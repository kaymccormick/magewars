/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import javafx.beans.Observable;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

/**
 * A list that allows listeners to track changes when they occur.
 *
 * @see ListChangeListener
 * @see ListChangeListener.Change
 * @param <E> the list element type
 * @since JavaFX 2.0
 */
public interface ObservableList<E> extends List<E>, Observable {
    /**
     * Add a listener to this observable list.
     * @param listener the listener for listening to the list changes
     */
    public void addListener(ListChangeListener<? super E> listener);

    /**
     * Tries to removed a listener from this observable list. If the listener is not
     * attached to this list, nothing happens.
     * @param listener a listener to remove
     */
    public void removeListener(ListChangeListener<? super E> listener);

    /**
     * A convenient method for var-arg adding of elements.
     * @param elements the elements to add
     * @return true (as specified by Collection.add(E))
     */
    public boolean addAll(E... elements);

    /**
     * Clears the ObservableList and add all the elements passed as var-args.
     * @param elements the elements to set
     * @return true (as specified by Collection.add(E))
     * @throws NullPointerException if the specified arguments contain one or more null elements
     */
    public boolean setAll(E... elements);

    /**
     * Clears the ObservableList and add all elements from the collection.
     * @param col the collection with elements that will be added to this observableArrayList
     * @return true (as specified by Collection.add(E))
     * @throws NullPointerException if the specified collection contains one or more null elements
     */
    public boolean setAll(Collection<? extends E> col);

    /**
     * A convenient method for var-arg usage of removaAll method.
     * @param elements the elements to be removed
     * @return true if list changed as a result of this call
     */
    public boolean removeAll(E... elements);

    /**
     * A convenient method for var-arg usage of retain method.
     * @param elements the elements to be retained
     * @return true if list changed as a result of this call
     */
    public boolean retainAll(E... elements);

    /**
     * Basically a shortcut to sublist(from, to).clear()
     * As this is a common operation, ObservableList has this method for convenient usage.
     * @param from the start of the range to remove (inclusive)
     * @param to the end of the range to remove (exclusive)
     * @throws IndexOutOfBoundsException if an illegal range is provided
     */
    public void remove(int from, int to);

}
