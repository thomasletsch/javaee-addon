package org.vaadin.addons.javaee.fields;

import org.vaadin.addons.javaee.container.EntityContainer;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.data.Property;
import com.vaadin.ui.ComboBox;

public class ManyToOneRelationField<ENTITY extends PersistentEntity> extends ComboBox {

    private static final long serialVersionUID = 1L;

    public ManyToOneRelationField() {
    }

    @SuppressWarnings("unchecked")
    public ENTITY getSelectedEntity() {
        if (getValue() != null && getValue() instanceof Long && getContainerDataSource() instanceof EntityContainer) {
            Long id = (Long) getValue();
            EntityContainer<ENTITY> containerDataSource = (EntityContainer<ENTITY>) getContainerDataSource();
            return containerDataSource.getItem(id).getEntity();
        }
        return null;
    }

    @Override
    protected void setValue(Object newValue, boolean repaintIsNotNeeded) throws Property.ReadOnlyException {
        if (newValue instanceof PersistentEntity) {
            PersistentEntity persistentEntity = (PersistentEntity) newValue;
            super.setValue(persistentEntity.getId(), repaintIsNotNeeded);
        } else {
            super.setValue(newValue, repaintIsNotNeeded);
        }
    }

}
