package org.vaadin.addons.javaee.page;

import javax.inject.Inject;

import org.vaadin.addons.javaee.TranslationKeys;
import org.vaadin.addons.javaee.buttons.ButtonBar;
import org.vaadin.addons.javaee.buttons.NextButton;
import org.vaadin.addons.javaee.buttons.handler.CanHandleNextButton;
import org.vaadin.addons.javaee.events.NavigationEvent;

import com.googlecode.javaeeutils.jpa.PersistentEntity;

public abstract class BasicListAndEditForwardPage<ENTITY extends PersistentEntity> extends BasicListAndEditPage<ENTITY> implements
        CanHandleNextButton {

    private static final long serialVersionUID = 1L;

    protected NextButton nextButton;

    @Inject
    javax.enterprise.event.Event<NavigationEvent> navigation;

    public BasicListAndEditForwardPage(String pageName) {
        super(pageName);
    }

    @Override
    protected ButtonBar initButtons() {
        initializeNextButton();
        initializeEditButton();
        initializeNewButton();
        ButtonBar buttonLayout = new ButtonBar();
        buttonLayout.addComponent(nextButton);
        buttonLayout.addComponent(editButton);
        buttonLayout.addComponent(newButton);
        return buttonLayout;
    }

    protected void initializeNextButton() {
        nextButton = new NextButton(this, translationService.get(TranslationKeys.BUTTON_NEXT));
    }

    @Override
    public void nextClicked() {
        showReadonly();
        form.save();
        if (getNextPage() != null) {
            navigateToNextPage();
        } else {
            table.selectFirst();
        }
    }

    protected String getNextPage() {
        return null;
    }

    protected void navigateToNextPage() {
        navigation.fire(new NavigationEvent(getNextPage()));
    }

    @Override
    protected void showReadonly() {
        super.showReadonly();
        nextButton.setEnabled(false);
    }

    @Override
    protected void showReadWrite() {
        super.showReadWrite();
        nextButton.setEnabled(true);
    }

}
