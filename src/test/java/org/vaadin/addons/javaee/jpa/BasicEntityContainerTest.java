package org.vaadin.addons.javaee.jpa;

import org.junit.Before;
import org.vaadin.addons.javaee.container.AbstractEntityContainer;

public class BasicEntityContainerTest extends BaseEntityTest {

    protected static final int PROPERTIES_AMOUNT = 7;

    protected AbstractEntityContainer<TestEntity> container;

    @Before
    public void startUp() {
        JPAEntityProvider provider = new JPAEntityProvider();
        provider.entityManager = em;
        container = new JPAEntityContainer<>(TestEntity.class, provider);
    }

}