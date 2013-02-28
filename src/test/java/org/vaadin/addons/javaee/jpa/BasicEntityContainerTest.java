package org.vaadin.addons.javaee.jpa;

import org.junit.Before;

public class BasicEntityContainerTest extends BaseEntityTest {

    protected static final int PROPERTIES_AMOUNT = 7;

    protected EntityContainer<TestEntity> container;

    @Before
    public void startUp() {
        JPAEntityProvider provider = new JPAEntityProvider();
        provider.entityManager = em;
        container = new EntityContainer<>(TestEntity.class, provider);
    }

}