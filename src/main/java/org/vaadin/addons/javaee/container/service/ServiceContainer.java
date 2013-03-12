package org.vaadin.addons.javaee.container.service;

import javax.ejb.EJB;

import org.vaadin.addons.javaee.container.AbstractEntityContainer;
import org.vaadin.addons.javaee.container.EntityContainer;
import org.vaadin.addons.javaee.container.jpa.JPAEntityProvider;

import com.googlecode.javaeeutils.jpa.PersistentEntity;

public abstract class ServiceContainer<ENTITY extends PersistentEntity> extends AbstractEntityContainer<ENTITY> {

    private static final long serialVersionUID = 1L;

    @EJB
    private JPAEntityProvider jpaEntityProvider;

    /**
     * Must be overwritten for being usable
     */
    @Override
    public <SUB_ENTITY extends PersistentEntity> EntityContainer<SUB_ENTITY> getSubContainer(String propertyId) {
        return null;
    }

}
