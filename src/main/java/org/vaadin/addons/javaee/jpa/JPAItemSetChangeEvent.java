package org.vaadin.addons.javaee.jpa;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.data.Container;
import com.vaadin.data.Container.ItemSetChangeEvent;


public class JPAItemSetChangeEvent<ENTITY extends PersistentEntity> implements ItemSetChangeEvent {

    private final EntityContainer<ENTITY> jpaContainer;

    public JPAItemSetChangeEvent(EntityContainer<ENTITY> jpaContainer) {
        this.jpaContainer = jpaContainer;
    }

    @Override
    public Container getContainer() {
        return jpaContainer;
    }

}
