package org.vaadin.addons.javaee.page;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class Header extends HorizontalLayout {

    private Label title;

    public Header() {
        setWidth(Portal.HEADER_WIDTH, Unit.PIXELS);
        setHeight(Portal.HEADER_HEIGHT, Unit.PIXELS);
        title = new Label();
        addComponent(title);
    }

    public void setTitle(String newTitle) {
        title.setValue(newTitle);
    }
}
