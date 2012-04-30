package org.vaadin.addons.javaee.buttons.clickhandler;

import org.vaadin.addons.javaee.buttons.BasicButton;
import org.vaadin.addons.javaee.buttons.handler.CanHandlePrevButton;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class PrevClickHandler implements ClickListener {

    private final CanHandlePrevButton handle;

    private final BasicButton button;

    public PrevClickHandler(BasicButton button, CanHandlePrevButton handle) {
        this.button = button;
        this.handle = handle;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        if (event.getButton() == button) {
            handle.prevClicked();
        }
    }

}
