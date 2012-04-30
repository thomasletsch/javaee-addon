package org.vaadin.addons.javaee.buttons;

import org.vaadin.addons.javaee.TranslationKeys;
import org.vaadin.addons.javaee.buttons.clickhandler.EditClickHandler;
import org.vaadin.addons.javaee.buttons.handler.CanHandleEditButton;

public class EditButton extends BasicButton {

    public EditButton() {
        super(TranslationKeys.BUTTON_EDIT);
    }

    public EditButton(CanHandleEditButton canHandle) {
        super(TranslationKeys.BUTTON_EDIT);
        addListener(new EditClickHandler(this, canHandle));
    }

    public EditButton(CanHandleEditButton canHandle, String title) {
        super(TranslationKeys.BUTTON_EDIT, title);
        addListener(new EditClickHandler(this, canHandle));
    }

}
