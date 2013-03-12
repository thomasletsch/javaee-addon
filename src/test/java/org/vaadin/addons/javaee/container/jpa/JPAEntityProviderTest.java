/*******************************************************************************
 * Copyright 2012 Thomas Letsch
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *******************************************************************************/
package org.vaadin.addons.javaee.container.jpa;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.vaadin.addons.javaee.container.SortDefinition;
import org.vaadin.addons.javaee.container.jpa.JPAEntityProvider;

import com.vaadin.data.util.filter.SimpleStringFilter;

public class JPAEntityProviderTest extends BaseEntityTest {

    private ArrayList<SortDefinition> sortDefinitions = new ArrayList<SortDefinition>();

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
        List<TestEntity> list = provider.find(TestEntity.class, null, sortDefinitions);
        assertNotNull(list);
        assertEquals(1, list.size());
    }

    @Test
    public void testFindFiltered() {
        TestEntity first = createTestEntity();
        first.setTestString("First");
        JPAEntityProvider provider = new JPAEntityProvider();
        provider.entityManager = em;
        List<TestEntity> list = provider
                .find(TestEntity.class, new SimpleStringFilter("testString", "First", false, true), sortDefinitions);
        assertEquals(1, list.size());
    }

    @Test
    public void testFindFilteredOut() {
        TestEntity first = createTestEntity();
        first.setTestString("Last");
        JPAEntityProvider provider = new JPAEntityProvider();
        provider.entityManager = em;
        List<TestEntity> list = provider
                .find(TestEntity.class, new SimpleStringFilter("testString", "First", false, true), sortDefinitions);
        assertEquals(0, list.size());
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
