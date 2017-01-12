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

import javafx.beans.Observable;
import javafx.beans.WeakListener;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.util.StringConverter;

import java.text.ParseException;

public abstract class BidirectionalBinding<T> implements ChangeListener<T>, WeakListener {

    private static void checkParameters(Object property1, Object property2) {
        if ((property1 == null) || (property2 == null)) {
            throw new NullPointerException("Both properties must be specified.");
        }
        if (property1 == property2) {
            throw new IllegalArgumentException("Cannot bind property to itself");
        }
    }

    public static <T> BidirectionalBinding bind(Property<T> property1, Property<T> property2) {
        checkParameters(property1, property2);
        final BidirectionalBinding binding =
                ((property1 instanceof DoubleProperty) && (property2 instanceof DoubleProperty)) ?
                        new BidirectionalDoubleBinding((DoubleProperty) property1, (DoubleProperty) property2)
                : ((property1 instanceof FloatProperty) && (property2 instanceof FloatProperty)) ?
                        new BidirectionalFloatBinding((FloatProperty) property1, (FloatProperty) property2)
                : ((property1 instanceof IntegerProperty) && (property2 instanceof IntegerProperty)) ?
                        new BidirectionalIntegerBinding((IntegerProperty) property1, (IntegerProperty) property2)
                : ((property1 instanceof LongProperty) && (property2 instanceof LongProperty)) ?
                        new BidirectionalLongBinding((LongProperty) property1, (LongProperty) property2)
                : ((property1 instanceof BooleanProperty) && (property2 instanceof BooleanProperty)) ?
                        new BidirectionalBooleanBinding((BooleanProperty) property1, (BooleanProperty) property2)
                : new TypedGenericBidirectionalBinding<T>(property1, property2);
        property1.setValue(property2.getValue());
        property1.addListener(binding);
        property2.addListener(binding);
        return binding;
    }

    public static <T> void unbind(Property<T> property1, Property<T> property2) {
        checkParameters(property1, property2);
        final BidirectionalBinding binding = new UntypedGenericBidirectionalBinding(property1, property2);
        property1.removeListener(binding);
        property2.removeListener(binding);
    }

    public static void unbind(Object property1, Object property2) {
        checkParameters(property1, property2);
        final BidirectionalBinding binding = new UntypedGenericBidirectionalBinding(property1, property2);
        if (property1 instanceof ObservableValue) {
            ((ObservableValue) property1).removeListener(binding);
        }
        if (property2 instanceof Observable) {
            ((ObservableValue) property2).removeListener(binding);
        }
    }
    
    public static BidirectionalBinding bindNumber(Property<Integer> property1, IntegerProperty property2) {
        return bindNumber(property1, (Property<Number>)property2);
    }
    
    public static BidirectionalBinding bindNumber(Property<Long> property1, LongProperty property2) {
        return bindNumber(property1, (Property<Number>)property2);
    }
    
    public static BidirectionalBinding bindNumber(Property<Float> property1, FloatProperty property2) {
        return bindNumber(property1, (Property<Number>)property2);
    }
    
    public static BidirectionalBinding bindNumber(Property<Double> property1, DoubleProperty property2) {
        return bindNumber(property1, (Property<Number>)property2);
    }

    private static <T extends Number> BidirectionalBinding bindNumber(Property<T> property1, Property<Number> property2) {
        checkParameters(property1, property2);
        
        final BidirectionalBinding<Number> binding = new TypedNumberBidirectionalBinding<T>(property1, property2);
        
        property1.setValue((T)property2.getValue());
        property1.addListener(binding);
        property2.addListener(binding);
        return binding;
    }
    
    public static <T extends Number> void unbindNumber(Property<T> property1, Property<Number> property2) {
        checkParameters(property1, property2);
        final BidirectionalBinding binding = new UntypedGenericBidirectionalBinding(property1, property2);
        if (property1 instanceof ObservableValue) {
            ((ObservableValue) property1).removeListener(binding);
        }
        if (property2 instanceof Observable) {
            ((ObservableValue) property2).removeListener(binding);
        }
    }

    private final int cachedHashCode;

    private BidirectionalBinding(Object property1, Object property2) {
        cachedHashCode = property1.hashCode() * property2.hashCode();
    }

    protected abstract Object getProperty1();

    protected abstract Object getProperty2();

    @Override
    public int hashCode() {
        return cachedHashCode;
    }

