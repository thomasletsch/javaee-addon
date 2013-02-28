package org.vaadin.addons.javaee.jpa;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

public class EntityContainerRetrievalTest extends BasicEntityContainerTest {

    private TestEntity entity3;

    private TestEntity entity2;

    private TestEntity entity1;

    @Test
    public void testGetItemLong() {
        EntityItem<TestEntity> item = container.getItem(entity1.getId());
        assertNotNull("item", item);
        assertEquals("entity", entity1, item.getEntity());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetItemObject() {
        EntityItem<TestEntity> item = (EntityItem<TestEntity>) container.getItem((Object) entity1.getId());
        assertNotNull("item", item);
        assertEquals("entity", entity1, item.getEntity());
    }

    @Test
    public void testGetItemIds() {
        Collection<?> itemIds = container.getItemIds();
        assertEquals("size", 3, itemIds.size());
        assertEquals("id", entity1.getId(), itemIds.iterator().next());
    }

    @Test
    public void testSize() {
        assertEquals("size", 3, container.size());
    }

    @Test
    public void testContainsId() {
        assertTrue("contains", container.containsId(entity1.getId()));
    }

    @Test
    public void testContainsIdFalse() {
        assertFalse("contains", container.containsId(entity1.getId() + 10));
    }

    @Test
    public void testFirstItemId() {
        assertEquals(entity1.getId(), container.firstItemId());
    }

    @Test
    public void testLastItemId() {
        assertEquals(entity3.getId(), container.lastItemId());
    }

    @Test
    public void testNextItemId() {
        assertEquals(entity2.getId(), container.nextItemId(entity1.getId()));
    }

    @Test
    public void testNextItemIdFromLast() {
        assertNull(container.nextItemId(entity3.getId()));
    }

    @Test
    public void testPrevItemId() {
        assertEquals(entity1.getId(), container.prevItemId(entity2.getId()));
    }

    @Test
    public void testIsFirstId() {
        assertTrue(container.isFirstId(entity1.getId()));
    }

    @Test
    public void testIsFirstIdFalse() {
        assertFalse(container.isFirstId(entity2.getId()));
    }

    @Test
    public void testIsLastId() {
        assertTrue(container.isLastId(entity3.getId()));
    }

    @Test
    public void testIsLastIdFalse() {
        assertFalse(container.isLastId(entity2.getId()));
    }

    @Before
    public void createEntities() {
        entity1 = createTestEntity();
        entity2 = createTestEntity();
        entity3 = createTestEntity();

    }

}
