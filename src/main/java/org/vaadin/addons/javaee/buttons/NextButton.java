package org.vaadin.addons.javaee.buttons;

import org.vaadin.addons.javaee.TranslationKeys;
import org.vaadin.addons.javaee.buttons.clickhandler.NextClickHandler;
import org.vaadin.addons.javaee.buttons.handler.CanHandleNextButton;

public class NextButton extends BasicButton {

    public NextButton() {
        super(TranslationKeys.BUTTON_NEXT);
    }

    public NextButton(CanHandleNextButton canHandle) {
        super(TranslationKeys.BUTTON_NEXT);
        addListener(new NextClickHandler(this, canHandle));
    }

    public NextButton(CanHandleNextButton canHandle, String title) {
        super(TranslationKeys.BUTTON_NEXT, title);
        addListener(new NextClickHandler(this, canHandle));
    }

}
