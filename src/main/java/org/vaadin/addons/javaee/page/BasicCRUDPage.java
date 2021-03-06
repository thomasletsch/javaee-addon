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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.addons.javaee.buttons.ButtonBar;
import org.vaadin.addons.javaee.buttons.DeleteButton;
import org.vaadin.addons.javaee.buttons.EditButton;
import org.vaadin.addons.javaee.buttons.NewButton;
import org.vaadin.addons.javaee.buttons.handler.CanHandleDeleteButton;
import org.vaadin.addons.javaee.buttons.handler.CanHandleEditButton;
import org.vaadin.addons.javaee.buttons.handler.CanHandleNewButton;
import org.vaadin.addons.javaee.container.EntityContainer;
import org.vaadin.addons.javaee.form.BasicEditForm;
import org.vaadin.addons.javaee.i18n.TranslationKeys;
import org.vaadin.addons.javaee.i18n.TranslationService;
import org.vaadin.addons.javaee.table.BasicEntityTable;

import com.googlecode.javaeeutils.jpa.PersistentEntity;

public abstract class BasicCRUDPage<ENTITY extends PersistentEntity> extends AbstractContentView implements CanHandleNewButton,
        CanHandleEditButton, CanHandleDeleteButton {

    private static final long serialVersionUID = 1L;

    private static Logger log = LoggerFactory.getLogger(BasicCRUDPage.class);

    @Inject
    protected TranslationService translationService;

    protected EntityContainer<ENTITY> container;

    private ButtonBar buttons = new ButtonBar();

    private NewButton addItemButton;

    private EditButton editItemButton;

    private DeleteButton removeItemButton;

    private AddEditDialog addEditDialog;

    public BasicCRUDPage(final String pageName) {
        super(pageName);
    }

    public abstract EntityContainer<ENTITY> getContainer();

    public abstract BasicEntityTable<ENTITY> getTable();

    protected abstract BasicEditForm<ENTITY> getForm();

    @Override
    protected void initView() {
        super.initView();
        container = getContainer();

        getTable().setCaption(translationService.getText(container.getEntityClass().getSimpleName() + "s"));
        addComponent(getTable());
        initButtons();
        addComponent(buttons);

        addEditDialog = new AddEditDialog(container, getForm(), translationService);
    }

    private void initButtons() {
        addItemButton = new NewButton(this, translationService.getText(TranslationKeys.BUTTON_NEW));
        buttons.addComponent(addItemButton);
        editItemButton = new EditButton(this, translationService.getText(TranslationKeys.BUTTON_EDIT));
        buttons.addComponent(editItemButton);
        removeItemButton = new DeleteButton(this, translationService.getText(TranslationKeys.BUTTON_DELETE));
        buttons.addComponent(removeItemButton);
    }

    @Override
    public void onShow(String comingFrom, Map<String, Object> parameters) {
    }

    @Override
    public void deleteClicked() {
        if (getTable().isAnySelected()) {
            getTable().removeSelectedItem();
        } else {
            log.error("You have to select a " + getPageName() + ".");
        }
    }

    @Override
    public void editClicked() {
        if (getTable().isAnySelected()) {
            addEditDialog.editSelected(getTable());
        } else {
            log.error("You have to select a " + getPageName() + ".");
        }
    }

    @Override
    public void newClicked() {
        addEditDialog.editNew();
    }

}
