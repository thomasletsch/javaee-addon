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
import org.vaadin.addons.javaee.form.BasicEditForm;
import org.vaadin.addons.javaee.i18n.TranslationKeys;
import org.vaadin.addons.javaee.i18n.TranslationService;
import org.vaadin.addons.javaee.table.BasicEntityTable;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.ui.Button;

public abstract class BasicListAndEditPage<ENTITY extends PersistentEntity> extends AbstractContentView implements CanHandleSaveButton,
        CanHandleNewButton, CanHandleEditButton {

    private static final long serialVersionUID = 1L;

    public static final float FORM_RATIO = 80;

    public static final float TABLE_RATIO = 15;

    public static final String ID_PARAMETER = "ID";

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

    protected abstract BasicEditForm<ENTITY> getForm();

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
    public void onShow(String comingFrom, Map<String, Object> parameters) {
        getTable().refreshCache();
        showReadWrite();
        editSelectedRecordOrNew(parameters != null ? (Long) (parameters.get(ID_PARAMETER)) : null);
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

    protected void editSelectedRecordOrNew(Long id) {
        if (getTable().getItemIds().isEmpty()) {
            editNewRecord();
        } else if (id != null) {
            getTable().select(id);
            getForm().edit(getTable().getSelectedEntityItem());
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

    @Override
    public boolean containsUnsavedValues() {
        return getForm().containsUnsavedValues();
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
