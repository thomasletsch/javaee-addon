package com.optible.vaadin.utils.jpa;

import javax.persistence.Entity;

import org.javaeeutils.jpa.AuditableEntity;


@Entity
public class TestEntity extends AuditableEntity {

    private String testString;

    public TestEntity() {
    }

    public TestEntity(String testString) {
        this.setTestString(testString);
    }

    public String getTestString() {
        return testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }

}
