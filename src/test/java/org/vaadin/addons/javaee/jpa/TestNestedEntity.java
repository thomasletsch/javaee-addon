package org.vaadin.addons.javaee.jpa;

public class TestNestedEntity {

    public static final String UNINITIALIZED_PROPERTY = "uninitializedEntity";

    public static final String INITIALIZED_PROPERTY = "initializedTestEntity";

    private TestEntity uninitializedEntity;

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
