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
import org.vaadin.addons.javaee.buttons.NextButton;
import org.vaadin.addons.javaee.buttons.handler.CanHandleNextButton;
import org.vaadin.addons.javaee.i18n.TranslationKeys;
import org.vaadin.addons.javaee.navigation.NavigationEvent;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.event.ShortcutAction.KeyCode;

public abstract class BasicListAndEditForwardPage<ENTITY extends PersistentEntity> extends BasicListAndEditPage<ENTITY> implements
        CanHandleNextButton {

    private static final long serialVersionUID = 1L;

    protected NextButton nextButton;

    @Inject
    protected javax.enterprise.event.Event<NavigationEvent> navigation;

    public BasicListAndEditForwardPage(String pageName) {
        super(pageName);
    }

    @Override
    protected ButtonBar initButtons() {
        initializeNextButton();
        initializeEditButton();
        initializeNewButton();
        ButtonBar buttonLayout = new ButtonBar();
        buttonLayout.addComponent(nextButton);
        buttonLayout.addComponent(editButton);
        buttonLayout.addComponent(newButton);
        return buttonLayout;
    }

    protected void initializeNextButton() {
        nextButton = new NextButton(this, translationService.getText(TranslationKeys.BUTTON_NEXT));
        nextButton.setClickShortcut(KeyCode.ENTER);
    }

    @Override
    public void nextClicked() {
        if (!getForm().isValid()) {
            return;
        }
        saveClicked();
        if (getNextPage() != null) {
            navigateToNextPage();
        } else {
            getTable().refreshRowCache();
            getTable().selectFirst();
        }
    }

    /**
     * Should be overwritten to enable automatic navigation.
     */
    protected String getNextPage() {
        return null;
    }

    /**
     * Can be overwritten for e.g. adding parameters to navigation event
     */
    protected void navigateToNextPage() {
        navigation.fire(new NavigationEvent(getNextPage()));
    }

    @Override
    protected void showReadonly() {
        super.showReadonly();
        nextButton.setEnabled(false);
    }

    @Override
    protected void showReadWrite() {
        super.showReadWrite();
        nextButton.setEnabled(true);
    }

}
