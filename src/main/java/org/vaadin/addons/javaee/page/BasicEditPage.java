package org.vaadin.addons.javaee.page;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.vaadin.addons.javaee.TranslationKeys;
import org.vaadin.addons.javaee.buttons.ButtonBar;
import org.vaadin.addons.javaee.buttons.CanHandleSaveButton;
import org.vaadin.addons.javaee.buttons.SaveButton;
import org.vaadin.addons.javaee.form.BasicEntityForm;
import org.vaadin.addons.javaee.i18n.TranslationService;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.ui.VerticalLayout;

public abstract class BasicEditPage<ENTITY extends PersistentEntity> extends PortalPagePanel implements CanHandleSaveButton {

    @Inject
    protected TranslationService translationService;

    private BasicEntityForm<ENTITY> form;

    private final String entityName;

    public BasicEditPage(String pageName) {
        this(pageName, pageName);
    }

    public BasicEditPage(String pageName, String entityName) {
        super(pageName);
        this.entityName = entityName;
    }

    @PostConstruct
    public void init() {
        VerticalLayout editPanel = new VerticalLayout();
        editPanel.setMargin(true);
        editPanel.setSpacing(true);
        editPanel.setCaption(translationService.get(TranslationKeys.TITLE_EDIT) + ": " + translationService.get(getEntityName()));
        form = getForm();
        if (form != null)
            editPanel.addComponent(form);
        ButtonBar buttonBar = initButtons();
        editPanel.addComponent(buttonBar);
        addComponent(editPanel);
    }

    protected abstract BasicEntityForm<ENTITY> getForm();

    protected ButtonBar initButtons() {
        ButtonBar buttonLayout = new ButtonBar();
        SaveButton saveButton = new SaveButton(this, translationService.get(TranslationKeys.BUTTON_SAVE));
        buttonLayout.addComponent(saveButton);
        return buttonLayout;
    }

    @Override
    public void saveClicked() {
        form.save();
    }

    public String getEntityName() {
        return entityName;
    }
}
