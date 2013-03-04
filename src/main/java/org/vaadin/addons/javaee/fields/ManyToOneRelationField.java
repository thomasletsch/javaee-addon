package org.vaadin.addons.javaee.fields;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.data.Property;
import com.vaadin.ui.ComboBox;

public class ManyToOneRelationField<ENTITY extends PersistentEntity> extends ComboBox {

    private static final long serialVersionUID = 1L;

    public ManyToOneRelationField() {
    }

    @Override
    protected void setValue(Object newValue, boolean repaintIsNotNeeded) throws Property.ReadOnlyException {
        if (newValue instanceof PersistentEntity) {
            PersistentEntity persistentEntity = (PersistentEntity) newValue;
            super.setValue(persistentEntity, repaintIsNotNeeded);
        } else {
            super.setValue(newValue, repaintIsNotNeeded);
        }
    }

}
