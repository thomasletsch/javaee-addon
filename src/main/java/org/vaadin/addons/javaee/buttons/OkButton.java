package org.vaadin.addons.javaee.buttons;

import org.vaadin.addons.javaee.TranslationKeys;
import org.vaadin.addons.javaee.buttons.clickhandler.OkClickHandler;
import org.vaadin.addons.javaee.buttons.handler.CanHandleOkButton;

public class OkButton extends BasicButton {

    public OkButton() {
        super(TranslationKeys.BUTTON_OK);
    }

    public OkButton(CanHandleOkButton canHandle) {
        super(TranslationKeys.BUTTON_OK);
        addListener(new OkClickHandler(this, canHandle));
    }

    public OkButton(CanHandleOkButton canHandle, String title) {
        super(TranslationKeys.BUTTON_OK, title);
        addListener(new OkClickHandler(this, canHandle));
    }

}
