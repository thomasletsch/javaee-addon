package org.vaadin.addons.javaee.jpa;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.javaeeutils.jpa.manager.EntityManagerHelper;
import org.junit.After;
import org.junit.Before;

public class BaseEntityTest {

    private EntityManagerFactory emFactory;

    protected EntityManager em;

    protected TestEntity createTestEntity() {
        TestEntity testEntity = new TestEntity("test");
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
