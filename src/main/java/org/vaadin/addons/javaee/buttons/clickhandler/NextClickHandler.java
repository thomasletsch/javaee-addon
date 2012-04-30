package org.vaadin.addons.javaee.buttons.clickhandler;

import org.vaadin.addons.javaee.buttons.BasicButton;
import org.vaadin.addons.javaee.buttons.handler.CanHandleNextButton;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class NextClickHandler implements ClickListener {

    private final CanHandleNextButton handle;

    private final BasicButton button;

    public NextClickHandler(BasicButton button, CanHandleNextButton handle) {
        this.button = button;
        this.handle = handle;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        if (event.getButton() == button) {
            handle.nextClicked();
        }
    }

}
