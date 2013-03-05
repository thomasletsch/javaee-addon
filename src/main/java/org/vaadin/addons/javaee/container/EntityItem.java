/*******************************************************************************
 * Copyright 2012 Thomas Letsch
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *******************************************************************************/
package org.vaadin.addons.javaee.container;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.data.Buffered;
import com.vaadin.data.Property;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.MethodPropertyDescriptor;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.data.util.VaadinPropertyDescriptor;

public class EntityItem<ENTITY extends PersistentEntity> extends PropertysetItem implements Buffered {

    private static final long serialVersionUID = 1L;

    private final EntityContainer<ENTITY> entityContainer;

    private ENTITY entity;

    public EntityItem(EntityContainer<ENTITY> entityContainer, ENTITY entity) {
        this.entityContainer = entityContainer;
        this.setEntity(entity);
        addPropertyDescriptors(entity);
    }

    @Override
    public Property<?> getItemProperty(Object id) {
        Property<?> itemProperty = super.getItemProperty(id);
        if (itemProperty == null) {
            addNestedItem(id);
            itemProperty = super.getItemProperty(id);
        }
        return itemProperty;
    }

    /**
     * Adds a nested property to the item.
     * 
     * @param nestedPropertyId
     *            property id to add. This property must not exist in the item already and must of of form "field1.field2" where field2 is a
     *            field in the object referenced to by field1
     */
    public void addNestedProperty(String nestedPropertyId) {
        addItemProperty(nestedPropertyId, new EntityProperty<Object>(getEntity(), nestedPropertyId));
    }

    private void addNestedItem(Object id) {
        if (id instanceof String) {
            String nestedId = (String) id;
            if (nestedId.contains(".")) {
                addNestedProperty(nestedId);
            }
        }
    }

    @Override
    public void commit() throws SourceException, InvalidValueException {
        if (getEntity().getId() == null) {
            EntityItem<ENTITY> addedItem = entityContainer.addItem(getEntity());
            setEntity(addedItem.getEntity());
        } else {
            entityContainer.updateItem(this);
        }
    }

    @Override
    public void discard() throws SourceException {
        entityContainer.refreshItem(this);
    }

    @Override
    public void setBuffered(boolean buffered) {
    }

    @Override
    public boolean isBuffered() {
        return true;
    }

    @Override
    public boolean isModified() {
        return false;
    }

    /**
     * <p>
     * Perform introspection on a Java Bean class to find its properties.
     * </p>
     * 
     * <p>
     * Note : This version only supports introspectable bean properties and their getter and setter methods. Stand-alone <code>is</code> and
     * <code>are</code> methods are not supported.
     * </p>
     * 
     * @param beanClass
     *            the Java Bean class to get properties for.
     * @return an ordered map from property names to property descriptors
     */
    static <ENTITY> LinkedHashMap<String, VaadinPropertyDescriptor<ENTITY>> getPropertyDescriptors(final Class<ENTITY> beanClass) {
        final LinkedHashMap<String, VaadinPropertyDescriptor<ENTITY>> pdMap = new LinkedHashMap<String, VaadinPropertyDescriptor<ENTITY>>();

        // Try to introspect, if it fails, we just have an empty Item
        try {
            List<PropertyDescriptor> propertyDescriptors = getBeanPropertyDescriptor(beanClass);

            // Add all the bean properties as MethodProperties to this Item
            // later entries on the list overwrite earlier ones
            for (PropertyDescriptor pd : propertyDescriptors) {
                final Method getMethod = pd.getReadMethod();
                if ((getMethod != null) && getMethod.getDeclaringClass() != Object.class) {
                    VaadinPropertyDescriptor<ENTITY> vaadinPropertyDescriptor = new MethodPropertyDescriptor<ENTITY>(pd.getName(),
                            pd.getPropertyType(), pd.getReadMethod(), pd.getWriteMethod());
                    pdMap.put(pd.getName(), vaadinPropertyDescriptor);
                }
            }
        } catch (final java.beans.IntrospectionException ignored) {
        }

        return pdMap;
    }

    /**
     * Returns the property descriptors of a class or an interface.
     * 
     * For an interface, superinterfaces are also iterated as Introspector does not take them into account (Oracle Java bug 4275879), but in
     * that case, both the setter and the getter for a property must be in the same interface and should not be overridden in subinterfaces
     * for the discovery to work correctly.
     * 
     * For interfaces, the iteration is depth first and the properties of superinterfaces are returned before those of their subinterfaces.
     * 
     * @param beanClass
     * @return
     * @throws IntrospectionException
     */
    private static List<PropertyDescriptor> getBeanPropertyDescriptor(final Class<?> beanClass) throws IntrospectionException {
        // Oracle bug 4275879: Introspector does not consider superinterfaces of
        // an interface
        if (beanClass.isInterface()) {
            List<PropertyDescriptor> propertyDescriptors = new ArrayList<PropertyDescriptor>();

            for (Class<?> cls : beanClass.getInterfaces()) {
                propertyDescriptors.addAll(getBeanPropertyDescriptor(cls));
            }

            BeanInfo info = Introspector.getBeanInfo(beanClass);
            propertyDescriptors.addAll(Arrays.asList(info.getPropertyDescriptors()));

            return propertyDescriptors;
        }
        BeanInfo info = Introspector.getBeanInfo(beanClass);
        return Arrays.asList(info.getPropertyDescriptors());
    }

    public ENTITY getEntity() {
        return entity;
    }

    public void setEntity(ENTITY entity) {
        this.entity = entity;
        for (Object id : new LinkedList<>(getItemPropertyIds())) {
            removeItemProperty(id);
        }
        addPropertyDescriptors(entity);
    }

    @SuppressWarnings("unchecked")
    private void addPropertyDescriptors(ENTITY entity) {
        for (VaadinPropertyDescriptor<ENTITY> pd : getPropertyDescriptors((Class<ENTITY>) entity.getClass()).values()) {
            addItemProperty(pd.getName(), pd.createProperty(entity));
        }
    }

}
