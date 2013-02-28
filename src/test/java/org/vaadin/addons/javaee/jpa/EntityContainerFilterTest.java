package org.vaadin.addons.javaee.jpa;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.vaadin.data.util.filter.Compare.Equal;

public class EntityContainerFilterTest extends BasicEntityContainerTest {

    private TestEntity entity3;

    private TestEntity entity2;

    private TestEntity entity1;

    @Test
    public void testRemoveAllItems() {
        createTestEntity();
        container.removeAllItems();
        assertTrue("contains", container.getItemIds().isEmpty());
    }

    @Test
    public void testGetItemIds() {
        Collection<?> itemIds = container.getItemIds();
        assertEquals("size", 2, itemIds.size());
        assertEquals("id", entity1.getId(), itemIds.iterator().next());
    }

    @Test
    public void testSize() {
        assertEquals("size", 2, container.size());
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
        assertEquals(entity3.getId(), container.nextItemId(entity1.getId()));
    }

    @Test
    public void testNextItemIdFiltered() {
        assertNull(container.nextItemId(entity2.getId()));
    }

    @Test
    public void testPrevItemId() {
        assertEquals(entity1.getId(), container.prevItemId(entity3.getId()));
    }

    @Test
    public void testPrevItemIdFiltered() {
        assertNull(container.prevItemId(entity2.getId()));
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

    @Test
    public void testAddContainerFilter() {
        container.addContainerFilter(new Equal(TEST_PROPERTY, UPDATED_TEST_STRING));
        assertEquals("size", 0, container.size());
    }

    @Test
    public void testRemoveContainerFilter() {
        Equal filter = new Equal(TEST_PROPERTY, UPDATED_TEST_STRING);
        container.addContainerFilter(filter);
        assertEquals("size", 0, container.size());
        container.removeContainerFilter(filter);
        assertEquals("size", 2, container.size());
    }

    @Test
    public void testRemoveAllContainerFilters() {
        container.removeAllContainerFilters();
        assertEquals("size", 3, container.size());
    }

    @Test
    @Ignore
    public void testNeedsFiltering() {
        fail("Not yet implemented");
    }

    @Before
    public void createEntities() {
        entity1 = createTestEntity();
        entity2 = createTestEntity();
        entity2.setTestString(UPDATED_TEST_STRING);
        entity3 = createTestEntity();
        container.addContainerFilter(new Equal(TEST_PROPERTY, ORIGINAL_TEST_STRING));
    }
}
