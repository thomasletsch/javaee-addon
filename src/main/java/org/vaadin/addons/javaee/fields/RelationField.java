package org.vaadin.addons.javaee.fields;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.ui.AbstractField;

public class RelationField extends AbstractField<PersistentEntity> {

    private static final long serialVersionUID = 1L;

    private Class<PersistentEntity> entityClass;

    public void setEntityClass(Class<PersistentEntity> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public Class<PersistentEntity> getType() {
        return entityClass;
    }

}
