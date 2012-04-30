package org.vaadin.addons.javaee.buttons;

import org.vaadin.addons.javaee.TranslationKeys;
import org.vaadin.addons.javaee.buttons.clickhandler.PrevClickHandler;
import org.vaadin.addons.javaee.buttons.handler.CanHandlePrevButton;

public class PrevButton extends BasicButton {

    public PrevButton() {
        super(TranslationKeys.BUTTON_PREV);
    }

    public PrevButton(CanHandlePrevButton canHandle) {
        super(TranslationKeys.BUTTON_PREV);
        addListener(new PrevClickHandler(this, canHandle));
    }

    public PrevButton(CanHandlePrevButton canHandle, String title) {
        super(TranslationKeys.BUTTON_PREV, title);
        addListener(new PrevClickHandler(this, canHandle));
    }

}
