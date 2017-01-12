/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
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
package com.sun.javafx.collections;

/**
 */
public interface FloatArraySyncer {

    /**
     * This method is used to sync arrays on pulses. This method expects
     * the same array was synced before. The usage is similar to toArray method
     * so always use it as following: {@code dest = source.syncTo(dest);}
     * @param array previously synced array
     * @return a synced array, which is the same or new array (depending on
     * the change).
     */
    float[] syncTo(float[] array);
}
