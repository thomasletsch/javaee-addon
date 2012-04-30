package org.vaadin.addons.javaee.buttons;

import org.vaadin.addons.javaee.TranslationKeys;
import org.vaadin.addons.javaee.buttons.clickhandler.ResetClickHandler;
import org.vaadin.addons.javaee.buttons.handler.CanHandleResetButton;

public class ResetButton extends BasicButton {

    public ResetButton() {
        super(TranslationKeys.BUTTON_RESET);
    }

    public ResetButton(CanHandleResetButton canHandle) {
        super(TranslationKeys.BUTTON_RESET);
        addListener(new ResetClickHandler(this, canHandle));
    }

    public ResetButton(CanHandleResetButton canHandle, String title) {
        super(TranslationKeys.BUTTON_RESET, title);
        addListener(new ResetClickHandler(this, canHandle));
    }

}
