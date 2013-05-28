/*******************************************************************************
 * Copyright 2013 Thomas Letsch (contact@thomas-letsch.de)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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

    void refreshCache();

    void clear();

    void refreshEntity(Long id);

}
