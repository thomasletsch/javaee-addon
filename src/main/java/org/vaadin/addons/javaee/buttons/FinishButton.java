package org.vaadin.addons.javaee.buttons;

import org.vaadin.addons.javaee.TranslationKeys;
import org.vaadin.addons.javaee.buttons.clickhandler.FinishClickHandler;
import org.vaadin.addons.javaee.buttons.handler.CanHandleFinishButton;

public class FinishButton extends BasicButton {

    public FinishButton() {
        super(TranslationKeys.BUTTON_FINISH);
    }

    public FinishButton(CanHandleFinishButton canHandle) {
        super(TranslationKeys.BUTTON_FINISH);
        addListener(new FinishClickHandler(this, canHandle));
    }

    public FinishButton(CanHandleFinishButton canHandle, String title) {
        super(TranslationKeys.BUTTON_FINISH, title);
        addListener(new FinishClickHandler(this, canHandle));
    }

}
