package org.vaadin.addons.javaee.container.jpa;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.vaadin.addons.javaee.container.AbstractEntityContainer;
import org.vaadin.addons.javaee.container.EntityContainer;

import com.googlecode.javaeeutils.jpa.PersistentEntity;

public class EntityContainerTest extends BasicEntityContainerTest {

    private AbstractEntityContainer<TestNestedEntity> nestedContainer;

    @Test
    public void testGetSubContainer() {
        String propertyId = TestNestedEntity.INITIALIZED_PROPERTY;
        EntityContainer<PersistentEntity> subContainer = nestedContainer.getSubContainer(propertyId);
        assertEquals(propertyId, TestEntity.class, subContainer.getEntityClass());
    }

    @Override
    @Before
    public void startUp() {
        JPAEntityProvider provider = new JPAEntityProvider();
        provider.entityManager = em;
        nestedContainer = new JPAEntityContainer<>(TestNestedEntity.class, provider);
    }

}
