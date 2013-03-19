/*******************************************************************************
 * Copyright 2013 Thomas Letsch (contact@thomas-letsch.de)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.vaadin.addons.javaee.fields;


import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.googlecode.javaeeutils.reflection.ReflectionUtils;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.Label;

public class OneToOneRelationField<ENTITY extends PersistentEntity> extends CustomField<ENTITY> {

    private static final long serialVersionUID = 1L;

    private String captionProperty;

    private Label label;

    public OneToOneRelationField() {
    }

    public void setCaptionProperty(String captionProperty) {
        this.captionProperty = captionProperty;
    }

    @Override
    protected void setInternalValue(ENTITY newValue) {
        super.setInternalValue(newValue);
        Object value = ReflectionUtils.getValue(newValue, captionProperty);
        label.setValue((value == null) ? "" : value.toString());
    }

    @Override
    protected Component initContent() {
        label = new Label();
        return label;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends ENTITY> getType() {
        return (Class<? extends ENTITY>) PersistentEntity.class;
    }

}
