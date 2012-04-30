package org.vaadin.addons.javaee.buttons;

import org.vaadin.addons.javaee.TranslationKeys;
import org.vaadin.addons.javaee.buttons.clickhandler.CancelClickHandler;
import org.vaadin.addons.javaee.buttons.handler.CanHandleCancelButton;

public class CancelButton extends BasicButton {

    public CancelButton() {
        super(TranslationKeys.BUTTON_CANCEL);
    }

    public CancelButton(CanHandleCancelButton canHandle) {
        super(TranslationKeys.BUTTON_CANCEL);
        addListener(new CancelClickHandler(this, canHandle));
    }

    public CancelButton(CanHandleCancelButton canHandle, String title) {
        super(TranslationKeys.BUTTON_CANCEL, title);
        addListener(new CancelClickHandler(this, canHandle));
    }

}
