package org.vaadin.addons.javaee.buttons.clickhandler;

import org.vaadin.addons.javaee.buttons.BasicButton;
import org.vaadin.addons.javaee.buttons.handler.CanHandleCancelButton;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class CancelClickHandler implements ClickListener {

    private final CanHandleCancelButton handle;

    private final BasicButton button;

    public CancelClickHandler(BasicButton button, CanHandleCancelButton handle) {
        this.button = button;
        this.handle = handle;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        if (event.getButton() == button) {
            handle.cancelClicked();
        }
    }

}
