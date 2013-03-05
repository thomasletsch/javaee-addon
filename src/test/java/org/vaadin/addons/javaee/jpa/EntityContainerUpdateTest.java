package org.vaadin.addons.javaee.jpa;

import static org.junit.Assert.*;

import org.junit.Test;
import org.vaadin.addons.javaee.container.EntityItem;

import com.vaadin.data.Property;

public class EntityContainerUpdateTest extends BasicEntityContainerTest {

    @Test
    @SuppressWarnings("unchecked")
    public void testUpdateItem() {
        TestEntity entity = createTestEntity();
        EntityItem<TestEntity> item = container.getItem(entity.getId());
        Property<String> itemProperty = (Property<String>) item.getItemProperty(TestEntity.TEST_PROPERTY);
        itemProperty.setValue(TestEntity.UPDATED_TEST_STRING);
        container.updateItem(item);
        assertEquals(TestEntity.TEST_PROPERTY, TestEntity.UPDATED_TEST_STRING, entity.getTestString());
    }

    @Test
    public void testRemoveItem() {
        TestEntity entity = createTestEntity();
        container.removeItem(entity.getId());
        assertFalse("contains", container.containsId(entity.getId()));
    }

    @Test
    public void testRemoveAllItems() {
        createTestEntity();
        container.removeAllItems();
        assertTrue("contains", container.getItemIds().isEmpty());
    }

    @Test
    public void testAddItem() {
        Long newItemId = (Long) container.addItem();
        assertNotNull("id", newItemId);
        assertTrue("contains", container.containsId(newItemId));
    }

    @Test
    public void testAddItemENTITY() {
        TestEntity testEntity = new TestEntity("test");
        EntityItem<TestEntity> newItem = container.addItem(testEntity);
        assertNotNull("newItem", newItem);
        assertTrue("contains", container.containsId(newItem.getEntity().getId()));
    }

}
