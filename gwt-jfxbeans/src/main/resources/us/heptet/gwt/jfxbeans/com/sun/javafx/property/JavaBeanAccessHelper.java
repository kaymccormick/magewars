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
package com.sun.javafx.property;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javafx.beans.property.ReadOnlyObjectProperty;

public final class JavaBeanAccessHelper {
    

    private static boolean initialized;
    
    private JavaBeanAccessHelper() {
        
    }
    
    public static <T> ReadOnlyObjectProperty<T> createReadOnlyJavaBeanProperty(Object bean, String propertyName) throws NoSuchMethodException{
        init();
        if(!initialized)
        {
            throw new NoSuchMethodException();
        }
        throw new UnsupportedOperationException("Java beans are not supported.");
    }
    
    private static void init() {
        if (!initialized) {
            initialized = true;
        }
    }
    
}
