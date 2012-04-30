package org.vaadin.addons.javaee.buttons;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class ButtonClickedHandler implements ClickListener {

    @Override
    public void buttonClick(ClickEvent event) {
        if (event.getButton() instanceof BasicButton) {
            BasicButton basicButton = (BasicButton) event.getButton();
            basicButton.fireEvent();
        }
    }

}
