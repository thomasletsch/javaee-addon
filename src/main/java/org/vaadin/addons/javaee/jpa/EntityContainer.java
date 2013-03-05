package org.vaadin.addons.javaee.jpa;

import java.lang.annotation.Annotation;
import java.util.List;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.data.Container;

public interface EntityContainer<ENTITY extends PersistentEntity> extends Container, Container.Ordered, Container.Filterable,
        Container.Sortable, Container.ItemSetChangeNotifier, Container.PropertySetChangeNotifier {

    abstract <SUB_ENTITY extends PersistentEntity> EntityContainer<SUB_ENTITY> getSubContainer(Class<SUB_ENTITY> entityClass);

    abstract Class<ENTITY> getEntityClass();

    abstract void updateItem(EntityItem<ENTITY> item);

    abstract void refreshItem(EntityItem<ENTITY> item);

    abstract void loadItemWithRelations(EntityItem<ENTITY> item);

    abstract EntityItem<ENTITY> getItem(Long itemId);

    abstract EntityItem<ENTITY> addItem(ENTITY entity);

    abstract Class<?> getCollectionType(String propertyId);

    abstract List<String> getPropertyNames();

    abstract <T extends Annotation> T getAnnotation(String fieldName, Class<T> annotationClass);

    abstract void needsFiltering();

    abstract List<ENTITY> findAllEntities();

}