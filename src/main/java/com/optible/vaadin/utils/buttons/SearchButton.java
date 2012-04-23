package com.optible.vaadin.utils.buttons;

import com.optible.vaadin.utils.TranslationKeys;

public class SearchButton extends BasicButton {

    public SearchButton() {
        super(TranslationKeys.BUTTON_SEARCH);
    }

    public SearchButton(CanHandleSearchButton canHandle) {
        super(TranslationKeys.BUTTON_SEARCH);
        addListener(new SearchClickHandler(this, canHandle));
    }

}
