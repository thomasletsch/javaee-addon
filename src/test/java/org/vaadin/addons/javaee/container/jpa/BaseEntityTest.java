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

import static org.junit.Assert.fail;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;

import com.googlecode.javaeeutils.jpa.manager.EntityManagerHelper;

public class BaseEntityTest {

    private EntityManagerFactory emFactory;

    protected EntityManager em;

    protected TestEntity createTestEntity() {
        TestEntity testEntity = new TestEntity(TestEntity.ORIGINAL_TEST_STRING);
        em.persist(testEntity);
        em.flush();
        TestEntity savedEntity = testEntity.getSavedEntity(testEntity);
        return savedEntity;
    }

    @Before
    public void setUp() throws Exception {
        try {
            emFactory = Persistence.createEntityManagerFactory("JavaEEAddonTestUnit");
            em = emFactory.createEntityManager();
            EntityManagerHelper.put(em);
            em.getTransaction().begin();
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Exception during JPA EntityManager instanciation.");
        }
    }

    @After
    public void tearDown() throws Exception {
        em.flush();
        em.getTransaction().rollback();
        EntityManagerHelper.reset();
        if (em != null) {
            em.close();
        }
        if (emFactory != null) {
            emFactory.close();
        }
    }
}
