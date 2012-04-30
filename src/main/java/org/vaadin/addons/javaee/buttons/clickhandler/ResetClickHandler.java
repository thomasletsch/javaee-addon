package org.vaadin.addons.javaee.buttons.clickhandler;

import org.vaadin.addons.javaee.buttons.BasicButton;
import org.vaadin.addons.javaee.buttons.handler.CanHandleResetButton;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class ResetClickHandler implements ClickListener {

    private final CanHandleResetButton handle;

    private final BasicButton button;

    public ResetClickHandler(BasicButton button, CanHandleResetButton handle) {
        this.button = button;
        this.handle = handle;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        if (event.getButton() == button) {
            handle.resetClicked();
        }
    }
}
