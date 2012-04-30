package org.vaadin.addons.javaee.buttons.clickhandler;

import org.vaadin.addons.javaee.buttons.BasicButton;
import org.vaadin.addons.javaee.buttons.handler.CanHandleFinishButton;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class FinishClickHandler implements ClickListener {

    private final CanHandleFinishButton handle;

    private final BasicButton button;

    public FinishClickHandler(BasicButton button, CanHandleFinishButton handle) {
        this.button = button;
        this.handle = handle;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        if (event.getButton() == button) {
            handle.finishClicked();
        }
    }

}
