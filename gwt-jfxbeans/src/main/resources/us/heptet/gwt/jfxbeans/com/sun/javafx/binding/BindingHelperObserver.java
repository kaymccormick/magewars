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

package com.sun.javafx.binding;


import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Binding;

public class BindingHelperObserver implements InvalidationListener {

    private final Binding<?> ref;

    public BindingHelperObserver(Binding<?> binding) {
        if (binding == null) {
            throw new NullPointerException("Binding has to be specified.");
        }
        ref = binding;
    }

    @Override
    public void invalidated(Observable observable) {
        final Binding<?> binding = ref;
        if (binding == null) {
            observable.removeListener(this);
        } else {
            binding.invalidate();
        }
    }

}
