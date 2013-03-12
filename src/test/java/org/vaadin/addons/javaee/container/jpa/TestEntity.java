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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

import com.googlecode.javaeeutils.jpa.AuditableEntity;

@Entity
@XmlRootElement
public class TestEntity extends AuditableEntity {

    @Column(name = "TEST_STRING")
    private String testString;

    public static final String ORIGINAL_TEST_STRING = "test";

    public static final String UPDATED_TEST_STRING = "updated";

    protected static final String TEST_PROPERTY = "testString";

    public TestEntity() {
    }

    public TestEntity(String testString) {
        this.setTestString(testString);
    }

    public TestEntity(Long id, String testString) {
        this.setTestString(testString);
        setId(id);
    }

    public String getTestString() {
        return testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }

}
