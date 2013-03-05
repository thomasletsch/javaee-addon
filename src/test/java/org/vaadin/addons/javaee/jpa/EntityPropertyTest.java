package org.vaadin.addons.javaee.jpa;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.vaadin.addons.javaee.container.EntityProperty;

public class EntityPropertyTest {

    private TestEntity testEntity;

    private TestNestedEntity nestedEntity;

    private EntityProperty<String> initializedEntityProperty;

    private EntityProperty<String> testEntityProperty;

    private EntityProperty<Object> uninitializedEntityProperty;

    @Before
    public void setUp() {
        testEntity = new TestEntity(TestEntity.ORIGINAL_TEST_STRING);
        nestedEntity = new TestNestedEntity();
        nestedEntity.setInitializedTestEntity(testEntity);
        testEntityProperty = new EntityProperty<>(testEntity, TestEntity.TEST_PROPERTY);
        initializedEntityProperty = new EntityProperty<>(nestedEntity, TestNestedEntity.INITIALIZED_PROPERTY + "."
                + TestEntity.TEST_PROPERTY);
        uninitializedEntityProperty = new EntityProperty<>(nestedEntity, TestNestedEntity.UNINITIALIZED_PROPERTY + "."
                + TestEntity.TEST_PROPERTY);
    }

    @Test
    public void testGetType() {
        assertEquals(String.class, testEntityProperty.getType());
    }

    @Test
    public void testGetTypeComplex() {
        EntityProperty<Object> entityProperty = new EntityProperty<>(nestedEntity, TestNestedEntity.UNINITIALIZED_PROPERTY);
        assertEquals(TestEntity.class, entityProperty.getType());
    }

    @Test
    public void testGetTypeNested() {
        assertEquals(String.class, initializedEntityProperty.getType());
    }

    @Test
    public void testGetTypeNestedUninitialized() {
        assertEquals(String.class, uninitializedEntityProperty.getType());
    }

    @Test
    public void testIsReadonly() {
        assertEquals(false, testEntityProperty.isReadOnly());
    }

    @Test
    public void testIsReadonlyNested() {
        assertEquals(false, initializedEntityProperty.isReadOnly());
    }

    @Test
    public void testIsReadonlyNestedUninitialized() {
        assertEquals(false, uninitializedEntityProperty.isReadOnly());
    }

    @Test
    public void testGetValue() {
        assertEquals(TestEntity.ORIGINAL_TEST_STRING, testEntityProperty.getValue());
    }

    @Test
    public void testGetValueNested() {
        assertEquals(TestEntity.ORIGINAL_TEST_STRING, initializedEntityProperty.getValue());
    }

    @Test
    public void testGetValueNestedUninitialized() {
        assertNull(uninitializedEntityProperty.getValue());
    }

    @Test
    public void testSetValue() {
        testEntityProperty.setValue(TestEntity.UPDATED_TEST_STRING);
        assertEquals(TestEntity.UPDATED_TEST_STRING, testEntity.getTestString());
    }

    @Test
    public void testSetValueNested() {
        initializedEntityProperty.setValue(TestEntity.UPDATED_TEST_STRING);
        assertEquals(TestEntity.UPDATED_TEST_STRING, testEntity.getTestString());
    }

    @Test
    public void testSetValueNestedUninitialized() {
        uninitializedEntityProperty.setValue(TestEntity.UPDATED_TEST_STRING);
        assertEquals(TestEntity.UPDATED_TEST_STRING, nestedEntity.getUninitializedEntity().getTestString());
    }

}
