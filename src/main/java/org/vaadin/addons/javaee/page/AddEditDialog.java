/*******************************************************************************
 * Copyright 2012 Thomas Letsch
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *******************************************************************************/
package org.vaadin.addons.javaee.page;

import org.vaadin.addons.javaee.TranslationKeys;
import org.vaadin.addons.javaee.buttons.ButtonBar;
import org.vaadin.addons.javaee.buttons.CancelButton;
import org.vaadin.addons.javaee.buttons.OkButton;
import org.vaadin.addons.javaee.buttons.handler.CanHandleCancelButton;
import org.vaadin.addons.javaee.buttons.handler.CanHandleOkButton;
import org.vaadin.addons.javaee.form.BasicEntityForm;
import org.vaadin.addons.javaee.i18n.TranslationService;
import org.vaadin.addons.javaee.jpa.EntityContainer;
import org.vaadin.addons.javaee.jpa.EntityItem;
import org.vaadin.addons.javaee.table.BasicEntityTable;

import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class AddEditDialog extends Window implements CanHandleOkButton, CanHandleCancelButton {

    private static final long serialVersionUID = 1L;

    private TranslationService translationService;

    protected BasicEntityForm<?> form;

    private final EntityContainer<?> container;

    protected OkButton okButton;

    protected CancelButton cancelButton;

    private VerticalLayout content;

    public AddEditDialog(EntityContainer<?> container, BasicEntityForm<?> form, TranslationService translationService) {
        this.translationService = translationService;
        this.container = container;
        this.form = form;

        setId(container.getEntityClass().getSimpleName() + "AddEditDialog");
        setCaption(translationService.get(container.getEntityClass().getSimpleName()));
        setWidth(300, Unit.PIXELS);

        content = new VerticalLayout();
        setContent(content);
        content.addComponent(form);
        initButtons();
    }

    protected void initButtons() {
        ButtonBar buttons = new ButtonBar();
        OkButton okButton = new OkButton(this, translationService.get(TranslationKeys.BUTTON_OK));
        buttons.addComponent(okButton);
        CancelButton cancelButton = new CancelButton(this, translationService.get(TranslationKeys.BUTTON_CANCEL));
        buttons.addComponent(cancelButton);
        content.addComponent(buttons);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void editSelected(BasicEntityTable<?> table) {
        form.edit((EntityItem) container.getItem(table.getValue()));
        UI.getCurrent().addWindow(this);
        center();
    }

    public void editNew() {
        form.editNew();
        UI.getCurrent().addWindow(this);
        center();
    }

    @Override
    public void cancelClicked() {
        UI.getCurrent().removeWindow(this);
    }

    @Override
    public void okClicked() {
        form.save();
        UI.getCurrent().removeWindow(this);
    }

}