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
import org.vaadin.addons.javaee.buttons.SearchButton;
import org.vaadin.addons.javaee.buttons.handler.CanHandleSearchButton;
import org.vaadin.addons.javaee.form.BasicSearchForm;
import org.vaadin.addons.javaee.i18n.TranslationKeys;
import org.vaadin.addons.javaee.i18n.TranslationService;
import org.vaadin.addons.javaee.table.BasicEntityTable;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;

public abstract class BasicSearchAndListPage<ENTITY extends PersistentEntity> extends AbstractContentView implements CanHandleSearchButton {

    private static final long serialVersionUID = 1L;

    public static final float SEARCH_FORM_RATIO = 40;

    public static final float SEARCH_RESULT_TABLE_RATIO = 55;

    @Inject
    protected TranslationService translationService;

    public BasicSearchAndListPage(String pageName) {
        super(pageName);
    }

    protected abstract BasicEntityTable<ENTITY> getResultTable();

    protected abstract BasicSearchForm<ENTITY> getSearchForm();

    /**
     * Can be overwritten
     */
    protected ButtonBar initSearchButtons() {
        Button findItem = new SearchButton(this, translationService.getText(TranslationKeys.BUTTON_SEARCH));
        findItem.setClickShortcut(KeyCode.ENTER);
        ButtonBar buttonLayout = new ButtonBar();
        buttonLayout.addComponent(findItem);
        return buttonLayout;
    }

    @Override
    protected void initView() {
        super.initView();
        createSearchSection();
        createListSection();
    }

    protected void createListSection() {
        addComponent(getResultTable(), SEARCH_RESULT_TABLE_RATIO);
    }

    protected void createSearchSection() {
        addComponent(getSearchForm(), SEARCH_FORM_RATIO);
        ButtonBar buttonBar = initSearchButtons();
        addComponent(buttonBar, BUTTON_RATIO);
    }

    @Override
    public void searchClicked() {
        getResultTable().removeAllContainerFilters();
        getResultTable().addContainerFilter(getSearchForm().getValuesAsFilter(false));
    }

}
