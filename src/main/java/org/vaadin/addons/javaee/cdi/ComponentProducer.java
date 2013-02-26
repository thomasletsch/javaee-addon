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
