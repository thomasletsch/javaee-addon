package org.vaadin.addons.javaee.jpa;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;

import org.junit.Ignore;
import org.junit.Test;

import com.vaadin.data.Property;

public class EntityContainerPropertiesTest extends BasicEntityContainerTest {

    @Test
    public void testGetEntityClass() {
        assertEquals("entityClass", TestEntity.class, container.getEntityClass());
    }

    @Test
    public void testGetContainerPropertyIds() {
        Collection<?> propertyIds = container.getContainerPropertyIds();
        assertEquals("size", PROPERTIES_AMOUNT, propertyIds.size());
    }

    @Test
    public void testAddContainerProperty() {
        container.addContainerProperty("test", String.class, "");
        Collection<?> propertyIds = container.getContainerPropertyIds();
        assertEquals("size", PROPERTIES_AMOUNT + 1, propertyIds.size());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetContainerProperty() {
        TestEntity entity = createTestEntity();
        Property<String> containerProperty = (Property<String>) container.getContainerProperty(entity.getId(), TestEntity.TEST_PROPERTY);
        assertEquals("content", entity.getTestString(), containerProperty.getValue());
    }

    @Test
    public void testRemoveContainerProperty() {
        container.removeContainerProperty(TestEntity.TEST_PROPERTY);
        Collection<?> propertyIds = container.getContainerPropertyIds();
        assertEquals("size", PROPERTIES_AMOUNT - 1, propertyIds.size());
    }

    @Test
    public void testGetType() {
        assertEquals("type", String.class, container.getType(TestEntity.TEST_PROPERTY));
    }

    @Test
    @Ignore
    public void testGetCollectionType() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetPropertyNames() {
        List<String> propertyNames = container.getPropertyNames();
        assertEquals("size", PROPERTIES_AMOUNT, propertyNames.size());
    }

    @Test
    @Ignore
    public void testAddListenerPropertySetChangeListener() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testAddPropertySetChangeListener() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testRemovePropertySetChangeListener() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testRemoveListenerPropertySetChangeListener() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetAnnotation() {
        assertNotNull("Column", container.getAnnotation(TestEntity.TEST_PROPERTY, Column.class));
    }

}
