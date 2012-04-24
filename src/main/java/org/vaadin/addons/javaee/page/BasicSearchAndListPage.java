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

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.vaadin.addons.javaee.TranslationKeys;
import org.vaadin.addons.javaee.buttons.ButtonBar;
import org.vaadin.addons.javaee.buttons.CanHandleSearchButton;
import org.vaadin.addons.javaee.buttons.SearchButton;
import org.vaadin.addons.javaee.form.BasicSearchForm;
import org.vaadin.addons.javaee.i18n.TranslationService;
import org.vaadin.addons.javaee.table.BasicEntityTable;

import com.googlecode.javaeeutils.jpa.PersistentEntity;
import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public abstract class BasicSearchAndListPage<ENTITY extends PersistentEntity> extends PortalPagePanel implements CanHandleSearchButton {

    @Inject
    protected TranslationService translationService;

    private BasicSearchForm<ENTITY> searchForm;

    private BasicEntityTable<ENTITY> table;

    private final String entityName;

    public BasicSearchAndListPage(String pageName, String entityName) {
        super(pageName);
        this.entityName = entityName;
    }

    protected abstract BasicEntityTable<ENTITY> getResultTable();

    /**
     * Can be overwritten
     */
    protected BasicSearchForm<ENTITY> getSearchForm() {
        return null;
    }

    /**
     * Can be overwritten
     */
    protected ButtonBar initSearchButtons() {
        Button findItem = new SearchButton(this, translationService.get(TranslationKeys.BUTTON_SEARCH));
        ButtonBar buttonLayout = new ButtonBar();
        buttonLayout.addComponent(findItem);
        return buttonLayout;
    }

    @PostConstruct
    protected void init() {
        VerticalLayout searchPanel = new VerticalLayout();
        searchPanel.setMargin(true);
        searchPanel.setSpacing(true);
        searchPanel.setCaption(translationService.get(TranslationKeys.TITLE_SEARCH) + ": " + translationService.get(entityName));
        searchForm = getSearchForm();
        if (searchForm != null)
            searchPanel.addComponent(searchForm);
        ButtonBar buttonBar = initSearchButtons();
        searchPanel.addComponent(buttonBar);
        addComponent(searchPanel);
        Panel listPanel = new Panel(translationService.get(entityName + "s"));
        table = getResultTable();
        listPanel.addComponent(table);
        addComponent(listPanel);
    }

    @Override
    public void searchClicked() {
        table.removeAllContainerFilters();
        table.addContainerFilter(searchForm.getValuesAsFilter());
        table.enableRefresh();
    }

}
