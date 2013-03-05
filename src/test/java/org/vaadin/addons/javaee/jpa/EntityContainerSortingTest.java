package org.vaadin.addons.javaee.jpa;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class EntityContainerSortingTest extends BasicEntityContainerTest {

    @Test
    public void testGetItemIds() {
        TestEntity entity = createTestEntity();
        Collection<?> itemIds = container.getItemIds();
        assertEquals("size", 1, itemIds.size());
        assertEquals("id", entity.getId(), itemIds.iterator().next());
    }

    @Test
    public void testSize() {
        createTestEntity();
        assertEquals("size", 1, container.size());
    }

    @Test
    public void testContainsId() {
        TestEntity entity = createTestEntity();
        assertTrue("contains", container.containsId(entity.getId()));
    }

    @Test
    public void testFirstItemId() {
        fail("Not yet implemented");
    }

    @Test
    public void testLastItemId() {
        fail("Not yet implemented");
    }

    @Test
    public void testNextItemId() {
        fail("Not yet implemented");
    }

    @Test
    public void testPrevItemId() {
        fail("Not yet implemented");
    }

    @Test
    public void testIsFirstId() {
        fail("Not yet implemented");
    }

    @Test
    public void testIsLastId() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetSortableContainerPropertyIds() {
        fail("Not yet implemented");
    }

    @Override
    @Before
    public void startUp() {
        JPAEntityProvider provider = new JPAEntityProvider();
        provider.entityManager = em;
        container = new JPAEntityContainer<>(TestEntity.class, provider);
    }

}
