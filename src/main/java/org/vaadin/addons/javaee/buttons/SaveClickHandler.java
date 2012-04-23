package org.vaadin.addons.javaee.buttons;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class SaveClickHandler implements ClickListener {

    private final CanHandleSaveButton handle;

    private final SaveButton saveButton;

    public SaveClickHandler(SaveButton saveButton, CanHandleSaveButton handle) {
        this.saveButton = saveButton;
        this.handle = handle;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        if (event.getButton() == saveButton) {
            handle.saveClicked();
        }
    }

}
