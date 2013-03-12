package org.vaadin.addons.javaee.container.jpa;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.googlecode.javaeeutils.jpa.PersistentEntity;

@Entity
public class TestNestedEntity extends PersistentEntity {

    public static final String UNINITIALIZED_PROPERTY = "uninitializedEntity";

    public static final String INITIALIZED_PROPERTY = "initializedTestEntity";

    @ManyToOne
    private TestEntity uninitializedEntity;

    @ManyToOne
    private TestEntity initializedTestEntity;

    public TestEntity getUninitializedEntity() {
        return uninitializedEntity;
    }

    public void setUninitializedEntity(TestEntity uninitializedEntity) {
        this.uninitializedEntity = uninitializedEntity;
    }

    public TestEntity getInitializedTestEntity() {
        return initializedTestEntity;
    }

    public void setInitializedTestEntity(TestEntity initializedTestEntity) {
        this.initializedTestEntity = initializedTestEntity;
    }

}
