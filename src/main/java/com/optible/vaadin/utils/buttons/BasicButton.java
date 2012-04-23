package com.optible.vaadin.utils.buttons;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.optible.vaadin.utils.i18n.TranslationService;
import com.vaadin.ui.Button;

public class BasicButton extends Button {

    @Inject
    protected TranslationService translationService;

    private final String titleKey;

    /**
     * Constructor to be used without CDI
     */
    public BasicButton(String titleKey, TranslationService translationService) {
        super();
        this.titleKey = titleKey;
        this.translationService = translationService;
        finishButton();
        setDebugId(titleKey);
    }

    /**
     * Constructor to be used with CDI
     */
    public BasicButton(String titleKey) {
        super();
        this.titleKey = titleKey;
        setDebugId(titleKey);
    }

    @PostConstruct
    protected void finishButton() {
        setCaption(translationService.get(titleKey, getLocale()));
    }

}
