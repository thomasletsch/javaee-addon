package org.vaadin.addons.javaee.fields.factory;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.vaadin.addons.javaee.container.EntityContainer;
import org.vaadin.addons.javaee.fields.spec.FieldSpecification;
import org.vaadin.addons.javaee.jpa.JPAEntityContainer;

import com.vaadin.data.Container;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.TableFieldFactory;

@Singleton
public class DefaultEntityTableFieldFactory implements TableFieldFactory {

    private static final long serialVersionUID = 1L;

    @Inject
    private GlobalFieldFactory entityFieldFactory;

    private TableFieldFactory defaultTableFieldFactory = DefaultFieldFactory.get();

    @Override
    public Field<?> createField(Container container, Object itemId, Object propertyId, Component uiContext) {
        if (container instanceof EntityContainer<?>) {
            JPAEntityContainer<?> entityContainer = (JPAEntityContainer<?>) container;
            FieldSpecification fieldSpec = new FieldSpecification((String) propertyId);
            return entityFieldFactory.createField(entityContainer, fieldSpec);
        }
        return defaultTableFieldFactory.createField(container, itemId, propertyId, uiContext);
    }

}
