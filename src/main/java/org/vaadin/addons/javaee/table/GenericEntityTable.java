package org.vaadin.addons.javaee.table;

import org.vaadin.addons.javaee.jpa.EntityContainer;

import com.googlecode.javaeeutils.jpa.PersistentEntity;

public class GenericEntityTable<ENTITY extends PersistentEntity> extends BasicEntityTable<ENTITY> {

    private EntityContainer<ENTITY> entityContainer;

    public GenericEntityTable(Class<ENTITY> entityClass) {
        super(entityClass);
    }

    @Override
    public EntityContainer<ENTITY> getContainer() {
        return entityContainer;
    }

    public void setContainer(EntityContainer<ENTITY> entityContainer) {
        this.entityContainer = entityContainer;
    }

}