    @Override
    public boolean wasGarbageCollected() {
        return (getProperty1() == null) || (getProperty2() == null);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        final Object propertyA1 = getProperty1();
        final Object propertyA2 = getProperty2();
        if ((propertyA1 == null) || (propertyA2 == null)) {
            return false;
        }

        if (obj instanceof BidirectionalBinding) {
            final BidirectionalBinding otherBinding = (BidirectionalBinding) obj;
            final Object propertyB1 = otherBinding.getProperty1();
            final Object propertyB2 = otherBinding.getProperty2();
            if ((propertyB1 == null) || (propertyB2 == null)) {
                return false;
            }

            if (propertyA1 == propertyB1 && propertyA2 == propertyB2) {
                return true;
            }
            if (propertyA1 == propertyB2 && propertyA2 == propertyB1) {
                return true;
            }
        }
        return false;
    }

    private static class BidirectionalBooleanBinding extends BidirectionalBinding<Boolean> {
        private final BooleanProperty propertyRef1;
        private final BooleanProperty propertyRef2;
        private boolean updating = false;

        private BidirectionalBooleanBinding(BooleanProperty property1, BooleanProperty property2) {
            super(property1, property2);
            propertyRef1 = property1;
            propertyRef2 = property2;
        }

        @Override
        protected Property<Boolean> getProperty1() {
            return propertyRef1;
        }

        @Override
        protected Property<Boolean> getProperty2() {
            return propertyRef2;
        }

        @Override
        public void changed(ObservableValue<? extends Boolean> sourceProperty, Boolean oldValue, Boolean newValue) {
            if (!updating) {
                final BooleanProperty property1 = propertyRef1;
                final BooleanProperty property2 = propertyRef2;
                if ((property1 == null) || (property2 == null)) {
                    if (property1 != null) {
                        property1.removeListener(this);
                    }
                    if (property2 != null) {
                        property2.removeListener(this);
                    }
                } else {
                    try {
                        updating = true;
                        if (property1 == sourceProperty) {
                            property2.set(newValue);
                        } else {
                            property1.set(newValue);
                        }
                    } catch (RuntimeException e) {
                        if (property1 == sourceProperty) {
                            property1.set(oldValue);
                        } else {
                            property2.set(oldValue);
                        }
                        throw new RuntimeException(
                                "Bidirectional binding failed, setting to the previous value", e);
                    } finally {
                        updating = false;
                    }
                }
            }
        }
    }

    private static class BidirectionalDoubleBinding extends BidirectionalBinding<Number> {
        private final DoubleProperty propertyRef1;
        private final DoubleProperty propertyRef2;
        private boolean updating = false;

        private BidirectionalDoubleBinding(DoubleProperty property1, DoubleProperty property2) {
            super(property1, property2);
            propertyRef1 = property1;
            propertyRef2 = property2;
        }

        @Override
        protected Property<Number> getProperty1() {
            return propertyRef1;
        }

        @Override
        protected Property<Number> getProperty2() {
            return propertyRef2;
        }

        @Override
        public void changed(ObservableValue<? extends Number> sourceProperty, Number oldValue, Number newValue) {
            if (!updating) {
                final DoubleProperty property1 = propertyRef1;
                final DoubleProperty property2 = propertyRef2;
                if ((property1 == null) || (property2 == null)) {
                    if (property1 != null) {
                        property1.removeListener(this);
                    }
                    if (property2 != null) {
                        property2.removeListener(this);
                    }
                } else {
                    try {
                        updating = true;
                        if (property1 == sourceProperty) {
                            property2.set(newValue.doubleValue());
                        } else {
                            property1.set(newValue.doubleValue());
                        }
                    } catch (RuntimeException e) {
                        if (property1 == sourceProperty) {
                            property1.set(oldValue.doubleValue());
                        } else {
                            property2.set(oldValue.doubleValue());
                        }
                        throw new RuntimeException(
                                        "Bidirectional binding failed, setting to the previous value", e);
                    } finally {
                        updating = false;
                    }
                }
            }
        }
    }

    private static class BidirectionalFloatBinding extends BidirectionalBinding<Number> {
        private final FloatProperty propertyRef1;
        private final FloatProperty propertyRef2;
        private boolean updating = false;

        private BidirectionalFloatBinding(FloatProperty property1, FloatProperty property2) {
            super(property1, property2);
            propertyRef1 = property1;
            propertyRef2 = property2;
        }

        @Override
        protected Property<Number> getProperty1() {
            return propertyRef1;
        }

        @Override
        protected Property<Number> getProperty2() {
            return propertyRef2;
        }

