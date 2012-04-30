package org.vaadin.addons.javaee.buttons;

import org.vaadin.addons.javaee.TranslationKeys;
import org.vaadin.addons.javaee.buttons.clickhandler.DeleteClickHandler;
import org.vaadin.addons.javaee.buttons.handler.CanHandleDeleteButton;

public class DeleteButton extends BasicButton {

    public DeleteButton() {
        super(TranslationKeys.BUTTON_DELETE);
    }

    public DeleteButton(CanHandleDeleteButton canHandle) {
        super(TranslationKeys.BUTTON_DELETE);
        addListener(new DeleteClickHandler(this, canHandle));
    }

    public DeleteButton(CanHandleDeleteButton canHandle, String title) {
        super(TranslationKeys.BUTTON_DELETE, title);
        addListener(new DeleteClickHandler(this, canHandle));
    }

}
