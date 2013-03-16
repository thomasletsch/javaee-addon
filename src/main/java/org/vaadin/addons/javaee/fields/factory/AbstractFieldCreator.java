package org.vaadin.addons.javaee.fields.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.addons.javaee.container.EntityContainer;
import org.vaadin.addons.javaee.fields.spec.FieldSpecification;
import org.wamblee.inject.InjectorBuilder;

import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.Reindeer;

@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class AbstractFieldCreator<FIELD extends Field> implements FieldCreator<FIELD> {

    private static Logger log = LoggerFactory.getLogger(AbstractFieldCreator.class);

    protected EntityContainer<?> container;

    protected FieldSpecification fieldSpec;

    protected abstract Class<FIELD> getDefaultFieldType();

    /**
     * To be overwritten.
     */
    @SuppressWarnings("unused")
    protected void initializeField(FIELD field) {
    }

    @Override
    public FIELD createField() {
        FIELD field = instanciateField();
        commonFieldInit(field);
        initializeField(field);
        return field;
    }

    protected void commonFieldInit(FIELD field) {
        // If we could easily change the location of the label to left side, we could avoid having a separate label.
        field.setCaption(null);
        field.setBuffered(true);
        field.setStyleName(Reindeer.TEXTFIELD_SMALL);
        field.setId(container.getEntityClass().getSimpleName() + "." + fieldSpec.getName());
        InjectorBuilder.getInjector().inject(field);
    }

    protected FIELD instanciateField() {
        try {
            FIELD field = getFieldType().newInstance();
            return field;
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("Could not instanciate " + getFieldType(), e);
        }
        return (FIELD) new TextField();
    }

    protected Class<FIELD> getFieldType() {
        if (fieldSpec.getFieldType() == null) {
            return getDefaultFieldType();
        }
        return (Class<FIELD>) fieldSpec.getFieldType();
    }

    public void setContainer(EntityContainer<?> container) {
        this.container = container;
    }

    public void setFieldSpec(FieldSpecification fieldSpec) {
        this.fieldSpec = fieldSpec;
    }
}