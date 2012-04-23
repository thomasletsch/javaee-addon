package org.vaadin.addons.javaee.jpa;

import org.javaeeutils.jpa.PersistentEntity;

import com.vaadin.data.Container;
import com.vaadin.data.Container.PropertySetChangeEvent;


public class JPAPropertySetChangeEvent<ENTITY extends PersistentEntity> implements PropertySetChangeEvent {

    private final EntityContainer<ENTITY> jpaContainer;

    public JPAPropertySetChangeEvent(EntityContainer<ENTITY> jpaContainer) {
        this.jpaContainer = jpaContainer;
    }

    @Override
    public Container getContainer() {
        return jpaContainer;
    }

}
