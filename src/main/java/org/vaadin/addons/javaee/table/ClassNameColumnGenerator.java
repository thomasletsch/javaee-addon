package org.vaadin.addons.javaee.table;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.vaadin.addons.javaee.i18n.TranslationService;
import org.vaadin.addons.javaee.jpa.EntityItem;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

@Singleton
public class ClassNameColumnGenerator implements Table.ColumnGenerator {

    @Inject
    private TranslationService translationService;

    @Override
    public Component generateCell(Table source, Object itemId, Object columnId) {
        EntityItem<?> item = (EntityItem<?>) source.getItem(itemId);
        String name = item.getBean().getClass().getSimpleName();
        Label field = new Label();
        field.setValue(translationService.get(name));
        return field;
    }

}
