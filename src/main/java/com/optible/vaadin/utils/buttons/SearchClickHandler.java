package com.optible.vaadin.utils.buttons;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class SearchClickHandler implements ClickListener {

    private final CanHandleSearchButton handle;

    private final SearchButton searchButton;

    public SearchClickHandler(SearchButton searchButton, CanHandleSearchButton handle) {
        this.searchButton = searchButton;
        this.handle = handle;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        if (event.getButton() == searchButton) {
            handle.searchClicked();
        }
    }

}
