package org.vaadin.addons.javaee.buttons;

import org.vaadin.addons.javaee.TranslationKeys;
import org.vaadin.addons.javaee.buttons.clickhandler.NewClickHandler;
import org.vaadin.addons.javaee.buttons.handler.CanHandleNewButton;

public class NewButton extends BasicButton {

    public NewButton() {
        super(TranslationKeys.BUTTON_NEW);
    }

    public NewButton(CanHandleNewButton canHandle) {
        super(TranslationKeys.BUTTON_NEW);
        addListener(new NewClickHandler(this, canHandle));
    }

    public NewButton(CanHandleNewButton canHandle, String title) {
        super(TranslationKeys.BUTTON_NEW, title);
        addListener(new NewClickHandler(this, canHandle));
    }

}
