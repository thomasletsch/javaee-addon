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
package org.vaadin.addons.javaee.cdi;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.addons.javaee.form.FormSection;

@SessionScoped
public class ComponentProducer implements Serializable {

    private static final long serialVersionUID = 1L;

    private static Logger log = LoggerFactory.getLogger(ComponentProducer.class);

    @Produces
    @InjectedComponent
    public FormSection createFormSection(final InjectionPoint ip) {
        FormSection section;
        InjectedComponent annotation = ip.getAnnotated().getAnnotation(InjectedComponent.class);
        String name = annotation.name();
        if (ip.getType() instanceof Class) {
            Class<?> typeClass = (Class<?>) ip.getType();
            try {
                section = (FormSection) typeClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                log.error("Could not create " + typeClass, e);
                section = new FormSection();
            }
        } else {
            section = new FormSection();
        }
        section.setName(name);
        return section;
    }
}
