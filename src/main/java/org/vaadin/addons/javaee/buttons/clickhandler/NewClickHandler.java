package org.vaadin.addons.javaee.buttons.clickhandler;

import org.vaadin.addons.javaee.buttons.BasicButton;
import org.vaadin.addons.javaee.buttons.handler.CanHandleNewButton;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class NewClickHandler implements ClickListener {

    private final CanHandleNewButton handle;

    private final BasicButton button;

    public NewClickHandler(BasicButton button, CanHandleNewButton handle) {
        this.button = button;
        this.handle = handle;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        if (event.getButton() == button) {
            handle.newClicked();
        }
    }

}
