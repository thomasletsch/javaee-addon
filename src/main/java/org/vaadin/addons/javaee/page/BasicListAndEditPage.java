package org.vaadin.addons.javaee.page;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.vaadin.addons.javaee.TranslationKeys;
import org.vaadin.addons.javaee.buttons.ButtonBar;
import org.vaadin.addons.javaee.buttons.EditButton;
import org.vaadin.addons.javaee.buttons.NewButton;
import org.vaadin.addons.javaee.buttons.SaveButton;
import org.vaadin.addons.javaee.buttons.handler.CanHandleEditButton;
import org.vaadin.addons.javaee.buttons.handler.CanHandleNewButton;
import org.vaadin.addons.javaee.buttons.handler.CanHandleSaveButton;
import org.vaadin.addons.javaee.form.BasicEntityForm;
import org.vaadin.addons.javaee.i18n.TranslationService;
import org.vaadin.addons.javaee.table.BasicEntityTable;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.ui.Button;

public abstract class BasicListAndEditPage<ENTITY extends PersistentEntity> extends PortalPagePanel implements CanHandleSaveButton,
        CanHandleNewButton, CanHandleEditButton {

    @Inject
    protected TranslationService translationService;

    protected BasicEntityTable<ENTITY> table;

    protected BasicEntityForm<ENTITY> form;

    protected Button saveButton;

    protected Button newButton;

    protected Button editButton;

    protected Map<String, String> navigationParams;

    public BasicListAndEditPage(String pageName) {
        super(pageName);
    }

    protected abstract BasicEntityTable<ENTITY> getTable();

    protected abstract BasicEntityForm<ENTITY> getForm();

    @PostConstruct
    public void init() {
        form = getForm();
        table = getTable();
        form.connectWith(table);
        removeAllComponents();
        createEditSection();
        createListSection();
    }

    protected void createListSection() {
        addComponent(table);
    }

    protected void createEditSection() {
        addComponent(form);
        addComponent(initButtons());
    }

    protected ButtonBar initButtons() {
        initializeSaveButton();
        initializeEditButton();
        initializeNewButton();
        ButtonBar buttons = new ButtonBar();
        buttons.addComponent(saveButton);
        buttons.addComponent(editButton);
        buttons.addComponent(newButton);
        return buttons;
    }

    protected void initializeSaveButton() {
        saveButton = new SaveButton(this, translationService.get(TranslationKeys.BUTTON_SAVE));
    }

    protected void initializeNewButton() {
        newButton = new NewButton(this, translationService.get(TranslationKeys.BUTTON_NEW));
    }

    protected void initializeEditButton() {
        editButton = new EditButton(this, translationService.get(TranslationKeys.BUTTON_EDIT));
        editButton.setEnabled(false);
    }

    @Override
    public void onShow(String comingFrom) {
        showReadWrite();
        editFirstRecordOrNew();
    }

    @Override
    public void saveClicked() {
        if (!form.isValid()) {
            return;
        }
        showReadonly();
        form.save();
    }

    protected void editNewRecord() {
        form.editNew();
        table.setValue(null);
        showReadWrite();
    }

    protected void editFirstRecordOrNew() {
        if (table.getItemIds().isEmpty()) {
            editNewRecord();
        } else {
            table.selectFirst();
            form.edit(table.getSelectedEntityItem());
        }
    }

    @Override
    public void editClicked() {
        showReadWrite();
    }

    @Override
    public void newClicked() {
        editNewRecord();
    }

    protected void showReadonly() {
        disableForm();
        if (saveButton != null) {
            saveButton.setEnabled(false);
        }
        if (editButton != null) {
            editButton.setEnabled(true);
        }
    }

    protected void showReadWrite() {
        enableForm();
        if (saveButton != null) {
            saveButton.setEnabled(true);
        }
        if (editButton != null) {
            editButton.setEnabled(false);
        }
    }

    protected void enableForm() {
        form.setEnabled(true);
    }

    protected void disableForm() {
        form.setEnabled(false);
    }

}
