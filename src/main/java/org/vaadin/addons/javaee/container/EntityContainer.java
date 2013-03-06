package org.vaadin.addons.javaee.container;

import java.lang.annotation.Annotation;
import java.util.List;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.data.Container;

public interface EntityContainer<ENTITY extends PersistentEntity> extends Container, Container.Ordered, Container.Filterable,
        Container.Sortable, Container.ItemSetChangeNotifier, Container.PropertySetChangeNotifier {

    <SUB_ENTITY extends PersistentEntity> EntityContainer<SUB_ENTITY> getSubContainer(String propertyId);

    Class<ENTITY> getEntityClass();

    void updateItem(EntityItem<ENTITY> item);

    void refreshItem(EntityItem<ENTITY> item);

    void loadItemWithRelations(EntityItem<ENTITY> item);

    EntityItem<ENTITY> getItem(Long itemId);

    EntityItem<ENTITY> addItem(ENTITY entity);

    Class<?> getCollectionType(String propertyId);

    List<String> getPropertyNames();

    <T extends Annotation> T getAnnotation(String fieldName, Class<T> annotationClass);

    void needsFiltering();

    List<ENTITY> findAllEntities();

}