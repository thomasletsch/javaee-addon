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

import javax.inject.Inject;

import org.vaadin.addons.javaee.buttons.ButtonBar;
import org.vaadin.addons.javaee.buttons.SaveButton;
import org.vaadin.addons.javaee.buttons.handler.CanHandleSaveButton;
import org.vaadin.addons.javaee.form.BasicEditForm;
import org.vaadin.addons.javaee.i18n.TranslationKeys;
import org.vaadin.addons.javaee.i18n.TranslationService;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.ui.Panel;

/**
 * A page with one form and a save button.
 * 
 * @author thomas
 * 
 * @param <ENTITY>
 */
public abstract class BasicEditPage<ENTITY extends PersistentEntity> extends AbstractContentView implements CanHandleSaveButton {

    private static final long serialVersionUID = 1L;

    public static final float EDIT_FORM_RATIO = 40;

    public static final float REST_RATIO = 55;

    @Inject
    protected TranslationService translationService;

    private final String entityName;

    public BasicEditPage(String pageName) {
        this(pageName, pageName);
    }

    public BasicEditPage(String pageName, String entityName) {
        super(pageName);
        this.entityName = entityName;
    }

    @Override
    protected void initView() {
        super.initView();
        addComponent(getForm(), EDIT_FORM_RATIO);
        ButtonBar buttonBar = initButtons();
        addComponent(buttonBar, BUTTON_RATIO);
        addComponent(getThirdSection(), REST_RATIO);
    }

    protected Panel getThirdSection() {
        return new Panel();
    }

    protected abstract BasicEditForm<ENTITY> getForm();

    protected ButtonBar initButtons() {
        ButtonBar buttonBar = new ButtonBar();
        SaveButton saveButton = new SaveButton(this, translationService.getText(TranslationKeys.BUTTON_SAVE));
        buttonBar.addComponent(saveButton);
        return buttonBar;
    }

    @Override
    public void saveClicked() {
        getForm().save();
    }

    public String getEntityName() {
        return entityName;
    }

    @Override
    public boolean containsUnsavedValues() {
        return getForm().containsUnsavedValues();
    }
}
