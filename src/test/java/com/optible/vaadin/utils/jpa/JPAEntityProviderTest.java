package com.optible.vaadin.utils.jpa;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.vaadin.data.util.filter.SimpleStringFilter;

public class JPAEntityProviderTest extends BaseEntityTest {

    @Test
    public void testGet() {
        TestEntity entity = createTestEntity();
        JPAEntityProvider provider = new JPAEntityProvider();
        provider.entityManager = em;
        TestEntity savedTestEntity = provider.get(TestEntity.class, entity.getId());
        assertNotNull(savedTestEntity);
    }

    @Test
    public void testFind() {
        createTestEntity();
        JPAEntityProvider provider = new JPAEntityProvider();
        provider.entityManager = em;
        List<TestEntity> list = provider.find(TestEntity.class, null);
        assertNotNull(list);
        assertEquals(1, list.size());
    }

    @Test
    public void testFindFiltered() {
        TestEntity first = createTestEntity();
        first.setTestString("First");
        JPAEntityProvider provider = new JPAEntityProvider();
        provider.entityManager = em;
        List<TestEntity> list = provider.find(TestEntity.class, new SimpleStringFilter("testString", "First", false, true));
        assertEquals(1, list.size());
    }

    @Test
    public void testFindFilteredOut() {
        TestEntity first = createTestEntity();
        first.setTestString("Last");
        JPAEntityProvider provider = new JPAEntityProvider();
        provider.entityManager = em;
        List<TestEntity> list = provider.find(TestEntity.class, new SimpleStringFilter("testString", "First", false, true));
        assertEquals(0, list.size());
    }

    @Test
    public void testGetFirst() {
        TestEntity first = createTestEntity();
        first.setTestString("First");
        TestEntity last = createTestEntity();
        last.setTestString("Last");
        JPAEntityProvider provider = new JPAEntityProvider();
        provider.entityManager = em;
        TestEntity entity = provider.getFirst(TestEntity.class, null);
        assertNotNull(entity);
        assertEquals(first, entity);
    }

    @Test
    public void testGetLast() {
        TestEntity first = createTestEntity();
        first.setTestString("First");
        TestEntity last = createTestEntity();
        last.setTestString("Last");
        JPAEntityProvider provider = new JPAEntityProvider();
        provider.entityManager = em;
        TestEntity entity = provider.getLast(TestEntity.class, null);
        assertNotNull(entity);
        assertEquals(last, entity);
    }

    @Test
    public void testGetNext() {
        TestEntity first = createTestEntity();
        first.setTestString("First");
        TestEntity middle = createTestEntity();
        middle.setTestString("Middle");
        TestEntity last = createTestEntity();
        last.setTestString("Last");
        JPAEntityProvider provider = new JPAEntityProvider();
        provider.entityManager = em;
        TestEntity entity = provider.getNext(TestEntity.class, first.getId(), null);
        assertNotNull(entity);
        assertEquals(middle, entity);
    }

    @Test
    public void testGetNextFiltered() {
        TestEntity first = createTestEntity();
        first.setTestString("First");
        TestEntity middle = createTestEntity();
        middle.setTestString("Middle");
        TestEntity last = createTestEntity();
        last.setTestString("First");
        JPAEntityProvider provider = new JPAEntityProvider();
        provider.entityManager = em;
        TestEntity entity = provider.getNext(TestEntity.class, first.getId(), new SimpleStringFilter("testString", "First", false, true));
        assertNotNull(entity);
        assertEquals(last, entity);
    }

    @Test
    public void testGetNextFromLast() {
        TestEntity first = createTestEntity();
        first.setTestString("First");
        TestEntity last = createTestEntity();
        last.setTestString("Last");
        JPAEntityProvider provider = new JPAEntityProvider();
        provider.entityManager = em;
        TestEntity entity = provider.getNext(TestEntity.class, last.getId(), null);
        assertNull(entity);
    }

    @Test
    public void testGetPrev() {
        TestEntity first = createTestEntity();
        first.setTestString("First");
        TestEntity middle = createTestEntity();
        middle.setTestString("Middle");
        TestEntity last = createTestEntity();
        last.setTestString("First");
        JPAEntityProvider provider = new JPAEntityProvider();
        provider.entityManager = em;
        TestEntity entity = provider.getPrev(TestEntity.class, last.getId(), new SimpleStringFilter("testString", "First", false, true));
        assertNotNull(entity);
        assertEquals(first, entity);
    }

    @Test
    public void testGetPrevFiltered() {
        TestEntity first = createTestEntity();
        first.setTestString("First");
        TestEntity middle = createTestEntity();
        middle.setTestString("Middle");
        TestEntity last = createTestEntity();
        last.setTestString("Last");
        JPAEntityProvider provider = new JPAEntityProvider();
        provider.entityManager = em;
        TestEntity entity = provider.getPrev(TestEntity.class, last.getId(), null);
        assertNotNull(entity);
        assertEquals(middle, entity);
    }

    @Test
    public void testGetPrevFromFirst() {
        TestEntity first = createTestEntity();
        first.setTestString("First");
        TestEntity last = createTestEntity();
        last.setTestString("Last");
        JPAEntityProvider provider = new JPAEntityProvider();
        provider.entityManager = em;
        TestEntity entity = provider.getPrev(TestEntity.class, first.getId(), null);
        assertNull(entity);
    }

    @Test
    public void testSize() {
        createTestEntity();
        JPAEntityProvider provider = new JPAEntityProvider();
        provider.entityManager = em;
        Long size = provider.size(TestEntity.class, null);
        assertEquals((Long) 1L, size);
    }

    @Test
    public void testSizeFiltered() {
        TestEntity first = createTestEntity();
        first.setTestString("First");
        TestEntity last = createTestEntity();
        last.setTestString("Last");
        JPAEntityProvider provider = new JPAEntityProvider();
        provider.entityManager = em;
        Long size = provider.size(TestEntity.class, new SimpleStringFilter("testString", "First", false, true));
        assertEquals((Long) 1L, size);
    }

}