        @Override
        public void changed(ObservableValue<? extends Number> sourceProperty, Number oldValue, Number newValue) {
            if (!updating) {
                final FloatProperty property1 = propertyRef1;
                final FloatProperty property2 = propertyRef2;
                if ((property1 == null) || (property2 == null)) {
                    if (property1 != null) {
                        property1.removeListener(this);
                    }
                    if (property2 != null) {
                        property2.removeListener(this);
                    }
                } else {
                    try {
                        updating = true;
                        if (property1 == sourceProperty) {
                            property2.set(newValue.floatValue());
                        } else {
                            property1.set(newValue.floatValue());
                        }
                    } catch (RuntimeException e) {
                        if (property1 == sourceProperty) {
                            property1.set(oldValue.floatValue());
                        } else {
                            property2.set(oldValue.floatValue());
                        }
                        throw new RuntimeException(
                                "Bidirectional binding failed, setting to the previous value", e);
                    } finally {
                        updating = false;
                    }
                }
            }
        }
    }

    private static class BidirectionalIntegerBinding extends BidirectionalBinding<Number>{
        private final IntegerProperty propertyRef1;
        private final IntegerProperty propertyRef2;
        private boolean updating = false;

        private BidirectionalIntegerBinding(IntegerProperty property1, IntegerProperty property2) {
            super(property1, property2);
            propertyRef1 = property1;
            propertyRef2 = property2;
        }

        @Override
        protected Property<Number> getProperty1() {
            return propertyRef1;
        }

        @Override
        protected Property<Number> getProperty2() {
            return propertyRef2;
        }

        @Override
        public void changed(ObservableValue<? extends Number> sourceProperty, Number oldValue, Number newValue) {
            if (!updating) {
                final IntegerProperty property1 = propertyRef1;
                final IntegerProperty property2 = propertyRef2;
                if ((property1 == null) || (property2 == null)) {
                    if (property1 != null) {
                        property1.removeListener(this);
                    }
                    if (property2 != null) {
                        property2.removeListener(this);
                    }
                } else {
                    try {
                        updating = true;
                        if (property1 == sourceProperty) {
                            property2.set(newValue.intValue());
                        } else {
                            property1.set(newValue.intValue());
                        }
                    } catch (RuntimeException e) {
                        if (property1 == sourceProperty) {
                            property1.set(oldValue.intValue());
                        } else {
                            property2.set(oldValue.intValue());
                        }
                        throw new RuntimeException(
                                        "Bidirectional binding failed, setting to the previous value", e);
                    } finally {
                        updating = false;
                    }
                }
            }
        }
    }

    private static class BidirectionalLongBinding extends BidirectionalBinding<Number> {
        private final LongProperty propertyRef1;
        private final LongProperty propertyRef2;
        private boolean updating = false;

        private BidirectionalLongBinding(LongProperty property1, LongProperty property2) {
            super(property1, property2);
            propertyRef1 = property1;
            propertyRef2 = property2;
        }

        @Override
        protected Property<Number> getProperty1() {
            return propertyRef1;
        }

        @Override
        protected Property<Number> getProperty2() {
            return propertyRef2;
        }

        @Override
        public void changed(ObservableValue<? extends Number> sourceProperty, Number oldValue, Number newValue) {
            if (!updating) {
                final LongProperty property1 = propertyRef1;
                final LongProperty property2 = propertyRef2;
                if ((property1 == null) || (property2 == null)) {
                    if (property1 != null) {
                        property1.removeListener(this);
                    }
                    if (property2 != null) {
                        property2.removeListener(this);
                    }
                } else {
                    try {
                        updating = true;
                        if (property1 == sourceProperty) {
                            property2.set(newValue.longValue());
                        } else {
                            property1.set(newValue.longValue());
                        }
                    } catch (RuntimeException e) {
                        if (property1 == sourceProperty) {
                            property1.set(oldValue.longValue());
                        } else {
                            property2.set(oldValue.longValue());
                        }
                        throw new RuntimeException(
                                "Bidirectional binding failed, setting to the previous value", e);
                    } finally {
                        updating = false;
                    }
                }
            }
        }
    }

    private static class TypedGenericBidirectionalBinding<T> extends BidirectionalBinding<T> {
        private final Property<T> propertyRef1;
        private final Property<T> propertyRef2;
        private boolean updating = false;

        private TypedGenericBidirectionalBinding(Property<T> property1, Property<T> property2) {
            super(property1, property2);
            propertyRef1 = property1;
            propertyRef2 = property2;
        }

        @Override
        protected Property<T> getProperty1() {
            return propertyRef1;
        }

        @Override
        protected Property<T> getProperty2() {
            return propertyRef2;
        }

