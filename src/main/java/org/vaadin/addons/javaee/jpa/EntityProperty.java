package org.vaadin.addons.javaee.jpa;

import jodd.bean.BeanUtil;

import com.googlecode.javaeeutils.reflection.ReflectionUtils;
import com.vaadin.data.Property;
import com.vaadin.data.util.AbstractProperty;

public class EntityProperty<T> extends AbstractProperty<T> {

    private static final long serialVersionUID = 1L;

    private String propertyName;

    /**
     * Bean instance used as a starting point for accessing the property value.
     */
    private Object instance;

    /**
     * Constructs a nested method property for a given object instance. The property name is a dot separated string pointing to a nested
     * property, e.g. "manager.address.street".
     * 
     * @param instance
     *            top-level bean to which the property applies
     * @param propertyName
     *            dot separated nested property name
     * @throws IllegalArgumentException
     *             if the property name is invalid
     */
    public EntityProperty(Object instance, String propertyName) {
        this.instance = instance;
        this.propertyName = propertyName;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<? extends T> getType() {
        Class<?> beanClass = instance.getClass();
        return (Class<? extends T>) ReflectionUtils.getType(beanClass, propertyName);
    }

    @Override
    public boolean isReadOnly() {
        return super.isReadOnly();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getValue() {
        return (T) BeanUtil.getPropertySilently(instance, propertyName);
    }

    /**
     * Sets the value of the property. The new value must be assignable to the type of this property.
     * 
     * @param newValue
     *            the New value of the property.
     * @throws <code>Property.ReadOnlyException</code> if the object is in read-only mode.
     * @see #invokeSetMethod(Object)
     */
    @Override
    public void setValue(T newValue) throws ReadOnlyException {
        // Checks the mode
        if (isReadOnly()) {
            throw new Property.ReadOnlyException();
        }
        BeanUtil.setPropertyForced(instance, propertyName, newValue);

        fireValueChange();
    }

}
