package org.vaadin.addons.javaee.jpa;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.vaadin.addons.javaee.container.EntityContainer;

import com.googlecode.javaeeutils.jpa.PersistentEntity;

public class EntityContainerTest extends BasicEntityContainerTest {

    @Test
    public void testGetSubContainer() {
        String propertyId = "entityClass";
        EntityContainer<PersistentEntity> subContainer = container.getSubContainer(propertyId);
        assertEquals(propertyId, PersistentEntity.class, subContainer.getEntityClass());
    }

}
