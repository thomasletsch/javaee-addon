package com.optible.vaadin.utils.buttons;

import com.optible.vaadin.utils.TranslationKeys;

public class SaveButton extends BasicButton {

    public SaveButton() {
        super(TranslationKeys.BUTTON_SAVE);
    }

    public SaveButton(CanHandleSaveButton canHandle) {
        super(TranslationKeys.BUTTON_SAVE);
        addListener(new SaveClickHandler(this, canHandle));
    }

    public SaveButton(CanHandleSaveButton canHandle, String title) {
        super(TranslationKeys.BUTTON_SAVE, title);
        addListener(new SaveClickHandler(this, canHandle));
    }

}
