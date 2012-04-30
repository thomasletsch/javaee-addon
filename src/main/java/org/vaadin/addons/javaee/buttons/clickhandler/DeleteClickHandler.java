package org.vaadin.addons.javaee.buttons.clickhandler;

import org.vaadin.addons.javaee.buttons.BasicButton;
import org.vaadin.addons.javaee.buttons.handler.CanHandleDeleteButton;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class DeleteClickHandler implements ClickListener {

    private final CanHandleDeleteButton handle;

    private final BasicButton button;

    public DeleteClickHandler(BasicButton button, CanHandleDeleteButton handle) {
        this.button = button;
        this.handle = handle;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        if (event.getButton() == button) {
            handle.deleteClicked();
        }
    }

}
