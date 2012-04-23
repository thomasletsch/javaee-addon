package com.optible.vaadin.utils.page;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.javaeeutils.jpa.PersistentEntity;

import com.optible.vaadin.utils.TranslationKeys;
import com.optible.vaadin.utils.buttons.ButtonBar;
import com.optible.vaadin.utils.buttons.CanHandleSearchButton;
import com.optible.vaadin.utils.buttons.SearchButton;
import com.optible.vaadin.utils.form.BasicSearchForm;
import com.optible.vaadin.utils.i18n.TranslationService;
import com.optible.vaadin.utils.table.BasicEntityTable;
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
        Button findItem = new SearchButton(this);
        ButtonBar buttonLayout = new ButtonBar();
        buttonLayout.addComponent(findItem);
        return buttonLayout;
    }

    @PostConstruct
    protected void init() {
        VerticalLayout searchPanel = new VerticalLayout();
        searchPanel.setMargin(true);
        searchPanel.setSpacing(true);
        searchPanel.setCaption(translationService.get(TranslationKeys.TITLE_SEARCH, getLocale()) + ": "
                + translationService.get(entityName, getLocale()));
        searchForm = getSearchForm();
        if (searchForm != null)
            searchPanel.addComponent(searchForm);
        ButtonBar buttonBar = initSearchButtons();
        searchPanel.addComponent(buttonBar);
        addComponent(searchPanel);
        Panel listPanel = new Panel(translationService.get(entityName + "s", getLocale()));
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
