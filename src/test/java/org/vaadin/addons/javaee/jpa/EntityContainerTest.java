package org.vaadin.addons.javaee.jpa;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.googlecode.javaeeutils.jpa.PersistentEntity;

public class EntityContainerTest extends BasicEntityContainerTest {

    @Test
    public void testGetSubContainer() {
        EntityContainer<PersistentEntity> subContainer = container.getSubContainer(PersistentEntity.class);
        assertEquals("entityClass", PersistentEntity.class, subContainer.getEntityClass());
    }

}
