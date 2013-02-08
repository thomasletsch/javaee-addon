package org.vaadin.addons.javaee.page;

import java.util.Map;

import javax.inject.Inject;

import org.vaadin.addons.javaee.buttons.ButtonBar;
import org.vaadin.addons.javaee.buttons.EditButton;
import org.vaadin.addons.javaee.buttons.NewButton;
import org.vaadin.addons.javaee.buttons.SaveButton;
import org.vaadin.addons.javaee.buttons.handler.CanHandleEditButton;
import org.vaadin.addons.javaee.buttons.handler.CanHandleNewButton;
import org.vaadin.addons.javaee.buttons.handler.CanHandleSaveButton;
import org.vaadin.addons.javaee.form.BasicEntityForm;
import org.vaadin.addons.javaee.i18n.TranslationKeys;
import org.vaadin.addons.javaee.i18n.TranslationService;
import org.vaadin.addons.javaee.table.BasicEntityTable;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.ui.Button;

public abstract class BasicListAndEditPage<ENTITY extends PersistentEntity> extends AbstractContentView implements CanHandleSaveButton,
        CanHandleNewButton, CanHandleEditButton {

    private static final long serialVersionUID = 1L;

    @Inject
    protected TranslationService translationService;

    protected Button saveButton;

    protected Button newButton;

    protected Button editButton;

    protected Map<String, String> navigationParams;

    public BasicListAndEditPage(String pageName) {
        super(pageName);
    }

    protected abstract BasicEntityTable<ENTITY> getTable();

    protected abstract BasicEntityForm<ENTITY> getForm();

    @Override
    protected void initView() {
        super.initView();
        getForm().connectWith(getTable());
        createEditSection();
        createListSection();
    }

    protected void createListSection() {
        addComponent(getTable(), TABLE_RATIO);
    }

    protected void createEditSection() {
        addComponent(getForm(), FORM_RATIO);
        ButtonBar buttons = initButtons();
        addComponent(buttons, BUTTON_RATIO);
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
        saveButton = new SaveButton(this, translationService.getText(TranslationKeys.BUTTON_SAVE));
    }

    protected void initializeNewButton() {
        newButton = new NewButton(this, translationService.getText(TranslationKeys.BUTTON_NEW));
    }

    protected void initializeEditButton() {
        editButton = new EditButton(this, translationService.getText(TranslationKeys.BUTTON_EDIT));
        editButton.setEnabled(false);
    }

    @Override
    public void onShow(String comingFrom) {
        showReadWrite();
        editFirstRecordOrNew();
    }

    @Override
    public void saveClicked() {
        if (!getForm().isValid()) {
            return;
        }
        showReadonly();
        getForm().save();
    }

    protected void editNewRecord() {
        getForm().editNew();
        getTable().setValue(null);
        showReadWrite();
    }

    protected void editFirstRecordOrNew() {
        if (getTable().getItemIds().isEmpty()) {
            editNewRecord();
        } else {
            getTable().selectFirst();
            getForm().edit(getTable().getSelectedEntityItem());
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
        getForm().setEnabled(true);
    }

    protected void disableForm() {
        getForm().setEnabled(false);
    }

}