        @Override
        public void changed(ObservableValue<? extends T> sourceProperty, T oldValue, T newValue) {
            if (!updating) {
                final Property<T> property1 = propertyRef1;
                final Property<T> property2 = propertyRef2;
                if ((property1 == null) || (property2 == null)) {
                    if (property1 != null) {
                        property1.removeListener(this);
                    }
                    if (property2 != null) {
                        property2.removeListener(this);
                    }
                } else {
                    try {
                        updating = true;
                        if (property1 == sourceProperty) {
                            property2.setValue(newValue);
                        } else {
                            property1.setValue(newValue);
                        }
                    } catch (RuntimeException e) {
                        if (property1 == sourceProperty) {
                            property1.setValue(oldValue);
                        } else {
                            property2.setValue(oldValue);
                        }
                        throw new RuntimeException(
                                "Bidirectional binding failed, setting to the previous value", e);
                    } finally {
                        updating = false;
                    }
                }
            }
        }
    }
    
    private static class TypedNumberBidirectionalBinding<T extends Number> extends BidirectionalBinding<Number> {
        private final Property<T> propertyRef1;
        private final Property<Number> propertyRef2;
        private boolean updating = false;

        private TypedNumberBidirectionalBinding(Property<T> property1, Property<Number> property2) {
            super(property1, property2);
            propertyRef1 = property1;
            propertyRef2 = property2;
        }

        @Override
        protected Property<T> getProperty1() {
            return propertyRef1;
        }

        @Override
        protected Property<Number> getProperty2() {
            return propertyRef2;
        }

        @Override
        public void changed(ObservableValue<? extends Number> sourceProperty, Number oldValue, Number newValue) {
            if (!updating) {
                final Property<T> property1 = propertyRef1;
                final Property<Number> property2 = propertyRef2;
                if ((property1 == null) || (property2 == null)) {
                    if (property1 != null) {
                        property1.removeListener(this);
                    }
                    if (property2 != null) {
                        property2.removeListener(this);
                    }
                } else {
                    try {
                        updating = true;
                        if (property1 == sourceProperty) {
                            property2.setValue(newValue);
                        } else {
                            property1.setValue((T)newValue);
                        }
                    } catch (RuntimeException e) {
                        if (property1 == sourceProperty) {
                            property1.setValue((T)oldValue);
                        } else {
                            property2.setValue(oldValue);
                        }
                        throw new RuntimeException(
                                        "Bidirectional binding failed, setting to the previous value", e);
                    } finally {
                        updating = false;
                    }
                }
            }
        }
    }

    private static class UntypedGenericBidirectionalBinding extends BidirectionalBinding<Object> {

        private final Object property1;
        private final Object property2;

        public UntypedGenericBidirectionalBinding(Object property1, Object property2) {
            super(property1, property2);
            this.property1 = property1;
            this.property2 = property2;
        }

        @Override
        protected Object getProperty1() {
            return property1;
        }

        @Override
        protected Object getProperty2() {
            return property2;
        }

        @Override
        public void changed(ObservableValue<? extends Object> sourceProperty, Object oldValue, Object newValue) {
            throw new RuntimeException("Should not reach here");
        }
    }

    public abstract static class StringConversionBidirectionalBinding<T> extends BidirectionalBinding<Object> {

        private final Property<String> stringPropertyRef;
        private final Property<T> otherPropertyRef;
        private boolean updating;

        public StringConversionBidirectionalBinding(Property<String> stringProperty, Property<T> otherProperty) {
            super(stringProperty, otherProperty);
            stringPropertyRef = stringProperty;
            otherPropertyRef = otherProperty;
        }

        protected abstract String toString(T value);

        protected abstract T fromString(String value) throws ParseException;

        @Override
        protected Object getProperty1() {
            return stringPropertyRef;
        }

        @Override
        protected Object getProperty2() {
            return otherPropertyRef;
        }

        @Override
        public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
            if (!updating) {
                final Property<String> property1 = stringPropertyRef;
                final Property<T> property2 = otherPropertyRef;
                if ((property1 == null) || (property2 == null)) {
                    if (property1 != null) {
                        property1.removeListener(this);
                    }
                    if (property2 != null) {
                        property2.removeListener(this);
                    }
                } else {
                    try {
                        updating = true;
                        if (property1 == observable) {
                            try {
                                property2.setValue(fromString(property1.getValue()));
                            } catch (Exception e) {
                                Logging.getLogger().warning("Exception while parsing String in bidirectional binding", e);
                                property2.setValue(null);
                            }
                        } else {
                            try {
                                property1.setValue(toString(property2.getValue()));
                            } catch (Exception e) {
                                Logging.getLogger().warning("Exception while converting Object to String in bidirectional binding", e);
                                property1.setValue("");
                            }
                        }
                    } finally {
                        updating = false;
                    }
                }
            }
        }
    }

}
